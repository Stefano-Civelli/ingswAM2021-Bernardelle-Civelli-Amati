package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.ResourceType;

/**
 * The CardBehaviour that represents the producing behaviour: a leader card with this behaviour will convert white market marbles
 */
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
