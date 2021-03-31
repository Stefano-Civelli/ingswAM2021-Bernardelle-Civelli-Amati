package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.EndGameObserver;
import it.polimi.ingsw.model.track.EndGameObservable;
import java.util.*;
import java.util.stream.Collectors;

//non deve avere nessuno nella sua lista di observer nel caso in cui la partita sia multiplayer

public class DevelopCardDeck implements EndGameObservable {

   private final List<DevelopCard>[][] cardsCube;
   //observers are added to the Observerlist only for singleplayer game
   private final List<EndGameObserver> endGameObserverList;

   public DevelopCardDeck() {
      this.endGameObserverList = new ArrayList<>();
      cardsCube = new ArrayList[3][4];
      for (int i = 0; i < cardsCube.length; i++) {
         for (int j = 0; j < cardsCube[i].length; j++) {
            cardsCube[i][j] = new ArrayList<DevelopCard>(); //crea le 16 ArrayList
            //qua devo creare le carte e aggiungere 1 ad 1 tutte le carte al deck
            // cardsCube[i][j] = new DevelopCard( passare i parametri );

            //color order hardcodato: Green Blue Yellow Purple

         }
      }
//      HashMap<ResourceType, Integer> costMap = new HashMap<>();
//      HashMap<ResourceType, Integer> requirementMap = new HashMap<>();
//      HashMap<ResourceType, Integer> productMap = new HashMap<>();
//      CardFlag tempCardFlag = new CardFlag(1,DevelopCardColor.BLUE);
//      cardsCube[0][0].add(new DevelopCard(tempCardFlag, costMap, requirementMap, productMap));
//      costMap.clear();
//      requirementMap.clear();
//      productMap.clear();
//
//      cardsCube[0][0].add(new DevelopCard(tempCardFlag, costMap, requirementMap, productMap));
//
//      cardsCube[0][0].add(new DevelopCard(tempCardFlag, costMap, requirementMap, productMap));
//
//      cardsCube[0][0].add(new DevelopCard(tempCardFlag, costMap, requirementMap, productMap));

   }

   /**
    * returns the visible cards (the ones on top of the card square)
    *
    * @return matrix of DevelopCard
    */
   public DevelopCard[][] visibleCards() {
      DevelopCard[][] temp = new DevelopCard[4][4];
      for (int i = 0; i < cardsCube.length; i++) {
         for (int j = 0; j < cardsCube[i].length; j++) {
            temp[i][j] = cardsCube[i][j].get(cardsCube[i][j].size() - 1);
         }
      }
      return temp;

   }


//   // Play getCard sul deck
//   // io direi che lasciamo qua la classe e vediamo cosa ci dicono venerdi
//
//   public DevelopCard buyCard(int i, int j, int destinationSlot) throws CantBuyException { //diventerà void
//      //devo fare il controllo se soddisfo i requisiti qua
//      DevelopCard temp = new DevelopCard;
//      temp = cardsCube[i][j].get(cardsCube[i][j].size()-1);
//      try {
//         //Warehouse w = Game.getInstance().getCurrentPlayer().getWarehouse();
//         temp.buy(w,c,slotArray,slot);
//      }catch(){
//
//      }
//      cardsCube[i][j].remove(cardsCube[i][j].size()-1);
//      return temp;
//   }


   public List<DevelopCard> buyableCards(PlayerBoard playerBoard) {
      //forse va fatto con una lista
      return Arrays.stream(cardsCube).flatMap(Arrays::stream).flatMap(Collection::stream).filter(x -> x.isBuyable(playerBoard)).collect(Collectors.toList());
   }


   /**
    * method needed for the single player mode
    * it removes two cards of the lowes possible level from the top
    * @param color indicates the color of the cards to remove
    */
   public void RemoveTwoCards(DevelopCardColor color) {

      int column = color.getColumn();
      int k = 0;
      int numberOfCardsToRemove = 2;
      while (numberOfCardsToRemove > 0) {
         while (cardsCube[k][column].isEmpty()) {
            k++;
            //if there are no cards to remove simply return
            if (k == cardsCube.length) {
               //dovrebbe fare update di un observer che guarda se il game è finito
               return;
            }
         }
         cardsCube[k][column].remove(cardsCube[k][column].size() - 1);
         numberOfCardsToRemove--;
      }
   }

   public DevelopCard getCard(int row, int column) {
      return cardsCube[row][column].get(cardsCube[row][column].size() - 1);
   }

   public void removeCard(DevelopCard card) {
      int row = card.getCardFlag().getLevel();
      int column = card.getCardFlag().getColor().getColumn();

      cardsCube[row][column].remove(cardsCube[row][column].size() - 1);

      for (int i = 0; i < cardsCube.length; i++) {
         if (!cardsCube[i][column].isEmpty()) {
            return;
         }
      }
      notifyForEndGame();
   }

   @Override
   public void addToEndGameObserverList(EndGameObserver observerToAdd) {
      if (!endGameObserverList.contains(observerToAdd))
         endGameObserverList.add(observerToAdd);
   }

   @Override
   public void removeFromEndGameObserverList(EndGameObserver observerToRemove) {
      endGameObserverList.remove(observerToRemove);
   }

   @Override
   public void notifyForEndGame() {
      for (EndGameObserver x : endGameObserverList)
         x.update();
   }

}
