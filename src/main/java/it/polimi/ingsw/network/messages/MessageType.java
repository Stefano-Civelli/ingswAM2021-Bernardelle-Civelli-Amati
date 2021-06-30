package it.polimi.ingsw.network.messages;

/**
 * Represents the possible type of messages
 */
public enum MessageType {

  PING,
  JOIN_MATCH,
  NUMBER_OF_PLAYERS,
  LOGIN_SUCCESSFUL,
  WAIT_FOR_LOBBY_CREATION,
  LOBBY_CREATED,
  YOU_JOINED,
  OTHER_USER_JOINED,
  SERVER_DOWN,
  RECONNECTED,
  DISCONNECTED,
  STARTING_GAME_SETUP,
  CREATE_MATCH,

  ACTION, // -> se ho questo tipo il payload del messaggio deve contenere il Type dell'azione e i correti field per poter fare le doAction

  GAME_STARTED,

  MARKET_UPDATED,
  VATICAN_REPORT,
  TEMP_CHEST_UPDATE,
  TRACK_UPDATED,
  WAREHOUSE_UPDATE,
  DEVELOP_CARD_DECK_UPDATED,
  CARD_SLOT_UPDATE,
  CHEST_UPDATE,
  CHEST_MERGED,
  ACTIVATED_LEADERCARD_UPDATE,
  DISCARDED_LEADERCARD,

  LORENZO_TRACK_UPDATE,
  LORENZO_SHUFFLE_UPDATE,
  LORENZO_DECK_UPDATE,

  DECK_SETUP(true),
  MARKET_SETUP(true),
  LEADERCARD_SETUP(true),

  ERROR,
  QUIT,
  NEXT_TURN_STATE,
  CHEAT,
  GAME_ENDED;


  private final boolean isSetup;

  MessageType() {
    this.isSetup = false;
  }

  MessageType(boolean isSetup) {
    this.isSetup = isSetup;
  }

  /**
   * static factory method that constructs enum by string
   *
   * @param value string to create the enum
   * @return a new enumeration
   */
  public static MessageType fromValue(String value) {
    for (MessageType messageType : values()) {
      if (messageType.name().equals(value)) {
        return messageType;
      }
    }
    throw new IllegalArgumentException("invalid string value passed: " + value);
  }

  public boolean isSetup() {
    return this.isSetup;
  }
}
