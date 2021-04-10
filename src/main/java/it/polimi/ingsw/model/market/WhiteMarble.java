package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.*;

import java.util.Optional;

public class WhiteMarble extends MarketMarble{

    public WhiteMarble(){}

    // FARE CHE CONTROLLANO TUTTE LE LEADERCARD ONWHITE E SE DUE RITORNANO NON NULL ECCEZIONE!!
    /**
     * Adds the resources owed to the player due to this marble
     *
     * @param leaderCard the resource type in which convert this white marble
     * @throws NotEnoughSpaceException in this level of warehouse there isn't enough space
     */
    @Override
    public void addResource(InterfacePlayerBoard playerBoard, Optional<LeaderCard> leaderCard)
            throws NotEnoughSpaceException {
        if(leaderCard == null)
            throw new NullPointerException();
        if(leaderCard.isPresent()) {

        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }
}
