package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShuffleTokenTest {

    @Test
    void useTokenTest() throws IOException {
        List<ActionToken> tokens = new ArrayList<>(Arrays.asList(
                new DiscardToken(DevelopCardColor.BLUE), new DiscardToken(DevelopCardColor.GREEN),
                new DiscardToken(DevelopCardColor.PURPLE), new DiscardToken(DevelopCardColor.YELLOW),
                new ShuffleToken(), new ShuffleToken(), new StepForwardToken()
        ));
        Collections.shuffle(tokens);
        List<ActionToken> tokensTmp = new ArrayList<>(tokens);

        Track lorenzTrack = GSON.trackParser();
        int points0 = lorenzTrack.getTrack()[0].getVictoryPoints(),
                points1 = 0,
                position = 0;
        for(int i = 0; i < lorenzTrack.getTrack().length; i++)
            if(lorenzTrack.getTrack()[i].getVictoryPoints() > points0) {
                points1 = lorenzTrack.getTrack()[i].getVictoryPoints();
                position = i;
                break;
            }
        lorenzTrack.moveForward(position - 1);
        assertEquals(points0, lorenzTrack.calculateTrackScore());

        ActionToken token = new ShuffleToken();
        token.useToken(tokens, lorenzTrack, null);

        assertEquals(points1, lorenzTrack.calculateTrackScore());
        assertNotEquals(tokensTmp, tokens);
    }

    @Test
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void equalsTest() {
        assertEquals(new ShuffleToken(), new ShuffleToken());
        assertNotEquals(new ShuffleToken(), new DiscardToken(DevelopCardColor.BLUE));
        assertNotEquals(new ShuffleToken(), new DiscardToken(DevelopCardColor.GREEN));
        assertNotEquals(new ShuffleToken(), new DiscardToken(DevelopCardColor.PURPLE));
        assertNotEquals(new ShuffleToken(), new DiscardToken(DevelopCardColor.YELLOW));
        assertNotEquals(new ShuffleToken(), new StepForwardToken());
        assertNotEquals(new ShuffleToken(), new Object());
        assertNotEquals(new ShuffleToken(), null);
    }

}
