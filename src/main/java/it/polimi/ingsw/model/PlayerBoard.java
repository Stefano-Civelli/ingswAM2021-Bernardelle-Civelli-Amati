package it.polimi.ingsw.model;

import it.polimi.ingsw.model.leadercard.LeaderCard;
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
   private final File trackConfigFile = new File("src/SquareConfig.json");

   public PlayerBoard(String username, List<LeaderCard> leaderCards, Market market, DevelopCardDeck developCardDeck) throws IOException {
      this.username = username;
      this.leaderCards = new ArrayList<>(leaderCards);
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

      playerScore.add((warehouse.totalResources() + chest.totalNumberOfResources())/5);
      return playerScore.stream().reduce(0, Integer::sum);
   }

   /**
    * remove 1 of the leader cards from leaderCards array in the beginning of the game
    * @param leaderPosition, index of the leaderCard to remove
    */
   public void discardLeaderAtBegin(int leaderPosition) throws InvalidLeaderCardException {
      if(leaderCards.size() < 3)
         throw new InvalidLeaderCardException("U can not call this method at this point of the game");
      leaderCards.remove(leaderPosition);
   }

   //the caller of this method needs to call the method moveForward() on the other players if the exception isn't catch
   public void discardLeader(int leaderPosition) throws InvalidLeaderCardException {
      if(leaderCards.size() > 2 || leaderCards.get(leaderPosition).isActive())
         throw new InvalidLeaderCardException("U can't remove this card in this moment");
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


   public void setLeaderCardActive(int leaderToActivate) throws NotEnoughResourcesException, InvalidLeaderCardException {
      if(!leaderCards.get(leaderToActivate).isActive())
         leaderCards.get(leaderToActivate).activate(this);
   }

   public void addMarbleToWarehouse(int marbleIndex, Integer leaderPosition) throws InvalidLeaderCardException, NotEnoughSpaceException, MoreWhiteLeaderCardsException {
      if(leaderPosition == null)
         tempMarketMarble.get(marbleIndex).addResource(this, Optional.empty());
      else {
         if (marbleIndex < 0 || marbleIndex >= tempMarketMarble.size())
            throw new IndexOutOfBoundsException("The index of the marble u gave me doesn't match the length of my array");
         if (leaderPosition < 0 || leaderPosition >= leaderCards.size())
            throw new IndexOutOfBoundsException("The index of the leaderCard u gave me doesn't match the length of my array");
         if(leaderCards.get(leaderPosition).isActive())
            tempMarketMarble.get(marbleIndex).addResource(this, Optional.of(leaderCards.get(leaderPosition)));
         else
            throw new InvalidLeaderCardException("The leader card u want to use isn't been activated yet");
      }
   }
//   public void addMarbleToWarehouse(int marbleIndex, Optional<LeaderCard> leaderCard) throws InvalidLeaderCardException, NotEnoughSpaceException, MoreWhiteLeaderCardsException {
//         if(!leaderCard.isEmpty())
//
//         if (marbleIndex >= 0 && marbleIndex < tempMarketMarble.size())
//            tempMarketMarble.get(marbleIndex).addResource(this, leaderCard);
//         else
//            throw new IndexOutOfBoundsException("The index of the marble u gave me doesn't match the length of my array");
//      }
//      if (!leaderCards.contains(leaderCard))
//         throw new InvalidLeaderCardException("Your hand doesn't contain this card");
//      if(leaderCard != null && leaderCard.isActive())
//         tempMarketMarble.get(marbleIndex).addResource(this, leaderCard.resourceOnWhite());
//      else
//         throw new InvalidLeaderCardException("U need to activate the leader card before asking to use it");
//   }

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
}