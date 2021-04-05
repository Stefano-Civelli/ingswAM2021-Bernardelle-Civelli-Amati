package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.utility.GSON;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PlayerBoard implements InterfacePlayerBoard {

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
   private IStartBehaviour startBehaviour;
   File trackConfigFile = new File("src/SquareConfig.json");

   public PlayerBoard(String username, ArrayList<LeaderCard> leaderCards, Market market, DevelopCardDeck developCardDeck){
      this.username = username;
      this.leaderCards = new ArrayList<>(leaderCards);
      this.chest = new Chest();
      this.warehouse = new Warehouse();
      try {
         this.track = GSON.trackParser(trackConfigFile);
      } catch (IOException e) {
         e.printStackTrace();
      }
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
      List<Integer> playerscore = new ArrayList<>();

      playerscore.add(track.calculateTrackScore());
      playerscore.add(cardSlots.calculateDevelopCardScore());

      for(LeaderCard x : leaderCards)
         if(x.isActive())
            playerscore.add(x.getVictoryPoints());

      playerscore.add((warehouse.totalResources() + chest.totalNumberOfResources())/5);
      return playerscore.stream().reduce(0, Integer::sum);
   }

   /**
    * remove 1 of the leader cards from leadercards array
    * @param leaderPosition, index of the leadercard to remove
    */
   public void discardLeader(int leaderPosition){
      leaderCards.remove(leaderPosition);
   }


   public void addDevelopCard(int row, int column, int cardSlot) throws InvalidCardPlacementException, RowOrColumnNotExistsException {
      cardSlots.addDevelopCard(cardSlot, developCardDeck.getCard(row, column));
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    * @param column indicates which column get from market
    */
   public void shopMarketColumn(int column) throws RowOrColumnNotExistsException {
      tempMarketMarble = new ArrayList<>(market.pushInColumn(column));
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    * @param row indicates which row get from market
    */
   public void shopMarketRow(int row) throws RowOrColumnNotExistsException {
      tempMarketMarble = new ArrayList<>(market.pushInRow(row));
   }


   public void activateLeaderCard(int leaderToActivate) {
      if(!leaderCards.get(leaderToActivate).isActive())
         leaderCards.get(leaderToActivate).activate();
   }

   public void addMarbleToWarehouse(int marbleIndex, int level, LeaderCard leaderCard) throws InvalidLeaderCardException, LevelNotExistsException, IncorrectResourceTypeException, NotEnoughSpaceException {
      if (!leaderCards.contains(leaderCard))
         throw new InvalidLeaderCardException("Your hand doesn't contain this card");
      if (leaderCard == null) {
         if (marbleIndex >= 0 && marbleIndex < tempMarketMarble.size())
            tempMarketMarble.get(marbleIndex).addResource(this, level, null);
         else
            throw new IndexOutOfBoundsException("The index of the marble u gave me doesn't match the length of my array");
      }
      else if(leaderCard.isActive())
         tempMarketMarble.get(marbleIndex).addResource(this, level, leaderCard.resourceOnWhite());
      else
         throw new InvalidLeaderCardException("U need to activate the leader card before asking to use it");
   }

   //spostare anche questo in ProduceBehaviour
   public void baseProduction(ResourceType resource1, ResourceType resource2, ResourceType product) {
      if(warehouse.getNumberOf(resource1) + chest.getNumberOf(resource1) > 0 && warehouse.getNumberOf(resource2) + chest.getNumberOf(resource2) > 0) {
         tempResources = Stream.of(new Object[][]{
                 {resource1, 1},
                 {resource2, 1},
         }).collect(Collectors.toMap(data -> (ResourceType) data[0], data -> (Integer) data[1]));
         try {
            chest.addResources(product, 1);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public void startProducingProcedure(DevelopCard developCard){
      if (developCard.isActivatable(this))
         //forse la behaviour potrebbe venire iniettata dal controller in base a cosa deve fare. poi faccio solo il metodo che chiama il metodo della behaviour
         startBehaviour = new ProduceBehaviour(this, developCard);
   }

   public void startBuyingProcedure(DevelopCard developCard){
      if (developCard.isBuyable(this))
         startBehaviour = new BuyBehaviour(this, developCard);
   }

   //TODO
   public void useResource(){
      return;
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
      return null;
   }

   public DevelopCardDeck getDevelopCardDeck() {
      return developCardDeck;
   }
}