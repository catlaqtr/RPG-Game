package ui;

import characters.*;

import javax.swing.*;

public class StatAllocationDialog {

    public static String promptStatChoice(Player player) {
        String[] options;
        if (player instanceof Warrior) {
            options = new String[]{"Max Health (+15)", "Armor (+5)", "Bonus Damage (+5)", "Lifesteal (+2%)", "Block Chance (+1%)"};
        } else if (player instanceof Rogue) {
            options = new String[]{"Crit Chance (+3%)", "Dodge Chance (+3%)", "Attack Range (+2)", "Lifesteal (+2%)", "Bonus Damage (+5)"};
        } else if (player instanceof Mage) {
            options = new String[]{"Max Mana (+10)", "Mana Regen (+2)", "Magic Resist (+5)", "Crit Chance (+3%)", "Lifesteal (+2%)"};
        } else if (player instanceof Hunter) {
            options = new String[]{"Crit Chance (+2%)", "Attack Range (+2)", "Dodge Chance (+2%)", "Armor (+4)", "Lifesteal (+2%)"};
        } else {
            options = new String[]{"Max Health (+10)", "Max Mana (+5)", "Bonus Damage (+3)"};
        }


        return (String) JOptionPane.showInputDialog(null, "Allocate 1 stat point:", "Stat Allocation", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}

