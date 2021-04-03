package it.polimi.ingsw.model;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.MarketMarble;
import it.polimi.ingsw.modeltest.tracktest.Track;
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
      return playerscore.stream().reduce(0, (a,b) -> a+b);
   }

   /**
    * remove 2 of the 4 leader cards
    * @param leaderPosition1, index of the first card to remove
    * @param leaderPosition2, index of the second card to remove
    */
   public void discardLeader(int leaderPosition1, int leaderPosition2){
      leaderCards.remove(leaderPosition1);
      leaderCards.remove(leaderPosition2-1);
      return;
   }

   public void activateProduction(){
      return;
   }

   public void addDevelopCard() {
      return;
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    * @param column indicates which column get from market
    */
   public void shopMarketColumn(int column) {
      tempMarketMarble = new ArrayList<>(market.pushInColumn(column));
   }

   /**
    * Saves the market marbles taken from the market in tempMarketMarble
    * @param row indicates which row get from market
    */
   public void shopMarketRow(int row){
      tempMarketMarble = new ArrayList<>(market.pushInRow(row));
   }


   public void activateLeaderCard() {
      return;
   }

   public void addMarbleToWarehouse(){
      return;
   }

   //TODO
   public void baseProduction(ResourceType resource1, ResourceType resource2, ResourceType product) {
      tempResources = Stream.of(new Object[][] {
              { resource1, 1 },
              { resource2, 1 },
      }).collect(Collectors.toMap(data -> (ResourceType) data[0], data -> (Integer) data[1]));

      //dove metto product? non solo in questo caso ma anche in tutte le altre produce
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