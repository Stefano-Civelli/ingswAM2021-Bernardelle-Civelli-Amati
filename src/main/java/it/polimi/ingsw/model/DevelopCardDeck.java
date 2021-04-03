package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.EndGameObserver;
import it.polimi.ingsw.model.track.EndGameObservable;

import java.util.*;
import java.util.stream.Collectors;

//non deve avere nessuno nella sua lista di observer nel caso in cui la partita sia multiplayer

public class DevelopCardDeck implements EndGameObservable {

   //metto prima nella lista per fare il parsing e poi costruisco la matrice (forse posso farlo nel costruttore dopo che JSON ha finito)
   private List<DevelopCard> developCardList;
   private List<DevelopCard>[][] cardsCube;
   //observers are added to the Observerlist only for singleplayer game
   private final List<EndGameObserver> endGameObserverList = new ArrayList<>();

   public DevelopCardDeck(){

      //here costruisco la matrice e ci dispongo la lista di carte
      //color order hardcodato: Green Blue Yellow Purple
   }

   //TODO CANCELLARE QURESTO METODO PERCHE' SERVE SOLO PER IL TESTING
   public DevelopCard getDevelopCard() {
      return developCardList.get(1);
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

   public List<DevelopCard> buyableCards(PlayerBoard playerBoard) {
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
