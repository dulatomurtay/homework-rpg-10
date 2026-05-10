package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        Hero kira = new Hero("Kira",  120, 80, 15, 8, 300);
        Hero thane = new Hero("Thane",  90, 40, 20, 5, 150);

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Patrol the Eastern Road", QuestPriority.LOW,    50,  false));
        questLog.add(new Quest("Escort the Merchant", QuestPriority.NORMAL, 120, false));
        questLog.add(new Quest("Clear the Goblin Caves", QuestPriority.HIGH,   250, false));
        questLog.add(new Quest("Rescue the Guild Master", QuestPriority.URGENT, 500, true));
        questLog.add(new Quest("Recover the Lost Relic", QuestPriority.HIGH,   300, false));
        questLog.add(new Quest("Defeat the Shadow Drake", QuestPriority.URGENT, 800, true));

        GuildHall hall = new GuildHall();
        Captain captain = new Captain("Aldric", hall);
        Scout scout = new Scout("Lyra",   hall);
        Healer healer = new Healer("Borin",  hall);
        Quartermaster qm = new Quartermaster("Mira",   hall);

        CouncilRunResult result = new CouncilEngine()
                .runCouncil(List.of(kira, thane), questLog, hall);

        System.out.println("\n=== COUNCIL RUN COMPLETE ===");
        System.out.println(result);
    }
}