package it.polimi.ingsw.view;


import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.messages.Message;

public interface ViewInterface extends LorenzoViewInterface {
  
  void setClientTurnManager(ClientTurnManagerInterface turnManager);
  void displayMarbleShopping();
  void displayLeaderHand();
  void displayMarbleChoice();
  void displayNetworkSetup();
  void displayLogin();
  void displaySetupFailure();
  void displayDisconnected();
  void displayFailedLogin();
  void displayLoginSuccessful(String username);
  void displayLobbyCreated();
  void displayOtherUserJoined(Message msg);
  void displayYouJoined();
  void displayPlayersNumberChoice();
  void displayWaiting();
  void displayServerDown();
  void displayGameAlreadyStarted();
  void displayReconnection();
  void displayGameStarted();
  void displayRecievedLeadercards();
  void displayMarket();
  void displayPlayerTurn(String player);
  void displayYourTurn(String username);
  void displayDefaultCanvas(String username);
  void displayPlainCanvas();
  void startingSetupUpdate();
  void displayChooseLeaderOnWhite();
  void displayFinalPhase();
  void displayProducingPhase();
  void displayShoppingPhase();
  void displayNotBuyable();
  void displayInvalidLeadercard();
  void displayCannotDiscardActiveLeader();
  void displayNotActivatableProduction();
  void displayAlreadyProduced();
  void displayNotEnoughResources();
  void displayInvalidCardPlacement();
  
}
