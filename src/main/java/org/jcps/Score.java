package org.jcps;

/**
 * Represents a score in a game, consisting of a player's name and the time achieved.
 * <p>
 * This record stores the name of the player and the time taken to achieve the score.
 * </p>
 *
 * @param name The name of the player who achieved the score.
 * @param time The time taken to achieve the score.
 * @since 1.0
 */
record Score(String name, int time) {
}
