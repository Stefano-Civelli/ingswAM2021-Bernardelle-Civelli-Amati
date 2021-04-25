package it.polimi.ingsw.view;


import it.polimi.ingsw.network.messages.Message;

public interface ViewInterface {
  public void displaySetup();
  public void displayLogin();
  public void displaySetupFailure();
  public void displayDisconnected();
  public void displayFailedLogin(Message msg);
  public void displayLoginSuccessful();
  public void displayLobbyCreated();
  public void displayOtherUserJoined(Message msg);
  public void displayYouJoined();
  public void displayPlayersNumberChoice();
  public void displayWaiting();
}
