package it.polimi.ingsw.model.modelexceptions;

public class WrongResourceNumberException extends ModelException {

    private int expected,
            actual;

    public WrongResourceNumberException() {
        super();
    }

    public WrongResourceNumberException(String message) {
        super(message);
    }

    public WrongResourceNumberException(int expected, int actual) {
        this.expected = expected;
        this.actual = actual;
    }

    public WrongResourceNumberException(String message, int expected, int actual) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    public int getExpected() {
        return expected;
    }

    public int getActual() {
        return actual;
    }

}
