package it.polimi.ingsw.network.messages;

//fare attenzione perché per la CLI posso printare con lo stesso tipo (ad esempio LOGIN_FAILED) cose diverse (grazie al payload), pensare se puó essere fatto anche con GUI
public enum MessageType {

  PING,
  LOGIN,
  NUMBER_OF_PLAYERS,
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
  VATICAN_REPORT,
  TRACK_UPDATED,
  WAREHOUSE_UPDATE,
  DEVELOP_CARD_DECK_UPDATED,
  CARD_SLOT_UPDATE,
  CHEST_UPDATE,
  ACTIVATED_LEADERCARD_UPDATE,
  WINNING_PLAYER,

  DECK_SETUP(true),
  MARKET_SETUP(true),
  LEADERCARD_SETUP(true),

  ERROR,
  QUIT,
  NEXT_TURN_STATE,
  CHAT,

  PLAYER_CONNECTION, // a player connected
  PLAYER_DISCONNECTION; // a player disconnected

  private final boolean isSetup;

  MessageType() {
    this.isSetup = false;
  }

  MessageType(boolean isSetup) {
    this.isSetup = isSetup;
  }

  public static MessageType fromValue(String value) {
    for (MessageType messageType : values()) {
      if (messageType.name().equals(value)) {
        return messageType;
      }
    }
    throw new IllegalArgumentException("invalid string value passed: " + value);
  }

  public boolean isSetup() {
    return isSetup;
  }
}
