package characters;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int xp = 0;
    private int level = 1;
    private int bonusDamage = 0;
    private double critChance = 0.0;
    private int manaRegen = 1;
    private int attackRangeBonus = 0;

    public Player(String name, int maxHealth, int maxMana) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
    }

    public int attack() {
        int base = (int)(Math.random() * 6) + 5;
        base += attackRangeBonus;
        base += bonusDamage;

        if (Math.random() * 100 < critChance) {
            return -base * 2;
        }
        return base;
    }

    public int heal() {
        int manaCost = 5;
        int healAmount = 10;

        if (mana < manaCost) return 0;

        mana -= manaCost;
        int missingHealth = maxHealth - health;
        int actualHeal = Math.min(healAmount, missingHealth);
        health += actualHeal;

        return actualHeal;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public int fireball() {
        int manaCost = 10;
        if (mana < manaCost) return 0;

        mana -= manaCost;
        return (int)(Math.random() * 11) + 10;
    }

    public String gainXp(int amount) {
        xp += amount;
        StringBuilder result = new StringBuilder("You gained " + amount + " XP.\n");

        int xpForNextLevel = level * 20;

        while (xp >= xpForNextLevel) {
            xp -= xpForNextLevel;
            level++;
            maxHealth += 10;
            health = maxHealth;
            maxMana += 5;
            mana = maxMana;

            critChance += 2.0;
            manaRegen += 1;
            attackRangeBonus += 1;

            result.append("You leveled up to level ").append(level).append("! Your health and mana have increased.\n");
            result.append("You gained passive perks:\n");
            result.append("+2% Crit Chance, +1 Mana Regen, +1 Attack Range\n");

            xpForNextLevel = level * 20;
        }

        return result.toString();
    }

    public void increaseBonusDamage(int amount) {
        bonusDamage += amount;
    }

    public void increaseMaxHealth(int amount) {
        maxHealth += amount;
        health = maxHealth;
    }

    public void increaseMaxMana(int amount) {
        maxMana += amount;
        mana = maxMana;
    }

    public void restoreMana(int amount) {
        mana = Math.min(mana + amount, maxMana);
    }

    public void restoreHealth(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getXp() { return xp; }
    public int getLevel() { return level; }
    public int getManaRegen() { return manaRegen; }
    public int getBonusDamage() { return bonusDamage; }
    public double getCritChance() { return critChance; }
    public int getAttackRangeBonus() { return attackRangeBonus; }
}