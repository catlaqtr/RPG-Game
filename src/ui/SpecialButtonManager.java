package ui;

import characters.Player;

import javax.swing.*;

public class SpecialButtonManager {

    public static void update(JButton specialButton, Player player) {
        specialButton.setText("Cast " + player.getSpecialName());
        specialButton.setToolTipText(player.getSpecialName() + " (Costs " + player.getSpecialManaCost() + " mana)");
        specialButton.setEnabled(player.getMana() >= player.getSpecialManaCost());
    }
}
