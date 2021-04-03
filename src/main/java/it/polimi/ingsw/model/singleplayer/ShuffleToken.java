package it.polimi.ingsw.model.singleplayer;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.modeltest.tracktest.LorenzoTrack;

import java.util.Collections;
import java.util.List;

public class ShuffleToken implements ActionToken {

    public ShuffleToken() {}

    @Override
    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck) {
        Collections.shuffle(tokens);
        trackLorenzo.moveForward(1);
    }

}
