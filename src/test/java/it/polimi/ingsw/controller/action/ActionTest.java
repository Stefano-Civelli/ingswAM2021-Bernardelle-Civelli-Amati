package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.model.updatecontainers.TurnState;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ActionTest {

   private PlayerBoard playerBoard;
   private TurnManager turnManager;


   @BeforeEach
   void setUp() throws InvalidUsernameException, IOException, MaximumNumberOfPlayersException, NoConnectedPlayerException {

      Game game = new SinglePlayer(null);
      this.turnManager = new TurnManager(game, Collections.singletonList("pippo"));
      this.turnManager.startGame();
      this.playerBoard =  this.turnManager.getGame().getPlayerBoard("pippo");
      Map<ResourceType, Integer> resourcesMap = Map.of(
              ResourceType.GOLD, 1,
              ResourceType.SERVANT, 2
      );

      Message returnedMessage = this.turnManager.handleAction(new ChooseInitialResourcesAction("pippo", resourcesMap));

      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_RESOURCES_NUMBER);

      this.turnManager.handleAction(new ChooseInitialResourcesAction("pippo", new HashMap<>()));
      int leaderId1 = playerBoard.getLeaderCards().get(0).getLeaderId();
      int leaderId2 = playerBoard.getLeaderCards().get(1).getLeaderId();
      this.turnManager.handleAction(new DiscardInitialLeaderAction("pippo", leaderId1, leaderId2));
      assertEquals(playerBoard.getLeaderCards().size(), 2);

      returnedMessage = this.turnManager.handleAction(new ChooseInitialResourcesAction("pippo", new HashMap<>()));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);


   }

   @Test
   void disconnectionActionTest() throws IOException, MaximumNumberOfPlayersException, NoConnectedPlayerException {
      assertThrows(NoConnectedPlayerException.class, () -> this.turnManager.handleAction(new PlayerDisconnectionAction("pippo")));

      Game game = new Game(null);
      TurnManager localTurnManager = new TurnManager(game, List.of("pippo", "pluto"));
      localTurnManager.startGame();
      if(game.isFirst("pippo")) {
         Message returnedMessage = localTurnManager.handleAction(new PlayerDisconnectionAction("pippo"));
         assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
         assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pluto");
         assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.SETUP_CHOOSING_RESOURCES);
      }
      else
      {
         assertNull(localTurnManager.handleAction(new PlayerDisconnectionAction("pippo")));
      }
   }

   @Test
   void notAllowedActionsTest() throws IOException, MaximumNumberOfPlayersException, NoConnectedPlayerException {
      Game game = new SinglePlayer(null);
      TurnManager turnManager = new TurnManager(game, Collections.singletonList("pippo"));
      turnManager.startGame();

      Message returnedMessage = turnManager.handleAction(new DiscardInitialLeaderAction("pippo", 1, 2));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new ActivateLeaderAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new BaseProductionAction("pippo", ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new BuyDevelopCardAction("pippo", 1,2,3));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new ChooseLeaderOnWhiteMarbleAction("pippo", 2));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new DiscardLeaderAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new EndTurnAction("pippo"));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new InsertMarbleAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new LeaderProductionAction("pippo", 1, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new ProductionAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = turnManager.handleAction(new ShopMarketAction("pippo", true, 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);
   }

   @Test
   void wrongParametersActionTest() throws NoConnectedPlayerException, IOException, MaximumNumberOfPlayersException {
      Game game = new SinglePlayer(null);
      TurnManager turnManager = new TurnManager(game, Collections.singletonList("pippo"));
      turnManager.startGame();

      Message returnedMessage = turnManager.handleAction(new DiscardInitialLeaderAction( 1, 2));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new ActivateLeaderAction( 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new BaseProductionAction( ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new BuyDevelopCardAction( 1,2,3));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new ChooseLeaderOnWhiteMarbleAction( 2));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new DiscardLeaderAction( 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new EndTurnAction(null));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new InsertMarbleAction( 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new LeaderProductionAction( 1, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new ProductionAction( 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);

      returnedMessage = turnManager.handleAction(new ShopMarketAction( true, 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.INVALID_ACTION);
   }

   @Test
   void BaseProductionActionTest() throws NegativeQuantityException, AbuseOfFaithException, NoConnectedPlayerException {

      Message returnedMessage = this.turnManager.handleAction(new BaseProductionAction("pippo", ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.NOT_ENOUGH_RESOURCES);

      playerBoard.getChest().addResources(ResourceType.GOLD,50);
      playerBoard.getChest().addResources(ResourceType.STONE,50);
      playerBoard.getChest().addResources(ResourceType.SERVANT,50);
      playerBoard.getChest().addResources(ResourceType.SHIELD,50);
      playerBoard.getChest().endOfTurnMapsMerge();
      returnedMessage = this.turnManager.handleAction(new BaseProductionAction("pippo", ResourceType.GOLD, ResourceType.SERVANT, ResourceType.STONE));
      assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pippo");
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.PRODUCING);
      playerBoard.getChest().endOfTurnMapsMerge();
      assertEquals(playerBoard.getChest().getNumberOf(ResourceType.GOLD), 50-1);
      assertEquals(playerBoard.getChest().getNumberOf(ResourceType.SERVANT), 50-1);
      assertEquals(playerBoard.getChest().getNumberOf(ResourceType.STONE), 50+1);
   }

   @Test
   void shopMarketActionTest() throws NoConnectedPlayerException {

      Message returnedMessage = this.turnManager.handleAction(new ShopMarketAction("pippo", true, 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pippo");
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.SHOPPING);

      returnedMessage = this.turnManager.handleAction(new InsertMarbleAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pippo");
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.SHOPPING);

      this.turnManager.handleAction(new InsertMarbleAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pippo");
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.SHOPPING);

      this.turnManager.handleAction(new InsertMarbleAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pippo");
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.SHOPPING);

      returnedMessage = this.turnManager.handleAction(new InsertMarbleAction("pippo", 0));
      assertEquals(returnedMessage.getMessageType(), MessageType.NEXT_TURN_STATE);
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPlayer(), "pippo");
      assertEquals(returnedMessage.getPayloadByType(TurnState.class).getPhase(), PhaseType.FINAL);

   }

   private String removeVirgolette(String string){
      return string.replaceAll("\"", "");
   }

}