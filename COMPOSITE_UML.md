# Composite Pattern UML Diagram

## Class Diagram

```
┌────────────────────────────────────────────┐
│         <<interface>>                      │
│          CombatNode                        │
├────────────────────────────────────────────┤
│ + getName(): String                        │
│ + getHealth(): int                         │
│ + getAttackPower(): int                    │
│ + takeDamage(amount: int): void            │
│ + isAlive(): boolean                       │
│ + getChildren(): List<CombatNode>          │
│ + printTree(indent: String): void          │
└────────────────────────────────────────────┘
           ▲                    ▲
           │ implements         │ implements
           │                    │
    ┌──────┴────────────┐  ┌────┴──────────────┐
    │                   │  │                   │
┌───────────────┐  ┌──────────────────────────────┐
│abstract UnitLeaf  │  │   PartyComposite       │
├───────────────┤  ├──────────────────────────────┤
│ - name        │  │ - name                       │
│ - health      │  │ - children: List<CombatNode>│
│ - attackPower │  ├──────────────────────────────┤
├───────────────┤  │ + add(node)                  │
│ + getters     │  │ + remove(node)               │
│ + takeDamage()│  │ + getHealth() → sum          │
│ + isAlive()   │  │ + getAttackPower() → sum     │
│ + printTree() │  │ + takeDamage() → distribute  │
└───────────────┘  │ + isAlive() → any child      │
      ▲            │ + printTree() → recursive    │
      │ extends    │ - getAliveChildren()         │
      ├────────┐   └──────────────────────────────┘
      │        │            ▲
      │        │            │ extends
      │        │            │
  ┌────────┐ ┌─────────┐ ┌──────────┐
  │ Hero   │ │ Enemy   │ │ RaidGroup│
  │ Unit   │ │ Unit    │ │          │
  ├────────┤ ├─────────┤ ├──────────┤
  │ (leaf) │ │ (leaf)  │ │(composite)
  └────────┘ └─────────┘ └──────────┘
```

## Key Relationships

1. **Component**: `CombatNode` interface defines operations for both leaves and composites
2. **Leaf Nodes**: 
   - `HeroUnit` - extends `UnitLeaf`
   - `EnemyUnit` - extends `UnitLeaf`
   - Return `0` attack power when dead
   - Cannot have children
3. **Composite Nodes**:
   - `PartyComposite` - basic composite container
   - `RaidGroup` - extends `PartyComposite` (can be nested)
   - Aggregate operations across children
   - Support unlimited nesting
4. **Operations**:
   - `getHealth()`: Sum of all children health
   - `getAttackPower()`: Sum of alive children only
   - `takeDamage(amount)`: Distribute evenly across alive children
   - `isAlive()`: True if any child is alive
   - `printTree()`: Recursive display with indent

## Design Benefits

- **Uniform Interface**: Client code (RaidEngine) doesn't care if it's a single unit or a group
- **Tree Structures**: Support arbitrary nesting (groups containing groups)
- **Polymorphism**: Single method calls work for both leaves and composites
- **Scalability**: Easy to add new unit types without modifying engine logic

## Nesting Example

```
RaidGroup "Enemy Raid"
├── PartyComposite "Frontline"
│   ├── EnemyUnit "Goblin" (leaf)
│   └── EnemyUnit "Orc" (leaf)
└── (can add more composites or leaves)

PartyComposite "Heroes"
├── HeroUnit "Arthas" (leaf)
└── HeroUnit "Jaina" (leaf)
```

When `RaidEngine.takeDamage()` is called on "Enemy Raid":
1. Distributes damage to children: "Frontline"
2. "Frontline" distributes its portion to: "Goblin", "Orc"
3. Each leaf applies damage to its own health
