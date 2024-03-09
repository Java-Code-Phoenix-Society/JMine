package org.jcps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * CustomDialog class represents a custom dialog for configuring game settings.
 * <p>
 * It extends the Dialog class and implements the ActionListener and WindowListener interfaces.
 * This dialog allows users to specify the width, height, and number of mines for a custom game.
 * </p>
 * <p>
 * The dialog provides OK and Cancel buttons for confirming or canceling the custom game settings.
 * It also includes text fields for entering the width, height, and number of mines.
 * </p>
 * <p>
 * When the OK button is clicked, the dialog validates the input values and creates a new Game instance.
 * If the input values are invalid, error messages are displayed using a MessageBox.
 * </p>
 * <p>
 * This dialog is designed to be used within the context of a Java AWT or Swing application.
 * </p>
 *
 * @since 1.0
 */
class CustomDialog extends Dialog implements ActionListener, WindowListener {
    /**
     * The x-coordinate of the dialog.
     */
    public static final int CO_ORD_X = 158;
    /**
     * The y-coordinate of the dialog.
     */
    public static final int CO_ORD_Y = 158;
    /**
     * The width of the dialog.
     */
    public static final int WIDTH = 200;
    /**
     * The height of the dialog.
     */
    public static final int HEIGHT = 300;
    /**
     * The default background color for the dialog.
     */
    private static final Color DEFAULT_BACKGROUND;
    /**
     * The default foreground color for the dialog.
     */
    private static final Color DEFAULT_FOREGROUND;
    /**
     * The default font for the dialog.
     */
    private static final Font DEFAULT_FONT;

    static {
        DEFAULT_BACKGROUND = new Color(96, 0, 0);
        DEFAULT_FOREGROUND = Color.white;
        DEFAULT_FONT = new Font("Serif", Font.BOLD, 14);
    }

    /**
     * The text field for entering the width.
     */
    private final TextField widthField;
    /**
     * The text field for entering the height.
     */
    private final TextField heightField;
    /**
     * The text field for entering the number of mines.
     */
    private final TextField minesField;
    /**
     * The OK button for confirming the custom game settings.
     */
    private final JButton okButton;
    /**
     * The Cancel button for canceling the custom game settings.
     */
    private final JButton cancelButton;
    /**
     * The parent frame of the dialog.
     */
    private final Frame parent;
    /**
     * The game instance.
     */
    private Game game;
    /**
     * Flag indicating whether the dialog is activated.
     */
    private boolean activated;

    /**
     * Constructs a new CustomDialog with the specified parent frame.
     *
     * @param frame the parent frame
     */
    public CustomDialog(final Frame frame) {
        super(frame, "Custom Game", false);
        Color foreground = CustomDialog.DEFAULT_FOREGROUND;
        this.activated = false;
        this.setBounds(CO_ORD_X, CO_ORD_Y, WIDTH, HEIGHT);
        this.parent = frame;
        this.setFont(CustomDialog.DEFAULT_FONT);
        this.setBackground(CustomDialog.DEFAULT_BACKGROUND);
        this.setForeground(foreground);
        (this.okButton = new JButton("OK")).addActionListener(this);
        this.okButton.setBackground(foreground);
        (this.cancelButton = new JButton("Cancel")).addActionListener(this);
        this.cancelButton.setBackground(foreground);
        this.widthField = new TextField(3);
        this.heightField = new TextField(3);
        this.minesField = new TextField(3);
        this.widthField.setText("10");
        this.heightField.setText("10");
        this.minesField.setText("10");
        this.widthField.setBackground(foreground);
        this.widthField.setForeground(Color.black);
        this.heightField.setBackground(foreground);
        this.heightField.setForeground(Color.black);
        this.minesField.setBackground(foreground);
        this.minesField.setForeground(Color.black);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        gridBagConstraints.fill = 0;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        this.add(new Label("Width:"), gridBagConstraints);
        ++gridBagConstraints.gridy;
        this.add(new Label("Height:"), gridBagConstraints);
        ++gridBagConstraints.gridy;
        this.add(new Label("Mines:"), gridBagConstraints);
        ++gridBagConstraints.gridy;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        this.add(this.okButton, gridBagConstraints);
        ++gridBagConstraints.gridx;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        this.add(this.widthField, gridBagConstraints);
        ++gridBagConstraints.gridy;
        this.add(this.heightField, gridBagConstraints);
        ++gridBagConstraints.gridy;
        this.add(this.minesField, gridBagConstraints);
        ++gridBagConstraints.gridy;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        this.add(this.cancelButton, gridBagConstraints);
        this.pack();
    }

    /**
     * Gets the game instance.
     *
     * @param game the game instance
     * @return the game instance
     */
    public Game getGame(final Game game) {
        if (this.activated) {
            return this.game;
        }
        return game;
    }

    /**
     * Handles action events.
     *
     * @param actionEvent the action event
     */
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() != this.okButton) {
            if (actionEvent.getSource() == this.cancelButton) {
                this.setVisible(this.activated = false);
                this.setModal(false);
            }
            return;
        }
        int int1;
        int int2;
        int int3;
        try {
            int1 = Integer.parseInt(this.widthField.getText());
            int2 = Integer.parseInt(this.heightField.getText());
            int3 = Integer.parseInt(this.minesField.getText());
            this.game = new Game(int1, int2, int3);
        } catch (final NumberFormatException ex) {
            new MessageBox(this.parent, "Error", "Integer values please", 150);
            return;
        }
        if (int3 >= int1 * int2) {
            new MessageBox(this.parent, "Error", "Impossible Game!", 150);
            return;
        }
        if (int1 > 30 || int1 < 8) {
            new MessageBox(this.parent, "Error", "Choose width from " + 8 + " to " + 30, 150);
            return;
        }
        if (int2 > 24 || int2 < 8) {
            new MessageBox(this.parent, "Error", "Choose height from " + 8 + " to " + 24, 150);
            return;
        }
        if (int3 > JMine.MAX_MINES || int3 < 10) {
            new MessageBox(this.parent, "Error", "Choose mines from " + 10 + " to " + JMine.MAX_MINES, 150);
            return;
        }
        this.activated = true;
        this.setVisible(false);
        this.setModal(false);
    }

    /**
     * Handles the window closing event.
     *
     * @param windowEvent the window event
     */
    public void windowClosing(final WindowEvent windowEvent) {
        this.setVisible(false);
        this.setModal(false);
    }

    /**
     * Handles the window closed event.
     *
     * @param windowEvent the window event
     */
    public void windowClosed(final WindowEvent windowEvent) {
    }

    /**
     * Handles the window opened event.
     *
     * @param windowEvent the window event
     */
    public void windowOpened(final WindowEvent windowEvent) {
    }

    /**
     * Handles the window iconified event.
     *
     * @param windowEvent the window event
     */
    public void windowIconified(final WindowEvent windowEvent) {
    }

    /**
     * Handles the window de-iconified event.
     *
     * @param windowEvent the window event
     */
    public void windowDeiconified(final WindowEvent windowEvent) {
    }

    /**
     * Handles the window activated event.
     *
     * @param windowEvent the window event
     */
    public void windowActivated(final WindowEvent windowEvent) {
    }

    /**
     * Handles the window deactivated event.
     *
     * @param windowEvent the window event
     */
    public void windowDeactivated(final WindowEvent windowEvent) {
    }
}
