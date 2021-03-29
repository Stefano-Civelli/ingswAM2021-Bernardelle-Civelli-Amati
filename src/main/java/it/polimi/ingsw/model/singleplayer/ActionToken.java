package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.List;

public interface ActionToken {

    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck);

}
