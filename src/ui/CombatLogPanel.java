package ui;

import javax.swing.*;

public class CombatLogPanel {
    private final JTextArea textArea = new JTextArea(7, 30);
    private final JScrollPane scrollPane;

    public CombatLogPanel() {
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
    }


    public void append(String message) {
        textArea.append(message);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void clear() {
        textArea.setText("");
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
