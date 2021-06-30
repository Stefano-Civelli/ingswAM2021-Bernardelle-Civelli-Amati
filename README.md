# MAESTRI DEL RINASCIMENTO - Progetto IngSW - 2021
<img src="https://user-images.githubusercontent.com/63519918/123807789-73b62680-d8f0-11eb-94cf-faca0ef8a6a3.PNG" width=200px height=115 px align="right" />

## Project Description
The project consists of a Java version of the board game [Maestri del Rinascimento](https://www.craniocreations.it/prodotto/masters-of-renaissance/) by CranioCreations.

This repository includes:
* initial UML diagram;
* final UML diagram, automatically generated;
* the actual Game;
* all required resources to run the game.

## How to play
Our game both supports local games and online.

- In order to play in local mode you will need to run a client (either CLI or GUI) and once the game asks for playing local or online you will need to go for the first option; <br>
- You can also play online but you will need a running server and at least 1 client. 
If you can't run a server on your own you can use ours. You can start playing simply connecting to this ip address `35.212.247.189` and port `6754`.

You can find more information on how to run the game [`here`][running-link]

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules |[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)|
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#) |
| Local Game | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#)|
| Multiple Games | [![GREEN](http://placehold.it/15/44bb44/44bb44)](#)|
| Disconnection Resilience | [![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)|

[![GREEN](http://placehold.it/15/44bb44/44bb44)](#) Implemented &nbsp;&nbsp;&nbsp;
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#) WIP
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




## Project Documentation
| **[Installation][installation-link]**     | **[Compiling][compiling-link]**     |    **[Running][running-link]**       | **[Javadocs][javadocs]** | **[Communication<br>Protocol][communicationProtocol-link]**
|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|
| [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123874078-d7b00d80-d937-11eb-921b-e6c2873731a0.png" width="80px"> </p>][installation-link] | [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123873521-e0ecaa80-d936-11eb-8769-d3ca38c0f745.png" width="90px"> </p>][compiling-link] | [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123870984-63736b00-d933-11eb-92ed-ad35f055eaa7.png" width="60px"> </p>][running-link] | [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123876084-0d0a2a80-d93b-11eb-81a3-d3fd39ef1bf6.png" width="90px"> </p>][javadocs] | [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123874920-227e5500-d939-11eb-9ecb-abcfb76a7625.png" width="80px"> </p>][communicationProtocol-link]

## Gameplay Screenshots

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/CLI_Screenshot.PNG" align="left" />
_cli_
<br><br>
<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/GUI_screenshot1.PNG" align="center" />
_gui login_
<br><br>
<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/GUI_Screenshot2.PNG" align="left" />
_gui game_
<br><br>



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
[installation-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Installation
[compiling-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Compiling
[running-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Running
[javadocs]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Javadoc
