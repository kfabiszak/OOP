import java.lang.Math;
/**
 * Created by Ja on 2015-10-26.
 */
public class Route {

    /**
     * Współrzędne miasta początkowego
     */
    private Position cityA;
    /**
     * Współrzędne miasta końcowego
     */
    private Position cityB;
    /**
     * Odległość między miastami, obliczana przy wywołaniu metody getdistance
     */
    private double distance;
    /**
     * Odległość na osi X między miastami, obliczana przy wywołaniu getXdistance
     */
    private double xdistance;
    /**
     * Odległość na osi Y między miastami, obliczana przy wywołaniu getYdistance
     */
    private double ydistance;
    /**
     * Typ drogi - enum. Wodny lub powietrzny.
     */
    private RouteType routeType;

    private void Route() {

    }

    /**
     * @return the cityA
     */
    public Position getCityA() {
        return cityA;
    }

    /**
     * @param cityA the cityA to set
     */
    public void setCityA(Position cityA) {
        this.cityA = cityA;
    }

    /**
     * @return the cityB
     */
    public Position getCityB() {
        return cityB;
    }

    /**
     * @param cityB the cityB to set
     */
    public void setCityB(Position cityB) {
        this.cityB = cityB;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        distance = Math.sqrt(Math.pow(this.getXdistance(),2) + Math.pow(this.getYdistance(),2));
        return distance;
    }

    /**
     * @return the xdistance
     */
    public double getXdistance() {
        xdistance = Math.abs(this.cityB.getX() - this.cityA.getX());
        return xdistance;
    }

    /**
     * @return the ydistance
     */
    public double getYdistance() {
        ydistance = Math.abs(this.cityB.getY() - this.cityA.getY());
        return ydistance;
    }

}
