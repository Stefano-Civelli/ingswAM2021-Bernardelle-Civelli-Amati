package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.List;

public class DiscardToken implements ActionToken {

    private final DevelopCardColor color;

    public DiscardToken(DevelopCardColor color) {
        this.color = color;
    }

    @Override
    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck) {
        deck.RemoveTwoCards(this.color);
    }

}
