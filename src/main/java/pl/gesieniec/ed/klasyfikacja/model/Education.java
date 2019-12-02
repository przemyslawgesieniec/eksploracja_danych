package pl.gesieniec.ed.klasyfikacja.model;

public enum Education {
    PODSTAWOWE(0), SREDNIE(1), WYZSZE(2);

    private final int value;

    Education(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
