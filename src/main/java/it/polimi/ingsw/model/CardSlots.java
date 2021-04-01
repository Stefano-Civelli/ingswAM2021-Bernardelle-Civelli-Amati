package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelexceptions.InvalidCardPlacementException;

import java.util.ArrayList;
import java.util.List;

public class CardSlots {
  private final List<DevelopCard>[] developcards;
  private int totalCards;
  private final int numberOfCardSlots = 3;

  public CardSlots(){
    totalCards = 0;
    developcards = new ArrayList[numberOfCardSlots];
    for(int i=0; i<numberOfCardSlots; i++)
      developcards[i] = new ArrayList<DevelopCard>();
  }

  /**
   *
   * @return
   */
  public int calculateDevelopCardScore(){
    int score = 0;

    for(int i=0; i<numberOfCardSlots; i++)
      for(DevelopCard x : developcards[i])
        score += x.getVictoryPoints();

    return score;
  }

  /**
   *
   * @return
   */
  public DevelopCard returnTopCard(int slot) {
      return developcards[slot].get(developcards[slot].size()-1);
  }

  /**
   *
   * @param slot
   * @param developCard
   */
  public void addDevelopCard(int slot, DevelopCard developCard) throws InvalidCardPlacementException {
      int levelCardToAdd = developCard.getCardFlag().getLevel();

      if(levelCardToAdd == 1 && developcards[slot].isEmpty())
        developcards[slot].add(developCard);
      else
        throw new InvalidCardPlacementException();
      if(levelCardToAdd == developcards[slot].get(developcards[slot].size()-1).getCardFlag().getLevel()+1)
        developcards[slot].add(developCard);
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

    for(int i=0; i<numberOfCardSlots; i++)
      if (!developcards[i].isEmpty() && developcards[i].get(developcards[i].size()-1).isActivatable(playerboard))
        activatablecards.add(developcards[i].get(developcards[i].size()-1));

    return activatablecards;
  }

  public int getNumberOfCardSlots() {
    return numberOfCardSlots;
  }
}
