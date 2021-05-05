# MAESTRI DEL RINASCIMENTO - Progetto IngSW - 2021

## COMMUNICATION PROTOCOL

## Message Structure
- username (String) 
  - Client -> Server :  username of the message sender
  - Server -> Client :  
    - Model messages: username of the player that changed his model state
    - non-Model messages: username of the receiver (BroadCast on null)
- messageType (enum)
- payload (String)
  - if messageType == ACTION -> then payload is a JSON representation of an action class;
  - if messageType == MODEL_UPDATE -> then payload is a JSON representation of the objects (created as inner class) that contains the changes made to the model.


## Messages Table
Note: A primitive type as payload content (ex. int) simply means that it should be parsed to that type
| Source     | Message Type  | payload content    |  description
| :----:     |    :----:   |          :----:  |     :----  |
| Server     | LOGIN_SUCCESFUL       | null    | login procedure is succesfull
| Server     | RECONNECTED        | null     | client  successfully reconnects to the server
| Server     | ERROR        | [an error type](#Errors-Table)  | sent to client when an error occurs
| Server     |  NUMBER_OF_PLAYERS       | null     | tell the client that server is in a state where he expects a number of player
| Server/Client| PING        | null     | simple ping message to keep the socketTimeout from expiring
| Client     | NUMBER_OF_PLAYERS        |  the chosen number of players    | 
| Client     | LOGIN        |      | 
| Client     | ACTION        |      | 
| Client     | LOGIN        |      | 
| Client     | INIT_TURN |      | 
| Client     | END_TURN |      | 
| Client     | QUIT |      | 
| Server     | GAME_STARTED |      | 
| Server     | WAIT_FOR_LOBBY_CREATION |      | 
| Server     | LOBBY_CREATED |      | 
| Server     | YOU_JOINED |      | 
| Server     | OTHER_USER_JOINED |      | 
| Server     | SERVER_DOWN |      | 
| Server     | DISCONNECTED_SERVER_SIDE |      | 
| Server     | RECONNECTED |      | 
| Server     | DISCONNECTED |      | 
| Server     | NEXT_TURN_STATE |      | 
| Server     | GENERIC_MESSAGE |      | 
| Server     | MARKET_UPDATED |      | 
| Server     | VATICAN_REPORT |      | 
| Server     | TRACK_UPDATED |      | 
| Server     | WAREHOUSE_UPDATE |      | 
| Server     | DEVELOP_CARD_DECK_UPDATED |      | 
| Server     | CARD_SLOT_UPDATE |      | 
| Server     | CHEST_UPDATE |      | 
| Server     | ACTIVATED_LEADERCARD_UPDATE |      | 
| Server     | WINNING_PLAYER |      | 
| Server     | DECK_SETUP |      | 
| Server     | MARKET_SETUP |      | 
| Server     | LEADERCARD_SETUP |      | 


  
### Errors Table
| Error Type | description |
| :----: | :---- |
| MALFORMED_MESSAGE | 
| INVALID_LOGIN_USERNAME | username is empty or null 
| INVALID_NUMBER_OF_PLAYERS | the selected number of players is invalid
| GAME_ALREADY_STARTED | a player tries to LOGIN when the game is already started 
| INVALID_ACTION | action not correctly initialized 
| WRONG_ACTION | action can't be performed now 
| WRONG_PLAYER | not their turn 
| INVALID_USERNAME | username doesn't exist in game 
| WRONG_RESOURCES_NUMBER | In choosing initial resources 
| INVALID_LEADERCARD | leader card doesn't exist 
| INVALID_CARD_PLACEMENT | 
| INVALID_ROW_OR_COLUMN | row or column doesn't exist 
| INVALID_DEVELOP_CARD | develop card doesn't exist 
| MARBLE_NOT_EXIST | marble doesen't exist 
| CANNOT_DISCARD_ACTIVE_LEADER | tried to discard an active leader card 
| NEED_RESOURCE_TO_PRODUCE | resource to produce isn't specified 
| NOT_ACTIVATABLE_PRODUCTION | 
| ALREADY_PRODUCED | tried to activate production on something that has already produced in the same turn 
| NOT_ENOUGH_RESOURCES | there aren't enough resources to perform the action 
| NEGATIVE_QUANTITY | a specified quantity is negative 
| ABUSE_OF_FAITH | 
| NOT_BUYABLE | card isn't buyable for the player 
| NOT_ENOUGH_SPACE | 
| UNKNOWN_CONTROLLER_ERROR | 
| UNKNOWN_MODEL_ERROR | 
| UNKNOWN_ERROR | 
