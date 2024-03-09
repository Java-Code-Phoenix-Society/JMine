package org.jcps;

/**
 * The Game class represents a game with a specified width, height, and number of mines.
 * <p>
 * This class provides methods to retrieve the width, height, and number of mines of the game.
 * </p>
 * <p>
 * The width, height, and number of mines are set during the construction of the Game object.
 * </p>
 *
 * @since 1.0
 */
public class Game {
    /**
     * The width of the game.
     */
    int width;
    /**
     * The height of the game.
     */
    int height;
    /**
     * The number of mines in the game.
     */
    int mines;
    int x;
    int y;

    /**
     * Constructs a new Game object with the specified width, height, and number of mines.
     *
     * @param width  the width of the game
     * @param height the height of the game
     * @param mines  the number of mines in the game
     */
    public Game(final int width, final int height, final int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    /**
     * Returns the width of the game.
     *
     * @return the width of the game
     */
    public int width() {
        return this.width;
    }

    /**
     * Returns the height of the game.
     *
     * @return the height of the game
     */
    public int height() {
        return this.height;
    }

    /**
     * Returns the number of mines in the game.
     *
     * @return the number of mines in the game
     */
    public int mines() {
        return this.mines;
    }
}
