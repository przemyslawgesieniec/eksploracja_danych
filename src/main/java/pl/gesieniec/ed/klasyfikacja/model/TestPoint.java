package pl.gesieniec.ed.klasyfikacja.model;

public class TestPoint {
    String name;
    String surname;
    DistanceWithClass distanceWithClass;

    private TestPoint(final String name,
                      final String surname,
                      final DistanceWithClass distanceWithClass) {
        this.name = name;
        this.surname = surname;
        this.distanceWithClass = distanceWithClass;
    }

    private String getName() {
        return name;
    }

    private String getSurname() {
        return surname;
    }

    private DistanceWithClass getDistanceWithClass() {
        return distanceWithClass;
    }

    @Override
    public String toString() {
        return "TestPoint{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", distanceWithClass=" + distanceWithClass +
                '}';
    }
}
