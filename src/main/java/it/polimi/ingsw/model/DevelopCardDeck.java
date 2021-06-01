package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelObservables.DeckSetupObservable;
import it.polimi.ingsw.model.modelObservables.LorenzoDevDeckObservable;
import it.polimi.ingsw.model.modelObservables.ModelObservable;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.model.modelexceptions.InvalidDevelopCardException;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.util.*;
import java.util.stream.Collectors;


public class DevelopCardDeck implements EndGameObservable, ModelObservable, DeckSetupObservable, LorenzoDevDeckObservable {

   private final int NUMBER_OF_DECK_ROWS = 3;
   private final int NUMBER_OF_DECK_COLUMS = 4;

   @SuppressWarnings({"UnusedDeclaration", "MismatchedQueryAndUpdateOfCollection"}) // Because the field value is assigned using reflection
   private List<DevelopCard> developCardList;

   private transient List<DevelopCard>[][] cardsCube;

   private transient ModelObserver controller = null;

   //observers are added to the Observer list only for single player game.
   //So this Class should have an empty observer list if the game is multiplayer
   private final List<EndGameObserver> endGameObserverList = new ArrayList<>();

   /**
    * Default constructor because construction is handled by the cardParser method
    */
   public DevelopCardDeck() {}

   /**
    * this method is called by the cardParser method in GSON class to complete the setup process of this class
    * it creates the card matrix from the card list
    */
   @SuppressWarnings("unchecked")
   public void setupClass(){
      cardsCube = new ArrayList[NUMBER_OF_DECK_ROWS][NUMBER_OF_DECK_COLUMS];
      List<CardFlag> cardFlagList = developCardList.stream().map(DevelopCard::getCardFlag).distinct().collect(Collectors.toList());
      for (CardFlag cardFlag : cardFlagList) {
         List<DevelopCard> tempDevelopCardList = developCardList.stream()
                 .filter(x -> x.getCardFlag()
                         .equals(cardFlag))
                 .collect(Collectors.toList());
         cardsCube[cardFlag.getLevel()-1][cardFlag.getColor().getColumn()] = tempDevelopCardList;
      }

   }

   public void finalizeDeckSetup(ModelObserver controller){
      this.controller = controller;
      shuffleDeck(); // if you want to write tests that use the parsed Deck you need to move this call elsewhere
      notifyDeckSetup(GSON.getGsonBuilder().toJson( serializableIdDeck()));
   }


   /**
    * Shuffles the Deck
    */
   public void shuffleDeck(){
      for (List<DevelopCard>[] lists : cardsCube) {
         for (List<DevelopCard> list : lists) {
            Collections.shuffle(list);
         }
      }
   }

   //TODO probabilmente non serve questo metodo (o magari farlo private)
   /**
    * returns the visible cards (the ones on top of the card square)
    * @return matrix of DevelopCard
    */
   public ArrayList<DevelopCard> visibleCards() {
      ArrayList<DevelopCard> temp = new ArrayList<>();
      for (List<DevelopCard>[] lists : cardsCube) {
         for (List<DevelopCard> list : lists) {
            temp.add(list.get(list.size() - 1));
         }
      }
      return temp;
   }

   /**
    * Returns a list of the cards that the specified player can buy in a specific moment
    * @param playerBoard the player
    * @return the list of buyable cards
    */
   public List<DevelopCard> buyableCards(InterfacePlayerBoard playerBoard) {
      return visibleCards().stream()
              .filter(x -> x.isBuyable(playerBoard))
              .collect(Collectors.toList());
   }

   /**
    * method needed for the single player mode
    * Removes two cards of the lowes possible level and of the specified color from the top
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
               notifyForEndGame();
               //dovrebbe fare update di un observer che guarda se il game è finito
               return;
            }
         }
         cardsCube[k][column].remove(cardsCube[k][column].size() - 1);
         numberOfCardsToRemove--;
         notifyModelChange(GSON.getGsonBuilder().toJson( new Pair<>(k, column)));
      }
   }

   /**
    * returns the reference to a card contained in the Deck given the position row/column
    * NOTE: this method doesn't remove the card
    *
    * @param row the row of the card to return (start at 0). Row is related to the card level
    * @param column the column of the card to return (start at 0). Column is related to card the color
    * @return the card present at the given position
    * @throws InvalidDevelopCardException if the card position is invalid
    */
   public DevelopCard getCard(int row, int column) throws InvalidDevelopCardException {
      if(row<0 || row>2 || column<0 || column>3)
         throw new InvalidDevelopCardException("row is between 0 and " + (cardsCube.length-1) + " , column is between 0 and " + (cardsCube[0].length-1));

      return cardsCube[row][column].get(cardsCube[row][column].size() - 1);
   }

   /**
    * removes the specified card from the deck. Does nothing if the card is null
    * @param card the reference of the card to remove
    * @throws InvalidCardException if the given card is not a visible card
    */
   public void removeCard(DevelopCard card) throws InvalidCardException {
      if(card == null)
         return;
      int row = card.getCardFlag().getLevel() - 1;
      int column = card.getCardFlag().getColor().getColumn();

      try {
         if(getCard(row,column) != card)
            throw new InvalidCardException();
      } catch (InvalidDevelopCardException e) {
         e.printStackTrace();
      }
      cardsCube[row][column].remove(cardsCube[row][column].size() - 1);
      notifyModelChange(GSON.getGsonBuilder().toJson( new Pair<>(row, column)));

      //checks if the column where i removed a card is completly empty call the notifyForEndGame method
      for (List<DevelopCard>[] lists : cardsCube)
         if (!lists[column].isEmpty())
            return;

      notifyForEndGame();
   }

   @SuppressWarnings("unchecked")
   private List<Integer>[][] serializableIdDeck() {
      List<Integer>[][] idCube = new ArrayList[NUMBER_OF_DECK_ROWS][NUMBER_OF_DECK_COLUMS];
      for (int i=0; i<NUMBER_OF_DECK_ROWS; i++) {
         for (int j=0; j<NUMBER_OF_DECK_COLUMS; j++) {
            idCube[i][j] = cardsCube[i][j].stream().map(DevelopCard::getCardId).collect(Collectors.toList());
         }
      }

      return idCube;
   }

   public DevelopCard getCardFromId(int id) throws InvalidCardException {
      for(DevelopCard d : developCardList)
         if(d.getCardId() == id)
            return d;
      throw new InvalidCardException("there isn't a card with this id in the deck");
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

   @Override
   public void notifyModelChange(String msg) {
      if (controller != null)
         controller.devDeckUpdate(msg);
   }

   @Override
   public void notifyDeckSetup(String msg) {
      if (controller != null)
         controller.devDeckSetup(msg);
   }

   @Override
   public void notifyLorenzoDeckUpdate(String msg) {
      if (controller != null)
         controller.lorenzoDevDeckUpdate(msg);
   }
}
