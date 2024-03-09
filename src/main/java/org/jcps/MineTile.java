package org.jcps;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Represents a tile in a minesweeper game.
 * <p>
 * Each tile can be in different states such as hidden, flagged, revealed, or containing a mine.
 * </p>
 * <p>
 * This class provides methods for manipulating and drawing the tile.
 * </p>
 *
 * @since 1.0
 */
class MineTile {
    /**
     * Constant representing a hidden tile.
     */
    public static final int HIDDEN = 9;

    /**
     * Constant representing a flagged tile.
     */
    public static final int FLAG = 10;

    /**
     * Constant representing a tile marked with a question mark.
     */
    public static final int QMARK = 11;

    /**
     * Constant representing a tile containing a mine.
     */
    public static final int MINE = 12;

    /**
     * Constant representing a tile containing a red mine.
     */
    public static final int REDMINE = 13;

    /**
     * Constant representing a tile marked as wrong.
     */
    public static final int WRONG = 14;

    /**
     * Constant representing a tile marked with a question mark with a potential mine underneath.
     */
    public static final int QMARKP = 15;

    /**
     * Number of different tile images.
     */
    public static final int NUM_IMAGES = 16;

    /**
     * Array storing images for different tile states.
     */
    static Image[] images;

    static {
        MineTile.images = new Image[MineTile.NUM_IMAGES];
    }

    /**
     * Flag indicating whether the tile has been touched.
     */
    boolean touched;

    /**
     * The number of mines adjacent to this tile.
     */
    int number;

    /**
     * The current index representing the state of the tile.
     */
    int index;

    /**
     * The previous index representing the state of the tile.
     */
    int oldIndex;

    /**
     * The x-coordinate of the tile.
     */
    int x;

    /**
     * The y-coordinate of the tile.
     */
    int y;

    /**
     * Flag indicating whether the tile has been revealed.
     */
    private boolean revealed;

    /**
     * Constructs a MineTile object.
     *
     * @param b True if the tile contains a mine, false otherwise.
     */
    public MineTile(final boolean b) {
        this.number = (b ? -1 : 0);
        this.touched = false;
        this.index = MineTile.HIDDEN;
        this.oldIndex = this.index;
        this.revealed = false;
    }

    /**
     * Checks if the tile contains a mine.
     *
     * @return True if the tile contains a mine, false otherwise.
     */
    public boolean isMine() {
        return this.number < 0;
    }

    /**
     * Checks if the tile has been revealed.
     *
     * @return True if the tile has been revealed, false otherwise.
     */
    public boolean revealed() {
        return this.index != MineTile.FLAG && this.index != MineTile.QMARK && this.index != MineTile.HIDDEN;
    }

    /**
     * Gets the revealed status of the tile.
     *
     * @return True if the tile is revealed, false otherwise.
     */
    public boolean getRevealed() {
        return this.revealed;
    }

    /**
     * Sets the revealed status of the tile.
     *
     * @param status True to set the tile as revealed, false otherwise.
     */
    public void setRevealed(boolean status) {
        this.revealed = status;
    }


    /**
     * Touches the tile, indicating it has been interacted with.
     *
     * @param touched True to indicate the tile has been touched, false otherwise.
     */
    public void touch(final boolean touched) {
        this.touched = touched;
    }

    /**
     * Touches the tile with a specific index.
     *
     * @param index The index to set for the tile.
     */
    public void touch(final int index) {
        this.oldIndex = this.index;
        this.touch(true);
        this.index = index;
    }

    /**
     * Touches the tile.
     * If the tile is hidden, it will be touched with index 0.
     * If the tile is marked with a question mark, it will be marked as a question mark with a potential mine underneath.
     */
    public void touch() {
        if (this.revealed || this.index == MineTile.FLAG) {
            return;
        }
        if (this.index == MineTile.HIDDEN) {
            this.touch(0);
            return;
        }
        if (this.index == MineTile.QMARK) {
            this.touch(MineTile.QMARKP);
        }
    }

    /**
     * Retouches the tile, reverting it to its previous state.
     */
    public void retouch() {
        if (this.revealed || this.index == MineTile.FLAG) {
            return;
        }
        this.index = this.oldIndex;
        this.touched = true;
    }

    /**
     * Draws the tile with its current index at the specified location.
     *
     * @param graphics      The graphics context to draw the tile.
     * @param x             The x-coordinate of the tile's top-left corner.
     * @param y             The y-coordinate of the tile's top-left corner.
     * @param imageObserver An object waiting for notification about image updates.
     */
    public void draw(final Graphics graphics, final int x, final int y, final ImageObserver imageObserver) {
        if (MineTile.images[this.index] == null) {
            //System.out.println("NULL!!");
            return;
        }
        graphics.drawImage(MineTile.images[this.index], x, y, MineTile.images[this.index].getWidth(imageObserver),
                MineTile.images[this.index].getHeight(imageObserver), new Color(128, 128, 128), imageObserver);
    }
}
