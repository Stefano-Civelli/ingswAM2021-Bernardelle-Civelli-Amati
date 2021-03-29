package it.polimi.ingsw.model;

import it.polimi.ingsw.model.track.Track;
import java.util.*;


public class PlayerBoard {

   private final String username;
   private final CardSlots cardSlots;
   private final Warehouse warehouse;
   private final List<LeaderCard> leaderCards;
   private final Chest chest;
   private final Market market;
   private final Track track;
   private final DevelopCardDeck developCardDeck;
   private final List<MarketMarble> tempMarketMarble;
   private final Map<ResourceType, Integer> tempResources;


   public PlayerBoard(String username, ArrayList<LeaderCard> leaderCards, Market market, DevelopCardDeck developCardDeck){
      this.username = username;
      this.leaderCards = new ArrayList<>(leaderCards);
      this.chest = new Chest();
      this.warehouse = new Warehouse();
      this.track = new Track();
      this.cardSlots = new CardSlots();
      this.market = market;
      this.developCardDeck = developCardDeck;
      this.tempMarketMarble = new ArrayList<>();
      this.tempResources = new HashMap<>();
   }


   public int returnScore(){
      return 0;
   }

   public void discardLeader(int leaderPosition1, int leaderPosition2){
      return;
   }

   public void activateProduction(){
      return;
   }

   public void addDevelopCard(){
      return 0;
   }

   public void shopMarketColumn(){
      return;
   }
   public void shopMarketRow(){
      return;
   }

   public void activateLeadercard(){
      return;
   }

   public void addMarbleToWarehouse(){
      return;
   }

   public void baseProduction(){

   }




   public String getUsername() {
      return username;
   }

   public Warehouse getWarehouse() {
      return warehouse;
   }

   public Chest getChest() {
      return chest;
   }

   public Track getTrack() {
      return track;
   }

   public DevelopCardDeck getDevelopCardDeck() {
      return developCardDeck;
   }
}
