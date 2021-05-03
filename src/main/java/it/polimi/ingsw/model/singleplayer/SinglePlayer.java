package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.track.LorenzoTrack;
import it.polimi.ingsw.utility.GSON;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SinglePlayer extends Game {
   private final LinkedList<ActionToken> actionTokenStack; // don't change with List, it's required a LinkedList.
   private final LorenzoTrack lorenzoTrack;
   private final File lorenzoTrackConfigFile = new File("src/SquareConfig.json");

   public SinglePlayer(Controller controller) throws IOException {
      super(controller);
      this.lorenzoTrack = GSON.lorenzoTrackParser(lorenzoTrackConfigFile);

      this.actionTokenStack = new LinkedList<>(Arrays.asList(
              new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.GREEN),
              new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.PURPLE),
              new ShuffleToken(), new StepForwardToken(), new StepForwardToken()
      ));

      Collections.shuffle(this.actionTokenStack);
   }

   // TODO test, non sono sicuro che il metodo super funzioni correttamente con un solo player
   @Override
   public String nextPlayer(String currentPlayer) throws InvalidUsernameException {
      ActionToken token = this.actionTokenStack.remove();
      token.useToken(this.actionTokenStack, this.lorenzoTrack, super.developCardDeck);
      this.actionTokenStack.addLast(token);
      return super.nextPlayer(currentPlayer);
   }

}

