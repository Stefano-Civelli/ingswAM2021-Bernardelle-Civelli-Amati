package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.ActionType;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.view.cli.Color;

import java.util.List;

public class ClientTurnManager {
  private Client client;
  private PhaseType currentPhase;
  private String currentPlayer;


  public void handleNextPossiblePhases() {
    for(ActionType p : currentPhase.getAvailableActions()) {
      if(currentPhase.isSetup() || p==ActionType.INSERT_MARBLE || p==ActionType.CHOOSE_WHITE_LEADER)
        System.out.print("You need to ");
      else
        System.out.println("You can: ");
      switch (p) {
        case CHOSE_RESOURCES:
          if(client.getPlayerTurnPosition()!=1)
            System.out.print("choose " + client.getPlayerTurnPosition()/2 + " resource(s) to add");
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
          if(currentPhase.isSetup())
            System.out.println("discard 2 leader cards");
          else
            System.out.println("-" + Color.ANSI_RED.escape() + "D" + Color.RESET.escape() + "iscard a leader card");
          break;
        case ACTIVATE_LEADER:
          System.out.println("-" + Color.ANSI_RED.escape() + "A" + Color.RESET.escape() + "ctivate a leader card");
          break;
        case INSERT_MARBLE:
          System.out.print("insert one of the following marbles you got from market");
          //display delle marble con indice
          //rimuovere da temp
          //indice preso e mandato come action INSERT MARBLE
          break;
        case CHOOSE_WHITE_LEADER:
          System.out.print("use one of the 2 following leader to convert your white marble");
          break;
        case END_TURN:
          System.out.println("-" + Color.ANSI_RED.escape() + "E" + Color.RESET.escape() + "nd the turn");
          break;
      }
    }
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

}
