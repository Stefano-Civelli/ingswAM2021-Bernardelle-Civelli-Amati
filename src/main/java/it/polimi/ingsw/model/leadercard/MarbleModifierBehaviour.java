package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.ResourceType;

public class MarbleModifierBehaviour extends CardBehaviour {

   private final ResourceType onWhite;

   public MarbleModifierBehaviour(ResourceType resourceOnWhite) {
      this.onWhite = resourceOnWhite;
   }

   @Override
   public ResourceType getOnWhite() {
      return onWhite;
   }
}
