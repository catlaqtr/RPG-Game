package ui;

import characters.*;
import combat.CombatManager;

import javax.swing.*;

public class GameOverHandler {

    public static boolean handle(JFrame frame, Player[] player, Enemy[] enemy, CombatManager[] manager, int[] waveNumber, JButton attackButton, JButton specialButton, JButton statsButton, CombatLogPanel logPanel, Runnable updateLabels, Runnable updateSpecialUI) {

        int choice = JOptionPane.showConfirmDialog(frame, "You have been defeated!\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            player[0] = ClassSelector.selectClass();
            waveNumber[0] = 1;
            enemy[0] = new EnemyFactory().generate(waveNumber[0]);
            manager[0] = new CombatManager(player[0], enemy[0]);
            logPanel.clear();
            attackButton.setEnabled(true);
            specialButton.setEnabled(true);
            statsButton.setEnabled(true);
            updateLabels.run();
            updateSpecialUI.run();
            return true;
        } else {
            attackButton.setEnabled(false);
            specialButton.setEnabled(false);
            statsButton.setEnabled(false);
            return false;
        }
    }
}
