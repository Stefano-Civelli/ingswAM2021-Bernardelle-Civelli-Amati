package it.polimi.ingsw.model.market;

import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.cli.Color;

public enum MarbleColor {
    GREY ( Color.ANSI_GREY.escape() + ConfigParameters.marbleCharacter + Color.RESET.escape()),
    YELLOW (Color.ANSI_YELLOW.escape() + ConfigParameters.marbleCharacter + Color.RESET.escape()),
    BLUE (Color.ANSI_BLUE.escape() + ConfigParameters.marbleCharacter + Color.RESET.escape()),
    PURPLE (Color.ANSI_PURPLE.escape() + ConfigParameters.marbleCharacter + Color.RESET.escape()),
    RED (Color.ANSI_RED.escape() + ConfigParameters.marbleCharacter + Color.RESET.escape()),
    WHITE (Color.RESET.escape() + ConfigParameters.marbleCharacter + Color.RESET.escape());

    private String escape;

    MarbleColor(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }

    @Override
    public String toString(){
        return this.escape;
    }
}
