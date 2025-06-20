package ui;

import characters.*;
import combat.CombatManager;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    private final JFrame frame = new JFrame("My First RPG Window");
    private final EnemyFactory enemyFactory = new EnemyFactory();

    private final Player[] player = new Player[1];
    private final Enemy[] enemy = new Enemy[1];
    private final CombatManager[] combatManager = new CombatManager[1];
    private final int[] waveNumber = {1};

    private final JLabel playerLabel = new JLabel();
    private final JLabel enemyLabel = new JLabel();
    private final JLabel waveLabel = new JLabel();
    private final JLabel levelLabel = new JLabel();

    private final JButton attackButton = new JButton("Attack");
    private final JButton specialButton = new JButton();
    private final JButton statsButton = new JButton("Stats");

    private final CombatLogPanel logPanel = new CombatLogPanel();
    private final WaveManager waveManager = new WaveManager();

    public void launch() {
        player[0] = ClassSelector.selectClass(); // ✅ Properly assign the player
        initWindow();
        initGame();
    }

    private void initWindow() {
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(playerLabel);
        topPanel.add(enemyLabel);
        topPanel.add(waveLabel);
        topPanel.add(levelLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middlePanel.add(attackButton);
        middlePanel.add(specialButton);
        middlePanel.add(statsButton);
        frame.add(middlePanel, BorderLayout.CENTER);

        frame.add(logPanel.getScrollPane(), BorderLayout.SOUTH);
    }

    private void initGame() {
        enemy[0] = enemyFactory.generate(waveNumber[0]);
        combatManager[0] = new CombatManager(player[0], enemy[0]);

        attackButton.setToolTipText("Basic physical attack (may crit)");
        StatsButton.setup(statsButton, frame, player);

        Runnable updateLabels = () -> {
            playerLabel.setText("Player HP: " + player[0].getHealth() + "/" + player[0].getMaxHealth() + " Mana: " + player[0].getMana() + "/" + player[0].getMaxMana());
            String status = enemy[0].hasActiveStatus() ? " (" + enemy[0].getStatusText() + ")" : "";
            enemyLabel.setText("Enemy HP: " + enemy[0].getHealth() + "/" + enemy[0].getMaxHealth() + status);

            waveLabel.setText("Wave: " + waveNumber[0]);
            levelLabel.setText("Level: " + player[0].getLevel() + " | XP: " + player[0].getXp());
        };

        Runnable updateSpecialUI = () -> SpecialButtonManager.update(specialButton, player[0]);

        Runnable checkGameOver = () -> {
            if (!player[0].isAlive()) {
                GameOverHandler.handle(frame, player, enemy, combatManager, waveNumber, attackButton, specialButton, statsButton, logPanel, updateLabels, updateSpecialUI);
            }
        };

        Runnable nextWave = () -> {
            waveNumber[0]++;
            waveManager.advance(player[0], waveNumber[0], logPanel);
            enemy[0] = enemyFactory.generate(waveNumber[0]);
            combatManager[0] = new CombatManager(player[0], enemy[0]);
            updateLabels.run();
            updateSpecialUI.run();
        };

        attackButton.addActionListener(e -> {
            logPanel.clear(); // ✅ Clear log before showing new info
            logPanel.append(combatManager[0].playerAttack());

            if (enemy[0].isAlive()) {
                player[0].restoreMana(player[0].getManaRegen());
                logPanel.append("You regained " + player[0].getManaRegen() + " mana.\n");
            }
            updateLabels.run();
            updateSpecialUI.run();
            if (!enemy[0].isAlive()) nextWave.run();
            checkGameOver.run();
        });

        specialButton.addActionListener(e -> {
            logPanel.clear(); // ✅ Clear log before showing new info
            logPanel.append(combatManager[0].playerSpecial());

            if (enemy[0].isAlive()) {
                player[0].restoreMana(player[0].getManaRegen());
                logPanel.append("You regained " + player[0].getManaRegen() + " mana.\n");
            }
            updateLabels.run();
            updateSpecialUI.run();
            if (!enemy[0].isAlive()) nextWave.run();
            checkGameOver.run();
        });

        updateLabels.run();
        updateSpecialUI.run();
        frame.setVisible(true);
    }
}
