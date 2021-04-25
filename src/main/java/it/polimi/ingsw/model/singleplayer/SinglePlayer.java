package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.track.LorenzoTrack;
import it.polimi.ingsw.utility.GSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SinglePlayer extends Game {
   private final List<ActionToken> actionTokenStack;
   private final LorenzoTrack lorenzoTrack;
   private final File lorenzoTrackConfigFile = new File("src/SquareConfig.json");

   public SinglePlayer() throws IOException {
      super();
      this.lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);

      this.actionTokenStack = new ArrayList<ActionToken>(Arrays.asList(
              new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.GREEN),
              new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.PURPLE),
              new ShuffleToken(), new StepForwardToken(), new StepForwardToken()
      ));
   }

}

