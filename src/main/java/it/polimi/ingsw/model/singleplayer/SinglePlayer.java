package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.modeltest.tracktest.LorenzoTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SinglePlayer extends Game {
   private final List<ActionToken> actionTokenStack;
   private final LorenzoTrack lorenzoTrack;

   public SinglePlayer(){
      this.lorenzoTrack = new LorenzoTrack();

      this.actionTokenStack = new ArrayList<ActionToken>(Arrays.asList(
              new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.GREEN),
              new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.PURPLE),
              new ShuffleToken(), new StepForwardToken(), new StepForwardToken()
      ));
   }
}
