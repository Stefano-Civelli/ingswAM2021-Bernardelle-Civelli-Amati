package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.ResourceType;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * contains resources used in gui
 */
public class GuiResources {

   public static double marbleRadius = 16;
   public static double cardHeight = 138;
   public static double cardWidth = 90;
   public static double trackCrossHeight = 31;
   public static double trackCrossWidth = 32;
   public static double resourcesDimension = 30;
   public static String playerboardFXML = "fxml/playerboard.fxml";

   //NOTE: card images are contained in the JSON files

   //images
   protected static Image lorenzoMoveTrackToken = new Image(GuiResources.class.getResource("/images/punchboard/cerchio5.png").toString());
   protected static Image lorenzoShuffleToken = new Image(GuiResources.class.getResource("/images/punchboard/cerchio7.png").toString());
   protected static Image coin = new Image(GuiResources.class.getResource("/images/punchboard/coin.png").toString());
   protected static Image stone = new Image(GuiResources.class.getResource("/images/punchboard/stone.png").toString());
   protected static Image shield = new Image(GuiResources.class.getResource("/images/punchboard/shield.png").toString());
   protected static Image servant = new Image(GuiResources.class.getResource("/images/punchboard/servant.png").toString());
   public static Image popeFavorFront1 = new Image(GuiResources.class.getResource("/images/punchboard/pope_favor1_front.png").toString());
   public static Image popeFavorFront2 = new Image(GuiResources.class.getResource("/images/punchboard/pope_favor2_front.png").toString());
   public static Image popeFavorFront3 = new Image(GuiResources.class.getResource("/images/punchboard/pope_favor3_front.png").toString());
   public static Image faithTrackCross = new Image(GuiResources.class.getResource("/images/punchboard/faithTrackCross.png").toString());
   public static Image lorenzoFaithTrackCross = new Image(GuiResources.class.getResource("/images/punchboard/croce.png").toString());
   public static Image marketArrow = new Image(GuiResources.class.getResource("/images/MaterialArrowFilled.png").toString());
   public static Image redMarketArrow = new Image(GuiResources.class.getResource("/images/MaterialArrowFilledHover.png").toString());

   protected static Map<Integer, Image> deckTokensMap = Map.of(
           0, new Image(GuiResources.class.getResource("/images/punchboard/cerchio1.png").toString()),
           1, new Image(GuiResources.class.getResource("/images/punchboard/cerchio2.png").toString()),
           2, new Image(GuiResources.class.getResource("/images/punchboard/cerchio3.png").toString()),
           3, new Image(GuiResources.class.getResource("/images/punchboard/cerchio4.png").toString())
   );

   public static Map<ResourceType, Image> resTypeToImageMap = Map.of(
           ResourceType.GOLD, new Image(GuiResources.class.getResource("/images/punchboard/coin.png").toString()),
           ResourceType.STONE, new Image(GuiResources.class.getResource("/images/punchboard/stone.png").toString()),
           ResourceType.SERVANT, new Image(GuiResources.class.getResource("/images/punchboard/servant.png").toString()),
           ResourceType.SHIELD, new Image(GuiResources.class.getResource("/images/punchboard/shield.png").toString())
   );

}
