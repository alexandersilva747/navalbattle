package org.example.navalbattle.model;

import java.util.ArrayList;
import java.util.List;

public class Ship { private final int size;  // The number of cells the ship occupies
    private List<int[]> positions;  // List of positions occupied by the ship
    private List<int[]> hits;  // List of positions hit by the player

    /**
     * Constructor to create a ship with a specified size.
     *
     * @param size Number of cells occupied by the ship
     */
    public Ship(int size) {
        this.size = size;
        this.positions = new ArrayList<>();
        this.hits = new ArrayList<>();
    }

    /**
     * Sets the positions occupied by the ship on the board.
     *
     * @param positions List of positions (row, col) occupied by the ship
     */
    public void setPositions(List<int[]> positions) {
        this.positions = positions;
    }

    /**
     * Checks if the ship occupies a specific position on the board.
     *
     * @param row The row of the position
     * @param col The column of the position
     * @return True if the ship occupies the position; otherwise, false
     */
    public boolean isAtPosition(int row, int col) {
        return positions.stream().anyMatch(pos -> pos[0] == row && pos[1] == col);
    }

    /**
     * Marks a position as hit on the ship.
     *
     * @param row The row of the hit position
     * @param col The column of the hit position
     */
    public void hit(int row, int col) {
        hits.add(new int[]{row, col});
    }

    /**
     * Returns the size of the ship.
     *
     * @return The number of cells the ship occupies
     */
    public int getSize() {
        return size;
    }

    /**
     * Determines if the ship is fully sunk (all positions hit).
     *
     * @return True if the ship is sunk; otherwise, false
     */
    public boolean isSunk() {
        return hits.size() == positions.size();
    }
}