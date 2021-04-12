package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderProduceBehaviour;
import it.polimi.ingsw.model.leadercard.MarbleModifierBehaviour;
import it.polimi.ingsw.model.leadercard.StorageBehaviour;
import it.polimi.ingsw.model.modelexceptions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;

class WhiteMarbleTest {

    @Test
    void addNoLeaderCardsTest() throws IOException, NotEnoughSpaceException, MoreWhiteLeaderCardsException {
        InterfacePlayerBoard playerBoard = new PlayerBoard(
                "test", new ArrayList<>(), null, null);
        MarketMarble marble = new WhiteMarble();
        marble.addResource(playerBoard, Optional.empty());
        assertEquals(0, playerBoard.getWarehouse().totalResources());
    }

    @Test
    void addNoWhiteLeaderTest() throws IOException, NotEnoughSpaceException, MoreWhiteLeaderCardsException,
            NotEnoughResourcesException, InvalidLeaderCardException, AbuseOfFaithException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new StorageBehaviour(ResourceType.GOLD));
        InterfacePlayerBoard playerBoard1 = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1)), null, null);

        MarketMarble marble = new WhiteMarble();

        marble.addResource(playerBoard1, Optional.empty());
        assertEquals(0, playerBoard1.getWarehouse().totalResources());

        card1.setActive(playerBoard1);
        marble.addResource(playerBoard1, Optional.empty());
        assertEquals(0, playerBoard1.getWarehouse().totalResources());

        LeaderCard card2 = new LeaderCard(
                null, null, 0, new LeaderProduceBehaviour(ResourceType.SERVANT));
        InterfacePlayerBoard playerBoard2 = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1, card2)), null, null);
        playerBoard2.getWarehouse().addResource(ResourceType.SERVANT);

        marble.addResource(playerBoard2, Optional.empty());
        assertEquals(1, playerBoard2.getWarehouse().totalResources());

        card2.setActive(playerBoard2);
        marble.addResource(playerBoard2, Optional.empty());
        assertEquals(1, playerBoard2.getWarehouse().totalResources());
    }

    @Test
    void addNoActiveWhiteLeaderTest() throws IOException, NotEnoughSpaceException,
            MoreWhiteLeaderCardsException, AbuseOfFaithException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
        InterfacePlayerBoard playerBoard1 = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1)), null, null);
        playerBoard1.getWarehouse().addResource(ResourceType.GOLD);

        MarketMarble marble = new WhiteMarble();

        marble.addResource(playerBoard1, Optional.empty());
        assertEquals(1, playerBoard1.getWarehouse().totalResources());

        LeaderCard card2 = new LeaderCard(
                null, null, 0, new LeaderProduceBehaviour(ResourceType.SERVANT));
        InterfacePlayerBoard playerBoard2 = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1, card2)), null, null);
        playerBoard2.getWarehouse().addResource(ResourceType.SERVANT);

        marble.addResource(playerBoard2, Optional.empty());
        assertEquals(1, playerBoard2.getWarehouse().totalResources());
    }

    @Test
    void oneActiveWhiteLeaderTest() throws IOException, NotEnoughSpaceException,
            MoreWhiteLeaderCardsException, NotEnoughResourcesException, InvalidLeaderCardException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
        InterfacePlayerBoard playerBoard1 = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1)), null, null);
        card1.setActive(playerBoard1);

        MarketMarble marble = new WhiteMarble();

        marble.addResource(playerBoard1, Optional.empty());
        assertEquals(1, playerBoard1.getWarehouse().totalResources());
        assertEquals(1, playerBoard1.getWarehouse().getNumberOf(ResourceType.GOLD));

        LeaderCard card2 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.SERVANT));
        InterfacePlayerBoard playerBoard2 = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1, card2)), null, null);

        marble.addResource(playerBoard2, Optional.empty());
        assertEquals(1, playerBoard2.getWarehouse().totalResources());
        assertEquals(1, playerBoard1.getWarehouse().getNumberOf(ResourceType.GOLD));
    }

    @Test
    void twoActiveWhiteLeaderTest() throws IOException, NotEnoughResourcesException,
            InvalidLeaderCardException, NotEnoughSpaceException, MoreWhiteLeaderCardsException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
        LeaderCard card2 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.SERVANT));
        InterfacePlayerBoard playerBoard = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1, card2)), null, null);
        card1.setActive(playerBoard);
        card2.setActive(playerBoard);

        MarketMarble marble = new WhiteMarble();

        assertThrows(MoreWhiteLeaderCardsException.class, () -> marble.addResource(playerBoard, Optional.empty()));
        assertEquals(0, playerBoard.getWarehouse().totalResources());

        marble.addResource(playerBoard, Optional.of(card1));
        assertEquals(1, playerBoard.getWarehouse().totalResources());
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));

        marble.addResource(playerBoard, Optional.of(card2));
        assertEquals(2, playerBoard.getWarehouse().totalResources());
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.SERVANT));
    }

    @Test
    void faithLeaderTest() throws IOException, NotEnoughResourcesException,
            InvalidLeaderCardException, NotEnoughSpaceException, MoreWhiteLeaderCardsException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.FAITH));
        InterfacePlayerBoard playerBoard = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1)), null, null);
        card1.setActive(playerBoard);

        MarketMarble marble = new WhiteMarble();

        int points0 = playerBoard.getTrack().getTrack()[0].getVictoryPoints(),
                points1 = 0,
                position = 0;
        for(int i = 0; i < playerBoard.getTrack().getTrack().length; i++)
            if(playerBoard.getTrack().getTrack()[i].getVictoryPoints() > points0) {
                points1 = playerBoard.getTrack().getTrack()[i].getVictoryPoints();
                position = i;
                break;
            }
        playerBoard.getTrack().moveForward(position - 1);
        assertEquals(points0, playerBoard.getTrack().calculateTrackScore());
        marble.addResource(playerBoard, Optional.empty());
        assertEquals(points1, playerBoard.getTrack().calculateTrackScore());
    }

    @Test
    void faithTwoLeaderTest() throws IOException, NotEnoughResourcesException, InvalidLeaderCardException,
            NotEnoughSpaceException, MoreWhiteLeaderCardsException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
        LeaderCard card2 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.FAITH));
        InterfacePlayerBoard playerBoard = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1, card2)), null, null);
        card1.setActive(playerBoard);
        card2.setActive(playerBoard);

        MarketMarble marble = new WhiteMarble();

        assertThrows(MoreWhiteLeaderCardsException.class, () -> marble.addResource(playerBoard, Optional.empty()));
        assertEquals(0, playerBoard.getWarehouse().totalResources());

        marble.addResource(playerBoard, Optional.of(card1));
        assertEquals(1, playerBoard.getWarehouse().totalResources());
        assertEquals(1, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));

        int points0 = playerBoard.getTrack().getTrack()[0].getVictoryPoints(),
                points1 = 0,
                position = 0;
        for(int i = 0; i < playerBoard.getTrack().getTrack().length; i++)
            if(playerBoard.getTrack().getTrack()[i].getVictoryPoints() > points0) {
                points1 = playerBoard.getTrack().getTrack()[i].getVictoryPoints();
                position = i;
                break;
            }
        playerBoard.getTrack().moveForward(position - 1);
        assertEquals(points0, playerBoard.getTrack().calculateTrackScore());
        marble.addResource(playerBoard, Optional.of(card2));
        assertEquals(points1, playerBoard.getTrack().calculateTrackScore());
    }

    @Test
    void noSpaceTest() throws NotEnoughResourcesException, InvalidLeaderCardException, AbuseOfFaithException,
            NotEnoughSpaceException, IOException {
        LeaderCard card1 = new LeaderCard(
                null, null, 0, new MarbleModifierBehaviour(ResourceType.GOLD));
        InterfacePlayerBoard playerBoard = new PlayerBoard(
                "test", new ArrayList<>(List.of(card1)), null, null);
        card1.setActive(playerBoard);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);
        playerBoard.getWarehouse().addResource(ResourceType.GOLD);

        MarketMarble marble = new WhiteMarble();
        assertThrows(NotEnoughSpaceException.class, () -> marble.addResource(playerBoard, Optional.of(card1)));
        assertEquals(3, playerBoard.getWarehouse().totalResources());
        assertEquals(3, playerBoard.getWarehouse().getNumberOf(ResourceType.GOLD));
    }

    @Test
    @SuppressWarnings("all") // For null value for Optional type, I can't find the specific string
    void nullLeaderTest() throws IOException {
        InterfacePlayerBoard playerBoard = new PlayerBoard(
                "test", new ArrayList<>(), null, null);
        MarketMarble marble = new WhiteMarble();
        assertThrows(NullPointerException.class, () -> marble.addResource(playerBoard, null));
    }

    @Test
    @SuppressWarnings({"AssertBetweenInconvertibleTypes"})
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
