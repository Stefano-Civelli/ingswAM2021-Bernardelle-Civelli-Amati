package it.polimi.ingsw.view;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.market.MarbleColor;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplePlayerState {

   private final int NUMBER_OF_NORMAL_LEVELS = 3;
   private final int MAX_SPECIAL_LEVELS = 2;

   private int trackPosition;
   private boolean[] vaticanFlipped;
   private final List<Integer> leaderCards; //identified by ID
   private final Map<ResourceType, Integer> chest;
   private final Map<ResourceType, Integer> tempChest;
   private final Pair<ResourceType, Integer>[] warehouseLevels;
   private final List<Pair<ResourceType, Integer>> leaderLevels;
   private final List<Integer> cardSlot1;
   private final List<Integer> cardSlot2;
   private final List<Integer> cardSlot3;

   public SimplePlayerState() {
      this.trackPosition = 0;
      this.leaderCards = new ArrayList<>();
      this.chest = new HashMap<>();
      this.tempChest = new HashMap<>();
      this.warehouseLevels = new Pair[this.NUMBER_OF_NORMAL_LEVELS];

      for (int i=0; i<NUMBER_OF_NORMAL_LEVELS; i++)
         this.warehouseLevels[i] = new Pair<>(null, null);

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

            if (level.getKey().equals(resource)) {
               if (level.equals(update.getLevel())) {
                  level = new Pair<>(resource, update.getQuantity());
                  return;
               } else {
                  Pair<ResourceType, Integer> temp = new Pair<>(warehouseLevels[update.getLevel()].getKey(), warehouseLevels[update.getLevel()].getValue());
                  warehouseLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
                  level = new Pair<>(temp.getKey(), temp.getValue());
                  return;
               }
            }
         }
         //se non presente creo
         warehouseLevels[update.getLevel()] = new Pair<>(resource, update.getQuantity());
      }
      else //leader cards
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
      JsonObject jsonObject = (JsonObject) JsonParser.parseString(payload);
      int zone = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("key"), Integer.class);
      boolean flip = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("value").getAsString(), Boolean.class);

      if(flip)
         vaticanFlipped[zone] = flip;
   }

   private void chestUpdate(String payload) {
      JsonObject jsonObject = (JsonObject) JsonParser.parseString(payload);
      ResourceType resource = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("key"), ResourceType.class);
      int quantity = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("value"), Integer.class);

      for(Map.Entry<ResourceType, Integer> entry : chest.entrySet())
         if(resource.equals(entry.getKey()))
            chest.put(resource, quantity);
   }

   private void tempChestUpdate(String payload) {
      JsonObject jsonObject = (JsonObject) JsonParser.parseString(payload);
      ResourceType resource = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("key"), ResourceType.class);
      int quantity = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("value"), Integer.class);

      for(Map.Entry<ResourceType, Integer> entry : tempChest.entrySet())
         if(resource.equals(entry.getKey()))
            tempChest.put(resource, quantity);
   }

   private void cardSlotUpdate(String payload) {
      JsonObject jsonObject = (JsonObject) JsonParser.parseString(payload);
      int devCardID = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("key"), Integer.class);
      int slot = GSON.getGsonBuilder().fromJson(jsonObject.getAsJsonObject().get("value").getAsString(), Integer.class);

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
}
