package ui;

import characters.Player;

public class WaveManager {
    public void advance(Player player, int waveNumber, CombatLogPanel logPanel) {
        String xpResult = player.gainXp(10);
        logPanel.append(xpResult);


        player.restoreHealth(10);
        player.restoreMana(10);
        logPanel.append("You feel a surge of energy between waves. +10 HP and +10 Mana.\n");
    }
}
