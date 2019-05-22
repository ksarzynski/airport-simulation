package main.java.airport.app.place;

public class DutyFreeZone extends Place {

    private Integer flow;

    public DutyFreeZone(String name, Integer maxPeopleAmount, Integer flow)

    {
        super(name, maxPeopleAmount);
        this.flow = flow;
    }

    public Integer getFlow()
    {
        return this.flow;
    }

    @Override
    public void movePassengers() {
        //TODO method
    }
}
