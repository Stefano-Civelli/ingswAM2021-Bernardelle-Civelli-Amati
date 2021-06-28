package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.LocalVirtualView;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.MarbleModifierBehaviour;
import it.polimi.ingsw.model.leadercard.StorageBehaviour;
import it.polimi.ingsw.model.modelexceptions.*;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.network.client.LocalVirtualModel;
import it.polimi.ingsw.network.messages.ErrorType;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.utility.GSON;

import java.io.IOException;
import java.util.*;

import it.polimi.ingsw.view.gui.GuiResources;
import javafx.scene.image.Image;
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
      Map resourcesMap = Map.of(
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
   }

   @Test
   void notAllowedActionsTest() throws IOException, InvalidUsernameException, MaximumNumberOfPlayersException, NoConnectedPlayerException {
      Game game = new SinglePlayer(null);
      TurnManager turnManager = new TurnManager(game, Collections.singletonList("pippo"));
      turnManager.startGame();
      PlayerBoard playerboard =  turnManager.getGame().getPlayerBoard("pippo");

      Message returnedMessage = this.turnManager.handleAction(new DiscardInitialLeaderAction("pippo", 1, 2));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = this.turnManager.handleAction(new ActivateLeaderAction("pippo", 1));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      returnedMessage = this.turnManager.handleAction(new BaseProductionAction("pippo", ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);



      //returnedMessage = this.turnManager.handleAction(new BuyDevelopCardAction("pippo", ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);

      //returnedMessage = this.turnManager.handleAction(new ChooseInitialResourcesAction("pippo", ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(removeVirgolette(returnedMessage.getPayload())), ErrorType.WRONG_ACTION);
   }


   private String removeVirgolette(String string){
      String modifiedString = string.replaceAll("\"", "");
      return modifiedString;
   }

   @Test
   void BaseProductionactionTest() throws NegativeQuantityException, AbuseOfFaithException, NoConnectedPlayerException {

      Message returnedMessage = this.turnManager.handleAction(new BaseProductionAction("pippo", ResourceType.GOLD, ResourceType.GOLD, ResourceType.GOLD));
      assertEquals(returnedMessage.getMessageType(), MessageType.ERROR);
      assertEquals(ErrorType.fromValue(returnedMessage.getPayload()), ErrorType.WRONG_RESOURCES_NUMBER);

      playerBoard.getChest().addResources(ResourceType.GOLD,50);
      playerBoard.getChest().addResources(ResourceType.STONE,50);
      playerBoard.getChest().addResources(ResourceType.SERVANT,50);
      playerBoard.getChest().addResources(ResourceType.SHIELD,50);
      playerBoard.getChest().endOfTurnMapsMerge();
      //this.turnManager.handleAction(new BaseProductionAction());

   }

}