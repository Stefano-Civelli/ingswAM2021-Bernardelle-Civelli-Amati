package it.polimi.ingsw.network.messages;

//fare attenzione perché per la CLI posso printare con lo stesso tipo (ad esempio LOGIN_FAILED) cose diverse (grazie al payload), pensare se puó essere fatto anche con GUI
public enum MessageType {

  PING,
  LOGIN,
  NUMBER_OF_PLAYERS,
  LOGIN_FAILED,
  LOGIN_SUCCEEDED,
  LOBBY_CREATED,
  U_JOINED  ,
  OTHER_USER_JOINED,
  SERVER_DOWN,

  ACTION, // -> se ho questo tipo il payload del messaggio deve contenere il Type dell'azione e i correti field per poter fare le doAction

  GENERIC_MESSAGE,
  INIT_TURN,
  END_TURN,

  MARKET_UPDATED,
  TRACK_UPDATED,
  DEVELOP_CARD_DECK_UPDATED,
  WINNING_PLAYER;
}
