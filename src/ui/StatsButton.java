package ui;

import characters.Player;

import javax.swing.*;

public class StatsButton {
    public static void setup(JButton statsButton, JFrame frame, Player[] playerRef) {
        statsButton.setToolTipText("View your current stats");

        statsButton.addActionListener(e -> {
            Player player = playerRef[0];
            String statsInfo = String.format("""
                    Class: %s
                    Level: %d
                    XP: %d
                    Health: %d / %d
                    Mana: %d / %d
                    Bonus Damage: %d
                    Crit Chance: %.1f%%
                    Mana Regen: %d
                    Attack Range Bonus: %d
                    
                    Armor: %d
                    Magic Resist: %d
                    Dodge Chance: %.1f%%
                    Block Chance: %.1f%%
                    Lifesteal: %.1f%%
                    """, player.getClass().getSimpleName(), player.getLevel(), player.getXp(), player.getHealth(), player.getMaxHealth(), player.getMana(), player.getMaxMana(), player.getBonusDamage(), player.getCritChance(), player.getManaRegen(), player.getAttackRangeBonus(), player.getArmor(), player.getMagicResist(), player.getDodgeChance(), player.getBlockChance(), player.getLifesteal());

            JOptionPane.showMessageDialog(frame, statsInfo, "Your Stats", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
