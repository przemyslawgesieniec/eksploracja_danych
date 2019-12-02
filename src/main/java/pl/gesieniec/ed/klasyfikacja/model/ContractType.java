package pl.gesieniec.ed.klasyfikacja.model;

public enum ContractType {
    BEZROBOTNY(0),
    UMOWA_O_DZIELO(10),
    UMOWA_NA_CZAS_OKRESLONY(20),
    SAMOZATRUDNIENIE(30),
    UMOWA_NA_CZAS_NIEOKRESLONY(40);

    private final int value;

    ContractType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
