package characters;

import combat.AttackResult;
import combat.DamageType;

public class Mage extends Player {
    public Mage(String name) {
        super(name, 40, 50);
        critChance = 5.0;
        manaRegen = 2;
        armor = 1;
        magicResist = 5;
        dodgeChance = 5.0;
        blockChance = 0.0;
        lifesteal = 10.0;
    }

    @Override
    protected String applyClassBonuses() {
        maxMana += 10;
        magicResist += 2;
        return "Mage bonus: +10 Max Mana, +2 Magic Resist\n";
    }

    @Override
    public AttackResult useSpecial() {
        if (mana < 10) return new AttackResult(0, DamageType.MAGIC, null);
        mana -= 10;
        int damage = (int) (Math.random() * 10) + 20;
        StatusEffect burn = new StatusEffect("Burn", 3, 3, DamageType.MAGIC);
        return new AttackResult(damage, DamageType.MAGIC, burn);
    }

    @Override
    public int getSpecialManaCost() {
        return 10;
    }

    @Override
    public String getSpecialName() {
        return "Fireball";
    }
}
