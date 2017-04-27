
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Created by Ja on 2015-10-26.
 */
public abstract class Vehicle implements Runnable {

    /**
     * Obraz pojazdu (będzie obracany)
     */
    private BufferedImage img;
    /**
     * Obraz pojazdu niezmienny
     */
    protected BufferedImage imgConst;
    /**
     * Typ pojazdu: 0 - samolot pasażerski, 1 - samolot wojskowy, 2 - statek
     * wycieczkowy, 3 - lotniskowiec
     */
    private int vehicleType;
    /**
     * Prędkość pojazdu
     */
    private int maxSpeed;
    /**
     * Współrzędne
     */
    private Position position;
    /**
     * Punkt startowy
     */
    private City startPoint;
    /**
     * Cel
     */
    private City target;
    /**
     * Skrzyżowanie na drodze do celu
     */
    private City midTarget;
    /**
     * Identyfikator pojazdu
     */
    private int id;
    //private Info panel;
    //private Route route;
    /**
     * Stan pojazdu: "0" - obsługa po stworzeniu, "1" - do skrzyżowania, "2" -
     * ze skrzyżowania do celu, "3" - obsługa (po przyjeździe), "4" - postój,
     * "5" - stan do obliczania kątów.
     */
    private int state;
    /**
     * Kąt wektora między skrzyżowaniem a celem
     */
    protected double angle;
    /**
     * Kąt między miejscem startu a skrzyżowaniem
     */
    protected double midAngle;
    /**
     * Zmienna potrzebna przy usuwaniu. Używana w pętli while w run(). Jeśli
     * usuwam pojazd zmieniam na false oraz usuwam obiekt.
     */
    private boolean alive;
    /**
     * Zmienna określająca czy pojazd jest "wstrzymany" (zawieszony)
     */
    private boolean stay;
    /**
     * Zmienna określająca czy pojazd został zatrzymany przed skrzyżowaniem
     * (true) czy przed miastem (false)
     */
    private boolean stayWhere;
    
    /**
     * Konstruktor klasy bazowej pojazdu Nadaje unikalny identyfikator pojazdu
     */
    public Vehicle() {
        this.alive = true;
        this.id = World.nextID();
    }

    /**
     * Pobiera obraz pojazdu z pliku
     *
     * @param name Nazwa pliku z obrazem
     * @throws IOException
     */
    protected void getImage(String name) throws IOException {
        File file = new File(name);
        this.setImg(ImageIO.read(file));
        this.imgConst = ImageIO.read(file);
    }

    /**
     * Losuje punkt początkowy
     *
     * @return Wylosowane lotnisko
     */
    public City setTourPoint() {
        City point = null;
        Random rand = new Random();
        int luck;
        switch (this.getVehicleType()) {
            case (0):
                luck = rand.nextInt(World.airports.size());
                point = World.airports.get(luck);
                break;
            case (1):
                break;
            case (2):
                luck = rand.nextInt(World.ports.size());
                point = World.ports.get(luck);
                break;
            case (3):
                luck = rand.nextInt(World.ports.size());
                point = World.ports.get(luck);
                break;
        }
        return point;

    }

    /**
     * Funkcja usuwa obiekt. Kończy pętlę while w funkcji run() oraz usuwa
     * obiekt z odpowiedniej listy.
     */
//    public void delete() {
//        this.setAlive(false);
//        switch (this.vehicleType) {
//            case (0):
//                World.planes.remove(this);
//                break;
//            case (1):
//                World.militaryAircrafts.remove(this);
//                break;
//            case (2):
//                World.cruisers.remove(this);
//                break;
//            case (3):
//                World.carriers.remove(this);
//                break;
//        }
//    }

    /**
     * @return the startPoint
     */
    public City getStartPoint() {
        return startPoint;
    }

    /**
     * @param startPoint the startPoint to set
     */
    public void setStartPoint(City startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * @return the target
     */
    public City getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(City target) {
        this.target = target;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the midTarget
     */
    public City getMidTarget() {
        return midTarget;
    }

    /**
     * @param midTarget the midTarget to set
     */
    public void setMidTarget(City midTarget) {
        this.midTarget = midTarget;
    }

    /**
     * @return the img
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(BufferedImage img) {
        this.img = img;
    }

    /**
     * @return the vehicleType
     */
    public int getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return the alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * @param alive the alive to set
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * @return the maxSpeed
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * @param maxSpeed the maxSpeed to set
     */
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return the stay
     */
    public boolean isStay() {
        return stay;
    }

    /**
     * @param stay the stay to set
     */
    public void setStay(boolean stay) {
        this.stay = stay;
    }

    /**
     * @return the stayWhere
     */
    public boolean isStayWhere() {
        return stayWhere;
    }

    /**
     * @param stayWhere the stayWhere to set
     */
    public void setStayWhere(boolean stayWhere) {
        this.stayWhere = stayWhere;
    }
}
