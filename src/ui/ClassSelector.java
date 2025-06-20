package ui;

import characters.*;

import javax.swing.*;

public class ClassSelector {

    public static Player selectClass() {
        String[] classes = {"Warrior", "Mage", "Hunter", "Rogue"};
        int choice = JOptionPane.showOptionDialog(null, "Choose your class:", "Character Class", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, classes, classes[0]);

        return switch (choice) {
            case 1 -> new Mage("Hero");
            case 2 -> new Hunter("Hero");
            case 3 -> new Rogue("Hero");
            default -> new Warrior("Hero");
        };
    }
}
