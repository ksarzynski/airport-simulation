package airport.app.person;

/**
 * pracownik ogolnie, zawierajacy metody i zmienne ktore dziedzicza poszczegolni pracownicy
 * zmienna o nazwie efficiency odpowiada za wydajnosc pracownika, pracownik o wyzszej wydajnosci
 * spowoduje obsluzenie wiekszej ilosci pasazerow np ( w kasie )
 */
public class Employee extends Person{
    private Integer efficiency;

    Employee(String name, Integer efficiency) {
        super(name);
        this.efficiency = efficiency;
    }

    public Integer getEfficiency()
    {
        return this.efficiency;
    }
}

