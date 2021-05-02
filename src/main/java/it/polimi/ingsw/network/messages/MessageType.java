package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.DevelopCardColor;

//fare attenzione perché per la CLI posso printare con lo stesso tipo (ad esempio LOGIN_FAILED) cose diverse (grazie al payload), pensare se puó essere fatto anche con GUI
public enum MessageType {

  PING,
  LOGIN,
  NUMBER_OF_PLAYERS,
  INVALID_NUMBER_OF_PLAYERS,
  LOGIN_SUCCESSFUL,
  WAIT_FOR_LOBBY_CREATION,
  LOBBY_CREATED,
  YOU_JOINED,
  OTHER_USER_JOINED,
  SERVER_DOWN,
  DISCONNECTED_SERVER_SIDE,
  RECONNECTED,
  DISCONNECTED,

  ACTION, // -> se ho questo tipo il payload del messaggio deve contenere il Type dell'azione e i correti field per poter fare le doAction

  GENERIC_MESSAGE,
  GAME_STARTED,
  INIT_TURN,
  END_TURN,

  MARKET_UPDATED,
  TRACK_UPDATED,
  DEVELOP_CARD_DECK_UPDATED,
  CARD_SLOT_UPDATE,
  WINNING_PLAYER,

  DECK_SETUP,
  MARKET_SETUP,
  LEADERCARD_SETUP,

  ERROR,
  QUIT,
  NEXT_TURN_STATE;

  public static MessageType fromValue(String value) {
    for (MessageType messageType : values()) {
      if (messageType.name().equals(value)) {
        return messageType;
      }
    }
    throw new IllegalArgumentException("invalid string value passed: " + value);
  }

}
