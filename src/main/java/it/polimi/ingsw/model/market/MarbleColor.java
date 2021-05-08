package it.polimi.ingsw.model.market;

import it.polimi.ingsw.view.cli.Color;

public enum MarbleColor {
    GREY ( Color.ANSI_GREY.escape() + "⚫"),
    YELLOW (Color.ANSI_YELLOW.escape() + "⚫"),
    BLUE (Color.ANSI_BLUE.escape() + "⚫"),
    PURPLE (Color.ANSI_PURPLE.escape() + "⚫"),
    RED (Color.ANSI_RED.escape() + "⚫"),
    WHITE (Color.RESET.escape() + "⚫");

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
