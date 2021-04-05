package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.DevelopCardDeck;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.List;

/**
 * The public interface of a generic action token used to perform and represent Lorenzo's actions in a single player game
 */
public interface ActionToken {

    /**
     * Perform the token action
     *
     * @param tokens the token deck (used to shuffle)
     * @param trackLorenzo Lorenzo's track (used to move foreword Lorenzo's faith marker)
     * @param deck the development cards' deck (used to discard cards)
     */
    void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck);

}
