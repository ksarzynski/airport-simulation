package airport.app.person;

public class Employee extends Person{
    private Integer efficiency;

    Employee(String name, Integer efficiency) {
        super(name);
        this.efficiency = efficiency;
    }

    public void setEfficiency(Integer efficiency)
    {
        this.efficiency = efficiency;
    }

    public Integer getEfficiency()
    {
        return this.efficiency;
    }
}
