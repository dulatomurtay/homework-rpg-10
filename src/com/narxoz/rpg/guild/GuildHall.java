package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();

    @Override
    public void register(GuildMember member) {
        addSubscriber("*", member);
        for (String topic : member.topics()) {
            addSubscriber(topic, member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        List<GuildMember> specific = subscribersFor(topic);
        List<GuildMember> targets  = specific.isEmpty() ? subscribersFor("*") : specific;
        for (GuildMember member : targets) {
            if (member != from) {
                member.receive(topic, from, payload);
            }
        }
    }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}