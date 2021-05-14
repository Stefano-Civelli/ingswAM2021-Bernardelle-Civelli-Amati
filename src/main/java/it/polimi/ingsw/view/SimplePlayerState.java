package it.polimi.ingsw.view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.market.MarbleColor;
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
   private final List<Integer> cardSlot1;
   private final List<Integer> cardSlot2;
   private final List<Integer> cardSlot3;

   public SimplePlayerState() {
      this.trackPosition = 0;
      this.chest = new HashMap<>();
      this.tempChest = new HashMap<>();
      this.warehouseLevels = new Pair[this.NUMBER_OF_NORMAL_LEVELS];

      this.leaderLevels = new ArrayList<>(this.MAX_SPECIAL_LEVELS);
      for (int i=0; i<MAX_SPECIAL_LEVELS; i++)
         this.leaderLevels.add(new Pair<>(null, null));

      this.cardSlot1 = new ArrayList<>();
      this.cardSlot2 = new ArrayList<>();
      this.cardSlot3 = new ArrayList<>();

      this.vaticanFlipped = new boolean[3];
      for(int i=0; i<3; i++)
         vaticanFlipped[i] = false;
   }

   public void setupLeaderCard(String payload){
      this.leaderCards = GSON.getGsonBuilder().fromJson(payload, List.class);
   }

   public Pair<ResourceType, Integer>[] getWarehouseLevels() {
      return warehouseLevels;
   }

   public void warehouseUpdate(String payload) {
      Warehouse.WarehouseUpdate update = GSON.getGsonBuilder().fromJson(payload, Warehouse.WarehouseUpdate.class);

      ResourceType resource = update.getResourceType();

      //normalLevels
      if(update.getLevel()<3) {
         //controllo se la risorsa é presente
         for (int i = 0; i < warehouseLevels.length; i++) {
            Pair<ResourceType, Integer> level = warehouseLevels[i];

            if (level != null) {
               if (level.getKey().equals(resource)) {
                  if (level.equals(update.getLevel())) {
                     level = new Pair<>(resource, update.getQuantity());
                     return;
                  } else {
                     if(warehouseLevels[update.getLevel()] == null) {
                        warehouseLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                        level = null;
                        return;
                     }
                     Pair<ResourceType, Integer> temp = new Pair<>(warehouseLevels[update.getLevel()].getKey(), warehouseLevels[update.getLevel()].getValue());
                     warehouseLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                     level = new Pair<>(temp.getKey(), temp.getValue());
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
      Type token = new TypeToken<Pair<Integer, Boolean>>(){}.getType();
      Pair<Integer, Boolean> pair = GSON.getGsonBuilder().fromJson(payload, token);
      int zone = pair.getKey();
      boolean flip = pair.getValue();

      vaticanFlipped[zone] = flip;
   }

   private void chestUpdate(String payload) {
      Type token = new TypeToken<Pair<ResourceType, Integer>>(){}.getType();
      Pair<ResourceType, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      ResourceType resource = pair.getKey();
      int quantity = pair.getValue();

      for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
         if(resource.equals(entry.getKey()))
            chest.put(resource, quantity);
   }

   private void tempChestUpdate(String payload) {
      Type token = new TypeToken<Pair<ResourceType, Integer>>(){}.getType();
      Pair<ResourceType, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      ResourceType resource = pair.getKey();
      int quantity = pair.getValue();

      for(Map.Entry<ResourceType, Integer> entry : tempChest.entrySet())
         if(resource.equals(entry.getKey()))
            tempChest.put(resource, quantity);
   }

   private void cardSlotUpdate(String payload) {
      Type token = new TypeToken<Pair<Integer, Integer>>(){}.getType();
      Pair<Integer, Integer> pair = GSON.getGsonBuilder().fromJson(payload, token);
      int devCardID = pair.getKey();
      int slot = pair.getValue();

      if(slot == 0)
         cardSlot1.add(devCardID);
      if(slot == 1)
         cardSlot2.add(devCardID);
      if(slot == 2)
         cardSlot3.add(devCardID);
   }

   //TODO clonare
   public Map<ResourceType, Integer> getChest() {
      return chest;
   }

   public void mergeTempChest() {
      for(Map.Entry<ResourceType, Integer> entry : tempChest.entrySet()){
         chest.put(entry.getKey(), chest.containsKey(entry.getKey()) ? chest.get(entry.getKey()) + entry.getValue() : entry.getValue());
      }
      this.tempChest.clear();
   }

   /**
    * delete a leadercard from the SimplePlayerState
    *
    * @param indexOfLeaderToDiscard
    */
   public void discardLeader(int indexOfLeaderToDiscard){
      leaderCards.remove(indexOfLeaderToDiscard - 1);
   }

   public void activatedLeaderUpdate(String payload){
   }

   public List<Integer> getLeaderCards() {
      return leaderCards;
   }

   @Override
   public void notifyStateChange() {
      //observerList.stream().forEach(x -> x.fai la display della canvas del player che ha cambiato stato (cioè this playerstate));

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
}
