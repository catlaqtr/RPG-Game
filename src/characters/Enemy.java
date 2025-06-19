package characters;

public class Enemy {
    private String name;
    private int health;
    private int maxHealth;
    private int baseAttackMin;
    private int baseAttackMax;

    public Enemy(String name, int health, int maxHealth, int baseAttackMin, int baseAttackMax) {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.baseAttackMin = baseAttackMin;
        this.baseAttackMax = baseAttackMax;
    }

    public int attack() {
        return (int)(Math.random() * (baseAttackMax - baseAttackMin + 1)) + baseAttackMin;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
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
}