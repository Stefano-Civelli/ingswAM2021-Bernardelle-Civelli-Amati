package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelexceptions.AbuseOfFaithException;
import it.polimi.ingsw.model.modelexceptions.RowOrColumnNotExistsException;

import java.util.*;
import java.util.stream.Collectors;

public class Market {

    private final int N_ROW = 3,
            N_COLUMN = 4;

    private MarketMarble slide;
    private final MarketMarble[][] marbles;


    public Market() {
        this.marbles = new MarketMarble[this.N_ROW][this.N_COLUMN];
        try {
            List<MarketMarble> marbles = new ArrayList<>(List.of(
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
        } catch (AbuseOfFaithException ignored) {}
    }

    public int getNumberOfRow() {
        return this.N_ROW;
    }

    public int getNumberOfColumn() {
        return this.N_COLUMN;
    }

    private List<MarketMarble> getRow(int row) throws RowOrColumnNotExistsException {
        try {
            return new ArrayList<>(Arrays.asList(this.marbles[row]));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RowOrColumnNotExistsException("This row doesn't exist in market");
        }
    }

    private  List<MarketMarble> getColumn(int column) throws RowOrColumnNotExistsException {
        try {
            return Arrays.stream(this.marbles).map(i -> i[column]).collect(Collectors.toCollection(ArrayList::new));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RowOrColumnNotExistsException("This row doesn't exist in market");
        }
    }

    /**
     * Push the marble in slide in the specified row from right
     *
     * @param row the row in which to push the marble
     * @return the row before pushing the marble
     * @throws RowOrColumnNotExistsException this row doesn't exist
     */
    public List<MarketMarble> pushInRow(int row) throws RowOrColumnNotExistsException {
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

    /**
     * Push the marble in slide in the specified row from right
     *
     * @param column the row in which to push the marble
     * @return the row before pushing the marble
     * @throws RowOrColumnNotExistsException this column doesn't exist
     */
    public List<MarketMarble> pushInColumn(int column) throws RowOrColumnNotExistsException {
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

    /**
     * Get the current arrangement of marbles in this market
     *
     * @return the marbles' arrangement
     */
    public MarketMarble[][] getStatus() {
        return this.marbles.clone();
    }

}
