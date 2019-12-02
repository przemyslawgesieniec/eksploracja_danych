package pl.gesieniec.ed.klasyfikacja;

import pl.gesieniec.ed.common.FileReader;
import pl.gesieniec.ed.common.Mappers;
import pl.gesieniec.ed.klasyfikacja.model.DistanceWithClass;
import pl.gesieniec.ed.klasyfikacja.model.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Classification {

    private static final String CLASSIFICATION_FILE_PATH = "C:/studia/eksploracja_danych/src/main/resources/klasyfikacja.txt";

    private static final Integer K_NEAREST_NEIGHBOURS = 5;

    public static void classify() {

        final List<Person> people = FileReader.readFile(CLASSIFICATION_FILE_PATH, Mappers.mapToPerson);
        final List<Person> referencePeople = people.stream().filter(p -> p.getCreditGiven() != null).collect(Collectors.toList());
        final List<Person> testPeople = people.stream().filter(p -> p.getCreditGiven() == null).collect(Collectors.toList());

        final List<Person> pointsTrue = referencePeople
                .stream()
                .filter(Person::getCreditGiven)
                .collect(Collectors.toList());

        final List<Person> pointsFalse = referencePeople
                .stream()
                .filter(person -> !person.getCreditGiven())
                .collect(Collectors.toList());


        testPeople.forEach(testPoint -> {

            final List<Double> trueDistances = calculatePointDistanceFromClass(pointsTrue, testPoint.getPoint());
            final List<Double> falseDistances = calculatePointDistanceFromClass(pointsFalse, testPoint.getPoint());

            Collections.sort(trueDistances);
            Collections.sort(falseDistances);

            final List<DistanceWithClass> distanceClassTrue = trueDistances
                    .stream()
                    .limit(K_NEAREST_NEIGHBOURS)
                    .map(e -> new DistanceWithClass(e, true))
                    .collect(Collectors.toList());

            final List<DistanceWithClass> distanceClassFalse = falseDistances
                    .stream()
                    .limit(K_NEAREST_NEIGHBOURS)
                    .map(e -> new DistanceWithClass(e, false))
                    .collect(Collectors.toList());


            final List<DistanceWithClass> distanceWithClasses = new ArrayList<>();
            distanceWithClasses.addAll(distanceClassFalse);
            distanceWithClasses.addAll(distanceClassTrue);

            Collections.sort(distanceWithClasses);

            System.out.println("distanceWithClasses : AFTER sort");
            distanceWithClasses.forEach(System.out::println);

            final Boolean classificationVerdict = getClassificationVerdict(distanceWithClasses);

            System.out.println(testPoint.getName() + " classified as " + classificationVerdict);

        });

    }

    private static List<Double> calculatePointDistanceFromClass(final List<Person> classPoints,
                                                                final List<Integer> point) {
        return classPoints
                .stream()
                .map(classPoint -> calculateEuclideanDistance.apply(classPoint.getPoint(), point))
                .collect(Collectors.toList());
    }

    private static BiFunction<List<Integer>, List<Integer>, Double> calculateEuclideanDistance =
            (p1, p2) -> {
                double sum = 0;
                for (int i = 0; i < p1.size(); i++) {
                    sum += (p1.get(i) - p2.get(i)) * (p1.get(i) - p2.get(i));
                }
                return Math.sqrt(sum);
            };

    private static Boolean getClassificationVerdict(final List<DistanceWithClass> distanceWithClassesList){
        final Integer classificationValue = distanceWithClassesList
                .stream()
                .limit(K_NEAREST_NEIGHBOURS)
                .map(calculateClassificationValue)
                .reduce(0, (a, b) -> a + b);

        return classificationValue >= 0;
    }

    private static Function<DistanceWithClass,Integer> calculateClassificationValue = distanceWithClass -> {
        if(distanceWithClass.getGroup()){
            return 1;
        }
        return -1;
    };

}