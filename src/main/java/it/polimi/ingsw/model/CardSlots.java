package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.EndGameObserver;
import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;
import it.polimi.ingsw.model.track.EndGameObservable;

import java.util.ArrayList;
import java.util.List;

public class CardSlots implements EndGameObservable {
  private final List<List<DevelopCard>> developcards;
  private final int numberofcardslots = 3;
  private int totalCards;
  private final List<EndGameObserver> endGameObserverList;


  public CardSlots(){
    totalCards = 0;
    developcards = new ArrayList<>();
    for(int i=0; i<numberofcardslots; i++)
      developcards.add(new ArrayList<DevelopCard>());
    endGameObserverList = new ArrayList<>();
  }

  /**
   * calculate the amount of victory points of the DevelopCard in CardSlots
   * @return the score of CardSlots
   */
  public int calculateDevelopCardScore(){
    int score = 0;

    for(List<DevelopCard> x : developcards)
      for(DevelopCard y : x)
        score += y.getVictoryPoints();

    return score;
  }

  /**
   * find the card that is positioned in the last position of a slot
   * @param slot, slot of which the caller wants to know the top card
   * @return the last card of a slot (the highest in level)
   */
  public DevelopCard returnTopCard(int slot) {
    if(developcards.get(slot).isEmpty())
       return new DevelopCard(0);
    return developcards.get(slot).get(developcards.get(slot).size()-1);
  }

  /**
   * add a DevelopCard in one of the slots if that is possible
   * @param slot, slot in which the caller wants to put the card
   * @param developCard, card to add
   */
  public void addDevelopCard(int slot, DevelopCard developCard) throws InvalidCardPlacementException {
      int levelCardToAdd = developCard.getCardFlag().getLevel();

      for(List<DevelopCard> d : developcards)
        if(d.contains(developCard))
          throw new InvalidCardPlacementException();

      if(levelCardToAdd == 1 && developcards.get(slot).isEmpty()) {
        developcards.get(slot).add(developCard);
        addedACardInACardSlot();
        return;
      }
      if(levelCardToAdd == developcards.get(slot).get(developcards.get(slot).size()-1).getCardFlag().getLevel()+1) {
        developcards.get(slot).add(developCard);
        addedACardInACardSlot();
      }
      else
        throw new InvalidCardPlacementException();
  }

  /**
   * Let the player knows which are the cards in CardSlots that can be used to produce
   * @param playerboard of the player who wants to know which card can active
   * @return a list of activatable cards, the one the player can use to produce
   */
  public ArrayList<DevelopCard> activatableCards(PlayerBoard playerboard){
    ArrayList<DevelopCard> activatablecards = new ArrayList<>();

    for(List<DevelopCard> x : developcards)
      if (!x.isEmpty() && x.get(x.size()-1).isActivatable(playerboard))
        activatablecards.add(x.get(x.size()-1));

    return activatablecards;
  }

  /**
   * getter for the number of slots
   * @return the number of slots
   */
  public int getNumberOfCardSlots() {
    return numberofcardslots;
  }

  /**
   * add 1 to card's counter
   */
  private void addedACardInACardSlot(){
    totalCards++;
    if (totalCards == 7)
      notifyForEndGame();
  }

  @Override
  public void addToEndGameObserverList(EndGameObserver observerToAdd) {
    if(!endGameObserverList.contains(observerToAdd))
      endGameObserverList.add(observerToAdd);
  }

  @Override
  public void removeFromEndGameObserverList(EndGameObserver observerToRemove) {
    endGameObserverList.remove(observerToRemove);
  }

  @Override
  public void notifyForEndGame() {
    for(EndGameObserver x : endGameObserverList)
      x.update();
  }
}
