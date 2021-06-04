package it.polimi.ingsw.model.singleplayer;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelObservables.ModelObservable;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.Collections;
import java.util.List;

/**
 * Represents Lorenzo's action tokens that shuffle action token's deck and move foreword of one position Lorenzo's faith marker in a single player game
 */
public class ShuffleToken implements ActionToken, ModelObservable {

    private ModelObserver controller;
    public ShuffleToken() {}

    public ShuffleToken(ModelObserver controller) {
        this.controller = controller;
    }

    /**
     * Perform the token action: shuffle the token's deck and move foreword of one position Lorenzo's faith marker
     *
     * @param tokens the token's deck to shuffle
     * @param trackLorenzo Lorenzo's track Lorenzo's track used to move foreword Lorenzo's faith marker
     * @param deck this parameter will be ignored in this type of token
     */
    @Override
    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck) {
        trackLorenzo.moveForward(1);
        Collections.shuffle(tokens);
        notifyModelChange("");
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public void notifyModelChange(String msg) {
        if(this.controller != null)
            controller.lorenzoShuffleUpdate();
    }
}

