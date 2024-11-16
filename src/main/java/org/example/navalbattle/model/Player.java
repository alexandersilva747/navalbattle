package org.example.navalbattle.model;

import java.io.Serializable;

/**
 * Represents a player in the game.
 * This class is responsible for storing the player's nickname
 * and supports serialization to save and load player data.
 *
 * Implements {@link Serializable} for data persistence.
 *
 * @author Estudiantes: Lady Vanessa Matabanchoy Lasso 2370571
 * Sebastian Orejuela Albornoz 2242232
 * Olman Alexander Silva Zuñiga 2343025
 * @version 1.0
 */
public class Player implements Serializable {

    // Serial version UID for ensuring compatibility during deserialization
    private static final long serialVersionUID = 1L;

    /**
     * The player's nickname.
     */
    private String nickname;

    /**
     * Sets the player's nickname.
     *
     * @param nickname the nickname to set for the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Retrieves the player's nickname.
     *
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }
}
