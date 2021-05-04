package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.DevelopCardColor;

public enum ErrorType {
    INVALID_ACTION,
    WRONG_PLAYER, // not their turn
    INVALID_USERNAME,
    NOT_ENOUGH_RESOURCES,
    INVALID_LEADERCARD,
    CANNOT_DISCARD_ACTIVE_LEADER,
    ALREADY_PRODUCED,
    NEGATIVE_QUANTITY,
    ABUSE_OF_FAITH,
    NOT_BUYABLE,
    INVALID_CARD_PLACEMENT,
    INVALID_ROW_OR_COLUMN,
    INVALID_DEVELOP_CARD,
    WRONG_RESOURCES_NUMBER, // In choosing initial resources
    NOT_ENOUGH_SPACE,
    MARBLE_NOT_EXIST,
    NEED_RESOURCE_TO_PRODUCE, // For leader and base produce
    NOT_ACTIVATABLE_PRODUCTION,
    UNKNOWN_CONTROLLER_ERROR,
    UNKNOWN_MODEL_ERROR,
    UNKNOWN_ERROR,
    MALFORMED_MESSAGE,
    GAME_ALREADY_STARTED,
    INVALID_LOGIN_USERNAME;


    public static ErrorType fromValue(String value) {
        for (ErrorType errorType : values()) {
            if (errorType.name().equals(value)) {
                return errorType;
            }
        }
        throw new IllegalArgumentException("invalid string value passed: " + value);
    }

}
