package pl.gesieniec.ed.segmentacja;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {

    final List<Double> coordinates;
    private Integer currentGroup;
    private Integer previousGroup;

    public Point(final List<Double> coordinates) {
        this.coordinates = coordinates;
        this.previousGroup = -1;
    }

    public Integer getCurrentGroup() {
        return currentGroup;
    }

    public Integer getPreviousGroup() {
        return previousGroup;
    }

    public void setCurrentGroup(final Integer currentGroup) {
        this.previousGroup = this.currentGroup;
        this.currentGroup = currentGroup;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Point point = (Point) o;
        return Objects.equals(coordinates, point.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }

    public Point add(final Point externalPoint) {
        final List<Double> newPoint = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            newPoint.add(coordinates.get(i) + externalPoint.getCoordinates().get(i));
        }
        return new Point(newPoint);
    }

    public Point divide(final Double divisor) {
        final List<Double> newPoint = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            newPoint.add(coordinates.get(i) / divisor);
        }
        return new Point(newPoint);
    }

    public Point multiply(final Point externalPoint) {
        final List<Double> newPoint = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            newPoint.add(coordinates.get(i) * externalPoint.getCoordinates().get(i));
        }
        return new Point(newPoint);
    }

    public Point substract(final Point externalPoint){
        final List<Double> newPoint = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            newPoint.add(coordinates.get(i) - externalPoint.getCoordinates().get(i));
        }
        return new Point(newPoint);
    }

    public boolean hasGroupChange(){
        return !currentGroup.equals(previousGroup);
    }
}
