package dev.jcps;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * The {@code GameParameters} class serves as a central repository for managing game parameters,
 * originally received from the HTML page when the game was an applet. It maintains a HashMap
 * to store level variables and game parameters.
 * <p>
 * The parameters managed by this class control various aspects of the game's behaviour and
 * appearance, such as gravity, friction, dimensions, colours, and initial settings of game objects.
 * </p>
 * <p>
 * <i>Note: Changes to the parameters in this class will affect the game globally.</i>
 * </p>
 *
 * @author neoFuzz
 * @since 1.0
 */
public class GameParameters {
    public static final String DIFFICULTY = "difficulty";
    public static final String FG_COLOR = "fgColor";
    public static final String BG_COLOR = "bgColor";
    public static final String IMAGE_PATH = "image_path";
    /**
     * A HashMap that stores key-value pairs representing various game parameters.
     * The keys are String identifiers for the parameters, and the values are their corresponding settings.
     */
    @SuppressWarnings("all")
    public HashMap<String, String> paramMap;

    /**
     * Constructs a {@code GameParameters} object and initialises it with the default game settings,
     * represented as key-value pairs in the {@code paramMap}.
     */
    GameParameters() {
        paramMap = new HashMap<>();
        paramMap.put(DIFFICULTY, "beginner"); // can be beginner, intermediate or expert
        paramMap.put(FG_COLOR, "#000000");
        paramMap.put(BG_COLOR, "#FFFFFF");
        paramMap.put(IMAGE_PATH, "images");

    }

    /**
     * Constructs a {@code GameParameters} object and initialises it with a given set of game settings.
     * The settings are provided as a HashMap of key-value pairs, which are then stored in the paramMap.
     *
     * @param hashMap The HashMap containing key-value pairs representing various game parameters.
     */
    GameParameters(@NotNull HashMap<String, String> hashMap) {
        paramMap = new GameParameters().paramMap;

        paramMap.put(DIFFICULTY, hashMap.get(DIFFICULTY));
        paramMap.put(FG_COLOR, hashMap.get(FG_COLOR));
        paramMap.put(BG_COLOR, hashMap.get(BG_COLOR));
        paramMap.put(IMAGE_PATH, hashMap.get(IMAGE_PATH));

    }

    /**
     * Retrieves an integer value associated with a specific key from the paramMap.
     *
     * @param key The key associated with the integer value to be retrieved.
     * @return The integer value associated with the specified key.
     * @throws NumberFormatException If the value associated with the key cannot be parsed as an integer.
     */
    public int getInt(String key) {
        return Integer.parseInt(paramMap.get(key));
    }

    /**
     * Associates a specific integer value with a specific key in the paramMap.
     * If the key is already present in the paramMap, this will update its associated value.
     *
     * @param key The key with which the specified integer value is to be associated.
     * @param i   The integer value to be associated with the specified key.
     */
    public void putInt(String key, int i) {
        paramMap.put(key, String.valueOf(i));
    }
}
