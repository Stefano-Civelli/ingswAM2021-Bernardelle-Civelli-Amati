package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.InvalidCardException;
import it.polimi.ingsw.model.updatecontainers.*;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.view.cli.drawer.LeaderConstructor;
import java.util.*;

/**
 * Class containing a simplified version of the PlayerBoard state
 */
public class SimplePlayerState {

   private final int NUMBER_OF_NORMAL_LEVELS = 3;
   private final int MAX_SPECIAL_LEVELS = 2;

   private int trackPosition;
   private boolean[] vaticanFlipped;
   private  List<Integer> notActiveLederCards; //identified by ID
   private  List<Integer> activeLeaderCards;   //identified by ID
   private final Map<ResourceType, Integer> chest;
   private final Map<ResourceType, Integer> tempChest;
   private final Pair<ResourceType, Integer>[] storageLevels;
   private final List<Integer>[] cardSlots;

   /**
    * Constructor for SimplePlayerState class
    */
   public SimplePlayerState() {
      this.trackPosition = 0;
      this.chest = new HashMap<>();
      for (ResourceType resType : ResourceType.values())
         chest.put(resType, 0);

      this.tempChest = new HashMap<>();
      this.storageLevels = new Pair[MAX_SPECIAL_LEVELS+NUMBER_OF_NORMAL_LEVELS];
      for(int i=0; i<MAX_SPECIAL_LEVELS+NUMBER_OF_NORMAL_LEVELS; i++)
         storageLevels[i] = new Pair(null, null);

      this.cardSlots = new List[3];
      for(int i=0; i<3; i++)
         cardSlots[i] = new ArrayList<>();

      this.vaticanFlipped = new boolean[3];
      for(int i=0; i<3; i++)
         vaticanFlipped[i] = false;

      this.activeLeaderCards = new ArrayList<>();

      this.notActiveLederCards = new ArrayList<>();
      for(int i=0; i<2; i++)
         notActiveLederCards.add(0);
      //adds 2 placeholder card with a special ID that doesn't match any real card
   }

   //-----------SETUP-------------------------------------------
   /**
    * Setups non activated LeaderCards
    * @param stateSetup, contains the list of LeaderCards that belong to this SimplePlayer
    */
   public void setupLeaderCard(LeaderSetup stateSetup) {
      this.notActiveLederCards = stateSetup.getLeaderList();
   }
   //-----------------------------------------------------------


   //----------UPDATE----------
   /**
    * Updates the warehouse
    * LeaderCards that works as a warehouse level are also updated with this method
    * @param update, update content
    */
   public void warehouseUpdate(WarehouseUpdate update) {
      ResourceType resource = update.getResourceType();

      if(update.getLevel()<3) {
         for (int i = 0; i < 3; i++) {

            if (storageLevels[i].getKey() != null) {
               if (storageLevels[i].getKey().equals(resource)) {
                  if (i == update.getLevel()) {
                     storageLevels[i] = new Pair<>(resource, update.getQuantity());
                     return;
                  } else {
                     Pair<ResourceType, Integer> temp = new Pair<>(storageLevels[update.getLevel()].getKey(), storageLevels[update.getLevel()].getValue());
                     storageLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                     storageLevels[i] = new Pair<>(temp.getKey(), temp.getValue());
                     return;
                  }
               }
            }
         }
      }
      storageLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
   }

   /**
    * Updates the track
    * @param stateUpdate, update content
    */
   public void trackUpdate(TrackUpdate stateUpdate) {
      int newPosition = stateUpdate.getPlayerPosition();
      this.trackPosition = newPosition;
   }

   /**
    * Updates the vatican report cells
    * @param stateUpdate, update content
    */
   public void vaticanReportUpdate(VaticanReport stateUpdate) {
      int zone = stateUpdate.getZone();
      boolean flip = stateUpdate.isActive();

      vaticanFlipped[zone] = flip;
   }

   /**
    * Updates the chest
    * @param stateUpdate, update content
    */
   public void chestUpdate(ChestUpdate stateUpdate) { //serve?
      ResourceType resource = stateUpdate.getResourceType();
      int quantity = stateUpdate.getQuantity();

      this.chest.put(resource, quantity);
   }

   /**
    * Updates the temporary chest
    * @param stateUpdate, update content
    */
   public void tempChestUpdate(ChestUpdate stateUpdate) {
      ResourceType resource = stateUpdate.getResourceType();
      int quantity = stateUpdate.getQuantity();

      this.tempChest.put(resource, quantity);
   }

   /**
    * Updates the CardSlots
    * @param stateUpdate, update content
    */
   public void cardSlotUpdate(CardSlotUpdate stateUpdate) {
      int devCardID = stateUpdate.getDevCardID();
      int slot = stateUpdate.getSlotNumber();

      cardSlots[slot].add(devCardID);
   }

   /**
    * Updates the LeaderCards activating one them
    * @param stateUpdate, update content
    */
   public void activatedLeaderUpdate(LeaderUpdate stateUpdate) {
      this.activeLeaderCards.add(stateUpdate.getCardId());
      this.notActiveLederCards.remove((Integer) stateUpdate.getCardId()); //potrebbe non andare a causa dell'indice
   }


   /**
    * discard a LeaderCard from the SimplePlayerState
    * @param indexOfLeaderToDiscard, index of the leader to discard (starting at 0)
    */
   public void discardLeader(int indexOfLeaderToDiscard) {
         notActiveLederCards.remove(indexOfLeaderToDiscard);
   }
   //----------------------------------------------------------


   //----------GETTERS-----------------------------------------
   /**
    * return non-active leader cards
    * NOTE: can return null
    * @return non-active leader cards
    */
   public List<Integer> getNotActiveLeaderCards() {
      return new ArrayList<>(notActiveLederCards);
   }

   /**
    * Returns the chest
    * @return the chest
    */
   public Map<ResourceType, Integer> getChest() {
      return new HashMap<>(chest);
   }

   /**
    * Returns normal levels of warehouse (so excluding leader levels if they are present)
    * @return normal levels of warehouse
    */
   public Pair<ResourceType, Integer>[] getWarehouseLevels() {
      Pair<ResourceType, Integer>[] warehouseLevels = new Pair[3];
      for(int i=0; i<3; i++)
         warehouseLevels[i] = new Pair<>(storageLevels[i]);
      return warehouseLevels;
   }

   /**
    * Returns levels of the warehouse that belongs to LeaderCards
    * NOTE: can return null
    * @return levels of the warehouse that belongs to LeaderCards
    */
   public List<Pair<ResourceType, Integer>> getLeaderLevels() {
      List<Pair<ResourceType, Integer>> tempList = new ArrayList<>();
      for(int i=3; i<5; i++)
         if(storageLevels[i].getKey() != null)
            tempList.add(storageLevels[i]);

      return tempList;
   }

   public int getTrackPosition() {
      return trackPosition;
   }

   public List<Integer>[] getCardSlots() {
      List<Integer>[] tempCardSlots = new List[3];
      for(int i=0; i<3; i++)
         tempCardSlots[i] = new ArrayList<>(cardSlots[i]);
      return tempCardSlots;
   }

   public boolean[] getVaticanFlipped() {
      return vaticanFlipped;
   }

   /**
    * Returns active LeaderCards present in this SimplePlayerState
    * @return active LeaderCards present in this SimplePlayerState
    */
   public List<Integer> getActiveLeaderCards(){
      return new ArrayList<>(this.activeLeaderCards);
   }

//   public List<Integer> getProducibleLeaders() {
//      List<Integer> producibleLeaderList = new ArrayList<>();
//
//      for(Integer leaderId : activeLeaderCards) {
//         ResourceType produceRequirement = null;
//         try {
//            produceRequirement = LeaderConstructor.getLeaderCardFromId(leaderId).getProductionRequirement();
//         } catch (InvalidCardException e) {
//            e.printStackTrace();
//         }
//         if (produceRequirement != null)
//            producibleLeaderList.add(leaderId);
//      }
//      return producibleLeaderList;
//   }
//
//   public List<Integer> getProducibleCardSlotsId() {
//      List<Integer> producibleCardList = new ArrayList<>();
//      for(List<Integer> slot : cardSlots)
//         if(!slot.isEmpty())
//            producibleCardList.add(slot.get(slot.size()-1));
//
//      return producibleCardList;
//   }

   //----------------------------------------------------------


   //----------UTILITY-----------------------------------------
   /**
    * Merges the temporary chest of this SimplePlayerState into his chest
    */
   public void mergeTempChest() {
      for(Map.Entry<ResourceType, Integer> entry : tempChest.entrySet()){
         chest.put(entry.getKey(), chest.containsKey(entry.getKey()) ? chest.get(entry.getKey()) + entry.getValue() : entry.getValue());
      }
      this.tempChest.clear();
   }

//   public boolean isBaseProductionActivatable() { //secondo me non serve
//      boolean isvalid = false;
//      int quantity = 0;
//      for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
//         quantity = entry.getValue();
//      for(Pair<ResourceType, Integer> p : warehouseLevels) {
//         if(p.getValue()!=null)
//            quantity = p.getValue();
//      }
//      for(Pair<ResourceType, Integer> p : leaderLevels) {
//         if(p.getValue()!=null)
//            quantity = p.getValue();
//      }
//      if(quantity>1)
//         isvalid = true;
//      return isvalid;
//   }

//   public Map<ResourceType, Integer> throwableResources() {
//      Map<ResourceType, Integer> resources = new HashMap<>();
//      for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
//         resources.put(entry.getKey(), entry.getValue());
//      for(Pair<ResourceType, Integer> p : warehouseLevels) {
//         if(p.getValue()!=null)
//            resources.put(p.getKey(), resources.containsKey(p.getKey()) ? resources.get(p.getKey()) + p.getValue() : p.getValue());
//      }
//      for(Pair<ResourceType, Integer> p : leaderLevels) {
//         if(p.getValue()!=null)
//            resources.put(p.getKey(), resources.containsKey(p.getKey()) ? resources.get(p.getKey()) + p.getValue() : p.getValue());
//      }
//
//      return resources;
//   }
   //----------------------------------------------------------
}
