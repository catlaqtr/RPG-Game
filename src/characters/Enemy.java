package characters;

public class Enemy {
    private final String name;
    private int health;
    private final int maxHealth;
    private final int baseAttackMin;
    private final int baseAttackMax;
    private StatusEffect statusEffect;

    public Enemy(String name, int health, int maxHealth, int baseAttackMin, int baseAttackMax) {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.baseAttackMin = baseAttackMin;
        this.baseAttackMax = baseAttackMax;

    }

    public int attack() {
        return (int) (Math.random() * (baseAttackMax - baseAttackMin + 1)) + baseAttackMin;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void applyStatus(StatusEffect effect) {
        this.statusEffect = effect;
    }

    public String processStatusEffect() {
        if (statusEffect != null && statusEffect.isActive()) {
            int damage = statusEffect.apply();
            takeDamage(damage);
            return name + " suffers " + damage + " damage from " + statusEffect.getName() + ".\n";
        }
        return "";
    }

    public boolean hasActiveStatus() {
        return statusEffect != null && statusEffect.isActive();
    }

    public String getStatusText() {
        return hasActiveStatus() ? statusEffect.getName() : "None";
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