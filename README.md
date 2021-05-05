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
| Source      | Message Type  | payload content    |  description
| :----:        |    :----:   |          :----:  |     :----  |
| Server     | LOGIN_SUCCESFUL       | null    | sent to client when login procedure is succesfull
| Server     | RECONNECTED        | null     | sent to client when he successfully reconnects to the server
| Server     | ERROR        | [an error type](#ERRORs-Description)     | sent to client when an error occurs


### Errors Table
| Error Type | description |
| :----: | :---- |
| MALFORMED_MESSAGE | 
| INVALID_LOGIN_USERNAME | if username is empty or null 
| GAME_ALREADY_STARTED | if a player tryes to LOGIN when the game is already started 
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
