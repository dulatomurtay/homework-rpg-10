package com.narxoz.rpg.guild;

import java.util.List;

public abstract class GuildMember {

    private final String name;
    private final GuildMediator mediator;

    protected GuildMember(String name, GuildMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.register(this);
    }

    public String getName() {
        return name;
    }

    protected GuildMediator getMediator() {
        return mediator;
    }

    public abstract void receive(String topic, GuildMember from, String payload);

    public List<String> topics() { return List.of(); }
}