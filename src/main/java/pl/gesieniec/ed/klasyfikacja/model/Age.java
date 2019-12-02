package pl.gesieniec.ed.klasyfikacja.model;

public enum Age {
    od_20_do_30(25),
    od_30_do_40(35),
    od_40_do_50(45),
    od_50_do_60(55),
    powyzej_60(65);

    private final int value;

    Age(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
