package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelObservables.CardSlotObservable;
import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;

import java.util.ArrayList;
import java.util.List;

public class CardSlots implements EndGameObservable, CardSlotObservable {
  private final List<List<DevelopCard>> developCards;
  private final int numberOfCardSlots = 3;
  private int totalCards;
  private final List<EndGameObserver> endGameObserverList;

  private transient ModelObserver controller = null;

  public CardSlots(){
    totalCards = 0;
    developCards = new ArrayList<>();
    for(int i = 0; i< numberOfCardSlots; i++)
      developCards.add(new ArrayList<>());
    endGameObserverList = new ArrayList<>();
  }

  public static class CardSlotUpdate {
    private final int devCardID;
    private final int slotNumber;

    public CardSlotUpdate(int devCardID, int slotNumber) {
      this.devCardID = devCardID;
      this.slotNumber = slotNumber;
    }

    public int getDevCardID() {
      return devCardID;
    }

    public int getSlotNumber() {
      return slotNumber;
    }
  }

  /**
   * Calculate the amount of victory points in CardSlots
   * @return the score of CardSlots (an int >= 0)
   */
  public int calculateDevelopCardScore(){
    int score = 0;

    for(List<DevelopCard> x : developCards)
      score += x.stream().map(DevelopCard::getVictoryPoints).reduce(0, Integer::sum);

    return score;
  }

  /**
   * Returns the card which is positioned on top of a slot
   * NOTE: if the slot is empty returns a DevelopCard with level 0
   *
   * @param slot, slot of which the caller wants to know the top card (int between 0 and {@code numberOfCardSlots})
   * @return the top card of a slot (the highest in level)
   */
  public DevelopCard returnTopCard(int slot) {
    if(developCards.get(slot).isEmpty())
       return new DevelopCard(new CardFlag(0,null), null, null, null, 0); //when returnTopCard is called on an empty slot I return a card with level equals to 0
    return developCards.get(slot).get(developCards.get(slot).size()-1);
  }

  /**
   * Add a DevelopCard in one of the slots
   * @param slot, slot in which the caller wants to put the card (int between 0 and numberOfCardSlots)
   * @param developCard, card to add
   */
  public void addDevelopCard(int slot, DevelopCard developCard) throws InvalidCardPlacementException, NullPointerException {
      int levelCardToAdd = developCard.getCardFlag().getLevel();

      if (slot<0 || slot>2)
        throw new IndexOutOfBoundsException("Slot need to be between 0 and " + (numberOfCardSlots-1));
      for(List<DevelopCard> d : developCards)
        if(d.contains(developCard))
          throw new InvalidCardPlacementException();

      if(levelCardToAdd == 1 && developCards.get(slot).isEmpty()) {
        developCards.get(slot).add(developCard);
        addedACardInACardSlot();
        notifyCardSlotUpdate(new CardSlotUpdate(developCard.getCardId(), slot));
        return;
      }
      if(!developCards.get(slot).isEmpty() && levelCardToAdd == developCards.get(slot).get(developCards.get(slot).size()-1).getCardFlag().getLevel()+1) {
        developCards.get(slot).add(developCard);
        addedACardInACardSlot();
        notifyCardSlotUpdate(new CardSlotUpdate(developCard.getCardId(), slot));
      }
      else
        throw new InvalidCardPlacementException();
  }

  /**
   * Returns the cards in CardSlots that can be used to produce
   * @param playerBoard of the player who wants to know which card can active
   * @return a list of activatable cards
   */
  public ArrayList<DevelopCard> activatableCards(InterfacePlayerBoard playerBoard) {
    ArrayList<DevelopCard> activatableCards = new ArrayList<>();

    for(List<DevelopCard> x : developCards)
      if (!x.isEmpty() && x.get(x.size()-1).isActivatable(playerBoard))
        activatableCards.add(x.get(x.size()-1));

    return activatableCards;
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

  public int numberOf(CardFlag cardFlag){
    int numberOf = 0;

    for(List<DevelopCard> x : developCards)
      numberOf += x.stream().filter( d -> d.getCardFlag().equals(cardFlag)).count();
    return numberOf;
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

  @Override
  public void notifyCardSlotUpdate(CardSlots.CardSlotUpdate msg) {
    if (controller != null)
      controller.cardSlotUpdate(msg);
  }

  public void setController(ModelObserver controller) {
    this.controller = controller;
  }
}
