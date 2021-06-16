package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.PhaseType;
import it.polimi.ingsw.model.updateContainers.TurnState;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

public class GuiTurnManager implements ClientTurnManagerInterface{

   private final Client client;
   private PhaseType currentPhase;
   private String currentPlayer;
   private final ViewInterface view;

   public GuiTurnManager(Client client, ViewInterface view) {

      this.currentPhase = PhaseType.SETUP_CHOOSING_RESOURCES;
      this.client = client;
      this.view = view;
   }

   @Override
   public void currentPhasePrint() {

      switch(currentPhase){
         case INITIAL:
            view.displayYourTurn(this.currentPlayer); // it's necessary in singleplayer, in multiplayer it's repeated
            break;
         case SETUP_CHOOSING_RESOURCES:
            view.displayMarbleChoice();
            break;
         case SETUP_DISCARDING_LEADERS:
            view.displayLeaderHand();
            break;
         case SHOPPING:
            view.displayMarbleShopping();
            break;
         case PRODUCING:
            view.displayProducingPhase();
            break;
         case SHOPPING_LEADER:
            view.displayChooseLeaderOnWhite();
            break;
         case FINAL:
            view.displayFinalPhase();
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


   public void setPlayers(List<String> stateUpdate) {
      //this.players = new ArrayList<>();
   }

}

