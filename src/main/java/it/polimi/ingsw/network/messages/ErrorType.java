package it.polimi.ingsw.network.messages;

/**
 * Represents the possible type of error that can be sent to client
 */
public enum ErrorType {

    MALFORMED_MESSAGE,
    GAME_ALREADY_STARTED, // player tryes to LOGIN when the game is already started
    INVALID_LOGIN_USERNAME, // username is empty or null
    INVALID_NUMBER_OF_PLAYERS,
    INVALID_ACTION, // action not correctly initialized
    WRONG_ACTION, // action can't be performed now
    WRONG_PLAYER, // not their turn
    INVALID_USERNAME, // username doesn't exist in game
    WRONG_RESOURCES_NUMBER, // In choosing initial resources
    INVALID_LEADERCARD, // leader card doesn't exist
    INVALID_CARD_PLACEMENT, //
    INVALID_ROW_OR_COLUMN, // row or column doesn't exist
    INVALID_DEVELOP_CARD, // develop card doesn't exist
    MARBLE_NOT_EXIST, // marble doesen't exist
    CANNOT_DISCARD_ACTIVE_LEADER, // tried to discard an active leader card
    NEED_RESOURCE_TO_PRODUCE, // resource to produce isn't specified
    NOT_ACTIVATABLE_PRODUCTION, //
    ALREADY_PRODUCED, // tried to activate production on something that has already produced in the same turn
    NOT_ENOUGH_RESOURCES, // there aren't enough resources to perform the action
    NEGATIVE_QUANTITY, // a specified quantity is negative
    ABUSE_OF_FAITH, //
    NOT_BUYABLE, // card isn't buyable for the player
    NOT_ENOUGH_SPACE, //
    UNKNOWN_CONTROLLER_ERROR,
    UNKNOWN_MODEL_ERROR,
    MATCH_ALREADY_EXISTS,
    CANNOT_JOIN_MATCH,
    UNKNOWN_ERROR;

    /**
     * static factory method that constructs enum by string
     *
     * @param value string to create the enum
     * @return a new enumeration
     */
    public static ErrorType fromValue(String value) {
        for (ErrorType errorType : values()) {
            if (errorType.name().equals(value)) {
                return errorType;
            }
        }
        throw new IllegalArgumentException("invalid string value passed: " + value);
    }

}
