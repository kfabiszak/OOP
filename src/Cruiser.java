
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Ja on 2015-10-26.
 */
public class Cruiser extends Craft {

    /**
     * Maksymalna liczba pasażerów
     */
    private int capacity;
    /**
     * Aktualna liczba pasażerów
     */
    private int passengerCount;
    /**
     * Nazwa firmy, do której należy statek
     */
    private String company;
    /**
     * "Kontener" przechowujący pasażerów
     */
    protected Container container;
    /**
     * Ścieżka po jakiej porusza się statek
     */
    private LinkedList<City> path;
    

    /**
     * Konstruktor klasy statku wycieczkowego
     */
    public Cruiser() throws IOException {
        this.getImage("cruiser.gif");
        this.container = new Container();
        this.path = new LinkedList();
        this.setVehicleType(2);
        Random rand = new Random();
        int luck = rand.nextInt(Companies.values().length);
        int i = 0;
        for (Companies c : Companies.values()) {
            if (i == luck) {
                this.company = c.toString();
            }
            i++;
        }
        this.setStartPoint(this.setTourPoint());
        this.setPosition(this.getStartPoint().getPosition());
        this.capacity = 5;
        this.setMaxSpeed(5);
        this.setState(0);
        for (int j = 0; j < this.capacity; j++) {
            Passenger passenger = new Passenger();
            this.container.passengers.add(passenger);
            Thread t = new Thread(passenger);
            World.threads.add(t);
            t.start();
        }
        World.cruisers.add(this);
    }
    
    /**
     * Funckja szukająca ścieżki.
     *
     * @param current Miasto z którego szukamy ścieżki
     */
    public void findPath(City current) {

        do {
            this.path.clear();
            City a, b;
            path.add(current);
            for (int i = 0; i < 10; i++) {
                if(!this.path.contains(a = this.path.get(i).connected.get((int) (Math.random() * this.path.get(i).connected.size())))){
                    this.path.add(a);
                }else{
                    break;
                }
            }
            while(this.path.getLast().getType() != 1){
                this.path.removeLast();
            }
            this.path.removeFirst();
        } while (this.path.size() <= 2);
    }
    
    /**
     * Znajduje losowy cel. Cel jest inny niż miejsce startu. Ustala kąt wektora
     */
    private void findAngle(City target) {
        
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        double cx = target.getPosition().getX();
        double cy = target.getPosition().getY();
        double rx = x - cx;
        double ry = y - cy;
        this.angle = Math.toDegrees(Math.atan2(rx, ry));
    }
    
    /**
     * Funkcja ustala kolejne współrzędne dla samolotu do poruszania się do
     * celu. Wywołuję ją dla trasy od miejsca startu do skrzyżowania oraz od
     * skrzyżowania do ostatecznego celu
     *
     * @param angle Kąt obliczony w funkcji "FindTarget". Podaję kąt wektora
     * między miejscem startu a skrzyżowaniem lub skrzyżowaniem a ostatecznym
     * celem,
     * @param endingState Stan w jakim znajdzie się samolot po dotarciu do celu.
     * "2" po dotarciu do skrzyżowania, tam zaczyna się poruszać do ostatecznego
     * celu "3" po dotarciu do ostatecznego celu.
     * @param target Cel pośredni (skrzyżowanie) lub ostateczny cel
     */
    public void goTo(double angle, int endingState, City target) {
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        double cx = target.getPosition().getX();
        double cy = target.getPosition().getY();
        double rx = x - cx;
        double ry = y - cy;

        int speed = this.getMaxSpeed();

        y -= (Math.cos(Math.toRadians(angle)) * speed);
        x -= (Math.sin(Math.toRadians(angle)) * speed);

        if (Math.abs(rx) < speed) {
            x = cx;
        }
        if (Math.abs(ry) < speed) {
            y = cy;
        }

        Position position = new Position();
        position.setX(x);
        position.setY(y);
        this.setPosition(position);

        double rot;
        if (cx >= x){
            rot = 270 - angle;
        }else{
            rot = 90 - angle;
        }
        double rotate = Math.toRadians(rot);
        double lX = this.imgConst.getWidth() / 2;
        double lY = this.imgConst.getHeight() / 2;
        AffineTransform t = AffineTransform.getRotateInstance(rotate, lX, lY);
        AffineTransformOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
        this.setImg(op.filter(this.imgConst, null));

        if (x == cx && y == cy) {
            this.setState(endingState);
        }
    }

    /**
     * Funkcja zarządzająca statkiem wycieczkowym
     */
    public void manage(){
        switch (this.getState()) {
            case (0):
                this.findPath(this.getStartPoint());
                this.setTarget(this.path.getLast());
                for (int i = 0; i < this.container.getPassengerCount(); i++) {
                    this.container.passengers.get(i).setHome(this.path.getLast());
                }
                this.setState(5);
                break;
            case (1):
                this.goTo(this.angle, 5, this.path.getFirst());
                break;
            case(5):
                this.path.addLast(this.path.pop());
                this.findAngle(this.path.getFirst());
                if(this.path.getFirst() == this.getTarget()){
                    this.setState(2);
                }else{
                    this.setState(1);
                }
                break;
            case (2):
                this.goTo(this.angle, 3, this.getTarget());
                break;
            case (3):
                this.getTarget().land();
                this.getTarget().addToContainer(this.container.passengers);
                for (int i = 0; i < this.container.passengers.size(); i++) {
                    this.container.passengers.get(i).setMoving(false);
                    this.container.passengers.get(i).setIn(this.getTarget());
                    if (this.container.passengers.get(i).getIn() == this.container.passengers.get(i).getHome()) {
                        this.container.passengers.get(i).setState(0);
                        this.container.passengers.get(i).setNeedsTarget(true);
                    } else if (this.container.passengers.get(i).getIn() == this.container.passengers.get(i).getTarget()) {
                        this.container.passengers.get(i).setState(2);
                    } else {
                        this.container.passengers.get(i).setState(1);
                    }
                }
                this.container.passengers.clear();
                this.setStartPoint(this.getTarget());
                this.findPath(this.getStartPoint());
                this.setTarget(this.path.getLast());
                for (int i = 0; i < this.getStartPoint().getPassengersCount(); i++) {
                    if (this.container.getPassengerCount() < this.getCapacity()) {
                        if (this.getTarget() == this.getStartPoint().getPassenger(i).getMidTarget()) {
                            if (this.getStartPoint().getPassenger(i).isReady()) {
                                this.container.passengers.add(this.getStartPoint().getPassenger(i));
                                this.getStartPoint().getPassenger(i).setMoving(true);
                                this.getStartPoint().removePassenger(i);
                            }
                        }
                    } else {
                        break;
                    }
                }
                this.getStartPoint().takeOff();
                this.setState(5);
                break;

            //case(4):  
            //case(4):  
        }
    }
    
    @Override
    public void run() {
        while(this.isAlive()){
            this.manage();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cruiser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return the passengerCount
     */
    public int getPassengerCount() {
        return passengerCount;
    }

    /**
     * @param passengerCount the passengerCount to set
     */
    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

}
