package airport.app.place;

import airport.app.person.Controller;
import airport.app.person.Employee;
import airport.app.person.Passenger;

import java.util.Date;
import java.util.List;

/**
 * Punkt kontroli, przechodza przez niego kolejni pasazerowie
 */
public class ControlPoint extends Place {

    private Date shiftStartTime;
    private Controller controller;
    private static int controlPointIndex;
    private boolean successor = false;

    public ControlPoint(String name, Integer maxPeopleAmount) { super(name, maxPeopleAmount); }

    void setController(Controller controller) { this.controller = controller; }

    void setShiftStartTime(Date shiftStartTime) { this.shiftStartTime = shiftStartTime; }

    private Controller removeController(){
        Controller tempController = this.controller;
        this.controller = null;
        return tempController;
    }

    public void setSuccessor(boolean successor) { this.successor = successor; }

    public boolean getSuccessor() { return this.successor; }

    /**
     *
     * @param controller - przypisywany kontroler przy otwieraniu punktu kontroli
     * @param date - czas otwarcia punktu kontroli
     */
    public void openPoint(Controller controller, Date date) {
        isOpen = true;
        controlPointIndex += 1;
        setController(controller);
        setShiftStartTime(date);
    }

    /**
     * metoda zwraca pasazerow ktorzy zostali w zamykanym punkcie zeby mogli byc przesunieci do otwartych punktow
     * @return lista pasazerow
     */
    public List closePoint(){
        this.isOpen = false;
        controlPointIndex -= 1;
        setShiftStartTime(null);
        List<Passenger> passengers = getPassengers();
        removePassengers();
        return passengers;
    }

    public int getOpenControlPointIndex() { return controlPointIndex; }

    public Date getShiftStartTime() { return shiftStartTime; }

    public Date getShiftEndTime() { return new Date(shiftStartTime.getTime() + (8 * 60 * 60 * 1000)); }

    /**
     * jezeli punkt zostaje zamkniety zwracany jest niepracuujacy juz kontroler
     * @param now obecna data do porownania
     * @return controller
     */
    public Controller checkWorkingHour(Date now) {
        if( isOpen && now.after(getShiftEndTime()) ){
            return removeController();
        } else {
            return null;
        }
    }

    public Integer getControllersEfficiency() { return controller.getEfficiency(); }

    public Employee getEmployee(){ return this.employee;}

}
