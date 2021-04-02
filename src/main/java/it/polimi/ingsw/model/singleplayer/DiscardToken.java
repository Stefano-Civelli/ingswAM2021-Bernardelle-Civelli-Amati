package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardColor;
import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.List;

public class DiscardToken implements ActionToken {

    private DevelopCardColor color;

    public DiscardToken(DevelopCardColor color) {
        this.color = color;
    }

    @Override
    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck) {

    }

}
