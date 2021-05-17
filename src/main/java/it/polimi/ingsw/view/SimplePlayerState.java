package it.polimi.ingsw.view;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplePlayerState implements SimpleStateObservable{

   private final int NUMBER_OF_NORMAL_LEVELS = 3;
   private final int MAX_SPECIAL_LEVELS = 2;

   private int trackPosition;
   private boolean[] vaticanFlipped;
   private  List<Integer> leaderCards; //identified by ID
   private final Map<ResourceType, Integer> chest;
   private final Map<ResourceType, Integer> tempChest;
   private final Pair<ResourceType, Integer>[] warehouseLevels;
   private final List<Pair<ResourceType, Integer>> leaderLevels;
   private final List<Integer>[] cardSlots;


   public SimplePlayerState() {
      this.trackPosition = 0;
      this.chest = new HashMap<>(); //potrei mettere ste 3 righe in una chestsetup
      for (ResourceType resType : ResourceType.values())
         chest.put(resType, 0); //questa inizializzazione forse non serve. dipende da come funziona la funzione di print

      this.tempChest = new HashMap<>();
      this.warehouseLevels = new Pair[this.NUMBER_OF_NORMAL_LEVELS];
      for(int i=0; i<3; i++)
         warehouseLevels[i] = new Pair(null, null);

      this.leaderLevels = new ArrayList<>(this.MAX_SPECIAL_LEVELS);
      for (int i=0; i<MAX_SPECIAL_LEVELS; i++)
         this.leaderLevels.add(new Pair<>(null, null));

      this.cardSlots = new List[3];
      for(int i=0; i<3; i++)
         cardSlots[i] = new ArrayList<>();

      this.vaticanFlipped = new boolean[3];
      for(int i=0; i<3; i++)
         vaticanFlipped[i] = false;
   }

   //-----------SETUP-------------------------------------------
   public void setupLeaderCard(String payload){
      this.leaderCards = GSON.getGsonBuilder().fromJson(payload, List.class);
   }

   /**
    * delete a leadercard from the SimplePlayerState
    *
    * @param indexOfLeaderToDiscard
    */
   public void discardLeader(int indexOfLeaderToDiscard){
      leaderCards.remove(indexOfLeaderToDiscard - 1);
   }
   //-----------------------------------------------------------


   //----------UPDATE-------------------------------------------
   public void warehouseUpdate(String payload) {
      Warehouse.WarehouseUpdate update = GSON.getGsonBuilder().fromJson(payload, Warehouse.WarehouseUpdate.class);

      ResourceType resource = update.getResourceType();

      //normalLevels
      if(update.getLevel()<3) {
         //controllo se la risorsa é presente
         for (int i = 0; i < warehouseLevels.length; i++) {

            if (warehouseLevels[i].getKey() != null) {
               if (warehouseLevels[i].getKey().equals(resource)) {
                  if (i == update.getLevel()) {
                     warehouseLevels[i] = new Pair<>(resource, update.getQuantity());
                     return;
                  } else {
                     Pair<ResourceType, Integer> temp = new Pair<>(warehouseLevels[update.getLevel()].getKey(), warehouseLevels[update.getLevel()].getValue());
                     warehouseLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                     warehouseLevels[i] = new Pair<>(temp.getKey(), temp.getValue());
                     return;
                  }
               }
            }
         }
         //se non presente creo
         warehouseLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
      }
      else //TODO modificare come fatto sopra
         for (int i = 0; i < leaderLevels.size(); i++) {
            Pair<ResourceType, Integer> level = leaderLevels.get(i);

            if (level.getKey().equals(resource)) {
               level = new Pair<>(resource, update.getQuantity());
               return;
            }
         }
   }

   public void trackUpdate(String payload) {
      int newPosition = GSON.getGsonBuilder().fromJson(payload, Integer.class);
      this.trackPosition = newPosition;
   }

   public void vaticanReportUpdate(String payload) {
      Track.VaticanReport update = GSON.getGsonBuilder().fromJson(payload, Track.VaticanReport.class);
      int zone = update.getZone();
      boolean flip = update.isActive();

      vaticanFlipped[zone] = flip;
   }

   public void chestUpdate(String payload) { //serve?
      Type token = new TypeToken<Pair<ResourceType, Integer>>(){}.getType();
      Pair<ResourceType, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      ResourceType resource = pair.getKey();
      int quantity = pair.getValue();

      this.chest.put(resource, quantity);
   }

   public void tempChestUpdate(String payload) {
      Type token = new TypeToken<Pair<ResourceType, Integer>>(){}.getType();
      Pair<ResourceType, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      ResourceType resource = pair.getKey();
      int quantity = pair.getValue();

      this.tempChest.put(resource, quantity);
   }

   public void cardSlotUpdate(String payload) {
      Type token = new TypeToken<Pair<Integer, Integer>>(){}.getType();
      Pair<Integer, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      int devCardID = pair.getKey();
      int slot = pair.getValue();

      cardSlots[slot].add(devCardID);

   }

   public void activatedLeaderUpdate(String payload){
      leaderCards.add(Integer.parseInt(payload));
   }
   //----------------------------------------------------------


   //----------GETTERS-----------------------------------------
   public List<Integer> getLeaderCards() {
      return new ArrayList<>(leaderCards);
   }

   public Map<ResourceType, Integer> getChest() {
      return new HashMap<>(chest);
   }

   public Pair<ResourceType, Integer>[] getWarehouseLevels() {
      return warehouseLevels;
   }
   //----------------------------------------------------------

   //----------UTILITY-----------------------------------------
   public void mergeTempChest() {
      for(Map.Entry<ResourceType, Integer> entry : tempChest.entrySet()){
         chest.put(entry.getKey(), chest.containsKey(entry.getKey()) ? chest.get(entry.getKey()) + entry.getValue() : entry.getValue());
      }
      this.tempChest.clear();
   }

   public boolean isBaseProductionActivatable() {
      boolean isvalid = false;
      int quantity = 0;
      for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
         quantity = entry.getValue();
      for(Pair<ResourceType, Integer> p : warehouseLevels) {
         if(p.getValue()!=null)
            quantity = p.getValue();
      }
      for(Pair<ResourceType, Integer> p : leaderLevels) {
         if(p.getValue()!=null)
            quantity = p.getValue();
      }
      if(quantity>1)
         isvalid = true;
      return isvalid;
   }

   public Map<ResourceType, Integer> throwableResources() {
      Map<ResourceType, Integer> resources = new HashMap<>();
      for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
         resources.put(entry.getKey(), entry.getValue());
      for(Pair<ResourceType, Integer> p : warehouseLevels) {
         if(p.getValue()!=null)
            resources.put(p.getKey(), resources.containsKey(p.getKey()) ? resources.get(p.getKey()) + p.getValue() : p.getValue());
      }
      for(Pair<ResourceType, Integer> p : leaderLevels) {
         if(p.getValue()!=null)
            resources.put(p.getKey(), resources.containsKey(p.getKey()) ? resources.get(p.getKey()) + p.getValue() : p.getValue());
      }

      return resources;
   }
   //----------------------------------------------------------

   @Override
   public void notifyStateChange() {
      //observerList.stream().forEach(x -> x.fai la display della canvas del player che ha cambiato stato (cioè this playerstate));

   }


}
