package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class Market {

    private static Market instance;

    private MarketMarble slide;
    private final MarketMarble[][] marbles;


    private Market() {
        final int nRow = 3,
                nColumn = 4;
        this.marbles = new MarketMarble[nRow][nColumn];
        List<MarketMarble> marbles= new ArrayList<>(Arrays.asList(
                new WhiteMarble(), new WhiteMarble(), new WhiteMarble(), new WhiteMarble(),
                new NormalMarble(ResourceType.SHIELD), new NormalMarble(ResourceType.SHIELD),
                new NormalMarble(ResourceType.STONE), new NormalMarble(ResourceType.STONE),
                new NormalMarble(ResourceType.GOLD), new NormalMarble(ResourceType.GOLD),
                new NormalMarble(ResourceType.SERVANT), new NormalMarble(ResourceType.SERVANT),
                new RedMarble()
        ));
        Collections.shuffle(marbles);
        Iterator<MarketMarble> marblesIterator = marbles.iterator();
        for(int i = 0; i < this.marbles.length; i++)
            for(int j = 0; j < this.marbles[i].length; j++)
                this.marbles[i][j] = marblesIterator.next();
        this.slide = marblesIterator.next();
    }

    public static Market getInstance() {
        if(Market.instance != null)
            return  Market.instance;
        Market.instance = new Market();
        return  Market.instance;
    }

    private List<MarketMarble> getRow(int row) {
        return new ArrayList<>(Arrays.asList(this.marbles[row]));
    }

    private  List<MarketMarble> getColumn(int column) {
        return Arrays.stream(this.marbles).map(i -> i[column]).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<MarketMarble> pushInRow(int row) {
        List<MarketMarble> marbles = this.getRow(row);
        MarketMarble swap1,
                swap2;
        swap1 = this.marbles[row][this.marbles[row].length - 1];
        this.marbles[row][this.marbles[row].length - 1] = this.slide;
        for(int i = this.marbles[row].length - 2; i >= 0; i--) {
            swap2 = this.marbles[row][i];
            this.marbles[row][i] = swap1;
            swap1 = swap2;
        }
        this.slide = swap1;
        return marbles;
    }

    public List<MarketMarble> pushInColumn(int column) {
        List<MarketMarble> marbles = this.getColumn(column);
        MarketMarble swap1,
                swap2;
        swap1 = this.marbles[this.marbles.length - 1][column];
        this.marbles[this.marbles.length - 1][column] = this.slide;
        for(int i = this.marbles.length - 2; i >= 0; i--) {
            swap2 = this.marbles[i][column];
            this.marbles[i][column] = swap1;
            swap1 = swap2;
        }
        this.slide = swap1;
        return marbles;
    }

    public MarketMarble[][] getStatus() {
        return this.marbles.clone();
    }

}
