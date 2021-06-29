package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.LocalVirtualView;
import it.polimi.ingsw.controller.NetworkVirtualView;
import it.polimi.ingsw.model.modelexceptions.InvalidUsernameException;
import it.polimi.ingsw.model.modelexceptions.MaximumNumberOfPlayersException;
import it.polimi.ingsw.model.modelexceptions.NoConnectedPlayerException;
import it.polimi.ingsw.network.server.Match;
import it.polimi.ingsw.network.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void addPlayerTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        assertEquals(game.getOrderedPlayers(), List.of());
        game.addPlayer("test1");
        assertEquals(game.getOrderedPlayers(), List.of("test1"));
        game.addPlayer("test2");
        assertEquals(game.getOrderedPlayers(), List.of("test1", "test2"));
        game.addPlayer("test3");
        assertEquals(game.getOrderedPlayers(), List.of("test1", "test2", "test3"));
        game.addPlayer("test4");
        assertEquals(game.getOrderedPlayers(), List.of("test1", "test2", "test3", "test4"));
    }

    @Test
    void addMaxPlayerTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        assertThrows(MaximumNumberOfPlayersException.class, () -> game.addPlayer("test5"));
    }

    @Test
    void getOrderedPlayersTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        List<String> players = game.getOrderedPlayers();
        assertEquals("test1", players.get(0));
        assertEquals("test2", players.get(1));
        assertEquals("test3", players.get(2));
        assertEquals("test4", players.get(3));
        assertTrue(players.remove("test1"));
        assertTrue(players.remove("test2"));
        assertTrue(players.remove("test3"));
        assertTrue(players.remove("test4"));
        assertTrue(players.isEmpty());
    }

    @Test
    void getPlayerBoardTest() throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        assertEquals("test1", game.getPlayerBoard("test1").getUsername());
        assertEquals("test2", game.getPlayerBoard("test2").getUsername());
        assertEquals("test2", game.getPlayerBoard("test2").getUsername());
    }

    @Test
    void getPlayerBoardInvalidUserTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        assertThrows(InvalidUsernameException.class, () -> game.getPlayerBoard("test").getUsername());
    }

    @Test
    void startGameTest() throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        String player = game.startGame();
        List<String> players = game.getOrderedPlayers();
        assertTrue(game.isFirstConnected(player));
        game.initialMoveForward();
        assertEquals(player, players.get(0));
        assertEquals(0, game.getPlayerBoard(players.get(0)).getTrack().getCurrentPosition());
        assertEquals(0, game.getPlayerBoard(players.get(1)).getTrack().getCurrentPosition());
        assertEquals(1, game.getPlayerBoard(players.get(2)).getTrack().getCurrentPosition());
        assertEquals(1, game.getPlayerBoard(players.get(3)).getTrack().getCurrentPosition());
    }

    @Test
    void playerConnectionTest()
            throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException, NoConnectedPlayerException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        assertTrue(game.isPlayerConnected("test1"));
        assertTrue(game.isPlayerConnected("test2"));
        assertTrue(game.isPlayerConnected("test3"));
        game.disconnectPlayer("test2");
        assertTrue(game.isPlayerConnected("test1"));
        assertFalse(game.isPlayerConnected("test2"));
        assertTrue(game.isPlayerConnected("test3"));
        game.disconnectPlayer("test2");
        assertTrue(game.isPlayerConnected("test1"));
        assertFalse(game.isPlayerConnected("test2"));
        assertTrue(game.isPlayerConnected("test3"));
        game.reconnectPlayer("test2");
        assertTrue(game.isPlayerConnected("test1"));
        assertTrue(game.isPlayerConnected("test2"));
        assertTrue(game.isPlayerConnected("test3"));
        game.reconnectPlayer("test2");
        assertTrue(game.isPlayerConnected("test1"));
        assertTrue(game.isPlayerConnected("test2"));
        assertTrue(game.isPlayerConnected("test3"));
        assertThrows(InvalidUsernameException.class, () -> game.isPlayerConnected("test"));
        assertThrows(InvalidUsernameException.class, () -> game.reconnectPlayer("test"));
        assertThrows(InvalidUsernameException.class, () -> game.disconnectPlayer("test"));
    }

    @Test
    void isFirstTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        game.startGame();
        List<String> players = game.getOrderedPlayers();
        assertTrue(game.isFirstConnected(players.get(0)));
        assertFalse(game.isFirstConnected(players.get(1)));
        assertFalse(game.isFirstConnected(players.get(2)));
        assertFalse(game.isFirstConnected(players.get(3)));
    }

    @Test
    void initialResourcesTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        game.startGame();
        List<String> players = game.getOrderedPlayers();
        assertEquals(0, game.initialResources(players.get(0)));
        assertEquals(1, game.initialResources(players.get(1)));
        assertEquals(1, game.initialResources(players.get(2)));
        assertEquals(2, game.initialResources(players.get(3)));
    }

    @Test
    void initialFaithTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        game.startGame();
        List<String> players = game.getOrderedPlayers();
        assertEquals(0, game.initialFaith(players.get(0)));
        assertEquals(0, game.initialFaith(players.get(1)));
        assertEquals(1, game.initialFaith(players.get(2)));
        assertEquals(1, game.initialFaith(players.get(3)));
    }

    @Test
    void nextPlayerTest() throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.startGame();
        List<String> players = game.getOrderedPlayers();
        assertEquals(players.get(1), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(0), game.nextConnectedPlayer(players.get(2)));
    }

    @Test
    void nextPlayerConnectionTest()
            throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException, NoConnectedPlayerException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.addPlayer("test4");
        game.startGame();
        List<String> players = game.getOrderedPlayers();

        game.disconnectPlayer(players.get(1));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(2)));
        assertEquals(players.get(0), game.nextConnectedPlayer(players.get(3)));

        game.disconnectPlayer(players.get(0));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(2)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(3)));

        game.disconnectPlayer(players.get(2));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(2)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(3)));

        game.reconnectPlayer(players.get(2));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(2)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(3)));

        game.reconnectPlayer(players.get(0));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(2)));
        assertEquals(players.get(0), game.nextConnectedPlayer(players.get(3)));

        game.reconnectPlayer(players.get(1));
        assertEquals(players.get(1), game.nextConnectedPlayer(players.get(0)));
        assertEquals(players.get(2), game.nextConnectedPlayer(players.get(1)));
        assertEquals(players.get(3), game.nextConnectedPlayer(players.get(2)));
        assertEquals(players.get(0), game.nextConnectedPlayer(players.get(3)));
    }

    @Test
    void nextPlayerInvalidUserTest() throws IOException, MaximumNumberOfPlayersException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.addPlayer("test2");
        game.addPlayer("test3");
        game.startGame();
        assertThrows(InvalidUsernameException.class, () -> game.nextConnectedPlayer("test"));
    }

    @Test
    void endGameTest() throws IOException, MaximumNumberOfPlayersException, InvalidUsernameException {
        Game game = new Game(null);
        game.addPlayer("test1");
        game.startGame();
        game.getPlayerBoard("test1").getTrack().moveForward(24);

        //needs to throw this exception because in reality a controller is needed to proceed,
        //for thesting purposes the controller is not needed
        assertThrows(NullPointerException.class, () -> game.nextConnectedPlayer("test1"));
    }
}
