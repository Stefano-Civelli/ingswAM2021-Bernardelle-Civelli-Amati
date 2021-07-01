package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.List;

/**
 * Represents Lorenzo's action tokens that move foreword of two position Lorenzo's faith marker in a single player game
 */
public class StepForwardToken implements ActionToken {

    public StepForwardToken() {}

    /**
     * Perform the token action: move foreword of two position Lorenzo's faith marker
     *
     * @param tokens this parameter will be ignored in this type of token
     * @param trackLorenzo Lorenzo's track Lorenzo's track used to move foreword Lorenzo's faith marker
     * @param deck this parameter will be ignored in this type of token
     */
    @Override
    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck) {
        trackLorenzo.moveForward(2);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }
}