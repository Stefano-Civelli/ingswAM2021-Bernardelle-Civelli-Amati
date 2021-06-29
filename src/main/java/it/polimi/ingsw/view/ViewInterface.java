package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.messages.Message;

public interface ViewInterface extends LorenzoViewInterface {

  void setClientTurnManager(ClientTurnManagerInterface turnManager);

  /**
   * Displays the marbles player got from the market and let him insert one
   */
  void displayMarbleShopping();

  /**
   * Displays the LeaderCard of this client that have not been activated yet
   */
  void displayLeaderHand();

  /**
   * Displays the four marbles associated to a resource
   */
  void displayMarbleChoice();

  /**
   * Display the network setup options.
   * Asks for Ip address and port of the server
   */
  void displayNetworkSetup();

  /**
   * Displays the login setup.
   * Asks for username, if the player want to join or create a match and the room name
   */
  void displayLogin();

  /**
   * Displays that the server can not be reached
   */
  void displaySetupFailure();

  void displayDisconnected();

  /**
   * Displays that the username is already taken
   */
  void displayFailedLogin();

  /**
   * Displays that login setup has been operated correctly
   * @param username
   */
  void displayLoginSuccessful(String username);

  /**
   * Displays that the lobby has been successfully created
   */
  void displayLobbyCreated();

  /**
   * Displays that another player joined the lobby
   * @param msg, message containing all the information to display
   */
  void displayOtherUserJoined(Message msg);

  /**
   * Displays that you have successfully joined the lobby
   */
  void displayYouJoined();

  /**
   * Displays the choice of the number of players
   */
  void displayPlayersNumberChoice();

  //non serve piu
  void displayWaiting();

  /**
   * Displays that the server has crashed
   */
  void displayServerDown();

  /**
   * Displays that the player can't join the game because it's already started
   */
  void displayGameAlreadyStarted();

  //non serve piu
  void displayReconnection();

  /**
   * Displays that the game is started
   */
  void displayGameStarted();

  //booooo
  void displayRecievedLeadercards();

  void displayMarket();

  /**
   * Displays that it's the turn of the player whose name is the one passed as parameter
   * @param player, player's username
   */
  void displayPlayerTurn(String player);

  /**
   * Displays to the player whose name is the one passed as parameter that is it's turn
   * @param username, player's username
   */
  void displayYourTurn(String username);

  /**
   * Displays the default canvas of the player whose name is the one passed as parameter
   * @param username, username of the player
   */
  void displayDefaultCanvas(String username);

  /**
   * Displays a plain canvas
   */
  void displayPlainCanvas();

  /**
   * Starts the setup of the game
   */
  void startingSetupUpdate();

  /**
   * Displays the choice of the LeaderCard in case the marble to insert is white
   */
  void displayChooseLeaderOnWhite();

  /**
   * Enable the only actions that can be done in Final Phase
   */
  void displayFinalPhase();

  /**
   * Enable the only actions that can be done in Production Phase
   */
  void displayProducingPhase();

  void displayShoppingPhase();

  /**
   * Displays that the player can't buy the card he wanted because he hasn't enough resources
   */
  void displayNotBuyable();

  /**
   * Displays that the player has inserted a LeaderCard that is not valid
   */
  void displayInvalidLeadercard();

  /**
   * Displays that the deletion of the leader the player wanted to discard can't be done because it's active
   */
  void displayCannotDiscardActiveLeader();

  /**
   * Displays that the production on a certain card can't be done because resources are not enough
   */
  void displayNotActivatableProduction();

  /**
   * Displays that the production on a certain card can't be done because it as already been used in this turn
   */
  void displayAlreadyProduced();

  /**
   * Displays that the action can't be performed because resources are not enough
   */
  void displayNotEnoughResources();

  /**
   * Displays that a card can't be placed in the slot the player wanted to
   */
  void displayInvalidCardPlacement();

  /**
   * Displays that the name of the match the player tried to create is occupied by another match
   */
  void displayMatchAlreadyExist();

  /**
   * Displays that the player cannot join the match they tried to:
   * i.e. the match doesn't exist, the match is full or the match is already started
   */
  void displayCannotJoinMatch();
}
