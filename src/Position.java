
/**
 * Created by Ja on 2015-10-26.
 */
public class Position {

    private double x;
    private double y;

    /**
     * Funkcja wyszukująca dla danego punktu najbliżej położone miasto (lotnisko
     * lub skrzyżowanie dla samolotów)
     *
     * @return Najbliżej położone miasto
     */
    public City closestCity() {
        City city = null;
        double closest = 9999;
        for (int i = 0; i < World.cities.size(); i++) {
            if ((World.cities.get(i).getType() == 0) || (World.cities.get(i).getType() == 2)) {
                if (World.cities.get(i).distance(this) < closest) {
                    city = World.cities.get(i);
                    closest = World.cities.get(i).distance(this);
                }
            }
        }
        return city;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
}
