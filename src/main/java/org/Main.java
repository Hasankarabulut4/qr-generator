package org;


import org.frame.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    Window window = new Window();
                    window.setVisible(true);
                }
        );
    }
}