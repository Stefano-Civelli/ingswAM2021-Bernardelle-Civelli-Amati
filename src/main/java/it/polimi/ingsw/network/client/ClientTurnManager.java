package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.ActionType;
import it.polimi.ingsw.controller.action.ChooseInitialResourcesAction;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientTurnManager {
  private Client client;
  private PhaseType currentPhase;
  private String currentPlayer;
  private Cli cli;

  public ClientTurnManager(Client client, Cli cli) {
    this.currentPhase = PhaseType.SETUP_CHOOSING_RESOURCES;
    this.client = client;
    this.cli = cli;
  }

  public void handleNextPossiblePhases() {
    for(ActionType p : currentPhase.getAvailableActions()) {
      if(!(currentPhase.equals(PhaseType.SETUP_CHOOSING_RESOURCES) && client.getPlayerTurnPosition()==1) || p==ActionType.INSERT_MARBLE || p==ActionType.CHOOSE_WHITE_LEADER)
        System.out.print("You need to ");
      else if(!currentPhase.isSetup())
        System.out.println("You can: ");
      switch (p) {
        case SETUP_CHOOSE_RESOURCES:
          if(client.getPlayerTurnPosition()!=1) {
            System.out.println("choose " + client.getPlayerTurnPosition() / 2 + " resource(s) to add from the following");
            cli.displayMarbleChoice();
            System.out.println("Which resource do you want to pick? (index)");
          }
          else {
            client.sendToServer(new Message(client.getUsername(), MessageType.ACTION, new ChooseInitialResourcesAction(new HashMap<>())));
          }
          break;
        case PRODUCE:
          System.out.println("-" + Color.ANSI_RED.escape() + "P" + Color.RESET.escape() + "roduce");
          break;
        case SHOP_MARKET:
          System.out.println("-" + Color.ANSI_RED.escape() + "S" + Color.RESET.escape() + "hop from market");
          break;
        case BUY_CARD:
          System.out.println("-" + Color.ANSI_RED.escape() + "B" + Color.RESET.escape() + "uy a develop card");
          break;
        case DISCARD_LEADER:
            System.out.println("-" + Color.ANSI_RED.escape() + "D" + Color.RESET.escape() + "iscard a leader card");
          break;
        case SETUP_DISCARD_LEADERS:
          System.out.println("discard 2 leader cards.");
          System.out.println("That's your 4 leader cards: ");
          cli.displayLeaderHand();
          System.out.println("You can have only 2 of them.\nWhich do you want to discard?");
          break;
        case ACTIVATE_LEADER:
          System.out.println("-" + Color.ANSI_RED.escape() + "A" + Color.RESET.escape() + "ctivate a leader card");
          break;
        case INSERT_MARBLE:
          System.out.print("insert one of the following marbles you got from market");
          //cli.displayMarbleChoice();
          //display delle marble con indice
          //rimuovere da temp
          //indice preso e mandato come action INSERT MARBLE
          break;
        case CHOOSE_WHITE_LEADER:
          System.out.print("use one of the 2 following leader to convert your white marble");
          //display delle leader con indice
          //indice preso e mandato come action CHOOSE_WHITE_LEADER
          break;
        case END_TURN:
          System.out.println("-" + Color.ANSI_RED.escape() + "E" + Color.RESET.escape() + "nd the turn");
          break;
      }
    }
  }

  public boolean validateInput(String input) {
    ActionType action = null;
    switch (input) {
      case "P":
        action = ActionType.PRODUCE;
        break;
      case "S":
        action = ActionType.SHOP_MARKET;
        break;
      case "B":
        action = ActionType.BUY_CARD;
        break;
      case "A":
        action = ActionType.ACTIVATE_LEADER;
        break;
      case "L":
        action = ActionType.LEADER_PRODUCE;
        break;
      case "E":
        action = ActionType.END_TURN;
        break;
      case "I":
        action = ActionType.INSERT_MARBLE;
        break;
      case "C":
        action = ActionType.CHOOSE_WHITE_LEADER;
        break;
      default: return false;
    }

    return currentPhase.isValid(action);
  }


  public void newPhase(PhaseType phase) {
    this.currentPhase = phase;
  }

  public void newCurrentPlayer(String currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public String getCurrentPlayer() {
    return currentPlayer;
  }

  public PhaseType getCurrentPhase() {
    return currentPhase;
  }

  public void setCurrentPlayer(String currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
}
