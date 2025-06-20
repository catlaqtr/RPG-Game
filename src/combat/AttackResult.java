package combat;

import characters.StatusEffect;

public class AttackResult {
    private final int damage;
    private final DamageType type;
    private final StatusEffect status;

    public AttackResult(int damage, DamageType type, StatusEffect status) {
        this.damage = damage;
        this.type = type;
        this.status = status;
    }

    public int getDamage() {
        return damage;
    }

    public DamageType getType() {
        return type;
    }

    public StatusEffect getStatus() {
        return status;
    }
}
