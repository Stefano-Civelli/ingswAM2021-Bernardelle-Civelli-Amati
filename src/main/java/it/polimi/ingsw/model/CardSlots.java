package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.EndGameObserver;
import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;
import it.polimi.ingsw.model.track.EndGameObservable;

import java.util.ArrayList;
import java.util.List;

public class CardSlots implements EndGameObservable {
  private final List<List<DevelopCard>> developCards;
  private final int numberOfCardSlots = 3;
  private int totalCards;
  private final List<EndGameObserver> endGameObserverList;


  public CardSlots(){
    totalCards = 0;
    developCards = new ArrayList<>();
    for(int i = 0; i< numberOfCardSlots; i++)
      developCards.add(new ArrayList<DevelopCard>());
    endGameObserverList = new ArrayList<>();
  }

  /**
   * Calculate the amount of victory points in CardSlots
   * @return the score of CardSlots (an int >= 0)
   */
  public int calculateDevelopCardScore(){
    int score = 0;

    for(List<DevelopCard> x : developCards)
      for(DevelopCard y : x)
        score += y.getVictoryPoints();

    return score;
  }

  /**
   * Returns the card which is positioned on top of a slot
   * @param slot, slot of which the caller wants to know the top card (int between 0 and numberOfCardSlots)
   * @return the top card of a slot (the highest in level)
   */
  public DevelopCard returnTopCard(int slot) {
    if(developCards.get(slot).isEmpty())
       return new DevelopCard(0); //when returnTopCard is called on an empty slot I return a card with level equals to 0
    return developCards.get(slot).get(developCards.get(slot).size()-1);
  }

  /**
   * Add a DevelopCard in one of the slots
   * @param slot, slot in which the caller wants to put the card (int between 0 and numberOfCardSlots)
   * @param developCard, card to add
   */
  public void addDevelopCard(int slot, DevelopCard developCard) throws InvalidCardPlacementException {
      int levelCardToAdd = developCard.getCardFlag().getLevel();

      for(List<DevelopCard> d : developCards)
        if(d.contains(developCard))
          throw new InvalidCardPlacementException();

      if(levelCardToAdd == 1 && developCards.get(slot).isEmpty()) {
        developCards.get(slot).add(developCard);
        addedACardInACardSlot();
        return;
      }
      if(!developCards.get(slot).isEmpty() && levelCardToAdd == developCards.get(slot).get(developCards.get(slot).size()-1).getCardFlag().getLevel()+1) {
        developCards.get(slot).add(developCard);
        addedACardInACardSlot();
      }
      else
        throw new InvalidCardPlacementException();
  }

  /**
   * Returns the cards in CardSlots that can be used to produce
   * @param playerBoard of the player who wants to know which card can active
   * @return a list of activatable cards
   */
  public ArrayList<DevelopCard> activatableCards(InterfacePlayerBoard playerBoard){
    ArrayList<DevelopCard> activatablecards = new ArrayList<>();

    for(List<DevelopCard> x : developCards)
      if (!x.isEmpty() && x.get(x.size()-1).isActivatable(playerBoard))
        activatablecards.add(x.get(x.size()-1));

    return activatablecards;
  }

  /**
   * Getter for the number of slots
   * @return the number of slots
   */
  public int getNumberOfCardSlots() {
    return numberOfCardSlots;
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
