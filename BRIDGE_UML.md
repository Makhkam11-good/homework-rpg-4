# Bridge Pattern UML Diagram

## Class Diagram

```
┌─────────────────────────────┐
│      <<interface>>          │
│    EffectImplementor        │
├─────────────────────────────┤
│ + computeDamage(int): int   │
│ + getEffectName(): String   │
└─────────────────────────────┘
           ▲
           │ implements
      ┌────┴─────┬──────────────┬──────────────┐
      │           │              │              │
      │           │              │              │
┌──────────┐ ┌─────────┐ ┌──────────┐ ┌────────────┐
│  Fire    │ │   Ice   │ │ Physical │ │   Shadow   │
│ Effect   │ │ Effect  │ │ Effect   │ │   Effect   │
├──────────┤ ├─────────┤ ├──────────┤ ├────────────┤
│+ compute │ │+ compute│ │+ compute │ │+ compute   │
│  Damage()│ │ Damage()│ │ Damage() │ │ Damage()   │
│+ getEffect│ │+ getEffect│ │+ getEffect│ │+ getEffect │
│  Name()  │ │ Name()  │ │ Name()   │ │ Name()     │
└──────────┘ └─────────┘ └──────────┘ └────────────┘
    x1.2       x1.1        x1.15        x1.25


┌──────────────────────────────────┐
│    abstract Skill                │
├──────────────────────────────────┤
│ - skillName: String              │
│ - basePower: int                 │
│ - effect: EffectImplementor      │ ◆─────>  EffectImplementor
├──────────────────────────────────┤
│ + getSkillName(): String         │
│ + getBasePower(): int            │
│ + getEffectName(): String        │
│ # resolvedDamage(): int          │
│ + cast(target): void (abstract)  │
└──────────────────────────────────┘
           ▲
           │ extends
      ┌────┴─────────────┐
      │                  │
┌─────────────────┐ ┌──────────────┐
│ SingleTarget    │ │  AreaSkill   │
│   Skill         │ │              │
├─────────────────┤ ├──────────────┤
│+ cast(target)   │ │+ cast(target)│
└─────────────────┘ └──────────────┘
```

## Key Relationships

1. **Bridge**: Both `Skill` hierarchy and `EffectImplementor` hierarchy can vary independently
2. **Abstraction**: `Skill` is the abstraction layer
3. **Refined Abstractions**: `SingleTargetSkill` and `AreaSkill` extend `Skill`
4. **Implementor**: `EffectImplementor` defines the contract for damage calculation
5. **Concrete Implementors**: `PhysicalEffect`, `FireEffect`, `IceEffect`, `ShadowEffect` provide specific damage algorithms

## Design Benefits

- **Decoupling**: Changes to skill mechanics don't affect effect implementations
- **Flexibility**: Any skill can use any effect without creating new classes
- **Extensibility**: Adding new skills or effects doesn't require modifying existing classes
- **Avoids Class Explosion**: Instead of 2 skills × 4 effects = 8 classes, we have 2 + 4 = 6 classes
