package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.track.LorenzoTrack;

import java.util.List;

/**
 * Represents Lorenzo's action tokens that discard two development cards of the same color in a single player game
 */
public class DiscardToken implements ActionToken {

    private final DevelopCardColor color;

    public DiscardToken(DevelopCardColor color) {
        if(color == null)
            throw new NullPointerException();
        this.color = color;
    }

    /**
     * Perform the token action: discard two development cards of the same color
     *
     * @param tokens this parameter will be ignored in this type of token
     * @param trackLorenzo this parameter will be ignored in this type of token
     * @param deck the development cards' deck in which cards must be removed
     */
    @Override
    public void useToken(List<ActionToken> tokens, LorenzoTrack trackLorenzo, DevelopCardDeck deck) {
        deck.RemoveTwoCards(this.color);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        return this.color == ((DiscardToken) obj).color;
    }

}
