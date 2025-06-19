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
        final Enemy[] enemy = { enemyFactory.generate(1) };
        final CombatManager[] combatManager = { new CombatManager(player[0], enemy[0]) };
        final int[] waveNumber = {1};

        JFrame frame = new JFrame("My First RPG Window");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setLayout(new GridLayout(2, 2, 10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel playerLabel = new JLabel();
        JLabel enemyLabel = new JLabel();
        JLabel waveLabel = new JLabel("Wave: " + waveNumber[0]);
        JLabel levelLabel = new JLabel("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());

        playerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        playerLabel.setForeground(new Color(0, 128, 0));
        enemyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        enemyLabel.setForeground(Color.RED);
        waveLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        waveLabel.setForeground(Color.BLUE);
        levelLabel.setFont(new Font("Monospaced", Font.BOLD, 13));

        topPanel.add(playerLabel);
        topPanel.add(enemyLabel);
        topPanel.add(waveLabel);
        topPanel.add(levelLabel);

        JTextArea combatLog = new JTextArea(7, 30);
        combatLog.setEditable(false);
        combatLog.setBackground(new Color(255, 250, 240));
        JScrollPane scrollPane = new JScrollPane(combatLog);

        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton attack = new JButton("Attack");
        JButton heal = new JButton("Heal");
        JButton fireball = new JButton("Fireball");
        JButton stats = new JButton("Stats");

        attack.setToolTipText("Deal physical damage (may crit)");
        heal.setToolTipText("Restore HP using mana");
        fireball.setToolTipText("Deal magic damage using 10 mana");
        stats.setToolTipText("View all your stats");

        stats.addActionListener(e -> {
            String statsInfo = String.format("""
                Level: %d
                XP: %d
                Health: %d / %d
                Mana: %d / %d
                Bonus Damage: %d
                Crit Chance: %.1f%%
                Mana Regen: %d
                Attack Range Bonus: %d
                """,
                    player[0].getLevel(),
                    player[0].getXp(),
                    player[0].getHealth(), player[0].getMaxHealth(),
                    player[0].getMana(), player[0].getMaxMana(),
                    player[0].getBonusDamage(),
                    player[0].getCritChance(),
                    player[0].getManaRegen(),
                    player[0].getAttackRangeBonus()
            );
            JOptionPane.showMessageDialog(frame, statsInfo, "Your Stats", JOptionPane.INFORMATION_MESSAGE);
        });

        Runnable updateLabels = () -> {
            playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() +
                    " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
            enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth());
            levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());
        };

        Runnable checkGameOver = () -> {
            if (!player[0].isAlive()) {
                int choice = JOptionPane.showConfirmDialog(frame, "You have been defeated!\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    player[0] = new Player("Hero", 50, 30);
                    enemy[0] = enemyFactory.generate(1);
                    waveNumber[0] = 1;
                    combatManager[0] = new CombatManager(player[0], enemy[0]);
                    waveLabel.setText("Wave: " + waveNumber[0]);
                    combatLog.setText("");
                    attack.setEnabled(true);
                    heal.setEnabled(true);
                    fireball.setEnabled(true);
                } else {
                    attack.setEnabled(false);
                    heal.setEnabled(false);
                    fireball.setEnabled(false);
                }
            }
        };

        Runnable nextWave = () -> {
            waveNumber[0]++;
            enemy[0] = enemyFactory.generate(waveNumber[0]);
            combatManager[0] = new CombatManager(player[0], enemy[0]);
            waveLabel.setText("Wave: " + waveNumber[0]);

            String xpResult = player[0].gainXp(10);
            combatLog.append(xpResult);
            if (xpResult.contains("leveled up")) {
                String[] options = {"+10 Max HP", "+5 Max Mana", "+2 Bonus Damage"};
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        "You leveled up! Choose a bonus:",
                        "Level Up!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                switch (choice) {
                    case 0 -> player[0].increaseMaxHealth(10);
                    case 1 -> player[0].increaseMaxMana(5);
                    case 2 -> player[0].increaseBonusDamage(2);
                }
            }

            combatLog.append("\nYou defeated the enemy! A new " + enemy[0].getName() + " appears!\n");
            player[0].restoreHealth(10);
            player[0].restoreMana(10);
            combatLog.append("You feel a surge of energy between waves. +10 HP and +10 Mana.\n");
            updateLabels.run();
        };

        attack.addActionListener(e -> {
            String result = combatManager[0].playerAttack();
            combatLog.append(result);
            if (enemy[0].isAlive()) {
                player[0].restoreMana(player[0].getManaRegen());
                combatLog.append("You regained " + player[0].getManaRegen() + " mana.\n");
            }
            updateLabels.run();
            if (!enemy[0].isAlive()) nextWave.run();
            checkGameOver.run();
            combatLog.setCaretPosition(combatLog.getDocument().getLength());
        });

        fireball.addActionListener(e -> {
            String result = combatManager[0].playerFireball();
            combatLog.append(result);
            if (enemy[0].isAlive()) {
                player[0].restoreMana(player[0].getManaRegen());
                combatLog.append("You regained " + player[0].getManaRegen() + " mana.\n");
            }
            updateLabels.run();
            if (!enemy[0].isAlive()) nextWave.run();
            checkGameOver.run();
            combatLog.setCaretPosition(combatLog.getDocument().getLength());
        });

        heal.addActionListener(e -> {
            String result = combatManager[0].playerHeal();
            combatLog.append(result);
            if (enemy[0].isAlive()) {
                player[0].restoreMana(player[0].getManaRegen());
                combatLog.append("You regained " + player[0].getManaRegen() + " mana.\n");
            }
            updateLabels.run();
            if (!enemy[0].isAlive()) nextWave.run();
            checkGameOver.run();
            combatLog.setCaretPosition(combatLog.getDocument().getLength());
        });

        middlePanel.add(attack);
        middlePanel.add(heal);
        middlePanel.add(fireball);
        middlePanel.add(stats);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        updateLabels.run();
        frame.setVisible(true);
    }
}