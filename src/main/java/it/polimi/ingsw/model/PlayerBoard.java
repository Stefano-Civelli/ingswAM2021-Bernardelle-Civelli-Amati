package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerBoard implements InterfacePlayerBoard, MoveForwardObserver, MoveForwardObservable {

   private final String username;
   private final CardSlots cardSlots;
   private final Warehouse warehouse;
   private final List<LeaderCard> leaderCards;
   private final Chest chest;
   private final Market market;
   private Track track;
   private final DevelopCardDeck developCardDeck;
   private List<MarketMarble> tempMarketMarble;
   private Map<ResourceType, Integer> tempResources;
   private int tempIndexWhiteToAdd;
   private final File trackConfigFile = new File("src/SquareConfig.json");
   private final Set<MoveForwardObserver> moveForwardObserverList = new HashSet<>();

   public PlayerBoard(String username, List<LeaderCard> leaderCards, Market market, DevelopCardDeck developCardDeck) throws IOException {
      this.username = username;
      this.leaderCards = leaderCards != null ? new ArrayList<>(leaderCards) : new ArrayList<>();
      this.chest = new Chest();
      this.warehouse = new Warehouse();
      this.track = GSON.trackParser(trackConfigFile);
      this.cardSlots = new CardSlots();
      this.market = market;
      this.developCardDeck = developCardDeck;
      this.tempMarketMarble = new ArrayList<>();
      this.tempResources = new HashMap<>();
   }

   /**
    * Calculate player's score
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
    * remove 1 of the leader cards from leaderCards array in the beginning of the game
    * @param leaderPosition, index of the leaderCard to remove (starts at 0)
    */
   public void discardLeaderAtBegin(int leaderPosition) throws InvalidLeaderCardException {
      if(leaderCards.size() < 3)
         throw new InvalidLeaderCardException("U can not call this method at this point of the game");
      leaderCards.remove(leaderPosition);
   }

   /**
    * remove 1 of the leader cards from leaderCards array during the game
    * once the card is removed the faith marker is moved forward by 1
    * @param leaderPosition, index of the leaderCard to remove (starts at 0)
    * @throws InvalidLeaderCardException if the leaderCard is activated or the index is outOfBound
    */
   public void discardLeader(int leaderPosition) throws InvalidLeaderCardException {
      if(leaderCards.size() > 2 || leaderCards.get(leaderPosition).isActive())
         throw new InvalidLeaderCardException("U can't remove this card in this moment");
      leaderCards.remove(leaderPosition);
      track.moveForward(1);
   }

   /**
    * pick a card from the DevelopCardDeck and place it in the CardSlot
    * @param row, row of the Deck that identifies the Develop to add (starts at 0)
    * @param column, column of the Deck that identifies the Develop to add (starts at 0)
    * @param cardSlot, slot of CardSlots where the card is placed (starts at 0)
    * @throws InvalidCardPlacementException if the card can't be placed in the slot passed as parameter
    * @throws RowOrColumnNotExistsException if the index of row or column doesn't exists
    */
   public void addDevelopCard(int row, int column, int cardSlot) throws InvalidCardPlacementException, RowOrColumnNotExistsException, NotBuyableException {
      developCardDeck.getCard(row, column).buy(this, cardSlot);
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    * @param column indicates which column get from market (starts at 0)
    */
   public List<MarketMarble> shopMarketColumn(int column) throws RowOrColumnNotExistsException {
      tempMarketMarble = new ArrayList<>(market.pushInColumn(column));
      return new ArrayList<>(tempMarketMarble);
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    * @param row indicates which row get from market (starts at 0)
    */
   public List<MarketMarble> shopMarketRow(int row) throws RowOrColumnNotExistsException {
      tempMarketMarble = new ArrayList<>(market.pushInRow(row));
      return new ArrayList<>(tempMarketMarble);
   }

   /**
    * add one of the tempMarketMarble resources to warehouse
    * @param marbleIndex, the index of the marble to remove from tempMarketMarble (starts at 0)
    * @throws MoreWhiteLeaderCardsException if the player has more than 1 leaderCard activated that modifies the white marble
    * @throws NotEnoughSpaceException if the resource can't be added
    */
   public void addMarbleToWarehouse(int marbleIndex) throws MoreWhiteLeaderCardsException, NotEnoughSpaceException {
         if (marbleIndex < 0 || marbleIndex >= tempMarketMarble.size())
            throw new IndexOutOfBoundsException("The index of the marble u gave me doesn't match the length of my array");
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
    * add a white marble to warehouse using the leaderCard passed as parameter
    * @param leaderPosition index of the leader to use to modify the color of the white marble
    * @throws InvalidLeaderCardException if the index is outOfBound
    * @throws NotEnoughSpaceException if, after have modified the white marble into a resource, there isn't place in the warehouse to place it
    */
   public void addWhiteToWarehouse(int leaderPosition) throws InvalidLeaderCardException, NotEnoughSpaceException {
      if (leaderPosition < 0 || leaderPosition >= leaderCards.size())
         throw new InvalidLeaderCardException("The index of the leaderCard u gave me doesn't match the length of my array");
      try {
         tempMarketMarble.get(tempIndexWhiteToAdd).addResource(this, leaderCards.get(leaderPosition));
      } catch (NotEnoughSpaceException e) {
         tempMarketMarble.remove(tempIndexWhiteToAdd);
         notifyForMoveForward();
         throw new NotEnoughSpaceException("you can't add this resource");
      } catch (WrongLeaderCardException e) {
         e.printStackTrace();
      }
   }

   /**
    * allows the player to throw 2 resources in exchange for 1 resource he wants
    * @param resource1, first resource the player wants to throw
    * @param resource2, second resource the player wants to throw
    * @param product, resource that the player wants to gain
    * @throws AbuseOfFaithException if one of the 3 resources is faith, he can't throw faith and he can't gain faith
    */
   public void baseProduction(ResourceType resource1, ResourceType resource2, ResourceType product) throws AbuseOfFaithException {
      if(warehouse.getNumberOf(resource1) + chest.getNumberOf(resource1) > 0 && warehouse.getNumberOf(resource2) + chest.getNumberOf(resource2) > 0) {
         tempResources = Stream.of(new Object[][]{
                 {resource1, 1},
                 {resource2, 1},
         }).collect(Collectors.toMap(data -> (ResourceType) data[0], data -> (Integer) data[1]));

         try {
            chest.addResources(product, 1);
         } catch (NegativeQuantityException e) {} //non si verifica mai perché la sto chiamando io e gli sto passando 1
      }
   }

   public String getUsername() {
      return username;
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
   public void removeFromMoveForwardObserverList (MoveForwardObserver observerToRemove) {
      moveForwardObserverList.remove(observerToRemove);
   }

   @Override
   public void notifyForMoveForward() {
      List<Pair<MoveForwardObserver, Integer>> orderedCall = new ArrayList<>();

      for(MoveForwardObserver x : moveForwardObserverList)
         orderedCall.add(new Pair<>(x, x.getTrack().getCurrentPosition()));

      orderedCall.sort(Comparator.comparing(Pair::getValue));


      for(Pair<MoveForwardObserver, Integer> x : orderedCall)
            x.getKey().update();
   }

   @Override
   public void update() {
      track.moveForward(1);
   }

   @Override
   public void addToMoveForwardObserverListObs(MoveForwardObserver observerToAdd) {
      if(moveForwardObserverList.add(observerToAdd))
         moveForwardObserverList.add(observerToAdd); //non penso sia necessaria
   }

   @Override
   public void addToMoveForwardObserverList(MoveForwardObserver observerToAdd) {
      if(moveForwardObserverList.add(observerToAdd))
         observerToAdd.addToMoveForwardObserverListObs(this);
   }

}