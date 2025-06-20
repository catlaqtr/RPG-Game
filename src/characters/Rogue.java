package characters;

import combat.AttackResult;
import combat.DamageType;

public class Rogue extends Player {
    public Rogue(String name) {
        super(name, 50, 25);
        critChance = 10.0;
        manaRegen = 1;
        armor = 2;
        magicResist = 1;
        dodgeChance = 10.0;
        blockChance = 5.0;
        lifesteal = 3.0;
    }

    @Override
    protected String applyClassBonuses() {
        critChance += 5.0;
        manaRegen += 1;
        return "Rogue bonus: +5% Crit Chance, +1 Mana Regen\n";
    }

    @Override
    public AttackResult useSpecial() {
        if (mana < 6) return new AttackResult(0, DamageType.PHYSICAL, null);
        mana -= 6;
        int damage = (int) (Math.random() * 12) + 10;
        StatusEffect bleed = new StatusEffect("Bleed", 3, 4, DamageType.PHYSICAL);
        return new AttackResult(damage, DamageType.PHYSICAL, bleed);
    }

    @Override
    public int getSpecialManaCost() {
        return 6;
    }

    @Override
    public String getSpecialName() {
        return "Backstab";
    }
}
