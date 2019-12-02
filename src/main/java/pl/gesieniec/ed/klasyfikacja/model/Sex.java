package pl.gesieniec.ed.klasyfikacja.model;

public enum Sex {
    MEZCZYZNA(0), KOBIETA(1);

    private final int value;

    Sex(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

