package characters;

import combat.AttackResult;
import ui.StatAllocationDialog;


public abstract class Player {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int mana;
    protected int maxMana;
    protected int xp = 0;
    protected int level = 1;
    protected int bonusDamage = 0;
    protected double critChance = 0.0;
    protected int manaRegen = 1;
    protected int attackRangeBonus = 0;
    protected int armor = 0;             // Reduces physical damage
    protected int magicResist = 0;       // Reduces magic damage
    protected double dodgeChance = 0.0;  // % to fully dodge attack
    protected double blockChance = 0.0;  // % to reduce damage by 50%
    protected double lifesteal = 0.0;    // % of damage dealt restored as HP


    public abstract int getSpecialManaCost();

    public abstract String getSpecialName();


    public Player(String name, int maxHealth, int maxMana) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
    }

    public int basicAttack() {
        int base = (int) (Math.random() * 6) + 5 + attackRangeBonus + bonusDamage;
        if (Math.random() * 100 < critChance) return -base * 2;
        return base;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void restoreMana(int amount) {
        mana = Math.min(mana + amount, maxMana);
    }

    public void restoreHealth(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    public abstract AttackResult useSpecial();


    public String gainXp(int amount) {
        xp += amount;
        StringBuilder result = new StringBuilder("You gained " + amount + " XP.\n");

        int xpForNextLevel = level * 20;

        while (xp >= xpForNextLevel) {
            xp -= xpForNextLevel;
            level++;

            result.append("You leveled up to level ").append(level).append("!\n");

            // ⬇️ Call subclass-defined method for unique bonuses
            result.append(applyClassBonuses());

            // ⬇️ Manual stat allocation
            String choice = StatAllocationDialog.promptStatChoice(this); // ✅

            if (choice != null) applyStatChoice(choice);

            // ⬇️ Shared passive bonuses
            critChance += 2.0;
            manaRegen += 1;
            attackRangeBonus += 1;

            result.append("Passive perks: +2% Crit, +1 Mana Regen, +1 Attack Range\n");

            xpForNextLevel = level * 20;
        }

        return result.toString();
    }

    protected abstract String applyClassBonuses();


    public void increaseMaxHealth(int amount) {
        maxHealth += amount;
        health = maxHealth;
    }

    public void increaseMaxMana(int amount) {
        maxMana += amount;
        mana = maxMana;
    }

    public void increaseBonusDamage(int amount) {
        bonusDamage += amount;
    }

    public void applyStatChoice(String choice) {
        switch (choice) {
            case "Max Health (+10)" -> increaseMaxHealth(10);
            case "Max Mana (+5)" -> increaseMaxMana(5);
            case "Bonus Damage (+1)" -> increaseBonusDamage(1);
            case "Armor (+1)" -> armor += 1;
            case "Crit Chance (+5%)" -> critChance += 5.0;
            case "Mana Regen (+1)" -> manaRegen += 1;
            case "Dodge Chance (+2%)" -> dodgeChance += 2.0;
            case "Lifesteal (+2%)" -> lifesteal += 2.0;
            case "Attack Range (+1)" -> attackRangeBonus += 1;
        }
    }


    // Getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public int getBonusDamage() {
        return bonusDamage;
    }

    public double getCritChance() {
        return critChance;
    }

    public int getManaRegen() {
        return manaRegen;
    }

    public int getAttackRangeBonus() {
        return attackRangeBonus;
    }

    public int getArmor() {
        return armor;
    }

    public int getMagicResist() {
        return magicResist;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public double getBlockChance() {
        return blockChance;
    }

    public double getLifesteal() {
        return lifesteal;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setMagicResist(int magicResist) {
        this.magicResist = magicResist;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public void setBlockChance(double blockChance) {
        this.blockChance = blockChance;
    }

    public void setLifesteal(double lifesteal) {
        this.lifesteal = lifesteal;
    }



}
