package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.ActionType;
import it.polimi.ingsw.controller.action.ChooseInitialResourcesAction;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.updatecontainers.TurnState;
import it.polimi.ingsw.view.ClientStateViewer;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.cli.Color;

import java.util.HashMap;

public class CliTurnManager implements ClientTurnManagerInterface {
  private Client client;
  private PhaseType currentPhase;
  private String currentPlayer;
  private ViewInterface view;
  private ClientStateViewer stateViewer;

  public CliTurnManager(Client client, ViewInterface view, ClientStateViewer stateViewer) {
    this.currentPhase = PhaseType.SETUP_CHOOSING_RESOURCES;
    this.client = client;
    this.view = view;
    this.stateViewer = stateViewer;
  }

  @Override
  public void currentPhasePrint(){
    switch(currentPhase){
      case SETUP_CHOOSING_RESOURCES:
        if(stateViewer.getPlayerTurnPosition()!=1) {
          view.displayDefaultCanvas(stateViewer.getUsername());
          System.out.println("\nYou need to choose " + stateViewer.getPlayerTurnPosition() / 2 + " resource(s) to add from the following");
          view.displayMarbleChoice();
          System.out.println("Which resource do you want to pick? (index)");
        }
        else {
          view.displayPlainCanvas();
          view.displayDefaultCanvas(stateViewer.getUsername());
          //FIXME serve per forza mandare un messaggio vuoto? è un po' brutto
          client.forwardAction(new ChooseInitialResourcesAction(new HashMap<>()));
        }
        break;
      case SETUP_DISCARDING_LEADERS:
        System.out.println("\nYou have to discard 2 leader cards.");
        System.out.println("That's your 4 leader cards: ");
        view.displayLeaderHand();
        System.out.println("You can have only 2 of them.\nWhich do you want to discard?");
        break;
      case SHOPPING:
        System.out.println("\nYou need to insert one of the following marbles you got from market: ");
        view.displayMarbleShopping();
        break;
      case SHOPPING_LEADER:
        view.displayChooseLeaderOnWhite();
        break;
      default: handleOtherPossiblePhases();
    }
  }


  public void handleOtherPossiblePhases() {
    view.displayDefaultCanvas(stateViewer.getUsername());
    System.out.println("\nYou can: ");

    for(ActionType p : currentPhase.getAvailableActions()) {
      switch (p) {
        case PRODUCE:
          System.out.println(" -" + Color.ANSI_RED.escape() + "P" + Color.RESET.escape() + "roduce");
          break;
        case SHOP_MARKET:
          System.out.println(" -" + Color.ANSI_RED.escape() + "S" + Color.RESET.escape() + "hop from market");
          break;
        case BUY_CARD:
          System.out.println(" -" + Color.ANSI_RED.escape() + "B" + Color.RESET.escape() + "uy a develop card");
          break;
        case DISCARD_LEADER:
            System.out.println(" -" + Color.ANSI_RED.escape() + "D" + Color.RESET.escape() + "iscard a leader card");
          break;
        case ACTIVATE_LEADER:
          System.out.println(" -" + Color.ANSI_RED.escape() + "A" + Color.RESET.escape() + "ctivate a leader card");
          break;
        case END_TURN:
          System.out.println(" -" + Color.ANSI_RED.escape() + "E" + Color.RESET.escape() + "nd the turn");
          break;
      }
    }
  }

  public boolean isValidInCurrenPhase(String input) {
    ActionType action = null;
    switch (input) {
      case "P": case "p":
        action = ActionType.PRODUCE;
        break;
      case "S": case "s":
        action = ActionType.SHOP_MARKET;
        break;
      case "B": case "b":
        action = ActionType.BUY_CARD;
        break;
      case "A": case "a":
        action = ActionType.ACTIVATE_LEADER;
        break;
      case "L": case "l":
        action = ActionType.LEADER_PRODUCE;
        break;
      case "E": case "e":
        action = ActionType.END_TURN;
        break;
      case "I": case "i":
        action = ActionType.INSERT_MARBLE;
        break;
      case "C": case "c":
        action = ActionType.CHOOSE_WHITE_LEADER;
        break;
      case "D": case "d":
        action = ActionType.DISCARD_LEADER;
        break;
      default: return false;
    }

    return currentPhase.isValid(action);
  }

  @Override
  public String getCurrentPlayer() {
    return currentPlayer;
  }

  public PhaseType getCurrentPhase() {
    return currentPhase;
  }

  @Override
  public void setCurrentPlayer(String currentPlayer) {
    this.currentPlayer = currentPlayer;
  }


  /**
   * set new phase and new currentPlayer
   *
   * @param newState the new Turn State
   */
  @Override
  public void setStateIsPlayerChanged(TurnState newState) {

    this.currentPhase = newState.getPhase(); //set new phase

    if (currentPlayer == null || !currentPlayer.equals(newState.getPlayer())) {
      this.currentPlayer = newState.getPlayer();
      if (client.getUsername().equals(this.currentPlayer))
        view.displayYourTurn(this.currentPlayer);
      else
        view.displayPlayerTurn(this.currentPlayer);
    }

    if(client.getUsername().equals(this.currentPlayer)) {
      this.currentPhasePrint();
    }
  }

}
