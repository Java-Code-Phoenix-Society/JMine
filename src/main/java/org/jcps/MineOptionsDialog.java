package org.jcps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

/**
 * The MineOptionsDialog class represents a dialog box for configuring the minefield options.
 * <p>
 * It extends the Dialog class and implements the ActionListener, WindowListener, and ItemListener interfaces.
 * This dialog allows users to specify options related to the minefield.
 * </p>
 * <p>
 * The dialog provides fields for setting the coordinates, width, and height of the dialog box.
 * It also includes constants for default background, foreground, and score colors, as well as default fonts.
 * </p>
 *
 * @since 1.0
 */
class MineOptionsDialog extends Dialog implements ActionListener, WindowListener, ItemListener {

    /**
     * The x-coordinate for the initial position of the dialog box.
     */
    public static final int CO_ORD_X = 150;
    /**
     * The y-coordinate for the initial position of the dialog box.
     */
    public static final int CO_ORD_Y = 150;
    /**
     * The width of the dialog box.
     */
    public static final int WIDTH = 200;
    /**
     * The height of the dialog box.
     */
    public static final int HEIGHT = 300;
    /**
     * The default background color for the dialog box.
     */
    private static final Color DEFAULT_BACKGROUND;
    /**
     * The default foreground color for the dialog box.
     */
    private static final Color DEFAULT_FOREGROUND;
    /**
     * The default color for displaying scores in the dialog box.
     */
    private static final Color DEFAULT_SCORE_COLOR;
    /**
     * The default font for text in the dialog box.
     */
    private static final Font DEFAULT_FONT;
    /**
     * The default font for displaying scores in the dialog box.
     */
    private static final Font DEFAULT_SCORE_FONT;

    static {
        DEFAULT_BACKGROUND = new Color(0, 0, 96);
        DEFAULT_FOREGROUND = Color.white;
        DEFAULT_SCORE_COLOR = new Color(255, 255, 96);
        DEFAULT_FONT = new Font("Serif", Font.BOLD, 14);
        DEFAULT_SCORE_FONT = new Font("Serif", Font.BOLD, 22);
    }

    /**
     * An array storing the high scores.
     */
    private final Score[] highScores;

    /**
     * An array storing labels for displaying scores.
     */
    private final Label[] scoreLabels;

    /**
     * Checkbox for selecting the beginner difficulty level.
     */
    private final Checkbox beginnerCheck;

    /**
     * Checkbox for selecting the intermediate difficulty level.
     */
    private final Checkbox intermediateCheck;

    /**
     * Checkbox for selecting the expert difficulty level.
     */
    private final Checkbox expertCheck;

    /**
     * Checkbox for selecting the custom difficulty level.
     */
    private final Checkbox customCheck;

    /**
     * Button for confirming dialog selections.
     */
    private final JButton okButton;

    /**
     * Button for canceling dialog actions.
     */
    private final JButton cancelButton;

    /**
     * Custom dialog for configuring custom game options.
     */
    private final CustomDialog cd;

    /**
     * Custom game object for storing custom game settings.
     */
    Game CUSTOM;

    /**
     * The current selected game difficulty.
     */
    private Game difficulty;

    /**
     * Flag indicating whether the dialog is activated or not.
     */
    private boolean activated;

    /**
     * Creates a new MineOptionDialog
     *
     * @param frame frame to be set as parent
     */
    public MineOptionsDialog(final Frame frame) {
        super(frame, "JMine Options", false);
        Color foreground = MineOptionsDialog.DEFAULT_FOREGROUND;
        this.difficulty = JMine.BEGINNER;
        this.CUSTOM = new Game(10, 10, 10);
        this.activated = false;
        this.setBounds(CO_ORD_X, CO_ORD_Y, WIDTH, HEIGHT);
        this.setFont(MineOptionsDialog.DEFAULT_FONT);
        this.setBackground(MineOptionsDialog.DEFAULT_BACKGROUND);
        this.setForeground(foreground);
        this.highScores = new Score[3];
        this.scoreLabels = new Label[3];
        for (int i = 0; i < 3; ++i) {
            (this.scoreLabels[i] = new Label("")).setFont(MineOptionsDialog.DEFAULT_SCORE_FONT);
            this.scoreLabels[i].setForeground(MineOptionsDialog.DEFAULT_SCORE_COLOR);
        }
        this.cd = new CustomDialog(frame);
        for (int j = 0; j < 3; ++j) {
            this.highScores[j] = new Score("Unknown", 999);
        }
        this.updateLabels();
        (this.okButton = new JButton("OK")).addActionListener(this);
        this.okButton.setBackground(foreground);
        (this.cancelButton = new JButton("Cancel")).addActionListener(this);
        this.cancelButton.setBackground(foreground);
        CheckboxGroup checkGroup = new CheckboxGroup();
        (this.beginnerCheck = new Checkbox("Beginner", this.difficulty == JMine.BEGINNER, checkGroup))
                .addItemListener(this);
        (this.intermediateCheck = new Checkbox(
                "Intermediate", this.difficulty == JMine.INTERMEDIATE, checkGroup))
                .addItemListener(this);
        (this.expertCheck = new Checkbox("Expert", this.difficulty == JMine.EXPERT, checkGroup))
                .addItemListener(this);
        (this.customCheck = new Checkbox("Custom...", this.difficulty == this.CUSTOM, checkGroup))
                .addItemListener(this);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.fill = 0;
        constraints.anchor = 17;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        this.add(this.customCheck, constraints);
        ++constraints.gridy;
        this.add(this.beginnerCheck, constraints);
        ++constraints.gridy;
        this.add(this.intermediateCheck, constraints);
        ++constraints.gridy;
        this.add(this.expertCheck, constraints);
        ++constraints.gridy;
        constraints.anchor = 16;
        final int gridY = constraints.gridy;
        this.add(this.okButton, constraints);
        constraints.anchor = 17;
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(new Label("Best Times"));
        ++constraints.gridy;
        GridBagConstraints gridBagConstraints6;
        for (int k = 0; k < this.scoreLabels.length; ++k, gridBagConstraints6 = constraints, ++gridBagConstraints6.gridy) {
            this.add(this.scoreLabels[k], constraints);
        }
        constraints.gridy = gridY;
        constraints.anchor = 14;
        this.add(this.cancelButton, constraints);
        this.pack();
        this.okButton.requestFocus();
    }

    /**
     * Updates the labels with the best scores
     */
    public void updateLabels() {
        final NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumIntegerDigits(3);
        instance.setMaximumIntegerDigits(3);
        for (int i = 0; i < 3; ++i) {
            this.scoreLabels[i].setText(instance.format(this.highScores[i].time()) + "   " + this.highScores[i].name());
        }
        this.pack();
    }

    /**
     * Gets the best scores
     *
     * @param game The game to get the score from
     * @return the Score object
     */
    public Score getBestScore(final Game game) {
        if (game == JMine.BEGINNER) {
            return this.highScores[0];
        }
        if (game == JMine.INTERMEDIATE) {
            return this.highScores[1];
        }
        if (game == JMine.EXPERT) {
            return this.highScores[2];
        }
        return new Score("", 0);
    }

    /**
     * Sets the best score for a game.
     *
     * @param game The game to set the score for
     * @param s    The name of the player who scored
     * @param time The time scored
     */
    public void setHighScore(final Game game, final String s, final int time) {
        if (game == JMine.BEGINNER) {
            this.highScores[0] = new Score(s, time);
        } else if (game == JMine.INTERMEDIATE) {
            this.highScores[1] = new Score(s, time);
        } else if (game == JMine.EXPERT) {
            this.highScores[2] = new Score(s, time);
        }
        this.updateLabels();
    }

    /**
     * Handles action events triggered by buttons in the message box.
     *
     * @param actionEvent the action event
     */
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.okButton) {
            this.activated = true;
            this.setVisible(false);
            this.setModal(false);
            return;
        }
        if (actionEvent.getSource() == this.cancelButton) {
            this.setVisible(this.activated = false);
            this.setModal(false);
        }
    }

    /**
     * Retrieves the game difficulty based on the current activation state of the dialog.
     *
     * @param game The default game difficulty to return if the dialog is not activated.
     * @return The game difficulty, either the current selected difficulty if the dialog is activated,
     * or the default game difficulty provided.
     */
    public Game getGame(final Game game) {
        if (this.activated) {
            return this.difficulty;
        }
        return game;
    }

    /**
     * Sets the game difficulty.
     *
     * @param difficulty difficulty to set the game to.
     */
    public void setGame(final Game difficulty) {
        this.difficulty = difficulty;
        if (this.difficulty == JMine.BEGINNER) {
            this.beginnerCheck.setState(true);
            return;
        }
        if (this.difficulty == JMine.INTERMEDIATE) {
            this.intermediateCheck.setState(true);
            return;
        }
        if (this.difficulty == JMine.EXPERT) {
            this.expertCheck.setState(true);
            return;
        }
        this.customCheck.setState(true);
    }

    /**
     * Handles item events triggered by checkboxes in the message box.
     *
     * @param itemEvent the event to be processed
     */
    public void itemStateChanged(final ItemEvent itemEvent) {
        if (itemEvent.getSource() == this.beginnerCheck && this.beginnerCheck.getState()) {
            this.difficulty = JMine.BEGINNER;
            return;
        }
        if (itemEvent.getSource() == this.intermediateCheck && this.intermediateCheck.getState()) {
            this.difficulty = JMine.INTERMEDIATE;
            return;
        }
        if (itemEvent.getSource() == this.expertCheck) {
            this.difficulty = JMine.EXPERT;
            return;
        }
        if (itemEvent.getSource() == this.customCheck && this.customCheck.getState()) {
            this.setModal(false);
            this.cd.setModal(true);
            this.cd.setVisible(true);
            this.setModal(true);
            this.difficulty = this.cd.getGame(this.difficulty);
        }
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
     * Handles the window deiconified event.
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
