package airport.app.person;

/**
 *  klasa rodzic dla kazdej klasy "ludzkiej" ktora dziedziczy po niej imie
 */
abstract class Person {
    private String name;

    Person(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
