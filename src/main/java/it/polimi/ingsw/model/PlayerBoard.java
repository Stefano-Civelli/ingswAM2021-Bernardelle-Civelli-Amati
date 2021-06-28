package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.model.modelobservables.ActivatedLeaderObservable;
import it.polimi.ingsw.model.modelobservables.LeaderDiscardObservable;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.model.updatecontainers.LeaderUpdate;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.io.IOException;
import java.util.*;

/**
 * Represents a player board containing all the element of the game that regards a single player
 */
public class PlayerBoard implements InterfacePlayerBoard, MoveForwardObservable, LeaderDiscardObservable, ActivatedLeaderObservable {

   private final String username;
   private final CardSlots cardSlots;
   private final Warehouse warehouse;
   private final List<LeaderCard> leaderCards;
   private final Chest chest;
   private final Market market;
   private final Track track;
   private final DevelopCardDeck developCardDeck;
   private List<MarketMarble> tempMarketMarble;
   private int tempIndexWhiteToAdd = -1;
   private final Set<MoveForwardObserver> moveForwardObserverList = new HashSet<>();
   private final boolean[] alreadyProduced;

   private transient ModelObserver controller;

   public PlayerBoard(String username, List<LeaderCard> leaderCards, Market market, DevelopCardDeck developCardDeck) throws IOException {
      this.username = username;
      this.leaderCards = leaderCards != null ? new ArrayList<>(leaderCards) : new ArrayList<>();
      this.chest = new Chest();
      this.warehouse = new Warehouse();
      this.track = GSON.trackParser();
      this.track.setUsername(username);
      this.cardSlots = new CardSlots();
      this.market = market;
      this.developCardDeck = developCardDeck;
      this.tempMarketMarble = new ArrayList<>();
      this.alreadyProduced = new boolean[this.cardSlots.getNumberOfCardSlots() + 3]; // 0 = base | 1 ... #CardSlots = devCards | (#CardSlots + 1) ... (#CardSlots + #Leder): leader
      Arrays.fill(this.alreadyProduced, false);
   }

   /**
    * Calculate player's score
    *
    * @return total points the player scored
    */
   public int returnScore(){
      List<Integer> playerScore = new ArrayList<>();

      playerScore.add(track.calculateTrackScore());
      playerScore.add(cardSlots.calculateDevelopCardScore());

      for(LeaderCard x : leaderCards)
         if(x.isActive())
            playerScore.add(x.getVictoryPoints());

      playerScore.add(Math.floorDiv((warehouse.totalResources() + chest.totalNumberOfResources()), 5));
      return playerScore.stream().reduce(0, Integer::sum);
   }

   /**
    * Remove 1 of the leader cards from leaderCards array in the beginning of the game
    *
    * @param leaderPosition1, index of the first leader card to remove (starts at 0)
    * @param leaderPosition2, index of the second leader card to remove (starts at 0), considering the first already removed
    * @throws InvalidLeaderCardException if one or both the leader cards don't exist
    */
   public void discardLeaderAtBegin(int leaderPosition1, int leaderPosition2) throws InvalidLeaderCardException {
      leaderCards.remove(getIndexFromId(leaderPosition1));
      leaderCards.remove(getIndexFromId(leaderPosition2));
   }

   /**
    * Remove 1 of the leader cards from leaderCards array during the game
    * once the card is removed the faith marker on other players is moved forward by 1
    *
    * @param leaderId, Id of the leaderCard to remove (starts at 0)
    * @throws InvalidLeaderCardException if the leaderCard is activated or the index is outOfBound
    */
   public void discardLeader(int leaderId) throws InvalidLeaderCardException, LeaderIsActiveException {
      if(leaderCards.get(getIndexFromId(leaderId)).isActive())
         throw new LeaderIsActiveException();
      leaderCards.remove(getIndexFromId(leaderId));
      track.moveForward(1);
      notifyLeaderDiscard(new LeaderUpdate(leaderId));
   }

   /**
    * Pick a card from the DevelopCardDeck and place it in the CardSlot
    *
    * @param row, row of the Deck that identifies the Develop to add (starts at 0)
    * @param column, column of the Deck that identifies the Develop to add (starts at 0)
    * @param cardSlot, slot of CardSlots where the card is placed (starts at 0)
    * @throws InvalidCardSlotException if the card can't be placed in the slot passed as parameter
    * @throws NotBuyableException if the player can't buy this card
    * @throws InvalidDevelopCardException if the index of row or column doesn't exists
    */
   public void addDevelopCard(int row, int column, int cardSlot)
           throws InvalidDevelopCardException, NotBuyableException, InvalidCardSlotException {
      developCardDeck.getCard(row, column).buy(this, cardSlot);
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    *
    * @param column indicates which column get from market (starts at 0)
    * @throws RowOrColumnNotExistsException if the specified index of the market doesn't exist
    */
   public List<MarketMarble> shopMarketColumn(int column) throws RowOrColumnNotExistsException {
      tempMarketMarble = new ArrayList<>(market.pushInColumn(column));
      return new ArrayList<>(tempMarketMarble);
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    *
    * @param row indicates which row get from market (starts at 0)
    * @throws RowOrColumnNotExistsException if the specified index of the market doesn't exist
    */
   public List<MarketMarble> shopMarketRow(int row) throws RowOrColumnNotExistsException {
      tempMarketMarble = new ArrayList<>(market.pushInRow(row));
      return new ArrayList<>(tempMarketMarble);
   }

   public List<MarketMarble> getTempMarketMarble() {
      return new ArrayList<>(tempMarketMarble);
   }

   /**
    * Add one of the tempMarketMarble resources to warehouse or track
    *
    * @param marbleIndex, the index of the marble to remove from tempMarketMarble (starts at 0)
    * @throws MoreWhiteLeaderCardsException if the player has more than 1 leaderCard activated that modifies the white marble
    * @throws NotEnoughSpaceException if the resource can't be added
    * @throws MarbleNotExistException if the specified marble doesn't exist
    */
   public void addMarbleToWarehouse(int marbleIndex)
           throws MoreWhiteLeaderCardsException, NotEnoughSpaceException, MarbleNotExistException {
         // TODO usare boolean invece che un'eccezione quando ci sono due leader che convertono una biglia bianca
         if (marbleIndex < 0 || marbleIndex >= tempMarketMarble.size())
            throw new MarbleNotExistException("The index of the marble u gave me doesn't match the length of my array");
            try {
               tempMarketMarble.get(marbleIndex).addResource(this);
            } catch (NotEnoughSpaceException e) {
                  tempMarketMarble.remove(marbleIndex);
                  notifyForMoveForward();
               throw new NotEnoughSpaceException("you can't add this resource");
            } catch (MoreWhiteLeaderCardsException e){
               tempIndexWhiteToAdd = marbleIndex;
               throw new MoreWhiteLeaderCardsException(e.getMessage());
         }
      tempMarketMarble.remove(marbleIndex);
   }

   /**
    * Add a white marble to warehouse using the leaderCard passed as parameter
    *
    * @param leaderId, Id of the leader to use to modify the color of the white marble
    * @throws InvalidLeaderCardException if the index is outOfBound
    * @throws NotEnoughSpaceException if, after have modified the white marble into a resource, there isn't place in the warehouse to place it
    */
   public void addWhiteToWarehouse(int leaderId) throws InvalidLeaderCardException, NotEnoughSpaceException, MarbleNotExistException {
      if(this.tempIndexWhiteToAdd == - 1)
         throw new MarbleNotExistException("you have no white marble to add");
      try {
         tempMarketMarble.get(tempIndexWhiteToAdd).addResource(this, leaderCards.get(getIndexFromId(leaderId)));
      } catch (NotEnoughSpaceException e) {
         tempMarketMarble.remove(tempIndexWhiteToAdd);
         notifyForMoveForward();
         throw new NotEnoughSpaceException("you can't add this resource");
      } catch (WrongLeaderCardException e) {
         e.printStackTrace();
      }
      tempMarketMarble.remove(tempIndexWhiteToAdd);
      this.tempIndexWhiteToAdd = -1;
   }

   /**
    * Allows the player to throw 2 resources in exchange for 1 resource he wants
    *
    * @param resource1 first resource the player wants to throw
    * @param resource2 second resource the player wants to throw
    * @param product resource that the player wants to gain
    * @throws AbuseOfFaithException if one of the 3 resources is faith, he can't throw faith and he can't gain faith
    * @throws NotEnoughResourcesException if the player has not the specified required resources
    * @throws AlreadyProducedException if this production has already been activated during this turn
    * @throws NeedAResourceToAddException if the specified product parameter is null
    */
   public void baseProduction(ResourceType resource1, ResourceType resource2, ResourceType product)
           throws AbuseOfFaithException, NotEnoughResourcesException,
           AlreadyProducedException, NeedAResourceToAddException {

      if(resource1 == null || resource2 == null || product == null)
         throw new NullPointerException();
      if(resource1.equals(ResourceType.FAITH) || resource2.equals(ResourceType.FAITH) || product.equals(ResourceType.FAITH))
         throw new AbuseOfFaithException("you can't consume or produce faith");

      if(alreadyProduced[0])
         throw new AlreadyProducedException();
      if(warehouse.getNumberOf(resource1) + chest.getNumberOf(resource1) > 0 && warehouse.getNumberOf(resource2) + chest.getNumberOf(resource2) > 0) {
         try {
            int remainingToRemove = warehouse.removeResources(resource1,1);
            chest.removeResources(resource1, remainingToRemove);

            remainingToRemove = warehouse.removeResources(resource2, 1);
            chest.removeResources(resource2, remainingToRemove);

            chest.addResources(product, 1);

            alreadyProduced[0] = true;
         } catch (NegativeQuantityException e) {
            //non si verifica mai perché la sto chiamando io e gli sto passando 1
            e.printStackTrace();
         } catch (NullPointerException e) {
            throw new NeedAResourceToAddException();
         }
      }
      else
         throw new NotEnoughResourcesException();
   }

   /**
    * Check if the all the marbles taken form market are finished and consumed (added to warehouse or track)
    *
    * @return true if the marbles are finished, false otherwise
    */
   public boolean areMarblesFinished() {
      return this.tempMarketMarble.isEmpty();
   }

   /**
    * Activate production on a develop card
    *
    * @param slotIndex index of cards slot where the card is
    * @throws NotActivatableException if the player can't activate this card
    * @throws AlreadyProducedException if this production has already been activated during this turn
    * @throws InvalidCardSlotException if the card slot doesn't exists
    */
   public void developProduce(int slotIndex) throws NotActivatableException, AlreadyProducedException, InvalidCardSlotException {
      if(this.alreadyProduced[slotIndex + 1])
         throw new AlreadyProducedException();
      if(this.cardSlots.returnTopCard(slotIndex).getCardFlag().getLevel() == 0)
         throw new NotActivatableException();
      this.cardSlots.returnTopCard(slotIndex).produce(this);
      this.alreadyProduced[slotIndex + 1] = true;
   }

   /**
    * Activate production on a leader card
    *
    * @param leaderId index of the leader on which the production must be activated
    * @param product resource that the player wants to gain
    * @throws AbuseOfFaithException if product is equal to faith
    * @throws NotEnoughResourcesException if the player has not the specified required resources
    * @throws AlreadyProducedException if this production has already been activated during this turn
    * @throws NeedAResourceToAddException if the specified product parameter is null
    */
   public void leaderProduce(int leaderId, ResourceType product) throws NotEnoughResourcesException,
           AbuseOfFaithException, NeedAResourceToAddException, AlreadyProducedException, InvalidLeaderCardException {
      if(this.alreadyProduced[cardSlots.getNumberOfCardSlots() + getIndexFromId(leaderId) + 1])
         throw new AlreadyProducedException();
      LeaderCard leaderToProduceOn = this.leaderCards.get(getIndexFromId(leaderId));
      leaderToProduceOn.leaderProduce(product, this);
      if(leaderToProduceOn.getProductionRequirement() != null)
         this.alreadyProduced[cardSlots.getNumberOfCardSlots() + getIndexFromId(leaderId) + 1] = true;
   }

   /**
    * Activate a leader card
    *
    * @param leaderCardId, Id of the leader card to activate
    * @throws InvalidLeaderCardException if the player doesn't own this card
    * @throws NotEnoughResourcesException if the player doesn't have the resources (CardFlags or ResourceType) to activate the card
    */
   public void setActiveLeadercard(int leaderCardId) throws InvalidLeaderCardException, NotEnoughResourcesException {
      this.leaderCards.get(getIndexFromId(leaderCardId)).setActive(this);
      notifyActivatedLeader(new LeaderUpdate(leaderCardId));
   }

   private int getIndexFromId(int leaderCardId) throws InvalidLeaderCardException {
      int index = -1;
      for(int i=0; i<leaderCards.size(); i++)
         if(leaderCards.get(i).getLeaderId() == leaderCardId)
            index = i;
      if(index == -1)
         throw new InvalidLeaderCardException();
      return index;
   }

   /**
    * Merge the chest's temporary resources into normal chest,
    * Forget all the production already activated
    * Consume any possible remaining marble that hasn't been added
    */
   public void enterFinalTurnPhase() {
      this.chest.endOfTurnMapsMerge();
      Arrays.fill(this.alreadyProduced, false);
      this.emptyTempMarbles();
   }

   private void emptyTempMarbles() {

      for(MarketMarble marble : this.tempMarketMarble) {
         try {
            marble.addResource(this);
         } catch (MoreWhiteLeaderCardsException | NotEnoughSpaceException e) {
            notifyForMoveForward();
         }
      }
      this.tempMarketMarble.clear();
   }

   public String getUsername() {
      return username;
   }

   public Set<MoveForwardObserver> getMoveForwardObserverList() {
      return new HashSet<>(moveForwardObserverList);
   }

   public void setEndGameObserver(EndGameObserver obs) {
      this.track.addToEndGameObserverList(obs);
      this.cardSlots.addToEndGameObserverList(obs);
      this.developCardDeck.addToEndGameObserverList(obs);
   }

   public void setTrackObserverOn (PlayerBoard playerBoard) {
   // FIXME penso debba fare qualcosa
   }

   @Override
   public Warehouse getWarehouse() {
      return warehouse;
   }

   @Override
   public Chest getChest() {
      return chest;
   }

   @Override
   public Track getTrack() {
      return track;
   }

   @Override
   public CardSlots getCardSlots() {
      return cardSlots;
   }

   @Override
   public ArrayList<LeaderCard> getLeaderCards() {
      return new ArrayList<>(this.leaderCards);
   }

   public DevelopCardDeck getDevelopCardDeck() {
      return developCardDeck;
   }

   @Override
   public void notifyForMoveForward() {
      List<Pair<MoveForwardObserver, Integer>> orderedCall = new ArrayList<>();

      for (MoveForwardObserver x : moveForwardObserverList)
         orderedCall.add(new Pair<>(x, x.getTrackPosition()));

      orderedCall.sort(Comparator.comparing(Pair::getValue));

      for (Pair<MoveForwardObserver, Integer> x : orderedCall)
         x.getKey().update();
   }

   // MoveForwardObserver is a track, so in multiplayer (after have instantiated all players) call this method on each
   // playerBoard passing the track of the other as parameter
   @Override
   public void addToMoveForwardObserverList(MoveForwardObserver observerToAdd) {
      moveForwardObserverList.add(observerToAdd);
   }

  public void setController(ModelObserver controller) {
      this.track.setController(controller);
      this.chest.setController(controller);
      this.warehouse.setController(controller);
      this.cardSlots.setController(controller);
      this.controller = controller;
  }

   @Override
   public void notifyLeaderDiscard(LeaderUpdate msg) {
      if (controller != null)
         controller.discardedLeaderUpdate(msg);
   }

   @Override
   public void notifyActivatedLeader(LeaderUpdate msg) {
      if (controller != null)
         controller.leaderUpdate(msg);
   }
}
