package it.polimi.ingsw.network.messages;

public enum ErrorType {
    INVALID_ACTION,
    INVALID_USERNAME,
    NOT_ENOUGH_RESOURCES,
    INVALID_LEADERCARD, // si usa sia se la leader non esiste sia se si cerca di rimuovere una leader attiva
    ALREADY_PRODUCED,
    NEGATIVE_QUANTITY,
    ABUSE_OF_FAITH,
    NOT_BUYABLE,
    INVALID_CARD_PLACEMENT,
    INVALID_ROW_OR_COLUMN, // Usato sia per le carte che per il mercato, magari dividere le eccezioni per poter creare due diversi errori
    WRONG_RESOURCES_NUMBER, // In choosing initial resources
    NOT_ENOUGH_SPACE,
    MARBLE_NOT_EXIST,
    NEED_RESOURCE_TO_PRODUCE, // For leader and base produce
    NOT_ACTIVATABLE_PRODUCTION,
    UNKNOWN_MODEL_ERROR,
    UNKNOWN_ERROR
}
