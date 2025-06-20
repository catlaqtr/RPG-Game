package characters;

import combat.DamageType;

public class StatusEffect {
    private final String name;
    private final int damagePerTurn;
    private final int duration;
    private final DamageType type;

    private int turnsRemaining;

    public StatusEffect(String name, int damagePerTurn, int duration, DamageType type) {
        this.name = name;
        this.damagePerTurn = damagePerTurn;
        this.duration = duration;
        this.type = type;
        this.turnsRemaining = duration;
    }

    public int apply() {
        if (turnsRemaining-- > 0) {
            return damagePerTurn;
        }
        return 0;
    }

    public boolean isActive() {
        return turnsRemaining > 0;
    }

    public String getName() {
        return name + " (" + turnsRemaining + ")";
    }

    public DamageType getType() {
        return type;
    }

}
