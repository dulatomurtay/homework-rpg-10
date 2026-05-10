@startuml

interface QuestIterator {
+hasNext(): boolean
+next(): Quest
}

class OrderedQuestIterator  { arrival order }
class ReverseQuestIterator  { newest first }
class PriorityQuestIterator { threshold filter }

QuestIterator <|.. OrderedQuestIterator
QuestIterator <|.. ReverseQuestIterator
QuestIterator <|.. PriorityQuestIterator

class QuestLog {
-quests: List<Quest>
+ordered(): QuestIterator
+reverse(): QuestIterator
+priorityAtLeast(t): QuestIterator
}

QuestLog ..> QuestIterator : creates
QuestLog o-- Quest

class Quest {
+title: String
+priority: QuestPriority
+rewardGold: int
+urgent: boolean
}

enum QuestPriority {
LOW
NORMAL
HIGH
URGENT
}

Quest --> QuestPriority

interface GuildMediator {
+register(member): void
+dispatch(topic, from, payload): void
}

class GuildHall implements GuildMediator {
-membersByTopic: Map<String, List>
+register()
+dispatch()
}

abstract class GuildMember {
-name: String
+topics(): List<String>
+receive(topic, from, payload): void
}

GuildMember --> GuildMediator : uses

class Captain      { +issueOrder()      topics: orders, alert   }
class Scout        { +reportRoute()     topics: recon, alert    }
class Healer       { +prepareAid()      topics: healing, alert  }
class Quartermaster{ +requestSupplies() topics: supplies, alert }

GuildMember <|-- Captain
GuildMember <|-- Scout
GuildMember <|-- Healer
GuildMember <|-- Quartermaster

GuildHall --> GuildMember : notifies

class Hero {
+name, hp, mana, gold: int
+attackPower, defense: int
}

class CouncilEngine {
+runCouncil(party, questLog, hall): CouncilRunResult
}

class CouncilRunResult {
+questsTraversed: int
+messagesRouted: int
+membersNotified: int
}

CouncilEngine ..> Hero
CouncilEngine ..> QuestLog
CouncilEngine ..> GuildMediator
CouncilEngine ..> CouncilRunResult : returns

@enduml