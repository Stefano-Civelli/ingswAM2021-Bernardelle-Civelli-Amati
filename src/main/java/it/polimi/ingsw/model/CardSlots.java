package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.EndGameObserver;
import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;
import it.polimi.ingsw.modeltest.tracktest.EndGameObservable;

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
   *
   * @return
   */
  public int calculateDevelopCardScore(){
    int score = 0;

    for(List<DevelopCard> x : developcards)
      for(DevelopCard y : x)
        score += y.getVictoryPoints();

    return score;
  }

  /**
   *
   * @return
   */
  public DevelopCard returnTopCard(int slot) {
    return developcards.get(slot).get(developcards.get(slot).size()-1);
  }

  /**
   *
   * @param slot
   * @param developCard
   */
  public void addDevelopCard(int slot, DevelopCard developCard) throws InvalidCardPlacementException {
      int levelCardToAdd = developCard.getCardFlag().getLevel();

      if(levelCardToAdd == 1 && developcards.get(slot).isEmpty())
        developcards.get(slot).add(developCard);
      else
        throw new InvalidCardPlacementException();
      if(levelCardToAdd == developcards.get(slot).get(developcards.get(slot).size()-1).getCardFlag().getLevel()+1)
        developcards.get(slot).add(developCard);
      else
        throw new InvalidCardPlacementException();
  }

  /**
   *
   * @param playerboard
   * @return
   */
  public ArrayList<DevelopCard> activatableCards(PlayerBoard playerboard){
    ArrayList<DevelopCard> activatablecards = new ArrayList<>();

    for(List<DevelopCard> x : developcards)
      if (!x.isEmpty() && x.get(x.size()-1).isActivatable(playerboard))
        activatablecards.add(x.get(x.size()-1));

    return activatablecards;
  }

  public int getNumberOfCardSlots() {
    return numberofcardslots;
  }

  public void addedACardInACardSlot(){
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
