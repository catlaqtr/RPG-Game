package characters;

import combat.AttackResult;
import combat.DamageType;

public class Hunter extends Player {
    public Hunter(String name) {
        super(name, 55, 25);
        attackRangeBonus = 3;
        critChance = 5.0;
        armor = 2;
        magicResist = 2;
        dodgeChance = 15.0;
        blockChance = 5.0;
        lifesteal = 5.0;
    }

    @Override
    protected String applyClassBonuses() {
        attackRangeBonus += 2;
        critChance += 3.0;
        return "Hunter bonus: +2 Attack Range, +3% Crit Chance\n";
    }

    @Override
    public AttackResult useSpecial() {
        if (mana < 8) return new AttackResult(0, DamageType.PHYSICAL, null);
        mana -= 8;
        int total = 0;
        for (int i = 0; i < 3; i++) {
            total += (int) (Math.random() * 5) + 5;
        }
        return new AttackResult(total, DamageType.PHYSICAL, null);
    }

    @Override
    public int getSpecialManaCost() {
        return 8;
    }

    @Override
    public String getSpecialName() {
        return "Multi-Shot";
    }
}
