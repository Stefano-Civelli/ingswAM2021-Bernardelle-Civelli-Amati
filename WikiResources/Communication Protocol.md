# Communication Protocol

## Premise

The communication protocol used is **JSON** based so it is completly **language indipendent**.
Using the provided documentation you can write your own client for our application using your favourite language.

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
    - if messageType == MODEL_UPDATE -> then payload is a JSON representation of the objects that contains the changes made to the model;
    - if messageType == ERROR -> then payload is a JSON representation of the error enumeration.


## Communication Examples


### Checking nodes connection
When connection is established both server and client set a socket timeout. Then client and server keep sending each other periodical messages to keep the timeout from expiring.<br>
Note: this isn’t a request - reply protocol. Every message is standAlone.<br>

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/deliverables/Network%20Sequence%20Diagram/pingSequenceDiagram.png" align="center" />


### Player connection with match creation

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/deliverables/Network%20Sequence%20Diagram/loginFirstSequenceDiagram.png" align="center" />

<br>
Example of a login message with new match creation:

```json
{
   "username": "adelaide",
   "messageType": "CREATE_MATCH",
   "payload": "GameID"
}
```

<br>
Example of a message from server asking for number of players:

```json
{
   "username": "adelaide",
   "messageType": "NUMBER_OF_PLAYERS",
   "payload": null
}
```

<br>
Example of a message from client to specify the number of players:

```json
{
   "username": "adelaide",
   "messageType": "NUMBER_OF_PLAYERS",
   "payload": "3"
}
```

<br>
Example of a possible error message during login:

```json
{
   "username": null,
   "messageType": "ERROR",
   "payload": "INVALID_LOGIN_USERNAME"
}
```


### Player connection joining an existing match

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/deliverables/Network%20Sequence%20Diagram/loginOthersSequenceDiagram.png" align="center" />

<br>
Example of a login message to join an existing match:

```json
{
   "username": "adelaide",
   "messageType": "JOIN_MATCH",
   "payload": "GameID"
}
```

### Start game

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/deliverables/Network%20Sequence%20Diagram/startGameSequenceDiagram.png" align="center" />

### Ask to perform an action

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/deliverables/Network%20Sequence%20Diagram/actionSequenceDiagram.png" align="center" />

<br>
Example of a request to perform an action to buy a develop card:

```json
{
   "username": "adelaide",
   "messageType": "ACTION",
   "payload":
   {
      "type": "BUY_CARD",
      "row": "2",
      "column": "0",
      "cardSlotIndex": "1"
   }
}
```

<br>
Examples of possible updates messages after a buy card actions:

```json
{
   "username": "adelaide",
   "messageType": "DEVELOP_CARD_DECK_UPDATED",
   "payload":
   {
      "row": "2",
      "column": "0"
   }
}
```

```json
{
   "username": "adelaide",
   "messageType": "CARD_SLOT_UPDATE",
   "payload":
   {
   "SlotNumber": "2",
   "DevelopCardID": "D47"
   }
}
```

<br>
Example of a possible error message after a buy card actions:

```json
{
   "username": "adelaide",
   "messageType": "ERROR",
   "payload": "INVALID_DEVELOP_CARD"
}
```

### End game

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/deliverables/Network%20Sequence%20Diagram/endGameSequenceDiagram.png" align="center" />

## Messages Table

| Source     | Message Type  | payload content    |  description
| :----:     |    :----:   |          :----:  |     :----:  |
| Server     | LOGIN_SUCCESFUL       | null    | login procedure has been succesfull completed
| Server     | RECONNECTED  | null | client has been successfully reconnected to server
| Server     | DISCONNECTED   |   player's username   | inform clients that a player has disconnected
| Server     | TEMP_CHEST_UPDATE  | null | tells every client that the current player has added a resource in the temp chest
| Server     | CHEST_MERGED  |  null  | needed to merge temporary chest with chest
| Server     | DISCARDED_LEADERCARD   |  LeaderUpdate object    | notify the client that a player has discarded a leadercard
| Server     | LORENZO_TRACK_UPDATE   |    TrackUpdate object     | notify client that Lorenzo has moved his faith marker
| Server     | LORENZO_SHUFFLE_UPDATE   |  null   | notify client that Lorenzo has shuffled the token stack
| Server     | LORENZO_DECK_UPDATE   |   DevelopCardDeckUpdate object    | notify client that Lorenzo has drawn 2 cards from the deck
| Server     | ERROR  | [`an error type`](#Errors-Tables)  | notifies the client that an error occured
| Server     | NUMBER_OF_PLAYERS       | null     | tell the client that server is in a state where he expects the number of players
| Server     | GAME_STARTED | ordered List\<String\> of the players' usernames | indicates to all clients that game has started
| Server     | WAIT_FOR_LOBBY_CREATION | a String containing the message description | indicates to the client that there is another player creating the lobby
| Server     | LOBBY_CREATED |   null   | tells the 1st client that lobby has been created succesfully
| Server     | YOU_JOINED | number of remaining required players (int) | confirms to the client that he joined the lobby succesfully
| Server     | OTHER_USER_JOINED |   number of remaining required players (int)   | tells every client that a new player joined
| Server     | SERVER_DOWN |  null    | notifies the client that the server is crashed
| Server     | NEXT_TURN_STATE | TurnState Object | updates the client about the new Turn state
| Server     | MARKET_UPDATED | MarketUpdate object | tells every client that the current player has modified the market and how
| Server     | VATICAN_REPORT | VaticanReport object | tells every client that a vatican card has flipped
| Server     | TRACK_UPDATED | TrackUpdate object | tells every client that the position of the current player has changed and how
| Server     | WAREHOUSE_UPDATE | WarehouseUpdate object | tells every client that the current player has added (removed) a resource in (from) the warehouse
| Server     | DEVELOP_CARD_DECK_UPDATED | DevelopCardDeckUpdate object | tells every client that a card has been removed
| Server     | CARD_SLOT_UPDATE | CardSlotUpdate object | tells every client that the current player has added a DevelopCard in one of his slots
| Server     | CHEST_UPDATE | ChestUpdate object | tells every client that the current player has added (removed) a resource in (from) the chest
| Server     | ACTIVATED_LEADERCARD_UPDATE | LeaderUpdate object | tells every client that the current player has activated a leader card
| Server     | GAME_ENDED | username and score of the winning player | tells every client who's the winner
| Server     | DECK_SETUP | DevelopCardDeckSetup object | updates the client about the initial state of the deck
| Server     | MARKET_SETUP | MarketSetup object | updates the client about the initial state of the market
| Server     | LEADERCARD_SETUP | LeaderSetup object | updates the client about his initial Leadercards
| Server/Client| PING        | null     | simple ping message to keep the socketTimeout from expiring
| Client     | NUMBER_OF_PLAYERS        |  the chosen number of players    |
| Client     | CREATE_MATCH | the match ID     | sent by client to create a new match
| Client     | JOIN_MATCH |  the match ID    | sent by client to join an existing match
| Client     | ACTION        | [`an Action`](#Action-Table)     | this message contains an Action performed by the client as payload.
| Client     | QUIT |  null  | sent by the client to the server to QUIT the game


### Action Table
| Action Type | parameters |description |
| :----: | :----: | :----: |
| ActivateLeaderAction | `leaderCardID` | activate the specified leaderCard
| BaseProductionAction |`resource1`, `resource2`, `product`| activate the Base production
| BuyDevelopCardAction | `row`,  `column`, `cardSlotIndex`| buy a developCard
| ChooseInitialResourcesAction |`resourcesMap`| choose resources at the beginning of the game
| ChooseLeaderOnWhiteMarbleAction |`leaderID`| choose which leadercard to activate to convert the white marble
| DiscardInitialLeaderAction |`leaderCardID1`, `leaderCardID2`| choose the 2 leadercards to discard at the beginning of the game
| DiscardLeaderAction |`leaderCardID`| discard a leader during the game
| EndTurnAction | | to end the player turn
| InsertMarbleAction |`marbleIndex`| insert a marble to the WareHouse
| LeaderProductionAction |`leaderId`, `product`| activate the production ability on a leadercard
| ProductionAction |`cardIndex`| activate production in a slot
| ShopMarketAction |`inRow`, `index`| select a market row or column. NOTE: inRow should be true if a row is pushed

### Errors Tables
#### Generic Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_ERROR |
| MALFORMED_MESSAGE | the server has recived a malformed Json so he cannot deserialize it into an action
| INVALID_LOGIN_USERNAME | username is empty or null
| INVALID_NUMBER_OF_PLAYERS | the selected number of players is invalid
| GAME_ALREADY_STARTED | a player tries to LOGIN when the game is already started
#### Conroller Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_CONTROLLER_ERROR |
| INVALID_ACTION | action not correctly initialized
| WRONG_ACTION | action can't be performed now
| WRONG_PLAYER | not their turn
#### Model Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_MODEL_ERROR |
| INVALID_USERNAME | in this match this username is already taken
| WRONG_RESOURCES_NUMBER | the number of the specified resources and the number of resources that belong to the player are different
| INVALID_LEADERCARD | leader card doesn't exist
| INVALID_CARD_PLACEMENT | the card cannot be placed in the specified slot or the slot doesn't exists
| INVALID_ROW_OR_COLUMN | row or column doesn't exist
| INVALID_DEVELOP_CARD | develop card doesn't exist
| MARBLE_NOT_EXIST | marble doesen't exist
| CANNOT_DISCARD_ACTIVE_LEADER | tried to discard an active leader card
| NEED_RESOURCE_TO_PRODUCE | resource to produce isn't specified
| NOT_ACTIVATABLE_PRODUCTION | the specified develop card can't produce in this phase of the game
| ALREADY_PRODUCED | tried to activate production on something that has already produced in the same turn
| NOT_ENOUGH_RESOURCES | there aren't enough resources to perform the action
| NEGATIVE_QUANTITY | a specified quantity is negative
| ABUSE_OF_FAITH | the player is trying to produce faith or consume it
| NOT_BUYABLE | the player can't buy the card he asked for
| NOT_ENOUGH_SPACE | the player has not enough space to contain these resources

<br>

## Write your own client

Here are some advices if you want to use the API provided by our server to create your own client in java using some of our classes.

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
   WarehouseUpdate stateUpdate  = msg.getPayloadByType(WarehouseUpdate.class);
```

If you have trouble finding the right object you can simply call the ` getPayload() ` method to view
a JSON representation of the received Object or look at [Messages Table](#Messages-Table) documentation.

#### Sending a message: Client -> Server

In most cases, messages sent to Server contain an [Action](#Action-Table) as payload.

Here is an example of how to create a message countaining a specific action:
```java
   Action buyCardAction = new BuyDevelopCardAction(row, column, cardSlot);
```


[communicationProtocolPDF-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/CommunicationProtocol.pdf