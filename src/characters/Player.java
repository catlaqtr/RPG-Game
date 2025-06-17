package characters;


public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int xp = 0;
    private int level = 1;


    public Player(String name, int maxHealth, int maxMana) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
    }


    public int attack() {
        // Return random damage between 5 and 10
        return (int)(Math.random() * 6) + 5; // 0–5 → +5 = 5–10
    }


    public int heal() {
        int manaCost = 5;
        int healAmount = 10;

        if (mana < manaCost) {
            return 0; // not enough mana
        }

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
        if (mana < manaCost) {
            return 0;
        }

        mana -= manaCost;
        return (int)(Math.random() * 11) + 10; // 10–20
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
            result.append("You leveled up to level ").append(level).append("! Your health and mana have increased.\n");
            xpForNextLevel = level * 20;
        }

        return result.toString();
    }



    public boolean isAlive(){
return health > 0;
}

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
}


