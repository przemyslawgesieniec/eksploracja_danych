package pl.gesieniec.ed.segmentacja;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import pl.gesieniec.ed.common.FileReader;
import pl.gesieniec.ed.common.Mappers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Segmentation {

    private static final String SEGMENTATION_FILE_PATH = "C:/studia/eksploracja_danych/src/main/resources/segmentacja.txt";
    private static final Integer NUMBER_OF_GROUPS = 5;

    public static void main(String[] args) throws IOException {

        final Map<Integer, Double> plotDataWC = new HashMap<>();
        final Map<Integer, Double> plotDataDC = new HashMap<>();

        final List<Point> points = FileReader.readFile(SEGMENTATION_FILE_PATH, Mappers.mapToPoints);

        final Map<Integer, Point> randomCenters = getRandomCenters(points);

        Map<Integer, List<Point>> firstClassifiedPoints = classifyPoints(points, randomCenters);

        int iterationCounter = 0;
        boolean hasChanged = true;
        while (hasChanged) {

            iterationCounter++;
            final Map<Integer, Point> newCenters = firstClassifiedPoints
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> calculateCenter.apply(e.getValue()))
                    );

            final Map<Integer, List<Point>> newGroups = classifyPoints(points, newCenters);

            final Double wcFactor = calculateWcFactor(newCenters, newGroups);
            final Double dcFactor = calculateDcFactor(newGroups);

            hasChanged = !newGroups.entrySet()
                                   .stream()
                                   .flatMap(entry -> entry.getValue().stream())
                                   .noneMatch(Point::hasGroupChange);

            if (hasChanged) {
                firstClassifiedPoints = classifyPoints(points, newCenters);
            }

            plotDataWC.put(iterationCounter, wcFactor);
            plotDataDC.put(iterationCounter, dcFactor);
            System.out.println("In iteration " + iterationCounter + " WC Factor is " + wcFactor + "and hasChanged=" + hasChanged);
        }
        drawChart(plotDataWC, "WC");
        drawChart(plotDataDC, "DC");
    }


    private static Map<Integer, List<Point>> classifyPoints(final List<Point> points,
                                                            Map<Integer, Point> centers) {

        final Map<Integer, List<Point>> mapOfPoints = getMapOfEmptyPoints(NUMBER_OF_GROUPS);
        Objects.requireNonNull(points).forEach(point -> {
            final Integer groupId = classifyPointToCenter(centers, point);
            point.setCurrentGroup(groupId);
            mapOfPoints.get(groupId).add(point);
        });
        return mapOfPoints;
    }

    private static Integer classifyPointToCenter(final Map<Integer, Point> centers,
                                                 final Point point) {

        final Map<Integer, Double> collect = centers
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> calculateEuclideanDistance.apply(
                                e.getValue().coordinates,
                                point.coordinates)
                        )
                );

        return collect.entrySet()
                      .stream()
                      .min(Comparator.comparing(Map.Entry::getValue))
                      .orElseThrow(IllegalArgumentException::new)
                      .getKey();
    }

    private static Map<Integer, Point> getRandomCenters(final List<Point> points) {
        final Map<Integer, Point> pointMap = new HashMap<>();
        for (int i = 0; i < NUMBER_OF_GROUPS; i++) {
            pointMap.put(i, points.get(new Random().nextInt(points.size())));
        }
        return pointMap;
    }

    private static BiFunction<List<Double>, List<Double>, Double> calculateEuclideanDistance =
            (p1, p2) -> {
                double sum = 0;
                for (int i = 0; i < p1.size(); i++) {
                    sum += (p1.get(i) - p2.get(i)) * (p1.get(i) - p2.get(i));
                }
                return Math.sqrt(sum);
            };

    private static Map<Integer, List<Point>> getMapOfEmptyPoints(final Integer size) {
        final Map<Integer, List<Point>> hashMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            hashMap.put(i, new ArrayList<>());
        }
        return hashMap;
    }

    private static Function<List<Point>, Point> calculateCenter =
            points -> points.stream()
                            .reduce(Point::add)
                            .map(point -> point.divide((double) points.size()))
                            .orElseThrow(ArithmeticException::new);

    private static Double calculateWcFactor(final Map<Integer, Point> centers,
                                            final Map<Integer, List<Point>> groups) {

        double wc = 0;

        for (Map.Entry<Integer, List<Point>> entry : groups.entrySet()) {
            Integer k = entry.getKey();
            List<Point> v = entry.getValue();
            for (Point point : v) {
                final Double distance = calculateEuclideanDistance.apply(centers.get(k).getCoordinates(), point.getCoordinates());
                wc += distance * distance;
            }
        }

        return wc;
    }

    private static Double calculateDcFactor(Map<Integer, List<Point>> groups) {

        double bc = 0;
        for (Integer k : groups.keySet()) {
            for (Point point : groups.get(k)) {
                double sumOfCoordinates = 0;
                for (Double coordinate : point.getCoordinates()) {
                    sumOfCoordinates += coordinate;
                }
                bc += sumOfCoordinates / (double) groups.get(k).size();
            }
        }
        return bc;
    }


    private static void drawChart(final Map<Integer, Double> plotData,
                                  final String name) throws IOException {
        XYChart chart = new XYChart(800, 600);
        List<Integer> x = new ArrayList<>(plotData.keySet());
        List<Double> y = new ArrayList<>(plotData.values());
        chart.addSeries("segmentation" + name, x, y);
        BitmapEncoder.saveBitmap(chart, "segmentation_plot" + name, BitmapEncoder.BitmapFormat.PNG);
    }


}
