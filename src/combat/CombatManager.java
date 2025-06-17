package combat;

import characters.Player;
import characters.Enemy;

public class CombatManager {
    private Player player;
    private Enemy enemy;

    public CombatManager(Player player, Enemy enemy){
        this.player = player;
        this.enemy = enemy;
    }

    public String playerAttack(){
        StringBuilder result = new StringBuilder();

        int playerDamage = player.attack();
        enemy.takeDamage(playerDamage);
        result.append("You dealt ").append(playerDamage).append(" damage to ").append(enemy.getName()).append(".\n");

        if (enemy.isAlive()) {
            int enemyDamage = enemy.attack();
            player.takeDamage(enemyDamage);
            result.append(enemy.getName()).append(" attacked you for ").append(enemyDamage).append(" damage.\n");
        } else {
            result.append("You defeated ").append(enemy.getName()).append("!\n");
        }

        return result.toString();
    }

    public String playerHeal(){
        StringBuilder result = new StringBuilder();

        int healed = player.heal();
        if (healed == 0) {
            result.append("Not enough mana to heal!\n");
        } else {
            result.append("You healed yourself for ").append(healed).append(" HP.\n");
        }

        if (enemy.isAlive()) {
            int enemyDamage = enemy.attack();
            player.takeDamage(enemyDamage);
            result.append(enemy.getName()).append(" attacked you for ").append(enemyDamage).append(" damage.\n");
        }

        return result.toString();
    }

    public String playerFireball() {
        StringBuilder result = new StringBuilder();

        int fireballDamage = player.fireball();
        if (fireballDamage == 0) {
            result.append("Not enough mana to cast Fireball!\n");
        } else {
            enemy.takeDamage(fireballDamage);
            result.append("You cast Fireball and dealt ").append(fireballDamage).append(" damage to ").append(enemy.getName()).append(".\n");
        }

        if (enemy.isAlive()) {
            int enemyDamage = enemy.attack();
            player.takeDamage(enemyDamage);
            result.append(enemy.getName()).append(" attacked you for ").append(enemyDamage).append(" damage.\n");
        } else {
            result.append("You defeated ").append(enemy.getName()).append("!\n");
        }

        return result.toString();
    }


    public Player getPlayer(){
        return player;
    }

    public Enemy getEnemy(){
        return enemy;
    }
}
