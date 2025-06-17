package ui;

import javax.swing.*;
import java.awt.*;
import combat.CombatManager;
import characters.Player;
import characters.Enemy;
import characters.EnemyFactory;


public class GameWindow {
    public static void main(String[] args) {
        EnemyFactory enemyFactory = new EnemyFactory();
        final Player[] player = { new Player("Hero", 50, 30) };
        final Enemy[] enemy = { new Enemy("Goblin", 40, 40) };
        final CombatManager[] combatManager = { new CombatManager(player[0], enemy[0]) };
        final int[] waveNumber = {1};

        // Create a window
        JFrame frame = new JFrame("My First RPG Window");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create top panel (Player and Enemy stats)
        JPanel topPanel = new JPanel();
        JLabel playerLabel = new JLabel();
        JLabel enemyLabel = new JLabel();
        JLabel waveLabel = new JLabel("Wave: " + waveNumber[0]);
        JLabel levelLabel = new JLabel("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());
        topPanel.add(levelLabel);


        playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
        enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());

        topPanel.add(playerLabel);
        topPanel.add(enemyLabel);
        topPanel.add(waveLabel);

        // Create combat log area
        JTextArea combatLog = new JTextArea(5, 30);
        combatLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(combatLog);

        // Create middle panel with buttons
        JPanel middlePanel = new JPanel();
        JButton attack = new JButton("Attack");
        JButton heal = new JButton("Heal");
        JButton fireball = new JButton("Fireball");


        // Attack Button Action
        attack.addActionListener(e -> {
            String result = combatManager[0].playerAttack();
            combatLog.append(result);

            // Update labels
            playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                    " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
            enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());

            if (!enemy[0].isAlive()) {
                waveNumber[0]++;
                enemy[0] = enemyFactory.generate(waveNumber[0]);

                combatManager[0] = new CombatManager(player[0], enemy[0]);
                waveLabel.setText("Wave: " + waveNumber[0]);
                String xpResult = player[0].gainXp(10); // Give 10 XP per enemy
                combatLog.append(xpResult);
                levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());


                combatLog.append("\nYou defeated the enemy! A new " + enemy[0].getName() + " appears!\n");
                enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
            }



            // Game Over check
            if (!player[0].isAlive()) {
                int choice = JOptionPane.showConfirmDialog(frame, "You have been defeated!\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // Reset game
                    player[0] = new Player("Hero", 50, 30);
                    enemy[0] = new Enemy("Goblin", 40, 40);
                    combatManager[0] = new CombatManager(player[0], enemy[0]);

                    playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                            " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
                    enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
                    combatLog.setText("");
                    levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());


                    attack.setEnabled(true);
                    heal.setEnabled(true);
                } else {
                    attack.setEnabled(false);
                    heal.setEnabled(false);
                }
            }
        });

        fireball.addActionListener(e -> {
            String result = combatManager[0].playerFireball();
            combatLog.append(result);

            playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                    " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
            enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());

            if (!enemy[0].isAlive()) {
                waveNumber[0]++;
                enemy[0] = enemyFactory.generate(waveNumber[0]);
                combatManager[0] = new CombatManager(player[0], enemy[0]);

                waveLabel.setText("Wave: " + waveNumber[0]);
                String xpResult = player[0].gainXp(10);
                combatLog.append(xpResult);
                levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());

                combatLog.append("\nYou defeated the enemy! A new " + enemy[0].getName() + " appears!\n");
                enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
            }

            if (!player[0].isAlive()) {
                int choice = JOptionPane.showConfirmDialog(frame, "You have been defeated!\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    player[0] = new Player("Hero", 50, 30);
                    enemy[0] = new Enemy("Goblin", 40, 40);
                    combatManager[0] = new CombatManager(player[0], enemy[0]);

                    playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                            " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
                    enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
                    combatLog.setText("");
                    levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());

                    attack.setEnabled(true);
                    heal.setEnabled(true);
                    fireball.setEnabled(true);
                } else {
                    attack.setEnabled(false);
                    heal.setEnabled(false);
                    fireball.setEnabled(false);
                }
            }
        });


        // Heal Button Action
        heal.addActionListener(e -> {
            String result = combatManager[0].playerHeal();
            combatLog.append(result);

            // Update labels
            playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                    " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
            enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());

            if (!enemy[0].isAlive()) {
                waveNumber[0]++;
                enemy[0] = enemyFactory.generate(waveNumber[0]);

                combatManager[0] = new CombatManager(player[0], enemy[0]);
                waveLabel.setText("Wave: " + waveNumber[0]);
                String xpResult = player[0].gainXp(10); // Give 10 XP per enemy
                combatLog.append(xpResult);
                levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());


                combatLog.append("\nYou defeated the enemy! A new " + enemy[0].getName() + " appears!\n");
                enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
            }

            // Game Over check
            if (!player[0].isAlive()) {
                int choice = JOptionPane.showConfirmDialog(frame, "You have been defeated!\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // Reset game
                    player[0] = new Player("Hero", 50, 30);
                    enemy[0] = new Enemy("Goblin", 40, 40);
                    combatManager[0] = new CombatManager(player[0], enemy[0]);

                    playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                            " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
                    enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
                    combatLog.setText("");

                    attack.setEnabled(true);
                    heal.setEnabled(true);
                    fireball.setEnabled(true);

                } else {
                    attack.setEnabled(false);
                    heal.setEnabled(false);

                }
            }
        });

        middlePanel.add(attack);
        middlePanel.add(heal);
        middlePanel.add(fireball);



        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Show the window
        frame.setVisible(true);
    }
}
