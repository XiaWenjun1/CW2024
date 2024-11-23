package com.example.demo.Object.Boss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The BossMovePattern class manages the movement pattern of the boss.
 * It handles the vertical movement sequence and ensures the boss moves in a random pattern.
 */
public class BossMovePattern {

    private static final int VERTICAL_VELOCITY = 5;
    private static final int ZERO = 0;
    private static final int MOVE_FREQUENCY_PER_CYCLE = 5; // Frequency of movement changes in a cycle
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 10; // Maximum frames moving in the same direction

    private final List<Integer> movePattern = new ArrayList<>(); // List defining the movement pattern
    private int consecutiveMovesInSameDirection; // Counter for consecutive moves in the same direction
    private int indexOfCurrentMove; // Index for the current move in the move pattern

    public BossMovePattern() {
        initializeMovePattern();
    }

    /**
     * Initializes the movement pattern for the boss.
     * The pattern contains alternating vertical movements with some stationary moves.
     */
    private void initializeMovePattern() {
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(ZERO);
        }
        Collections.shuffle(movePattern);
    }

    /**
     * Gets the next move in the movement pattern for the boss.
     *
     * @return The next move for the boss in the pattern.
     */
    public int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }
}