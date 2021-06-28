package it.polimi.ingsw.model.market;

import it.polimi.ingsw.utility.ConfigParameters;
import it.polimi.ingsw.view.cli.Color;

/**
 * Represents the color of a market marble, it's used in json serialization
 */
public enum MarbleColor {

    GREY (Color.ANSI_GREY.escape(), ConfigParameters.marbleCharacter + Color.RESET.escape(), javafx.scene.paint.Color.GREY),
    YELLOW (Color.ANSI_YELLOW.escape(), ConfigParameters.marbleCharacter + Color.RESET.escape(), javafx.scene.paint.Color.YELLOW),
    BLUE (Color.ANSI_BLUE.escape(), ConfigParameters.marbleCharacter + Color.RESET.escape(), javafx.scene.paint.Color.BLUE),
    PURPLE (Color.ANSI_PURPLE.escape(), ConfigParameters.marbleCharacter + Color.RESET.escape(), javafx.scene.paint.Color.PURPLE),
    RED (Color.ANSI_RED.escape(), ConfigParameters.marbleCharacter + Color.RESET.escape(), javafx.scene.paint.Color.RED),
    WHITE (Color.RESET.escape(), ConfigParameters.marbleCharacter + Color.RESET.escape(), javafx.scene.paint.Color.WHITE);

    private String escape;
    private String color;
    private javafx.scene.paint.Color guiColor;

    MarbleColor(String color, String escape, javafx.scene.paint.Color guiColor) {
        this.color = color;
        this.escape = escape;
        this.guiColor = guiColor;
    }

    public String getColor() {
        return color;
    }

    public javafx.scene.paint.Color getGuiColor(){
        return this.guiColor;
    }

    @Override
    public String toString(){
        return this.color + this.escape;
    }

    /**
     * Returns the MarbleColor value associated to the String color passed as parameter
     * @param color, String that represent the color of the marble
     * @return the MarbleColor value associated to the String color passed as parameter
     */
    public static MarbleColor getMarbleColorByColor(String color){
        for (MarbleColor marbleColor : values()) {
            if (marbleColor.getColor().equals(color)) {
                return marbleColor;
            }
        }
        throw new IllegalArgumentException("invalid string value passed: " + color);
    }
}
