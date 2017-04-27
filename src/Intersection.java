
import java.awt.Graphics;
import java.io.IOException;
import javafx.geometry.Pos;

import java.util.PrimitiveIterator;

/**
 * Created by Ja on 2015-10-26.
 */
public class Intersection extends City {

//    private Route routeA;
//    private Route routeB;
    /**
     * Współrzędne skrzyżowania
     */
    private Position position;
//    private boolean free;
    /**
     * Zmienna przechowująca wartość "true" jeśli obiekt jest skrzyżowaniem dla
     * samolotów, a "false" jeśli jest skrzyżowaniem dla statków
     */
    private boolean what;

    /**
     * Konstruktor klasy skrzyżowania. W konstruktorze nadawany jest typ
     * skrzyżowania.
     *
     * @param type Typ skrzyżowania: "0" - skrzyżowanie dla samolotów, "1" -
     * skrzyżowanie dla statków
     */
    public Intersection(int type) throws IOException {
        this.setCanChange(false);
        if (type == 0) {
            this.setType(2);
            this.getImage("aircross.gif");
        }else{
            this.setType(3);
            this.getImage("watercross.gif");
        }
    }

    private void pass() {

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

}


////////////////prawdopodobnie śmieci//////////////
//    /**
//     * Funkcja ustala typ skrzyżowania na "dla samolotów"
//     */
//    public void setair() {
//        this.what = true;
//    }
//
//    /**
//     * Funkcja ustala typ skrzyżowania na "dla statków"
//     */
//    public void setwater() {
//        this.what = false;
//    }
//
//    /**
//     * Funkcja zwraca wartość "true" jeśli obiekt jest skrzyżowaniem dla
//     * samolotów, a "false" jeśli jest skrzyżowaniem dla statków
//     *
//     * @return
//     */
//    public boolean what() {
//        return this.what;
//    }
