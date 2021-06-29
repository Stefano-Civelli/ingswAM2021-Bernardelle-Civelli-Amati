package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerTest {

   @Test
   void singlePlayerEndGameTest() throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException {
      Game game = new SinglePlayer(null);
      game.addPlayer("test1");
      game.startGame();
      game.gameStarted();
      game.getPlayerBoard("test1").getTrack().moveForward(24);

      //needs to throw this exception because in reality a controller is needed to proceed,
      //for thesting purposes the controller is not needed
      assertThrows(NullPointerException.class, () -> game.nextConnectedPlayer("test1"));
   }
}