package com.codefixerai.main;

import com.codefixerai.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the CodeFixerAI desktop application.
 *
 * This class is responsible for:
 * - Applying the custom dark Nimbus look and feel to Swing.
 * - Tweaking UIManager colors to create a modern dark theme.
 * - Launching the main Swing window (MainFrame) on the EDT.
 */


public class App {

    /**
     * Configures a dark Nimbus look and feel for the entire application.
     *
     * It:
     * - Iterates through installed LookAndFeels to find "Nimbus".
     * - Applies it if found.
     * - Silently ignores errors so the app still launches even if Nimbus is missing.
     */

    private static void applyDarkTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        Color bg = new Color(40, 40, 40);
        Color bgLight = new Color(55, 55, 55);
        Color fg = new Color(230, 230, 230);
        Color accent = new Color(70, 120, 200);


        /**
         * Applies custom color overrides on top of the Nimbus look and feel.
         *
         * This adjusts:
         * - Background and foreground colors for panels, text areas and tables.
         * - Accent color for buttons and selection highlights.
         *
         * The goal is to give the app a consistent, modern dark theme.
         */

        UIManager.put("control", bg);
        UIManager.put("info", bgLight);
        UIManager.put("nimbusBase", bg);
        UIManager.put("nimbusBlueGrey", bgLight);
        UIManager.put("nimbusLightBackground", bgLight);
        UIManager.put("text", fg);
        UIManager.put("menu", bg);
        UIManager.put("menuText", fg);
        UIManager.put("window", bg);
        UIManager.put("windowText", fg);
        UIManager.put("TextField.background", bgLight);
        UIManager.put("TextField.foreground", fg);
        UIManager.put("TextArea.background", bgLight);
        UIManager.put("TextArea.foreground", fg);
        UIManager.put("FormattedTextField.background", bgLight);
        UIManager.put("FormattedTextField.foreground", fg);
        UIManager.put("Button.background", accent);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Table.background", bgLight);
        UIManager.put("Table.foreground", fg);
        UIManager.put("Table.gridColor", new Color(80, 80, 80));
        UIManager.put("ScrollPane.background", bg);
        UIManager.put("Panel.background", bg);
    }

    /**
     * Standard Java entry point.
     *
     * - Applies the dark theme.
     * - Schedules creation of the MainFrame on the Swing event dispatch thread.
     */

    public static void main(String[] args) {
        applyDarkTheme();
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
