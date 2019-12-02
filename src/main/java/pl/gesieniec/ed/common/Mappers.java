package pl.gesieniec.ed.common;

import pl.gesieniec.ed.klasyfikacja.model.Age;
import pl.gesieniec.ed.klasyfikacja.model.ContractType;
import pl.gesieniec.ed.klasyfikacja.model.DistanceWithClass;
import pl.gesieniec.ed.klasyfikacja.model.Earnings;
import pl.gesieniec.ed.klasyfikacja.model.Education;
import pl.gesieniec.ed.klasyfikacja.model.Person;
import pl.gesieniec.ed.klasyfikacja.model.Sex;
import pl.gesieniec.ed.klasyfikacja.model.TestPoint;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Mappers {

    public static Function<String, Person> mapToPerson =
            line -> {
                final List<String> fields = Arrays.asList(line.split(" "));
                final String isCreditGivenString = String.valueOf(fields.get(7));
                final Boolean isCreditGiven = isCreditGivenString.equals("null") ? null : Boolean.valueOf(isCreditGivenString);

                return new Person(fields.get(0),
                        fields.get(1),
                        Enum.valueOf(Sex.class, fields.get(2)),
                        Enum.valueOf(Earnings.class, fields.get(3)),
                        Enum.valueOf(Education.class, fields.get(4)),
                        Enum.valueOf(ContractType.class, fields.get(5)),
                        Enum.valueOf(Age.class, fields.get(6)),
                        isCreditGiven);
            };

    public static Function<Person, List<Integer>> mapPersonToPoints =
            person -> Arrays.asList(
                    person.getAge().getValue(),
                    person.getContractType().getValue(),
                    person.getEarnings().getValue(),
                    person.getSex().getValue(),
                    person.getEducation().getValue());

}
