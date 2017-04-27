
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ja on 2015-10-26.
 */
public class MilitaryAircraft extends Aircraft {

    /**
     * Broń, w jaką wyposażóny jest samolot
     */
    private Weapon weapon;

    /**
     * Kontruktor klasy samolotu wojskowego
     */
    public MilitaryAircraft(Weapon w) throws IOException {
        this.getImage("Samolotw.gif");
        this.setMaxSpeed(5);
        this.setVehicleType(1);
        this.weapon = w;
        this.setMaxFuel(1000);
        this.refill();
        World.militaryAircrafts.add(this);
        this.setState(0);
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
    private void goTo(double angle, int endingState, City target) {
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
     * Funkcja zarządzająca samolotem wojskowym
     */
    private void manage() {
        System.out.println(this.getState());
        switch (this.getState()) {
            case (0):
                this.setTarget(this.getPosition().closestCity());
                this.findAngle(this.getTarget());
                this.setState(4);
                break;
            case (1):
                this.goTo(this.angle, 2, this.getTarget());
                break;
            case (2):
                this.goTo(this.angle, 3, this.getTarget());
                break;
            case (5):
                this.setTarget(this.getStartPoint().randTarget());
                this.findAngle(this.getTarget());
                this.setState(4);
                break;
            case (4):
                if (this.getTarget().getType() == 4) {
                    this.setState(2);
                } else {
                    this.setState(1);
                }
                break;
            case (3):
                this.getTarget().land();
                this.refill();
                this.setStartPoint(this.getTarget());
                 {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MilitaryAircraft.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.getStartPoint().takeOff();
                this.setState(5);
                break;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            this.manage();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(MilitaryAircraft.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
