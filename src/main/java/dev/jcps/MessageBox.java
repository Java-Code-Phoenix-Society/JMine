package dev.jcps;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The MessageBox class represents a dialog box for displaying messages and obtaining user input.
 * <p>
 * It extends the Dialog class and implements the ActionListener and WindowListener interfaces.
 * This class provides various types of message boxes with different button configurations.
 * </p>
 * <p>
 * Message boxes can have buttons for OK, Cancel, Yes, and No options, depending on the constructor parameters.
 * </p>
 * <p>
 * The user can interact with the message box by clicking on the buttons to perform actions such as closing the dialog or providing input.
 * </p>
 *
 * @since 1.0
 */
class MessageBox extends Dialog implements ActionListener, WindowListener {
    /**
     * Constant for specifying a message box with Yes, No, and Cancel buttons.
     */
    public static final int BTN_YESNOCANCEL = 11;

    /**
     * Constant for specifying a message box with Yes and No buttons.
     */
    public static final int BTN_YESNO = 14;

    /**
     * Constant for specifying a message box with OK and Cancel buttons.
     */
    public static final int BTN_OKCANCEL = 12;

    /**
     * Constant for specifying a message box with a Close button.
     */
    public static final int BTN_CLOSE = 13;

    /**
     * Constant representing the OK button.
     */
    public static final int OK = 1;

    /**
     * Constant representing the Cancel button.
     */
    public static final int CANCEL = 0;

    /**
     * Constant representing the Yes button.
     */
    public static final int YES = 2;

    /**
     * Constant representing the No button.
     */
    public static final int NO = 3;

    /**
     * The default height of the message box.
     */
    public static final int HEIGHT = 100;

    /**
     * The default x-coordinate of the message box.
     */
    public static final int CO_ORD_X = 200;

    /**
     * The default y-coordinate of the message box.
     */
    public static final int CO_ORD_Y = 200;

    /**
     * The event code representing the user's selection.
     */
    public static int pEvent;

    /**
     * The Close button for closing the message box.
     */
    private final Button close;

    /**
     * The OK button for confirming the message.
     */
    private final Button btnOK;

    /**
     * The Cancel button for canceling the message.
     */
    private final Button btnCancel;

    /**
     * The Yes button for confirming a positive choice.
     */
    private final Button btnYes;

    /**
     * The No button for confirming a negative choice.
     */
    private final Button btnNo;

    /**
     * Constructs a new MessageBox with the specified owner frame, title, message text, width, and button option.
     *
     * @param owner  the owner frame
     * @param title  the title of the message box
     * @param text   the message text
     * @param width  the width of the message box
     * @param option the button option for the message box
     */
    public MessageBox(final Frame owner, final String title, final String text, final int width, final int option) {
        super(owner, title, true);
        this.setBounds(CO_ORD_X, CO_ORD_Y, width, HEIGHT);
        this.setResizable(false);
        this.addWindowListener(this);
        Label message = new Label(text, 1);
        this.close = new Button("Close");
        this.btnOK = new Button("Ok");
        this.btnCancel = new Button("Cancel");
        this.btnYes = new Button("Yes");
        this.btnNo = new Button("No");
        this.close.addActionListener(this);
        this.btnOK.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnYes.addActionListener(this);
        this.btnNo.addActionListener(this);
        Panel buttonPanel = new Panel();
        switch (option) {
            case MessageBox.BTN_YESNOCANCEL: {
                buttonPanel.add(this.btnYes);
                buttonPanel.add(this.btnNo);
                buttonPanel.add(this.btnCancel);
                break;
            }
            case MessageBox.BTN_YESNO: {
                buttonPanel.add(this.btnYes);
                buttonPanel.add(this.btnNo);
                break;
            }
            case MessageBox.BTN_OKCANCEL: {
                buttonPanel.add(this.btnOK);
                buttonPanel.add(this.btnCancel);
                break;
            }
            default: {
                buttonPanel.add(this.close);
                break;
            }
        }
        this.add(message, "Center");
        this.add(buttonPanel, "South");
        this.setVisible(true);
    }

    /**
     * Constructs a new MessageBox with the specified owner frame, title, message text, and width.
     *
     * @param frame the owner frame
     * @param title the title of the message box
     * @param text  the message text
     * @param width the width of the message box
     */
    public MessageBox(final Frame frame, final String title, final String text, final int width) {
        this(frame, title, text, width, MessageBox.BTN_CLOSE);
    }

    /**
     * Handles action events triggered by buttons in the message box.
     *
     * @param actionEvent the action event
     */
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.close) {
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.btnOK) {
            MessageBox.pEvent = MessageBox.OK;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.btnCancel) {
            MessageBox.pEvent = MessageBox.CANCEL;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.btnYes) {
            MessageBox.pEvent = MessageBox.YES;
            this.setVisible(false);
            return;
        }
        if (actionEvent.getSource() == this.btnNo) {
            MessageBox.pEvent = MessageBox.NO;
            this.setVisible(false);
        }
    }

    /**
     * Handles the window closing event.
     *
     * @param windowEvent the window event
     */
    public void windowClosing(final WindowEvent windowEvent) {
        this.dispose();
    }

    /**
     * Handles the window closed event.
     *
     * @param windowEvent the window event
     */
    public void windowClosed(final WindowEvent windowEvent) {
        // Empty
    }

    /**
     * Handles the window opened event.
     *
     * @param windowEvent the window event
     */
    public void windowOpened(final WindowEvent windowEvent) {
        // Empty
    }

    /**
     * Handles the window iconified event.
     *
     * @param windowEvent the window event
     */
    public void windowIconified(final WindowEvent windowEvent) {
        // Empty
    }

    /**
     * Handles the window de-iconified event.
     *
     * @param windowEvent the window event
     */
    public void windowDeiconified(final WindowEvent windowEvent) {
        // Empty
    }

    /**
     * Handles the window activated event.
     *
     * @param windowEvent the window event
     */
    public void windowActivated(final WindowEvent windowEvent) {
        // Empty
    }

    /**
     * Handles the window deactivated event.
     *
     * @param windowEvent the window event
     */
    public void windowDeactivated(final WindowEvent windowEvent) {
        // Empty
    }
}
