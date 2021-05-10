package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplePlayerState {

   private final int NUMBER_OF_NORMAL_LEVELS = 3;
   private final int MAX_SPECIAL_LEVELS = 2;

   private int trackPosition;
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
   }

   public Pair<ResourceType, Integer>[] getWarehouseLevels() {
      return warehouseLevels;
   }

   public void warehouseUpdate(Warehouse.WarehouseUpdate update) {
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

   //TODO clonare
   public Map<ResourceType, Integer> getChest() {
      return chest;
   }
}
