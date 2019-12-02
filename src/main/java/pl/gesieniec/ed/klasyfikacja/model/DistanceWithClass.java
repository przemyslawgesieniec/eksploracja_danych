package pl.gesieniec.ed.klasyfikacja.model;

public class DistanceWithClass implements Comparable {

    private Double relativeDistance;
    private Boolean group;

    public DistanceWithClass(final Double relativeDistance,
                             final Boolean group) {
        this.relativeDistance = relativeDistance;
        this.group = group;
    }

    public Double getRelativeDistance() {
        return relativeDistance;
    }

    public Boolean getGroup() {
        return group;
    }

    @Override
    public int compareTo(final Object o) {
        return (int) Math.ceil(this.relativeDistance - ((DistanceWithClass) o).relativeDistance);
    }

    @Override
    public String toString() {
        return "DistanceWithClass{" +
                "relativeDistance=" + relativeDistance +
                ", group=" + group +
                '}';
    }
}
