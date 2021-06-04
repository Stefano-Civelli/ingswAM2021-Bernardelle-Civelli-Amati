package it.polimi.ingsw.view;


import it.polimi.ingsw.network.client.CliTurnManager;
import it.polimi.ingsw.network.client.ClientTurnManagerInterface;
import it.polimi.ingsw.network.messages.Message;

public interface ViewInterface extends LorenzoViewInterface {
  public void setClientTurnManager(ClientTurnManagerInterface turnManager);
  public void displayMarbleShopping();
  public void displayLeaderHand();
  public void displayMarbleChoice();
  public void displayNetworkSetup();
  public void displayLogin();
  public void displaySetupFailure();
  public void displayDisconnected();
  public void displayFailedLogin();
  public void displayLoginSuccessful(String username);
  public void displayLobbyCreated();
  public void displayOtherUserJoined(Message msg);
  public void displayYouJoined();
  public void displayPlayersNumberChoice();
  public void displayWaiting();
  public void displayServerDown();
  public void displayGameAlreadyStarted();
  public void displayReconnection();
  public void displayGameStarted();
  public void displayRecievedLeadercards();
  public void displayMarket();
  public void displayPlayerTurn(String player);
  public void displayYourTurn(String username);
  public void displayDefaultCanvas(String username);
  public void displayGameEnded(String payload);
  public void displayPlainCanvas();
  public void startingSetupUpdate();
}
