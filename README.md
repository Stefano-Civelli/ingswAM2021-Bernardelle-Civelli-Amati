# MAESTRI DEL RINASCIMENTO - Progetto IngSW - 2021
<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/Maestri_Logo.PNG" width=200px height=115 px align="right" />

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
| **[Running][running-link]**       | **[Javadocs][javadocs]** | **[Communication<br>Protocol][communicationProtocol-link]**
|-------------------------------------|-------------------------------------|-------------------------------------|
| [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123870984-63736b00-d933-11eb-92ed-ad35f055eaa7.png" width="60px"> </p>][running-link] | [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123876084-0d0a2a80-d93b-11eb-81a3-d3fd39ef1bf6.png" width="90px"> </p>][javadocs] | [<p align="center"> <img src="https://user-images.githubusercontent.com/62955439/123874920-227e5500-d939-11eb-9ecb-abcfb76a7625.png" width="80px"> </p>][communicationProtocol-link]

## Gameplay Screenshots

<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/CLI_Screenshot.PNG" align="center" />
<br><br>
<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/GUI_screenshot1.PNG" align="center" />
<br><br>
<img src="https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/blob/main/WikiResources/GUI_Screenshot2.PNG" align="center" />
<br><br>



[communicationProtocol-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Communication+Protocol
[running-link]: https://github.com/Stefano-Civelli/ingswAM2021-Bernardelle-Civelli-Amati/wiki/Running
[javadocs]: https://stefano-civelli.github.io/MoR-Javadoc/
