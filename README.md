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
| Basic rules |[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)|
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| Local Game | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#)|
| Multiple Games | [![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)|
| Disconnection Resilience | [![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)|


<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

## Test Coverage

**Coverage criteria: code lines.**

| Package | Coverage |
|:-----------------------|:------------------:|
| model | 80% |
| model: leadercard | 80% | 
| model: market | 87% | 
| model: singleplayer | 72% | 
| model: track | 92% | 
| controller | 56% | 




## COMMUNICATION PROTOCOL

See our **[communication Protocol wiki][communicationProtocol-link]**

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


[communicationProtocol-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Communication+Protocol
