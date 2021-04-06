package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCard;
import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.modelexceptions.RowOrColumnNotExistsException;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTokenTest {

    @Test
    void useTokenTest() throws IOException, RowOrColumnNotExistsException {
        DevelopCardDeck deck = GSON.cardParser(new File("src/DevelopCardConfig.json"));
        DevelopCard card = deck.getCard(0, DevelopCardColor.BLUE.getColumn()),
                cardU = deck.getCard(1, DevelopCardColor.BLUE.getColumn());
        DevelopCard cardG0 = deck.getCard(0, DevelopCardColor.GREEN.getColumn()),
                cardY0 = deck.getCard(0, DevelopCardColor.YELLOW.getColumn()),
                cardP0 = deck.getCard(0, DevelopCardColor.PURPLE.getColumn()),
                cardG1 = deck.getCard(1, DevelopCardColor.GREEN.getColumn()),
                cardY1 = deck.getCard(1, DevelopCardColor.YELLOW.getColumn()),
                cardP1 = deck.getCard(1, DevelopCardColor.PURPLE.getColumn()),
                cardG2 = deck.getCard(2, DevelopCardColor.GREEN.getColumn()),
                cardY2 = deck.getCard(2, DevelopCardColor.YELLOW.getColumn()),
                cardP2 = deck.getCard(2, DevelopCardColor.PURPLE.getColumn());

        ActionToken token = new DiscardToken(DevelopCardColor.BLUE);
        token.useToken(null, null, deck);
        assertNotEquals(card, deck.getCard(0, DevelopCardColor.BLUE.getColumn()));
        assertEquals(cardU, deck.getCard(1, DevelopCardColor.BLUE.getColumn()));

        deck.removeCard(deck.getCard(0, DevelopCardColor.BLUE.getColumn()));
        card = deck.getCard(1, DevelopCardColor.BLUE.getColumn());
        cardU = deck.getCard(2, DevelopCardColor.BLUE.getColumn());
        token.useToken(null, null, deck);
        assertNotEquals(card, deck.getCard(1, DevelopCardColor.BLUE.getColumn()));
        assertEquals(cardU, deck.getCard(2, DevelopCardColor.BLUE.getColumn()));

        token.useToken(null, null, deck);
        deck.removeCard(deck.getCard(1, DevelopCardColor.BLUE.getColumn()));
        card = deck.getCard(2, DevelopCardColor.BLUE.getColumn());
        token.useToken(null, null, deck);
        assertNotEquals(card, deck.getCard(2, DevelopCardColor.BLUE.getColumn()));

        assertEquals(cardG0, deck.getCard(0, DevelopCardColor.GREEN.getColumn()));
        assertEquals(cardY0, deck.getCard(0, DevelopCardColor.YELLOW.getColumn()));
        assertEquals(cardP0, deck.getCard(0, DevelopCardColor.PURPLE.getColumn()));
        assertEquals(cardG1, deck.getCard(1, DevelopCardColor.GREEN.getColumn()));
        assertEquals(cardY1, deck.getCard(1, DevelopCardColor.YELLOW.getColumn()));
        assertEquals(cardP1, deck.getCard(1, DevelopCardColor.PURPLE.getColumn()));
        assertEquals(cardG2, deck.getCard(2, DevelopCardColor.GREEN.getColumn()));
        assertEquals(cardY2, deck.getCard(2, DevelopCardColor.YELLOW.getColumn()));
        assertEquals(cardP2, deck.getCard(2, DevelopCardColor.PURPLE.getColumn()));
    }

    @Test
    void nullPointerTest() {
        assertThrows(NullPointerException.class, () -> new DiscardToken(null));
    }

    @Test
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void equalsTest() {
        assertEquals(new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.BLUE));
        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.GREEN));
        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.PURPLE));
        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.YELLOW));
        assertEquals(new DiscardToken(DevelopCardColor.GREEN), new DiscardToken(DevelopCardColor.GREEN));
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), new DiscardToken(DevelopCardColor.BLUE));
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), new DiscardToken(DevelopCardColor.PURPLE));
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), new DiscardToken(DevelopCardColor.YELLOW));
        assertEquals(new DiscardToken(DevelopCardColor.PURPLE), new DiscardToken(DevelopCardColor.PURPLE));
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), new DiscardToken(DevelopCardColor.BLUE));
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), new DiscardToken(DevelopCardColor.GREEN));
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), new DiscardToken(DevelopCardColor.YELLOW));
        assertEquals(new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.YELLOW));
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.BLUE));
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.GREEN));
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), new DiscardToken(DevelopCardColor.PURPLE));

        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), new ShuffleToken());
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), new ShuffleToken());
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), new ShuffleToken());
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), new ShuffleToken());

        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), new StepForwardToken());
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), new StepForwardToken());
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), new StepForwardToken());
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), new StepForwardToken());

        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), new Object());
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), new Object());
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), new Object());
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), new Object());

        assertNotEquals(new DiscardToken(DevelopCardColor.BLUE), null);
        assertNotEquals(new DiscardToken(DevelopCardColor.GREEN), null);
        assertNotEquals(new DiscardToken(DevelopCardColor.PURPLE), null);
        assertNotEquals(new DiscardToken(DevelopCardColor.YELLOW), null);
    }

}
