# MAESTRI DEL RINASCIMENTO - Progetto IngSW - 2021

## Project Description
The project consists of a Java version of the board game [Maestri del Rinascimento](https://www.craniocreations.it/prodotto/masters-of-renaissance/) by CranioCreations.

This repository includes:
* initial UML diagram;
* final UML diagram, automatically generated;
* the actual Game;
* all required resources to run the game.

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules |🟩 |
| Complete rules | 🟩 |
| Socket | 🟩 |
| CLI | 🟩 |
| GUI | 🟩 |
| Local Game | 🟩|

<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->


## COMMUNICATION PROTOCOL

## Message Structure
All messages share the following structure:

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
| Source     | Message Type  | payload content    |  description
| :----:     |    :----:   |          :----:  |     :----  |
| Server     | LOGIN_SUCCESFUL       | null    | login procedure is succesfull
| Server     | RECONNECTED        | null     | client  successfully reconnects to the server
| Server     | ERROR        | [an error type](#Errors-Tables)  | sent to client when an error occurs
| Server     |  NUMBER_OF_PLAYERS       | null     | tell the client that server is in a state where he expects a number of player
| Server     | GAME_STARTED | ordered list of the players | indicates to all the client that game has started
| Server     | WAIT_FOR_LOBBY_CREATION |      | indicates to the client to wait till the lobby has been created and then try again to login
| Server     | LOBBY_CREATED |      | tells the 1st client that lobby has been created succesfully
| Server     | YOU_JOINED | number of remaining required players | confirms to the client that he joined succesfully
| Server     | OTHER_USER_JOINED |   number of remaining required players    | tells every client that a new player joined
| Server     | SERVER_DOWN |      | 
| Server     | DISCONNECTED_SERVER_SIDE |      | 
| Server     | RECONNECTED |      | 
| Server     | DISCONNECTED |      | 
| Server     | NEXT_TURN_STATE |      |
| Server     | GENERIC_MESSAGE |      | 
| Server     | MARKET_UPDATED | MarketUpdate object | 
| Server     | VATICAN_REPORT | VaticanReport object | 
| Server     | TRACK_UPDATED | TrackUpdate object | tells every client the current position of the player's faith market 
| Server     | WAREHOUSE_UPDATE | WarehouseUpdate object | tells every client that the current player has added (removed) a resource in (from) the warehouse
| Server     | DEVELOP_CARD_DECK_UPDATED | DevelopCardDeckUpdate object | tells every client that a card has been removed 
| Server     | CARD_SLOT_UPDATE | CardSlotUpdate object | tells every client that the current player has added a DevelopCard in one of his slots
| Server     | CHEST_UPDATE | ChestUpdate object | tells every client that the current player has added (removed) a resource in (from) the chest 
| Server     | ACTIVATED_LEADERCARD_UPDATE | LeaderUpdate object | tells every client that the current player has activated a leader card 
| Server     | WINNING_PLAYER |      | 
| Server     | DECK_SETUP | DevelopCardDeckSetup object | tells all clients what is the beginning state of the deck
| Server     | MARKET_SETUP | MarketSetup object | 
| Server     | LEADERCARD_SETUP | LeaderSetup object | 
| Server/Client| PING        | null     | simple ping message to keep the socketTimeout from expiring
| Client     | NUMBER_OF_PLAYERS        |  the chosen number of players    | 
| Client     | LOGIN        |      | 
| Client     | ACTION        | [an Action](#Action-Table)     | 
| Client     | INIT_TURN |      | 
| Client     | END_TURN |      | 
| Client     | QUIT |      |


### Action Table
| Action Type | parameters |description |
| :----: | :---- | :---- |
| ActivateLeaderAction | `leaderCardID` | To activate the specified leaderCard
| BaseProductionAction |`resource1`, `resource2`, `product`| To activate the Base production
| BuyDevelopCardAction | `row`,  `column`, `cardSlotIndex`| 
| ChooseInitialResourcesAction |`resourcesMap`|
| ChooseLeaderOnWhiteMarbleAction |`leaderIndex`|
| DiscardInitialLeaderAction |`leaderCardID1`, `leaderCardID2`|
| DiscardLeaderAction |`leaderCardID`|
| EndTurnAction | | 
| InsertMarbleAction |`marbleIndex`|
| LeaderProductionAction |`leaderId`, `product`|
| ProductionAction |`cardIndex`|
| ShopMarketAction |`inRow`, `index`| inRow should be true if a row is pushed

### Errors Tables
#### Generic Errors
| Error Type | description |
| :----: | :---- |
| UNKNOWN_ERROR | 
| MALFORMED_MESSAGE | 
| INVALID_LOGIN_USERNAME | username is empty or null 
| INVALID_NUMBER_OF_PLAYERS | the selected number of players is invalid
| GAME_ALREADY_STARTED | a player tries to LOGIN when the game is already started 
#### Conroller Errors
| Error Type | description |
| :----: | :---- |
| UNKNOWN_CONTROLLER_ERROR |
| INVALID_ACTION | action not correctly initialized 
| WRONG_ACTION | action can't be performed now 
| WRONG_PLAYER | not their turn 
#### Model Errors
| Error Type | description |
| :----: | :---- |
| UNKNOWN_MODEL_ERROR | 
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


## API Reference

Here are some advices if you want to use the API provided by our server to create your own client.

Remember to also reference the [Communication Protocol](#COMMUNICATION-PROTOCOL) documentation for more 
informations on protocol structure.

#### Recieving a Message: Client <- Server
Upon reading a JSON from the socket, the client should parse it into a ` Message ` class.

For example:
  ```java
 while (true) {
          Message msg = messageParserFromJson(in.readLine());
          client.handleMessage(msg);
        }
```

You can than parse the object contained in the message in one of the provided
` updateContainers classes ` using the ` getPayloadByType() ` method.

For example:
 ```java
   WarehouseUpdate stateUpdate  = msg.getPayloadByType(WarehouseUpdate.class)
```

If you have trouble finding the right object you can simply call the ` getPayload() ` method to view
a JSON representation of the received Object or look at [Messages Table](#Messages-Table) documentation.

#### Sending a message: Client -> Server

In most cases, messages sent to Server contain an [Action](#Action-Table) as payload.

Here is an example of how to create a message countaining a specific action:
```java
   Action buyCardAction = new BuyDevelopCardAction(row, column, cardSlot);
```


