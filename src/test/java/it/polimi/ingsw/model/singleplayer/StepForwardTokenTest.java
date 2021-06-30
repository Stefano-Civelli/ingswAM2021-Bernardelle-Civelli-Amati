package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.track.Track;
import it.polimi.ingsw.utility.GSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StepForwardTokenTest {

    @Test
    void useTokenTest() throws IOException {
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
        lorenzTrack.moveForward(position - 2);
        assertEquals(points0, lorenzTrack.calculateTrackScore());

        ActionToken token = new StepForwardToken();
        token.useToken(null, lorenzTrack, null);

        assertEquals(points1, lorenzTrack.calculateTrackScore());
    }

    @Test
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    void equalsTest() {
        assertEquals(new StepForwardToken(), new StepForwardToken());
        assertNotEquals(new StepForwardToken(), new DiscardToken(DevelopCardColor.BLUE));
        assertNotEquals(new StepForwardToken(), new DiscardToken(DevelopCardColor.GREEN));
        assertNotEquals(new StepForwardToken(), new DiscardToken(DevelopCardColor.PURPLE));
        assertNotEquals(new StepForwardToken(), new DiscardToken(DevelopCardColor.YELLOW));
        assertNotEquals(new StepForwardToken(), new ShuffleToken());
        assertNotEquals(new StepForwardToken(), new Object());
        assertNotEquals(new StepForwardToken(), null);
    }

}
