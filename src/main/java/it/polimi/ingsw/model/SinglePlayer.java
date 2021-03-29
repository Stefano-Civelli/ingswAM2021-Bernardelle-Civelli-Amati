package it.polimi.ingsw.model;

import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.track.LorenzoTrack;
import java.util.*;

public class SinglePlayer extends Game {
   private final List<ActionToken> actionTokenStack;
   private final LorenzoTrack lorenzoTrack;

   public SinglePlayer(){
      this.actionTokenStack = new ArrayList<>();
      this.lorenzoTrack = new LorenzoTrack();
   }

}
