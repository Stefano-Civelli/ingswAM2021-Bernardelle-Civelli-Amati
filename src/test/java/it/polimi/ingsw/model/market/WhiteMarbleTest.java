package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardDeck;
import it.polimi.ingsw.model.modelexceptions.*;

import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class WhiteMarbleTest {

    @Test
    void addNoLeaderCardsTest() throws IOException, NotEnoughSpaceException, MoreWhiteLeaderCardsException {
        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);
        MarketMarble marble = new WhiteMarble();
        marble.addResource(playerBoard, Optional.empty());
        assertEquals(0, playerBoard.getWarehouse().totalResources());
    }

//    @Test
//    void addNoWhiteLeaderTest() throws IOException, NotEnoughResourcesException, InvalidLeaderCardException {
//        List<LeaderCard> cards = GSON.leaderCardParser(new File("src/LeaderCardConfig.json")).getLeaderCardList();
//        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);
//        LeaderCard card = null;
//        for(LeaderCard currentCard : cards) {
//            currentCard.setActive(playerBoard);
//            if(currentCard.resourceOnWhite() == null)
//
//        }
//    }



//    @Test
//    void addResourceTest() throws AbuseOfFaithException, IncorrectResourceTypeException, NegativeQuantityException,
//            LevelNotExistsException, NotEnoughSpaceException {
//        InterfacePlayerBoard playerBoard = new PlayerBoard("test", new ArrayList<>(), null, null);
//
//        MarketMarble marble = new WhiteMarble();
//        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
//        marble.addResource(playerBoard, 0, ResourceType.GOLD);
//        assertEquals(2, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));
//
//        marble.addResource(playerBoard, 1, ResourceType.SERVANT);
//        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.SERVANT));
//
//        int total = playerBoard.getWarehouse().totalResources();
//        marble.addResource(playerBoard, 2, null);
//        assertEquals(total, playerBoard.getWarehouse().totalResources());
//
//        int points0 = playerBoard.getTrack().getTrack()[0].getVictoryPoints(),
//                points1 = 0,
//                position = 0;
//        for(int i = 0; i < playerBoard.getTrack().getTrack().length; i++)
//            if(playerBoard.getTrack().getTrack()[i].getVictoryPoints() > points0) {
//                points1 = playerBoard.getTrack().getTrack()[i].getVictoryPoints();
//                position = i;
//                break;
//            }
//        playerBoard.getTrack().moveForward(position - 1);
//        assertEquals(points0, playerBoard.getTrack().calculateTrackScore());
//        marble.addResource(playerBoard, 1, ResourceType.FAITH);
//        assertEquals(0, playerBoard.getWarehouse().getNumberOf(ResourceType.FAITH));
//        assertEquals(points1, playerBoard.getTrack().calculateTrackScore());
//
//    }

    @Test
    @SuppressWarnings({"Possible", "AssertBetweenInconvertibleTypes"})
    void testEqualsTest() throws AbuseOfFaithException {
        assertEquals(new WhiteMarble(), new WhiteMarble());
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.SHIELD));
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.STONE));
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.SERVANT));
        assertNotEquals(new WhiteMarble(), new NormalMarble(ResourceType.GOLD));
        assertNotEquals(new WhiteMarble(), new RedMarble());
        assertNotEquals(new WhiteMarble(), new Object());
        assertNotEquals(new WhiteMarble(), null);
    }
}
