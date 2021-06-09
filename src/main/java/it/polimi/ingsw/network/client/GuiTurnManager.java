package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.ChooseInitialResourcesAction;
import it.polimi.ingsw.controller.action.ChooseLeaderOnWhiteMarbleAction;
import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.TurnManager;
import it.polimi.ingsw.view.ClientStateViewer;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiTurnManager implements ClientTurnManagerInterface{

   private Client client;
   private PhaseType currentPhase;
   private String currentPlayer;
   private ViewInterface view;

   public GuiTurnManager(Client client, ViewInterface view) {
      this.currentPhase = PhaseType.SETUP_CHOOSING_RESOURCES;
      this.client = client;
      this.view = view;
   }

   @Override
   public void currentPhasePrint() {

      switch(currentPhase){
         case SETUP_CHOOSING_RESOURCES:
            view.displayMarbleChoice();
//            if(stateViewer.getPlayerTurnPosition()!=1) {
//               view.displayDefaultCanvas(stateViewer.getUsername());
//               System.out.println("\nYou need to choose " + stateViewer.getPlayerTurnPosition() / 2 + " resource(s) to add from the following");
//               view.displayMarbleChoice();
//               System.out.println("Which resource do you want to pick? (index)");
//            }
//            else {
//               view.displayPlainCanvas();
//               view.displayDefaultCanvas(stateViewer.getUsername());
//
//            }
            break;
         case SETUP_DISCARDING_LEADERS:
            view.displayLeaderHand();
//            System.out.println("\nYou have to discard 2 leader cards.");
//            System.out.println("That's your 4 leader cards: ");
//            view.displayLeaderHand();
//            System.out.println("You can have only 2 of them.\nWhich do you want to discard?");
//            break;
         case SHOPPING:
//            System.out.println("\nYou need to insert one of the following marbles you got from market: ");
//            view.displayMarbleShopping();
//            break;
         case SHOPPING_LEADER:
            view.displayChooseLeaderOnWhite();
            break;
      }


   }

   @Override
   public String getCurrentPlayer() {
      return this.currentPlayer;
   }

   @Override
   public void setCurrentPlayer(String currentPlayer) {
      this.currentPlayer = currentPlayer;
   }

   @Override
   public boolean setStateIsPlayerChanged(TurnManager.TurnState newState) {
      this.currentPhase = newState.getPhase(); //set new phase

      if(!currentPlayer.equals(newState.getPlayer())) {
         this.currentPlayer = newState.getPlayer();
         return true;
      }
      return false;
   }


   public void setPlayers(String stateUpdate) {
      //this.players = new ArrayList<>();
   }
}

