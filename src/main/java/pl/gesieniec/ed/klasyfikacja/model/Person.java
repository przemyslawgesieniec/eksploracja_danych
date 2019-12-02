package pl.gesieniec.ed.klasyfikacja.model;

import pl.gesieniec.ed.common.Mappers;
import java.util.List;

public class Person {

    private String name;
    private String surname;
    private Sex sex;
    private Earnings earnings;
    private Education education;
    private ContractType contractType;
    private Age age;
    private Boolean isCreditGiven;
    private List<Integer> point;


    public Person(final String name,
                  final String surname,
                  final Sex sex,
                  final Earnings earnings,
                  final Education education,
                  final ContractType contractType,
                  final Age age,
                  final Boolean isCreditGiven) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.earnings = earnings;
        this.education = education;
        this.contractType = contractType;
        this.age = age;
        this.isCreditGiven = isCreditGiven;
        this.point = Mappers.mapPersonToPoints.apply(this);

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Sex getSex() {
        return sex;
    }

    public Earnings getEarnings() {
        return earnings;
    }

    public Education getEducation() {
        return education;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public Age getAge() {
        return age;
    }

    public Boolean getCreditGiven() {
        return isCreditGiven;
    }

    public List<Integer> getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", earnings=" + earnings +
                ", education=" + education +
                ", contractType=" + contractType +
                ", age=" + age +
                ", isCreditGiven=" + isCreditGiven +
                '}';
    }
}
