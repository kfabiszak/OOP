
import java.util.LinkedList;

/**
 * Created by Ja on 2015-10-26.
 */
public class Container {

    private int passengerCount;
    public LinkedList<Passenger> passengers;

    public Container() {
        this.passengers = new LinkedList();
    }

    private void manage() {

    }

    public int getPassengerCount() {
        this.passengerCount = this.passengers.size();
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;

    }
}
