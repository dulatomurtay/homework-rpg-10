package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;
import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0, messagesRouted = 0, membersNotified = 0;


        System.out.println("\n[Iterator 1] Ordered (arrival order):");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            System.out.printf("  [%d] %-28s priority=%-6s gold=%d urgent=%s%n",
                    questsTraversed, q.getTitle(), q.getPriority(), q.getRewardGold(), q.isUrgent());
        }

        System.out.println("\n[Iterator 2] Reverse (newest first):");
        QuestIterator reverse = questLog.reverse();
        int r = 0;
        while (reverse.hasNext()) {
            Quest q = reverse.next(); r++; questsTraversed++;
            System.out.printf("  [%d] %-28s priority=%s%n", r, q.getTitle(), q.getPriority());
        }

        System.out.println("\n[Iterator 3] Priority >= HIGH:");
        QuestIterator high = questLog.priorityAtLeast(QuestPriority.HIGH);
        int h = 0;
        while (high.hasNext()) {
            Quest q = high.next(); h++; questsTraversed++;
            System.out.printf("  [%d] %-28s priority=%s gold=%d%n",
                    h, q.getTitle(), q.getPriority(), q.getRewardGold());
        }


        QuestIterator planning = questLog.priorityAtLeast(QuestPriority.HIGH);
        GuildMember council = new GuildMember("Council", hall) {
            @Override public void receive(String t, GuildMember f, String p) {}
        };

        while (planning.hasNext()) {
            Quest q = planning.next();
            System.out.printf("%n  Planning: \"%s\" [%s]%n", q.getTitle(), q.getPriority());

            hall.dispatch("alert",    council, "New quest: " + q.getTitle()); messagesRouted++;
            if (q.isUrgent()) {
                hall.dispatch("alert", council, "URGENT: " + q.getTitle()); messagesRouted++;
            }
            hall.dispatch("supplies", council, "Prepare gear: " + q.getTitle()); messagesRouted++;
            hall.dispatch("recon",    council, "Scout route: " + q.getTitle()); messagesRouted++;
            if (q.getRewardGold() >= 200) {
                hall.dispatch("healing", council, "Prepare aid: " + q.getTitle()); messagesRouted++;
            }
        }
        hall.dispatch("orders", council, "Council adjourned."); messagesRouted++;

        membersNotified = messagesRouted * 2;

        System.out.println("\n--- Party ---");
        party.forEach(h2 -> System.out.println("  " + h2));

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }
}