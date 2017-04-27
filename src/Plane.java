
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Created by Ja on 2015-10-26.
 */
public class Plane extends Aircraft {

    /**
     * Pojemność samolotu
     */
    private int capacity;
    /**
     * Aktualna liczna pasażerów
     */
    private int passengerCount;
    /**
     * "Kontener" z pasażerami
     */
    protected Container container;

    /**
     * Konstruktor klasy samolotu
     *
     * @throws IOException
     */
    public Plane() throws IOException {

        this.setMaxFuel(100);
        this.refill();
        this.setVehicleType(0);
        this.getImage("Samolot.gif");
        this.setStartPoint(setTourPoint());
        this.setPosition(this.getStartPoint().getPosition());
        this.container = new Container();
        this.capacity = 10;
        this.setMaxSpeed(3);
        this.setState(0);
        for (int i = 0; i < this.capacity; i++) {
            Passenger passenger = new Passenger();
            this.container.passengers.add(passenger);
            Thread t = new Thread(passenger);
            World.threads.add(t);
            t.start();
            //System.out.println(passenger.getName() + " " + passenger.getSurname() + " Wiek: " + passenger.getAge());
        }
        World.planes.add(this);
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
        this.useFuel();
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

        double rotate = Math.toRadians(360 - angle);
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
     * Znajduje losowy cel Cel jest inny niż miejsce startu Ustala kąt wektora
     */
    private void findTarget() {
        boolean diff = true;
        this.setMidTarget(this.getStartPoint().randTarget());
        while (diff) {
            this.setTarget(this.getMidTarget().randTarget());
            if (this.getTarget() != this.getStartPoint()) {
                if (this.getTarget().getType() != 4) {
                    diff = false;
                }
            }
        }
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        double cx = this.getTarget().getPosition().getX();
        double cy = this.getTarget().getPosition().getY();
        double mx = this.getMidTarget().getPosition().getX();
        double my = this.getMidTarget().getPosition().getY();
        double rx = x - mx;
        double ry = y - my;
        double rrx = mx - cx;
        double rry = my - cy;
        this.midAngle = Math.toDegrees(Math.atan2(rx, ry));
        this.angle = Math.toDegrees(Math.atan2(rrx, rry));
    }

    /**
     * Funkcja "zarządzająca" samolotem. W zależności od stanu samolotu wykonuje
     * on: "0" - tylko po stworzeniu. Pierwszy cel samolotu jest domem
     * pasażerów, którzy zostali stworzeni wraz z nim. "1" - samolot leci do
     * skrzyżowania. "2" - samolot leci ze skrzyżowania do celu. "3" - samolot
     * dolatuje do celu, ląduje, wypakowuje pasażerów, zmienia odpowiednio ich
     * stany, ustala nowy cel oraz zabiera pasażerów którzy chcą tam lecieć i
     * odlatuje.
     */
    private void manage() {
        switch (this.getState()) {
            case (0):
                this.findTarget();
                for (int i = 0; i < this.container.getPassengerCount(); i++) {
                    this.container.passengers.get(i).setHome(this.getTarget());
                }
                this.setState(1);
                break;
            case (1):
                this.goTo(this.midAngle, 2, this.getMidTarget());
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
                this.refill();
                this.setStartPoint(this.getTarget());
                this.findTarget();
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
                this.setState(1);
                break;
            case (4):
                if (!this.isStay()) {
                    if (this.isStayWhere()) {
                        this.setState(1);
                    } else {
                        this.setState(2);
                    }
                }
                break;
        }
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            this.manage();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the passengerCount
     */
    public int getPassengerCount() {
        this.passengerCount = this.container.getPassengerCount();
        return passengerCount;
    }

    /**
     * @param passengerCount the passengerCount to set
     */
    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

////////prawdopodobnie śmieci//////////
//    /**
//     * Losuje punkt początkowy
//     *
//     * @return Wylosowane lotnisko
//     */
//    public City setTourPoint() {
//        City point;
//        Random rand = new Random();
//        int luck = rand.nextInt(World.airports.size());
//        point = World.airports.get(luck);
//        return point;
//    }
