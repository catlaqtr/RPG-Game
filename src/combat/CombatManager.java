package combat;

import characters.Player;
import characters.Enemy;

public class CombatManager {
    private Player player;
    private Enemy enemy;

    public CombatManager(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public String playerAttack() {
        StringBuilder log = new StringBuilder();

        int rawDamage = player.basicAttack();
        boolean isCrit = false;
        if (rawDamage < 0) {
            rawDamage = -rawDamage;
            isCrit = true;
        }

        // Build AttackResult manually (always PHYSICAL, no status)
        AttackResult result = new AttackResult(rawDamage, DamageType.PHYSICAL, null);

        // Apply damage
        enemy.takeDamage(result.getDamage());
        if (isCrit) log.append("CRITICAL HIT! ");
        log.append("You attacked and dealt ").append(result.getDamage()).append(" damage (Physical).\n");

        // Lifesteal
        if (player.getLifesteal() > 0) {
            int heal = (int) (result.getDamage() * (player.getLifesteal() / 100.0));
            player.restoreHealth(heal);
            log.append("You healed ").append(heal).append(" HP from lifesteal.\n");
        }

        // Enemy counterattack
        if (enemy.isAlive()) {
            log.append(enemyCounterattack());
        } else {
            log.append("You defeated ").append(enemy.getName()).append("!\n");
        }

        return log.toString();
    }


    public String playerSpecial() {
        AttackResult result = player.useSpecial();
        int rawDamage = result.getDamage();
        DamageType type = result.getType();

        if (rawDamage <= 0) return "Not enough mana or failed to cast special.\n";

        StringBuilder log = new StringBuilder();
        log.append("You used ").append(player.getSpecialName()).append(" and dealt ").append(rawDamage).append(" damage");

        if (type == DamageType.MAGIC) {
            log.append(" (Magic).\n");
        } else {
            log.append(" (Physical).\n");
        }

        // Future: enemy.applyStatus(result.getStatus());

// Apply damage to enemy
        enemy.takeDamage(rawDamage);

// ✅ Apply status effect if any
        if (result.getStatus() != null) {
            enemy.applyStatus(result.getStatus());
            log.append(enemy.getName()).append(" is now affected by ").append(result.getStatus().getName()).append(".\n");
        }


        // Lifesteal healing
        if (player.getLifesteal() > 0) {
            int heal = (int) (rawDamage * (player.getLifesteal() / 100.0));
            player.restoreHealth(heal);
            log.append("You healed ").append(heal).append(" HP from lifesteal.\n");
        }

        // Enemy counterattack
        if (enemy.isAlive()) {
            log.append(enemyCounterattack());
        } else {
            log.append("You defeated ").append(enemy.getName()).append("!\n");
        }

        return log.toString();
    }

    private String enemyCounterattack() {
        StringBuilder log = new StringBuilder();

        // ✅ Apply ongoing status damage (burn, bleed, etc.)
        String statusDamage = enemy.processStatusEffect();
        if (!statusDamage.isEmpty()) {
            log.append(statusDamage);
            if (!enemy.isAlive()) {
                log.append("The enemy was killed by the effect.\n");
                return log.toString(); // Enemy dies before attacking
            }
        }

        int incoming = enemy.attack();

        // 1. Dodge check
        if (Math.random() * 100 < player.getDodgeChance()) {
            log.append(enemy.getName()).append(" attacked, but you **dodged** it!\n");
            return log.toString();
        }

        // 2. Block check
        boolean blocked = Math.random() * 100 < player.getBlockChance();
        if (blocked) incoming /= 2;

        // 3. Damage reduction
        int reduced = incoming;
        String defenseNote = "";

        if (incoming > 0) {
            reduced = switch (incomingType()) {
                case PHYSICAL -> Math.max(0, incoming - player.getArmor());
                case MAGIC -> Math.max(0, incoming - player.getMagicResist());
            };

            if (reduced < incoming) {
                defenseNote = " (reduced from " + incoming + ")";
            }
        }

        player.takeDamage(reduced);

        // Final message
        if (blocked) {
            log.append("Blocked! ");
        }

        log.append(enemy.getName()).append(" attacked you for ").append(reduced).append(" damage").append(defenseNote).append(".\n");

        return log.toString();
    }


    private DamageType incomingType() {
        // For now, all enemy attacks are physical.
        return DamageType.PHYSICAL;
    }




    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
