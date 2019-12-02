package pl.gesieniec.ed.klasyfikacja.model;

public enum Earnings {
    NISKIE(5), SREDNIE(10), WYSOKIE(15);

    private final int value;

    Earnings(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
