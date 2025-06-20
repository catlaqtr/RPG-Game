package characters;

import combat.AttackResult;
import combat.DamageType;

public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 70, 20);
        armor = 5;
        magicResist = 2;
        blockChance = 10.0;
        dodgeChance = 5.0;
        lifesteal = 0.0;
    }

    @Override
    protected String applyClassBonuses() {
        maxHealth += 15;
        armor += 2;
        return "Warrior bonus: +15 Max HP, +2 Armor\n";
    }

    @Override
    public AttackResult useSpecial() {
        if (mana < 5) return new AttackResult(0, DamageType.PHYSICAL, null);
        mana -= 5;
        int damage = (int) (Math.random() * 10) + 15;
        return new AttackResult(damage, DamageType.PHYSICAL, null);
    }

    @Override
    public int getSpecialManaCost() {
        return 5;
    }

    @Override
    public String getSpecialName() {
        return "Power Slash";
    }
}
