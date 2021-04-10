package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.ResourceType;

import java.util.function.Supplier;

public class MarbleModifierBehaviour extends CardBehaviour {

   private ResourceType onWhite;

   @Override
   public ResourceType getOnWhite() {
      return onWhite;
   }
}
