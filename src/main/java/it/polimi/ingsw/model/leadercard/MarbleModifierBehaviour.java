package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.market.MarketMarble;

import java.util.function.Supplier;

public class MarbleModifierBehaviour extends CardBehaviour {

   private ResourceType onWhite;

   public MarbleModifierBehaviour(ResourceType resourceOnWhite) {
      this.onWhite = resourceOnWhite;
   }

   @Override
   public ResourceType getOnWhite() {
      return onWhite;
   }
}
