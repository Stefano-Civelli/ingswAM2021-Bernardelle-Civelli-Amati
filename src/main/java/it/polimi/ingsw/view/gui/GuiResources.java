package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.ResourceType;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Contains resources used in gui
 * (NOTE: card images are contained in the JSON files and loaded by their specific classes)
 */
public final class GuiResources {

   public final static double marbleRadius = 16;
   public final static double cardHeight = 138;
   public final static double cardWidth = 90;
   public final static double trackCrossHeight = 31;
   public final static double trackCrossWidth = 32;
   public final static double resourcesDimension = 30;

   //fxml files
   public final static String connectionFXML = "fxml/connection.fxml";
   public final static String loginFXML = "fxml/login.fxml";
   public final static String numberOfPlayerFXML = "fxml/numberOfPlayer.fxml";
   public final static String lobbyFXML = "fxml/lobby.fxml";
   public final static String gameboardFXML = "fxml/gameboard.fxml";
   public final static String playerboardFXML = "fxml/playerboard.fxml";
   public final static String endGameWinFXML = "fxml/endGameWin.fxml";
   public final static String endGameLoseFXML = "fxml/endGameLose.fxml";

   //NOTE: card images are contained in the JSON files

   //images
   protected final static Image logo = new Image(GuiResources.class.getResource("/images/Logo.png").toString());
   protected final static Image lorenzoMoveTrackToken = new Image(GuiResources.class.getResource("/images/punchboard/cerchio5.png").toString());
   protected final static Image lorenzoShuffleToken = new Image(GuiResources.class.getResource("/images/punchboard/cerchio7.png").toString());
   protected final static Image coin = new Image(GuiResources.class.getResource("/images/punchboard/coin.png").toString());
   protected final static Image stone = new Image(GuiResources.class.getResource("/images/punchboard/stone.png").toString());
   protected final static Image shield = new Image(GuiResources.class.getResource("/images/punchboard/shield.png").toString());
   protected final static Image servant = new Image(GuiResources.class.getResource("/images/punchboard/servant.png").toString());
   public final static Image popeFavorFront1 = new Image(GuiResources.class.getResource("/images/punchboard/pope_favor1_front.png").toString());
   public final static Image popeFavorFront2 = new Image(GuiResources.class.getResource("/images/punchboard/pope_favor2_front.png").toString());
   public final static Image popeFavorFront3 = new Image(GuiResources.class.getResource("/images/punchboard/pope_favor3_front.png").toString());
   public final static Image faithTrackCross = new Image(GuiResources.class.getResource("/images/punchboard/faithTrackCross.png").toString());
   public final static Image lorenzoFaithTrackCross = new Image(GuiResources.class.getResource("/images/punchboard/croce.png").toString());
   public final static Image marketArrow = new Image(GuiResources.class.getResource("/images/MaterialArrowFilled.png").toString());
   public final static Image redMarketArrow = new Image(GuiResources.class.getResource("/images/MaterialArrowFilledHover.png").toString());

   protected final static Map<Integer, Image> deckTokensMap = Map.of(
           0, new Image(GuiResources.class.getResource("/images/punchboard/cerchio1.png").toString()),
           1, new Image(GuiResources.class.getResource("/images/punchboard/cerchio2.png").toString()),
           2, new Image(GuiResources.class.getResource("/images/punchboard/cerchio3.png").toString()),
           3, new Image(GuiResources.class.getResource("/images/punchboard/cerchio4.png").toString())
   );

   public final static Map<ResourceType, Image> resTypeToImageMap = Map.of(
           ResourceType.GOLD, new Image(GuiResources.class.getResource("/images/punchboard/coin.png").toString()),
           ResourceType.STONE, new Image(GuiResources.class.getResource("/images/punchboard/stone.png").toString()),
           ResourceType.SERVANT, new Image(GuiResources.class.getResource("/images/punchboard/servant.png").toString()),
           ResourceType.SHIELD, new Image(GuiResources.class.getResource("/images/punchboard/shield.png").toString())
   );

   private GuiResources() {
      // private constructor to prevent instances of this class (a class can't be final and abstract in Java).
   }

}
