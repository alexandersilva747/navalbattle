package org.example.navalbattle.model;

import java.util.*;

/**
 * The EnemyFleet class represents the fleet of enemy ships on a 10x10 game board.
 * It handles the random placement of ships and provides methods to check if a shot
 * hits or misses a ship.
 */
public class EnemyFleet {
    private final List<Ship> ships = new ArrayList<>();  // List to store all ships in the fleet

    /**
     * Constructor that places ships randomly on the board.
     */
    public EnemyFleet() {
        placeShipsRandomly();
    }

    /**
     * Places each ship type (aircraft carrier, submarines, destroyers, frigates) randomly on the board.
     */
    private void placeShipsRandomly() {
        Random random = new Random();

        // Add ships with specified sizes
        addShip(4);  // Aircraft carrier (4 cells)
        addShip(3); addShip(3);  // Submarines (3 cells each)
        addShip(2); addShip(2); addShip(2);  // Destroyers (2 cells each)
        addShip(1); addShip(1); addShip(1); addShip(1);  // Frigates (1 cell each)

        // Position each ship on the board randomly
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();

                // Try placing ship; if position is valid, the ship is placed
                placed = placeShip(ship, row, col, horizontal);
            }
        }
    }

    /**
     * Adds a new ship to the fleet with the specified size.
     *
     * @param size The size (number of cells) occupied by the ship
     */
    private void addShip(int size) {
        ships.add(new Ship(size));
    }

    /**
     * Tries to position a ship on the board, ensuring no overlap with other ships.
     *
     * @param ship The ship to be placed
     * @param row The starting row for the ship
     * @param col The starting column for the ship
     * @param horizontal Whether the ship is placed horizontally or vertically
     * @return True if the ship is placed successfully; otherwise, false
     */
    private boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        List<int[]> positions = new ArrayList<>();

        // Check if the ship can be placed within bounds and without overlapping
        for (int i = 0; i < ship.getSize(); i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);

            if (r >= 10 || c >= 10 || isOccupied(r, c)) return false;

            positions.add(new int[]{r, c});
        }

        ship.setPositions(positions);  // Set ship positions if placement is valid
        return true;
    }

    /**
     * Checks if a position is already occupied by another ship.
     *
     * @param row The row to check
     * @param col The column to check
     * @return True if the position is occupied; otherwise, false
     */
    private boolean isOccupied(int row, int col) {
        return ships.stream().anyMatch(ship -> ship.isAtPosition(row, col));
    }

    /**
     * Checks if a shot hits any ship.
     *
     * @param row The row of the shot
     * @param col The column of the shot
     * @return True if a ship is hit; otherwise, false
     */
    public boolean isShipHit(int row, int col) {
        for (Ship ship : ships) {
            if (ship.isAtPosition(row, col)) {
                ship.hit(row, col);  // Mark position as hit
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the ship at the given position is fully sunk.
     *
     * @param row The row of the position
     * @param col The column of the position
     * @return True if the ship is sunk; otherwise, false
     */
    public boolean isShipSunkAt(int row, int col) {
        return ships.stream()
                .filter(ship -> ship.isAtPosition(row, col))
                .anyMatch(Ship::isSunk);
    }
}
