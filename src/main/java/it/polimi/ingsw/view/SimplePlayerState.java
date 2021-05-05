package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utility.Pair;

import java.sql.Array;
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
      this.leaderLevels = new ArrayList<>(this.MAX_SPECIAL_LEVELS);
      this.cardSlot1 = new ArrayList<>();
      this.cardSlot2 = new ArrayList<>();
      this.cardSlot3 = new ArrayList<>();
   }


}
