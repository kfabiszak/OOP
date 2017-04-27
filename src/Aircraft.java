
import javafx.geometry.Pos;

/**
 * Created by Ja on 2015-10-26.
 */
public abstract class Aircraft extends Vehicle {

    /**
     * Liczba personelu
     */
    private int staff;
    /**
     * Aktualna ilość paliwa
     */
    private double fuel;
    /**
     * Maksymalna ilość paliwa
     */
    private double maxFuel;
    /**
     * Procentowa ilość paliwa w samolocie
     */
    private double perFuel;

    private void emergency() {

    }

    public void useFuel() {
        setFuel(getFuel() - 1);
    }

    public void refill() {
        this.setFuel(this.getMaxFuel());
    }

    public double getPerFuel() {
        double per = (this.fuel / this.maxFuel);
        return per;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    /**
     * @return the fuel
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * @param fuel the fuel to set
     */
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    /**
     * @return the maxFuel
     */
    public double getMaxFuel() {
        return maxFuel;
    }

    /**
     * @param maxFuel the maxFuel to set
     */
    public void setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
    }
}
