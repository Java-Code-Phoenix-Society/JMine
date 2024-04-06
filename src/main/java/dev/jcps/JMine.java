package dev.jcps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * JMine is a class representing a Minesweeper game panel. It extends JPanel and implements
 * MouseListener, MouseMotionListener, KeyListener, Runnable, and JavaAppletAdapter interfaces
 * to handle user interactions, game logic, and rendering.
 * <p>
 * This class serves as the main component for displaying and interacting with the Minesweeper game.
 * It handles mouse and keyboard input events, updates game state based on user interactions,
 * and renders the game graphics.
 * <p>
 * To use JMine, simply instantiate an object of this class and add it to a Swing container.
 * You can then interact with the Minesweeper game through mouse clicks and keyboard input.
 * <p>
 * Example usage:
 * <pre>{@code
 * JMine jMine = new JMine();
 * JFrame frame = new JFrame("Minesweeper");
 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * frame.getContentPane().add(jMine);
 * frame.setVisible(true);
 * }</pre>
 */
public class JMine extends JPanel implements MouseListener, MouseMotionListener, KeyListener, JavaAppletAdapter {
    /**
     * Tile size in pixels
     */
    public static final int TILE_SIZE = 16;
    /**
     * minimum minefield size along the X-axis
     */
    public static final int MIN_X = 8;
    /**
     * minimum minefield size along the Y-axis
     */
    public static final int MIN_Y = 8;
    /**
     * maximum minefield size along the X-axis
     */
    public static final int MAX_X = 30;
    /**
     * maximum minefield size along the Y-axis
     */
    public static final int MAX_Y = 24;

    /**
     * minimum number of mines
     */
    public static final int MIN_MINES = 10;
    /**
     * maximum number of mines
     */
    public static final int MAX_MINES = 667;
    /**
     * Flag icon width
     */
    public static final int FLAGS_WIDTH = 13;
    /**
     * Flag icon height
     */
    public static final int FLAGS_HEIGHT = 23;
    /**
     * Face icon square side size
     */
    public static final int FACE_SIZE = 26;
    /**
     * time face width
     */
    public static final int TIME_WIDTH = 13;
    /**
     * time face height
     */
    public static final int TIME_HEIGHT = 23;

    /**
     * Represents the game difficulty level: Beginner.
     */
    public static final Game BEGINNER;

    /**
     * Represents the game difficulty level: Intermediate.
     */
    public static final Game INTERMEDIATE;

    /**
     * Represents the game difficulty level: Expert.
     */
    public static final Game EXPERT;

    /**
     * Represents a custom game configuration.
     */
    public static final Game CUSTOM;

    /**
     * The default background color for the game.
     */
    private static final Color DEFAULT_BACKGROUND;

    /**
     * The default foreground color for the game.
     */
    private static final Color DEFAULT_FOREGROUND;

    /**
     * The horizontal offset for positioning game components.
     */
    public static int OFFSET_X;

    /**
     * The vertical offset for positioning game components.
     */
    public static int OFFSET_Y;

    /**
     * The x-coordinate for positioning the flags display.
     */
    public static int FLAGS_X;

    /**
     * The y-coordinate for positioning the flags display.
     */
    public static int FLAGS_Y;

    /**
     * The x-coordinate for positioning the face display.
     */
    public static int FACE_X;

    /**
     * The y-coordinate for positioning the face display.
     */
    public static int FACE_Y;

    /**
     * The x-coordinate for positioning the timer display.
     */
    public static int TIME_X;

    /**
     * The y-coordinate for positioning the timer display.
     */
    public static int TIME_Y;

    /**
     * Indicates whether a 3-button mouse is supported.
     */
    public static boolean M_3BUTTON_MOUSE;

    /**
     * The frame for the JMine game.
     */
    static JFrame frame;

    static {
        DEFAULT_BACKGROUND = Color.decode("#434434");
        DEFAULT_FOREGROUND = Color.decode("#000000");
        JMine.OFFSET_X = 10;
        JMine.OFFSET_Y = 40;
        JMine.FLAGS_X = 3 + JMine.OFFSET_X;
        JMine.FLAGS_Y = 10;
        JMine.FACE_X = JMine.FLAGS_X + 39 + 3;
        JMine.FACE_Y = 10;
        JMine.TIME_X = JMine.FACE_X + 26 + 3;
        JMine.TIME_Y = 10;
        BEGINNER = new Game(MIN_X, MIN_Y, MIN_MINES);
        INTERMEDIATE = new Game(16, 16, 40);
        EXPERT = new Game(MAX_X, MAX_Y, 99);
        CUSTOM = new Game(10, 10, MIN_MINES);
    }

    /**
     * Stores parameters related to the game configuration.
     */
    public GameParameters gameParams;
    /**
     * Timer used to update the game state.
     */
    Timer timer;
    /**
     * The background color of the game.
     */
    private Color background;

    /**
     * The foreground color of the game.
     */
    private Color foreground;

    /**
     * Indicates whether mouse button 1 is pressed.
     */
    private boolean M_BUTTON1;

    /**
     * Indicates whether mouse button 2 is pressed.
     */
    private boolean M_BUTTON2;

    /**
     * Indicates whether mouse button 3 is pressed.
     */
    private boolean M_BUTTON3;

    /**
     * Indicates whether both mouse buttons are pressed.
     */
    private boolean M_BOTH;

    /**
     * The current mouse pointer location.
     */
    private Point mp;

    /**
     * Indicates whether a new game has started.
     */
    private boolean newGame;

    /**
     * Indicates whether the screen should be cleared.
     */
    private boolean clearScreen;

    /**
     * Stores the mine tiles grid for the game.
     */
    private MineTile[][] tiles;

    /**
     * Vector storing mine tiles to be painted.
     */
    private ArrayList<MineTile> paintMe;

    /**
     * Array of images representing different faces for the game.
     */
    private Image[] imgFace;

    /**
     * Array of images representing different digits for the timer.
     */
    private Image[] imgTime;

    /**
     * The current face displayed in the game.
     */
    private int face;

    /**
     * The previously displayed face.
     */
    private int oldFace;

    /**
     * Indicates whether the face display has changed.
     */
    private boolean faceChanged;

    /**
     * The number of hidden tiles in the game.
     */
    private int hidden;

    /**
     * The number of flagged tiles in the game.
     */
    private int flags;

    /**
     * Array storing individual digits of the flag count.
     */
    private int[] flagDigits;

    /**
     * Array storing individual digits of the timer count.
     */
    private int[] timeDigits;

    /**
     * The current timer count.
     */
    private int time;

    /**
     * The total number of mines in the game.
     */
    private int mines;

    /**
     * Indicates whether all tiles need to be repainted.
     */
    private boolean paintAll;

    /**
     * Indicates whether the flags display has changed.
     */
    private boolean flagsChanged;

    /**
     * Indicates whether the timer display has changed.
     */
    private boolean timeChanged;

    /**
     * The difficulty level of the game.
     */
    private Game difficulty;

    /**
     * Dialog for configuring game options.
     */
    private MineOptionsDialog mod;

    /**
     * The start time of the game.
     */
    private long startTime;

    /**
     * Indicates whether all game activities should stop.
     */
    private boolean stopAll;

    /**
     * Graphics context for the off-screen buffer.
     */
    private Graphics bufferGC;

    /**
     * Off-screen buffer for double buffering.
     */
    private Image buffer;

    /**
     * Constructs a new JMine object with default settings.
     */
    public JMine() {
        this.background = JMine.DEFAULT_BACKGROUND;
        this.foreground = JMine.DEFAULT_FOREGROUND;
        this.M_BUTTON1 = false;
        this.M_BUTTON2 = false;
        this.M_BUTTON3 = false;
        this.M_BOTH = false;
        this.newGame = true;
        this.clearScreen = true;
        this.face = Smile.SMILE;
        this.oldFace = Smile.SMILE;
        this.faceChanged = true;
        this.hidden = -1;
        this.mines = -1;
        this.paintAll = true;
        this.flagsChanged = false;
        this.timeChanged = false;
        this.difficulty = JMine.BEGINNER;
        this.stopAll = true;
    }

    /**
     * The entry point for launching the JMine game.
     * <p>
     * This method creates an instance of the JMine class, initializes a JFrame for the game window, and sets up the game environment.
     * It adds the JMine instance to the center of the JFrame and sets its layout to BorderLayout.
     * The JFrame is set to exit the application when closed and is made visible.
     * Finally, the method initializes the JMine instance, adds a key listener, and starts the game loop by calling the run method.
     * </p>
     *
     * @param args The command-line arguments (not used in this method).
     */
    public static void main(String[] args) {
        JMine jmine = new JMine();

        frame = new JFrame("JMine");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(jmine, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        jmine.init();
        frame.addKeyListener(jmine);
        frame.setVisible(true);
        jmine.timer = new Timer(1000, e -> jmine.run());
        jmine.run();
    }

    /**
     * Initializes the game application.
     * <p>
     * This method sets up the game environment by initializing various parameters and components.
     * It creates a new instance of GameParameters, sets the game difficulty to beginner, and initializes mouse and keyboard event listeners.
     * The method also initializes flags for mouse buttons, vectors for painting components, and arrays for flag and time digits.
     * It sets the size of the game window based on the difficulty level and loads parameters and images required for the game.
     * Finally, it starts a new game by calling the newGame method.
     * </p>
     */
    public void init() {
        gameParams = new GameParameters();
        this.difficulty = JMine.BEGINNER;
        this.M_BOTH = false;
        JMine.M_3BUTTON_MOUSE = false;
        this.M_BUTTON1 = false;
        this.M_BUTTON2 = false;
        this.M_BUTTON3 = false;
        this.tiles = null;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.paintMe = new ArrayList<>();
        this.flagDigits = new int[3];
        this.timeDigits = new int[3];
        this.setFlags(0);
        this.mod = new MineOptionsDialog(new JFrame());
        int winHeight = (difficulty.height() * TILE_SIZE) + FACE_SIZE + 60;
        int winWidth = (difficulty.width() * TILE_SIZE) + 20;
        this.setSize(winWidth, winHeight);
        frame.setSize(winWidth + 16, winHeight + 4);
        this.loadParameters();
        this.loadImages();
        this.newGame();
    }

    /**
     * Loads parameters for configuring the game.
     * <p>
     * This method retrieves parameters from the applet's HTML embedding code to customize the game settings.
     * It loads parameters for background color, foreground color, and game difficulty level.
     * If parameters are not specified or cannot be parsed, default values are used.
     * The method sets the background and foreground colors based on the retrieved parameters and updates the game difficulty accordingly.
     * </p>
     */
    public void loadParameters() {
        final String parameter = this.getParameter("bgColor");
        if (parameter != null) {
            try {
                this.background = Color.decode(parameter);
            } catch (final NumberFormatException ex) {
                this.background = JMine.DEFAULT_BACKGROUND;
            }
        } else {
            this.background = JMine.DEFAULT_BACKGROUND;
        }
        this.setBackground(this.background);
        final String parameter2 = this.getParameter("fgColor");
        if (parameter2 != null) {
            try {
                this.foreground = Color.decode(parameter2);
            } catch (final NumberFormatException ex2) {
                this.foreground = JMine.DEFAULT_FOREGROUND;
            }
        } else {
            this.foreground = JMine.DEFAULT_FOREGROUND;
        }
        this.setForeground(this.foreground);
        final String parameter3 = this.getParameter("difficulty");
        if (parameter3 == null) {
            this.difficulty = JMine.BEGINNER;
            return;
        }
        if (parameter3.equalsIgnoreCase("beginner")) {
            this.difficulty = JMine.BEGINNER;
            return;
        }
        if (parameter3.equalsIgnoreCase("intermediate")) {
            this.difficulty = JMine.INTERMEDIATE;
            return;
        }
        if (parameter3.equalsIgnoreCase("expert")) {
            this.difficulty = JMine.EXPERT;
            return;
        }
        this.difficulty = JMine.BEGINNER;
    }

    /**
     * Loads images required for the game.
     * <p>
     * This method initializes and loads images necessary for the game, such as mine tiles, faces, and timer digits.
     * It creates an image buffer, sets its dimensions based on the size of the component, and fills it with the background color.
     * Images are loaded from the specified image path, or the code base if no path is provided.
     * The method uses a MediaTracker to track the loading status of each image and handle errors if any occur.
     * </p>
     */
    public void loadImages() {
        final int n = 32;
        int w = this.getSize().width, h = this.getSize().height;
        this.buffer = this.createImage(w, h);
        if (this.buffer == null) {
            this.buffer = new BufferedImage(w, w, BufferedImage.TYPE_INT_ARGB);
        }
        (this.bufferGC = this.buffer.getGraphics()).setColor(this.background);
        final MediaTracker mediaTracker = new MediaTracker(this);
        String imagePath = this.getParameter("image_path");
        if (imagePath == null) {
            imagePath = "images/";
        } else if (imagePath.lastIndexOf("/") != imagePath.length() - 1) {
            imagePath = imagePath + "/";
        }
        Object cb = this.getCodeBase().toString();
        if (cb == null) {
            System.out.println("DEBUG: no F");
            cb = "/";
        }
        for (int i = 0; i < 16; ++i) {
            final String string = i + ".gif";
            MineTile.images[i] = this.getImage(cb.toString(), imagePath + string);
            mediaTracker.addImage(MineTile.images[i], i);
            try {
                this.showStatus("Loading image: " + (i + 1) + "/" + n + " (" + imagePath + string + ")");
                mediaTracker.waitForID(i);
            } catch (final InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            if (mediaTracker.isErrorID(i)) {
                this.showStatus("Error loading " + imagePath + string);
            }
        }
        this.imgFace = new Image[Smile.NUM_FACES];
        for (int j = 0; j < Smile.NUM_FACES; ++j) {
            final String string2 = "f" + j + ".gif";
            mediaTracker.addImage(this.imgFace[j] = this.getImage(cb.toString(), imagePath + string2), j + 16);
            try {
                this.showStatus("Loading image: " + (j + 16 + 1) + "/" + n + " (" + imagePath + string2 + ")");
                mediaTracker.waitForID(j + 16);
            } catch (final InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            if (mediaTracker.isErrorID(j + 16)) {
                this.showStatus("Error loading " + imagePath + string2);
            }
        }
        this.imgTime = new Image[11];
        for (int k = 0; k < 11; ++k) {
            final String tn = "t" + k + ".gif";
            mediaTracker.addImage(this.imgTime[k] = this.getImage(cb.toString(), imagePath + tn), k + 16 + 5 + 1);
            try {
                this.showStatus("Loading image: " + (k + 16 + 5 + 1) + "/" + n + " (" + imagePath + tn + ")");
                mediaTracker.waitForID(k + 16 + 5);
            } catch (final InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            if (mediaTracker.isErrorID(k + 16 + 5)) {
                this.showStatus("Error loading " + imagePath + tn);
            }
        }
    }

    /**
     * Handles the game state when the player wins the game.
     * <p>
     * This method sets the clearScreen flag to true, changes the face of the game to a winning face,
     * stops the game counter, reveals all remaining tiles, prompts the player to enter their name for the high score,
     * updates the high score if necessary, and resets the game to its initial state.
     * </p>
     */
    public void winGame() {
        this.clearScreen = true;
        this.setFace(Smile.WIN);
        this.stopCounter();
        this.setFlags(0);

        // Reveal all remaining tiles
        for (int i = 0; i < tiles.length * tiles[0].length; i++) {
            int x = i / tiles[0].length;
            int y = i % tiles[0].length;
            MineTile tile = tiles[x][y];

            if (tile.isMine()) {
                touch(x, y, 10);
            } else if (!tile.revealed()) {
                reveal(x, y);
            }
        }

        // Prompt for high score if current time is better than the previous best score
        if (this.time < this.mod.getBestScore(this.difficulty).time()) {
            try {
                Thread.sleep(500L);
            } catch (final InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            // Prompt for name and update high score
            final NameDialog nameDialog = new NameDialog(new Frame(), this.mod.getBestScore(this.difficulty).name());
            nameDialog.setModal(true);
            nameDialog.setVisible(true);
            this.mod.setHighScore(this.difficulty, nameDialog.getName(), this.time);

            // Reset the game
            this.mod.setGame(this.difficulty);
            this.mod.setModal(true);
            this.mod.setVisible(true);
            this.difficulty = this.mod.getGame(this.difficulty);
        }
    }

    /**
     * Handles the game state when the player loses the game.
     * <p>
     * This method sets the clearScreen flag to true, changes the face of the game to a losing face,
     * stops the game counter, reveals all hidden mines, and marks incorrectly flagged tiles as wrong.
     * </p>
     */
    public void loseGame() {
        this.clearScreen = true;
        this.setFace(Smile.LOSE);
        this.stopCounter();

        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[0].length; y++) {
                MineTile currentTile = this.tiles[x][y];
                if (currentTile.revealed()) {
                    continue; // Skip already revealed tiles
                }

                if (currentTile.isMine() && currentTile.index == MineTile.HIDDEN) {
                    currentTile.setRevealed(true);
                    this.touch(x, y, MineTile.MINE);
                } else if (currentTile.index == MineTile.FLAG && !currentTile.isMine()) {
                    currentTile.setRevealed(true);
                    this.touch(x, y, MineTile.WRONG);
                }
            }
        }
    }

    /**
     * Start a new game with the existing difficulty.
     */
    public void newGame() {
        this.newGame(this.difficulty);
    }

    /**
     * Start a new game with the specified difficulty.
     *
     * @param difficulty difficulty to set
     */
    public void newGame(final Game difficulty) {
        this.difficulty = difficulty;
        int dWidth = difficulty.width();
        int dHeight = difficulty.height();
        this.tiles = new MineTile[dWidth][dHeight];
        if (difficulty.mines() >= dWidth * dHeight) {
            System.out.println("Impossible game!");
            return;
        }
        int winHeight = (dHeight * TILE_SIZE) + FACE_SIZE + 60;
        int winWidth = (dWidth * TILE_SIZE) + 20;

        this.setSize(winWidth, winHeight);
        frame.setSize(winWidth + 16, winHeight + 4);

        this.setTime(0);
        this.M_BOTH = false;
        JMine.OFFSET_X = (winWidth - dWidth * TILE_SIZE) / 2;
        JMine.OFFSET_Y = 40;
        JMine.FLAGS_X = JMine.OFFSET_X + 3;
        JMine.FLAGS_Y = 10;
        JMine.FACE_X = JMine.OFFSET_X + dWidth * TILE_SIZE / 2 - FLAGS_WIDTH;
        JMine.FACE_Y = 10;
        JMine.TIME_X = JMine.OFFSET_X + dWidth * TILE_SIZE - 39;
        JMine.TIME_Y = 10;

        this.paintMe = new ArrayList<>();
        int n = 0;
        for (int i = 0; i < dWidth; ++i) {
            for (int j = 0; j < dHeight; ++j) {
                this.tiles[i][j] = new MineTile(n++ < difficulty.mines());
                this.paintMe.add(this.tiles[i][j]);
            }
        }
        Random rand = new Random();
        for (int i = 0; i < dWidth * dHeight; i++) {
            int x1 = rand.nextInt(dWidth);
            int y1 = rand.nextInt(dHeight);
            int x2 = rand.nextInt(dWidth);
            int y2 = rand.nextInt(dHeight);

            MineTile temp = tiles[x1][y1];
            tiles[x1][y1] = tiles[x2][y2];
            tiles[x2][y2] = temp;
        }
        for (int x = 0; x < dWidth; ++x) {
            for (int y = 0; y < dHeight; ++y) {
                this.touch(x, y, 9);
                this.tiles[x][y].x = x;
                this.tiles[x][y].y = y;
                if (!this.tiles[x][y].isMine()) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx == 0 && dy == 0) continue; // Skip current tile
                            int nx = x + dx;
                            int ny = y + dy;
                            if (nx >= 0 && nx < dWidth && ny >= 0 && ny < dHeight && this.tiles[nx][ny].isMine()) {
                                this.tiles[x][y].number++;
                            }
                        }
                    }
                }
            }
        }
        this.hidden = dWidth * dHeight;
        this.setFlags(this.flags = difficulty.mines());
        this.mines = difficulty.mines();
        this.flagsChanged = true;
        this.timeChanged = true;
        this.face = Smile.SMILE;
        this.faceChanged = true;
        this.newGame = true;
        frame.revalidate();
        frame.repaint();
        this.draw(this.clearScreen = true);
    }

    /**
     * Draw the play field.
     *
     * @param paintAll if true, paint all tiles.
     */
    public void draw(final boolean paintAll) {
        this.paintAll = paintAll;
        this.repaint();
    }

    /**
     * Draw the play field.
     */
    public void draw() {
        this.draw(false);
    }

    /**
     * Update the UI.
     *
     * @param graphics the <code>Graphics</code> context in which to paint
     */
    public void update(final Graphics graphics) {
        if (this.clearScreen) {
            this.paintAll = true;
        }
        if (this.clearScreen) {
            this.bufferGC.setColor(this.background);
            this.bufferGC.fillRect(0, 0, this.getSize().width, this.getSize().height);
            this.paint(this.bufferGC);
            graphics.drawImage(this.buffer, 0, 0, this.getSize().width, this.getSize().height, this);
            this.clearScreen = false;
            return;
        }
        if (this.paintAll) {
            this.paint(this.bufferGC);
            graphics.drawImage(this.buffer, JMine.OFFSET_X, JMine.FACE_Y, this.tiles.length * 16 + JMine.OFFSET_X,
                    this.tiles[0].length * 16 + JMine.OFFSET_Y, JMine.OFFSET_X, JMine.FACE_Y,
                    this.tiles.length * 16 + JMine.OFFSET_X, this.tiles[0].length * 16 + JMine.OFFSET_Y, this);
            return;
        }
        this.paint(graphics);
    }

    /**
     * Sets the smiley face graphic.
     * Use named fields from the Smile class:
     * <ul>
     *     <li>{@link Smile#SMILE}</li>
     *     <li>{@link Smile#WIN}</li>
     *     <li>{@link Smile#CLICK}</li>
     *     <li>{@link Smile#LOSE}</li>
     *     <li>{@link Smile#PRESSED}</li>
     * </ul>
     *
     * @param face the face graphic to display.
     */
    public void setFace(final int face) {
        if (this.oldFace != this.face) {
            this.oldFace = this.face;
        }
        this.face = face;
        this.faceChanged = true;
    }

    /**
     * Updates the flag counter.
     *
     * @param flags number of flags to set
     */
    public void setFlags(final int flags) {
        this.flags = flags;
        this.flagsChanged = true;
    }

    /**
     * Updates the timer.
     *
     * @param time time to show
     */
    public void setTime(final int time) {
        this.time = time;
        this.timeChanged = true;
    }

    /**
     * Touches a tile at the specified coordinates and updates its state.
     * <p>
     * This method updates the state of the tile located at the given coordinates (x, y) to the specified index.
     * If the index of the tile at the specified coordinates is different from the provided index,
     * the tile's state is updated, and it is added to the list of tiles to be repainted.
     * <p>
     * Note: The provided index should correspond to one of the predefined tile states.
     *
     * @param x     The x-coordinate of the tile to touch.
     * @param y     The y-coordinate of the tile to touch.
     * @param index The index representing the new state of the tile.
     */
    public void touch(final int x, final int y, final int index) {
        if (this.tiles[x][y].index != index) {
            this.paintMe.add(this.tiles[x][y]);
            this.tiles[x][y].touch(index);
        }
    }

    /**
     * Paints the game field.
     *
     * @param graphics the <code>Graphics</code> context in which to paint
     */
    public void paint(final Graphics graphics) {
        this.paintFace(graphics, this.paintAll);
        this.paintFlags(graphics, this.paintAll);
        this.paintTime(graphics, this.paintAll);
        this.paintTiles(graphics, this.paintAll);
        this.paintAll = true;
    }

    /**
     * Paints the smiley face.
     *
     * @param graphics the <code>Graphics</code> context in which to paint
     * @param b        if true, paint the updated smiley face.
     */
    public void paintFace(final Graphics graphics, final boolean b) {
        if (b || this.faceChanged) {
            graphics.drawImage(this.imgFace[this.face], JMine.FACE_X, JMine.FACE_Y, 26, 26, this);
            this.faceChanged = false;
        }
    }

    /**
     * Paints the flags on the field.
     *
     * @param graphics The <code>Graphics</code> context in which to paint.
     * @param b        If <code>true</code>, paints only the updated flags.
     */
    public void paintFlags(final Graphics graphics, final boolean b) {
        if (b || this.flagsChanged) {
            boolean b2 = false;
            if (this.flags < 0) {
                b2 = true;
                this.flags = -this.flags;
            }
            final int[] array = new int[3];
            if (this.flags > 999) {
                array[0] = 9;
                array[2] = (array[1] = 9);
            } else if (b2) {
                array[0] = 10;
                if (this.flags < -99) {
                    array[2] = (array[1] = 9);
                } else {
                    array[1] = this.flags / 10 % 10;
                    array[2] = this.flags % 10;
                }
            } else {
                array[0] = this.flags / 100 % 10;
                array[1] = this.flags / 10 % 10;
                array[2] = this.flags % 10;
            }
            for (int i = 0; i < 3; ++i) {
                if (this.flagDigits[i] != array[i] || b) {
                    this.flagDigits[i] = array[i];
                    graphics.drawImage(this.imgTime[this.flagDigits[i]], JMine.FLAGS_X + i * 13, JMine.FLAGS_Y,
                            FLAGS_WIDTH, FLAGS_HEIGHT, this);
                }
            }
            if (b2) {
                this.flags = -this.flags;
            }
            this.flagsChanged = false;
        }
    }

    /**
     * Paints the time on the screen.
     *
     * @param graphics The <code>Graphics</code> context in which to paint.
     * @param b        If <code>true</code>, paints only the updated time.
     */
    public void paintTime(final Graphics graphics, final boolean b) {
        if (b || this.timeChanged) {
            final int[] array = new int[3];
            if (this.time > 999) {
                this.stopCounter();
                array[0] = 9;
                array[2] = (array[1] = 9);
            } else {
                array[0] = this.time / 100 % 10;
                array[1] = this.time / 10 % 10;
                array[2] = this.time % 10;
            }
            for (int i = 0; i < 3; ++i) {
                if (this.timeDigits[i] != array[i] || b) {
                    this.timeDigits[i] = array[i];
                    graphics.drawImage(this.imgTime[this.timeDigits[i]], JMine.TIME_X + i * 13, JMine.TIME_Y,
                            TIME_WIDTH, TIME_HEIGHT, this);
                }
            }
            this.timeChanged = false;
        }
    }

    /**
     * Paints the tiles on the game field.
     *
     * @param graphics The <code>Graphics</code> context in which to paint.
     * @param b        If <code>true</code>, paints all tiles; if <code>false</code>, paints only the updated tiles.
     */
    public void paintTiles(final Graphics graphics, final boolean b) {
        if (this.tiles == null) {
            //System.out.println("NULL!!!");
            return;
        }
        if (b) {
            for (int i = 0; i < this.tiles.length; ++i) {
                for (int j = 0; j < this.tiles[i].length; ++j) {
                    if (this.tiles[i][j] == null) {
                        System.out.println("NULL!");
                    } else {
                        this.tiles[i][j].draw(graphics, JMine.OFFSET_X + 16 * i,
                                JMine.OFFSET_Y + 16 * j, this);
                        this.tiles[i][j].touched = false;
                    }
                }
            }
            return;
        }
        for (final MineTile mineTile : this.paintMe) {
            mineTile.draw(graphics, JMine.OFFSET_X + 16 * mineTile.x,
                    JMine.OFFSET_Y + 16 * mineTile.y, this);
            this.tiles[mineTile.x][mineTile.y].touched = false;
        }
        this.paintMe.clear();
    }

    /**
     * Reveals a tile on the game field and updates it.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     */
    public void reveal(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.tiles.length || y >= this.tiles[0].length) {
            return;
        }
        if (this.tiles[x][y].getRevealed()) {
            return;
        }
        if ((this.tiles[x][y].isMine() && this.tiles[x][y].index == MineTile.FLAG) ||
                this.tiles[x][y].index == MineTile.MINE || this.tiles[x][y].index == MineTile.REDMINE ||
                this.tiles[x][y].index == MineTile.WRONG) {
            return;
        }
        this.tiles[x][y].setRevealed(true);
        if (this.newGame) {
            this.newGame = false;
            this.startCounter();
            if (this.tiles[x][y].isMine()) {
                int x2 = 0;
                int y2 = 0;
                boolean b = false;
                for (int i = 0; i < this.tiles.length && !b; ++i) {
                    for (int j = 0; j < this.tiles[i].length && !b; ++j) {
                        if (!this.tiles[i][j].isMine() && (i != x || j != y)) {
                            b = true;
                            x2 = i;
                            y2 = j;
                        }
                    }
                }
                if (!this.tiles[x2][y2].isMine()) {
                    this.tiles[x2][y2].index = MineTile.HIDDEN;
                    this.tiles[x2][y2].number = -1;
                    this.tiles[x][y].index = MineTile.HIDDEN;
                    this.tiles[x][y].number = 0;
                    for (int i = 0; i < this.tiles.length; ++i) {
                        for (int j = 0; j < this.tiles[i].length; ++j) {
                            if (!this.tiles[i][j].isMine()) {
                                this.tiles[i][j].number = 0;
                                if (i > 0 && j > 0 && this.tiles[i - 1][j - 1].isMine()) {
                                    final MineTile mineTile = this.tiles[i][j];
                                    ++mineTile.number;
                                }
                                if (i > 0 && this.tiles[i - 1][j].isMine()) {
                                    final MineTile mineTile2 = this.tiles[i][j];
                                    ++mineTile2.number;
                                }
                                if (i > 0 && j < this.tiles[i].length - 1 && this.tiles[i - 1][j + 1].isMine()) {
                                    final MineTile mineTile3 = this.tiles[i][j];
                                    ++mineTile3.number;
                                }
                                if (j < this.tiles[i].length - 1 && this.tiles[i][j + 1].isMine()) {
                                    final MineTile mineTile4 = this.tiles[i][j];
                                    ++mineTile4.number;
                                }
                                if (i < this.tiles.length - 1 && j < this.tiles[i].length - 1 &&
                                        this.tiles[i + 1][j + 1].isMine()) {
                                    final MineTile mineTile5 = this.tiles[i][j];
                                    ++mineTile5.number;
                                }
                                if (i < this.tiles.length - 1 && this.tiles[i + 1][j].isMine()) {
                                    final MineTile mineTile6 = this.tiles[i][j];
                                    ++mineTile6.number;
                                }
                                if (i < this.tiles.length - 1 && j > 0 && this.tiles[i + 1][j - 1].isMine()) {
                                    final MineTile mineTile7 = this.tiles[i][j];
                                    ++mineTile7.number;
                                }
                                if (j > 0 && this.tiles[i][j - 1].isMine()) {
                                    final MineTile mineTile8 = this.tiles[i][j];
                                    ++mineTile8.number;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.tiles[x][y].isMine()) {
            this.touch(x, y, MineTile.REDMINE);
            this.loseGame();
            return;
        }
        --this.hidden;
        if (this.hidden < 0) {
            System.out.println("miscount!");
        }
        if (this.hidden == this.mines) {
            this.winGame();
        }
        if (this.tiles[x][y].index == MineTile.FLAG) {
            return;
        }
        this.touch(x, y, this.tiles[x][y].number);
        if (this.tiles[x][y].index == 0) {
            this.reveal(x - 1, y - 1);
            this.reveal(x - 1, y);
            this.reveal(x - 1, y + 1);
            this.reveal(x, y + 1);
            this.reveal(x + 1, y + 1);
            this.reveal(x + 1, y);
            this.reveal(x + 1, y - 1);
            this.reveal(x, y - 1);
            return;
        }
        if (this.face != 3 && this.face != 1) {
            this.setFace(Smile.SMILE);
        }
    }

    /**
     * The run method continuously updates the game timer and redraws the game screen.
     * <p>
     * This method is executed by the Timer to ensure smooth execution of the game loop.
     * It updates the game timer and redraws the game screen as long as the {@code stopAll} flag is not set
     * and a new game is not started.
     * <p>
     * During each iteration of the loop, the elapsed time since the game started is calculated,
     * and if the time has changed, it updates the game timer and redraws the game screen.
     *
     * @see Timer
     * @see Date#getTime()
     */
    public void run() {
        if (!this.stopAll && !this.newGame) {
            final long runTimer = new Date().getTime() - this.startTime;
            final int time = (int) (runTimer / 1000L);
            if (time != this.time && !this.stopAll && !this.newGame) {
                this.setTime(time);
                this.draw();
            }
        }
    }

    /**
     * Starts the game timer.
     * <p>
     * Initializes the timer variables, including setting the elapsed time to 0 and recording the start time.
     * Sets the stopAll flag to false, indicating that the timer is active.
     * If the game engine thread is not already running, it starts a new thread to run the game loop.
     * Additionally, starts the Swing Timer if it is not already running.
     *
     * @see Thread#start()
     * @see Date#getTime()
     * @see Timer#start()
     */
    public void startCounter() {
        this.time = 0;
        this.startTime = new Date().getTime();
        this.stopAll = false;
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Stops the game timer.
     * <p>
     * Sets the stopAll flag to true, indicating that all game activities should be stopped.
     * Invokes the stop() method to terminate the game engine thread.
     *
     * @see #stop()
     */
    public void stopCounter() {
        this.stopAll = true;
        this.stop();
    }

    /**
     * Stops the game engine Timer.
     */
    public void stop() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Handles key press events.
     * <p>
     * This method is called when a key is pressed while the game window is focused.
     * It switches based on the key code to perform different actions:<ul>
     * <li>If the 'Q' key is pressed, it stops the game timer and starts a new game.</li>
     * <li>If the 'R' key is pressed:</li>
     * <li>If the M_BUTTON2 flag is set, it performs a specific action related to the game's mechanics.</li>
     * <li>Otherwise, it invokes either the squareUp or retouch method based on the mouse pointer position.</li>
     * </ul>
     * Finally, it resets the mouse button flags and repaints the game screen.
     *
     * @param keyEvent The KeyEvent object representing the key press event.
     */
    public void keyPressed(final KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_Q: {
                this.stopCounter();
                this.newGame();
                return;
            }
            case KeyEvent.VK_R: {
                if (this.M_BUTTON2) {
                    this.squareUp(this.mp.x, this.mp.y);
                } else {
                    this.retouch(this.mp.x, this.mp.y);
                }
                this.M_BUTTON1 = false;
                this.M_BUTTON2 = false;
                this.M_BUTTON3 = false;
                this.repaint();
            }
            default: {
            }
        }
    }

    /**
     * Handles key release events.
     * <p>
     * This method is called when a key is released while the game window is focused.
     * Currently, it does not perform any actions.
     *
     * @param keyEvent The KeyEvent object representing the key release event.
     */
    public void keyReleased(final KeyEvent keyEvent) {
    }

    /**
     * Handles key typed events.
     * <p>
     * This method is called when a key is typed while the game window is focused.
     * Currently, it does not perform any actions.
     *
     * @param keyEvent The KeyEvent object representing the key typed event.
     */
    public void keyTyped(final KeyEvent keyEvent) {
    }

    /**
     * Handles mouse click events.
     * <p>
     * This method is called when the mouse is clicked within the game window.
     * Currently, it does not perform any actions.
     *
     * @param mouseEvent The MouseEvent object representing the mouse click event.
     */
    public void mouseClicked(final MouseEvent mouseEvent) {
    }

    /**
     * Handles mouse press events.
     * <p>
     * This method is called when a mouse button is pressed within the game window.
     * It determines the action based on the combination of mouse buttons pressed:<ul>
     * <li>If both left and right buttons are pressed simultaneously, a specific action is performed.</li>
     * <li>If only the left button is pressed, another action is performed.</li>
     * <li>If only the middle button is pressed, it sets a flag indicating the use of a three-button mouse.</li>
     * <li>If only the right button is pressed, it sets the M_BUTTON3 flag.</li></ul>
     * Finally, it determines the position of the mouse pointer and performs appropriate actions based on game logic.
     *
     * @param mouseEvent The MouseEvent object representing the mouse press event.
     */
    public void mousePressed(final MouseEvent mouseEvent) {
        switch (mouseEvent.getModifiersEx()) {
            case InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON3_DOWN_MASK: {
                // getMod() 20 - Completely unsure what is meant to happen here
                this.M_BUTTON1 = true;
                this.M_BUTTON3 = true;
                this.M_BUTTON2 = true;
                break;
            }
            case 0:
            case InputEvent.BUTTON1_DOWN_MASK: { // 0 & 16
                this.M_BUTTON1 = true;
                break;
            }
            case InputEvent.BUTTON2_DOWN_MASK: { // 8
                this.M_BUTTON2 = true;
                JMine.M_3BUTTON_MOUSE = true;
                break;
            }
            case InputEvent.BUTTON3_DOWN_MASK: { // 4
                this.M_BUTTON3 = true;
                break;
            }
            default: { // anything else
                System.out.println("Unknown mousePressed: " + mouseEvent.getModifiersEx());
                return;
            }
        }
        this.M_BUTTON2 = ((this.M_BUTTON1 && this.M_BUTTON3 && !JMine.M_3BUTTON_MOUSE) ||
                (this.M_BUTTON2 && JMine.M_3BUTTON_MOUSE));
        final int x = mouseEvent.getX();
        final int y = mouseEvent.getY();
        if (x >= JMine.FACE_X && x <= JMine.FACE_X + 26 && y >= JMine.FACE_Y && y <= JMine.FACE_Y + 26) {
            this.setFace(Smile.PRESSED);
            this.draw();
            return;
        }
        if (this.face == Smile.LOSE || this.face == Smile.WIN) {
            return;
        }
        final int x2 = (x - JMine.OFFSET_X) / 16;
        final int y2 = (y - JMine.OFFSET_Y) / 16;
        if (this.mp == null) {
            this.mp = new Point(x2, y2);
        }
        if (this.M_BUTTON2) {
            this.M_BOTH = true;
        }
        this.tilePress(x2, y2);
    }

    /**
     * Handles the action of pressing a tile on the game board based on the mouse buttons pressed.
     *
     * @param x The x-coordinate of the pressed tile.
     * @param y The y-coordinate of the pressed tile.
     * @see #setFace(int)
     * @see #squareDown(int, int)
     * @see #touch(int, int)
     * @see #setFlags(int)
     * @see #draw()
     */
    public void tilePress(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.tiles.length || y >= this.tiles[0].length) {
            return;
        }
        if (this.M_BUTTON2) {
            // Middle mouse button pressed
            this.setFace(Smile.CLICK);
            this.mp = new Point(x, y);
            this.squareDown(x, y);
        } else if (this.M_BUTTON1) {
            // Left mouse button pressed
            if (this.tiles[x][y].index == MineTile.HIDDEN) {
                this.touch(x, y, 0);
            } else if (this.tiles[x][y].index == MineTile.QMARK) {
                this.touch(x, y, MineTile.QMARKP);
            }
            this.mp = new Point(x, y);
            this.setFace(Smile.CLICK);
        } else if (this.M_BUTTON3) {
            // Right mouse button pressed
            if (this.tiles[x][y].index == MineTile.HIDDEN) {
                this.setFlags(this.flags - 1);
                this.touch(x, y, MineTile.FLAG);
            } else if (this.tiles[x][y].index == MineTile.FLAG) {
                this.touch(x, y, MineTile.QMARK);
                this.setFlags(this.flags + 1);
            } else if (this.tiles[x][y].index == MineTile.QMARK) {
                this.touch(x, y, MineTile.HIDDEN);
            }
        } else {
            // No mouse buttons recorded
            System.out.println("Tried to press tile, but no buttons recorded!");
        }
        this.draw();
    }

    /**
     * Handles the release of a tile after a mouse button press event.
     * <p>
     * Determines the action to perform based on the mouse button states:
     * <ul>
     * <li>If M_BUTTON2 is set, performs actions related to the game's mechanics based on the squareFlags method.</li>
     * <li>If M_BUTTON1 is set and M_BOTH is not set, reveals the tile using the reveal method.</li>
     * <li>Resets the face to {@code Smile.SMILE} if the current face is {@code Smile.CLICK}.</li>
     * </ul>
     * <p>
     * Finally, redraws the game screen after performing the necessary actions.
     *
     * @param x The x-coordinate of the released tile.
     * @param y The y-coordinate of the released tile.
     * @see #squareFlags(int, int)
     * @see #reveal(int, int)
     */
    public void tileRelease(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.tiles.length || y >= this.tiles[0].length) {
            return;
        }
        if (this.M_BUTTON2) {
            if (this.squareFlags(x, y) == this.tiles[x][y].number &&
                    this.tiles[x][y].number == this.tiles[x][y].index) {
                this.squareReveal(x, y);
            } else {
                this.squareUp(x, y);
                this.setFace(Smile.SMILE);
            }
            this.draw();
            return;
        }
        if (this.M_BUTTON1 && !this.M_BOTH) {
            this.reveal(x, y);
            if (this.face == Smile.CLICK) {
                this.setFace(Smile.SMILE);
            }
            this.draw();
        }
    }

    /**
     * Handles the release of the mouse button after a mouse press event.
     * <p>
     * Determines the appropriate action based on the combination of mouse button states and mouse event modifiers.
     * Performs actions such as starting a new game, loading game options, or handling tile releases.
     * Resets mouse button flags and sets the mouse pointer position.
     *
     * @param mouseEvent The MouseEvent object representing the mouse release event.
     * @see #tileRelease(int, int)
     */
    public void mouseReleased(final MouseEvent mouseEvent) {
        if (!this.M_BUTTON1 || !this.M_BUTTON3 || JMine.M_3BUTTON_MOUSE) {
            this.M_BOTH = false;
        }
        int men = mouseEvent.getModifiersEx();
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        if (x >= JMine.FACE_X && x <= JMine.FACE_X + 26 && y >= JMine.FACE_Y && y <= JMine.FACE_Y + 26) {
            this.setFace(Smile.SMILE);
            if (men == 256) {
                this.showStatus("Loading options...");
                this.mod.setGame(this.difficulty);
                this.mod.setModal(true);
                this.mod.setVisible(true);
                this.difficulty = this.mod.getGame(this.difficulty);
                this.setFace(Smile.SMILE);
                this.draw();
            }
            this.newGame();
        } else if (this.face != Smile.LOSE && this.face != Smile.WIN) {
            x = (x - JMine.OFFSET_X) / 16;
            y = (y - JMine.OFFSET_Y) / 16;
            this.tileRelease(x, y);
        }
        switch (men) {
            case InputEvent.BUTTON3_DOWN_MASK, InputEvent.BUTTON1_DOWN_MASK, 0: {
                this.M_BUTTON1 = false;
                break;
            }
            case InputEvent.BUTTON2_DOWN_MASK, 512: {
                this.M_BUTTON2 = false;
                JMine.M_3BUTTON_MOUSE = true;
                break;
            }
            case 256: {
                this.M_BUTTON3 = false;
                break;
            }
            default: {
                System.out.println("Unknown mouseReleased: " + mouseEvent.getModifiersEx());
                break;
            }
        }
        this.M_BUTTON2 = ((this.M_BUTTON1 && this.M_BUTTON3 && !JMine.M_3BUTTON_MOUSE) ||
                (this.M_BUTTON2 && JMine.M_3BUTTON_MOUSE));
        this.mp = new Point(x, y);
    }

    /**
     * Handles the mouse entering the game window.
     * <p>
     * Changes the face to {@code Smile.CLICK} if the current face is not {@code Smile.LOSE}, {@code Smile.WIN}, or {@code Smile.CLICK}.
     *
     * @param mouseEvent The MouseEvent object representing the mouse enter event.
     */
    public void mouseEntered(final MouseEvent mouseEvent) {
        if (this.face == Smile.LOSE || this.face == Smile.WIN || this.face == Smile.CLICK) {
            return;
        }
        if (this.M_BUTTON1) {
            this.setFace(Smile.CLICK);
            this.draw();
        }
    }

    /**
     * Handles the mouse exiting the game window.
     * <p>
     * Resets the face to {@code Smile.SMILE} if the current face is not {@code Smile.SMILE},
     * {@code Smile.LOSE}, or {@code Smile.WIN}.
     *
     * @param mouseEvent The MouseEvent object representing the mouse exit event.
     * @see Smile
     */
    public void mouseExited(final MouseEvent mouseEvent) {
        if (this.face != Smile.SMILE && this.face != Smile.LOSE && this.face != Smile.WIN) {
            this.setFace(Smile.SMILE);
            this.draw();
        }
    }

    /**
     * Handles mouse drag events.
     * <p>
     * Determines the appropriate action based on the mouse pointer position and button states.
     * Changes the face to {@code Smile.PRESSED} if the mouse pointer is over the game face.
     * Updates the game screen accordingly based on the game logic and mouse button states.
     *
     * @param mouseEvent The MouseEvent object representing the mouse drag event.
     */
    public void mouseDragged(final MouseEvent mouseEvent) {
        final int x = mouseEvent.getX();
        final int y = mouseEvent.getY();
        if (this.face == Smile.PRESSED) {
            if (x < JMine.FACE_X || x > JMine.FACE_X + 26 || y < JMine.FACE_Y || y > JMine.FACE_Y + 26) {
                this.setFace(this.oldFace);
            }
            this.draw();
            return;
        }
        if (x >= JMine.FACE_X && x <= JMine.FACE_X + 26 && y >= JMine.FACE_Y && y <= JMine.FACE_Y + 26) {
            this.setFace(Smile.PRESSED);
            this.draw();
            return;
        }
        final int xOffset = (x - JMine.OFFSET_X) / 16;
        final int yOffset = (y - JMine.OFFSET_Y) / 16;
        if (this.mp == null) {
            this.mp = new Point(xOffset, yOffset);
        }
        if (this.face == Smile.LOSE || this.face == Smile.WIN) {
            return;
        }
        if (xOffset != this.mp.x || yOffset != this.mp.y) {
            if (this.M_BUTTON2) {
                this.squareUp(this.mp.x, this.mp.y);
                this.squareDown(xOffset, yOffset);
                this.draw();
            } else if (this.M_BUTTON1 && !this.M_BOTH) {
                this.retouch(this.mp.x, this.mp.y);
                this.tilePress(xOffset, yOffset);
                this.draw();
            }
            this.mp = new Point(xOffset, yOffset);
        }
    }

    /**
     * Reveals a square of tiles centered at the specified coordinates (x, y) and its surrounding tiles.
     *
     * @param x The x-coordinate of the center tile.
     * @param y The y-coordinate of the center tile.
     * @see #reveal(int, int)
     */
    public void squareReveal(final int x, final int y) {
        this.reveal(x, y);
        this.reveal(x - 1, y - 1);
        this.reveal(x - 1, y);
        this.reveal(x - 1, y + 1);
        this.reveal(x, y + 1);
        this.reveal(x + 1, y + 1);
        this.reveal(x + 1, y);
        this.reveal(x + 1, y - 1);
        this.reveal(x, y - 1);
    }

    /**
     * Hides a square of tiles centered at the specified coordinates (x, y) and its surrounding tiles.
     *
     * @param x The x-coordinate of the center tile.
     * @param y The y-coordinate of the center tile.
     * @see #retouch(int, int)
     */
    public void squareUp(final int x, final int y) {
        this.retouch(x, y);
        this.retouch(x - 1, y - 1);
        this.retouch(x - 1, y);
        this.retouch(x - 1, y + 1);
        this.retouch(x, y + 1);
        this.retouch(x + 1, y + 1);
        this.retouch(x + 1, y);
        this.retouch(x + 1, y - 1);
        this.retouch(x, y - 1);
    }

    /**
     * Counts the number of flagged tiles in a square centered at the specified coordinates (x, y) and its surrounding tiles.
     *
     * @param x The x-coordinate of the center tile.
     * @param y The y-coordinate of the center tile.
     * @return The total number of flagged tiles.
     * @see #isFlag(int, int)
     */
    public int squareFlags(final int x, final int y) {
        return this.isFlag(x, y) + this.isFlag(x - 1, y - 1) + this.isFlag(x - 1, y) +
                this.isFlag(x - 1, y + 1) + this.isFlag(x, y + 1) + this.isFlag(x + 1, y + 1) +
                this.isFlag(x + 1, y) + this.isFlag(x + 1, y - 1) + this.isFlag(x, y - 1);
    }

    /**
     * Checks if a tile at the specified coordinates (n, n2) is flagged.
     *
     * @param n  The x-coordinate of the tile.
     * @param n2 The y-coordinate of the tile.
     * @return 1 if the tile is flagged, 0 otherwise.
     */
    public int isFlag(final int n, final int n2) {
        if (n < 0 || n2 < 0 || n >= this.tiles.length || n2 >= this.tiles[0].length) {
            return 0;
        }
        if (this.tiles[n][n2].index == MineTile.FLAG) {
            return 1;
        }
        return 0;
    }

    /**
     * Adds a tile to the list of tiles to repaint and updates its appearance to indicate a change in state.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @see #repaint()
     */
    public void retouch(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.tiles.length || y >= this.tiles[0].length) {
            return;
        }
        this.paintMe.add(this.tiles[x][y]);
        this.tiles[x][y].retouch();
    }

    /**
     * Adds a tile to the list of tiles to repaint and updates its appearance to indicate a change in state.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @see #repaint()
     */
    public void touch(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.tiles.length || y >= this.tiles[0].length) {
            return;
        }
        if (!this.tiles[x][y].getRevealed()) {
            this.paintMe.add(this.tiles[x][y]);
            this.tiles[x][y].touch();
        }
    }

    /**
     * Reveals a square of tiles centered at the specified coordinates (x, y) and its surrounding tiles.
     *
     * @param x The x-coordinate of the center tile.
     * @param y The y-coordinate of the center tile.
     * @see #touch(int, int)
     */
    public void squareDown(final int x, final int y) {
        this.touch(x, y);
        this.touch(x - 1, y - 1);
        this.touch(x - 1, y);
        this.touch(x - 1, y + 1);
        this.touch(x, y + 1);
        this.touch(x + 1, y + 1);
        this.touch(x + 1, y);
        this.touch(x + 1, y - 1);
        this.touch(x, y - 1);
    }

    /**
     * Handles the movement of the mouse.
     * <p>
     * Resets button states and square highlighting if the game options menu is not visible.
     *
     * @param mouseEvent The MouseEvent object representing the mouse movement event.
     * @see #squareUp(int, int)
     */
    public void mouseMoved(final MouseEvent mouseEvent) {
        if (this.mod.isVisible()) {
            return;
        }
        if (mouseEvent.getModifiersEx() == 0) {
            if ((this.M_BUTTON1 || this.M_BUTTON2 || this.M_BUTTON3) && this.mp != null) {
                this.squareUp(this.mp.x, this.mp.y);
            }
            this.M_BUTTON1 = false;
            this.M_BUTTON2 = false;
            this.M_BUTTON3 = false;
            this.M_BOTH = false;
        }
    }

    /**
     * Represents different facial expressions for the smiley face in the Minesweeper game.
     */
    static class Smile {
        /**
         * Represents a normal smiling face.
         */
        public static final int SMILE = 0;
        /**
         * Represents a winning face.
         */
        public static final int WIN = 1;
        /**
         * Represents a clicking face.
         */
        public static final int CLICK = 2;
        /**
         * Represents a losing face.
         */
        public static final int LOSE = 3;
        /**
         * Represents a pressed face.
         */
        public static final int PRESSED = 4;
        /**
         * Represents the total number of facial expressions available.
         */
        public static final int NUM_FACES = 5;
    }
}
