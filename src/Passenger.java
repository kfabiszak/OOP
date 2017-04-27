
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ja on 2015-10-26.
 */
public class Passenger implements Runnable {

    /**
     * Imię
     */
    private String name;
    /**
     * Nazwisko
     */
    private String surname;
    /**
     * Wiek
     */
    private int age;
    /**
     * Unikalny identyfikator
     */
    private int personalID;
    /**
     * Dom pasażera (miejsce startu i końca podróży)
     */
    private City home;
    /**
     * Aktualne miejsce pobytu pasażera
     */
    private City in;
    /**
     * Cel podróży
     */
    private City target;
    /**
     * Pośredni cel podróży (kolejny element na liście path)
     */
    private City midTarget;
    /**
     * Lista przechowująca kolejne punkty podróży pasażera
     */
    private LinkedList<City> path;
    /**
     * Zmienna określająca typ danej podróży: "true" - służbowa, "false" -
     * prywatna
     */
    private boolean tripType;
    /**
     * Zmienna określająca czy pasażer szuka celu. Posłuży przy ustalaniu celu
     * pasażera. True - pasażer ma już cel nie musimy szukać. False - pasażer
     * nie ma jeszcze celu
     */
    private boolean needsTarget;
    /**
     * Określa czy pasażer musi zmienić miasto z lotniska na port lub na odwrót.
     */
    private boolean needsChange;
    /**
     * Zmienna określająca czy pasażer jest w ruchu, czy np. czeka w mieście
     * itp.
     */
    private boolean moving;
    /**
     * Określa stan pasażera: 0 - w domu, 1 - w celu pośrednim, 2 - u celu, 3 -
     * stan pusty, tylko po stworzeniu
     */
    private int state;
    /**
     * Zmienna potrzebna przy usuwaniu. Używana w pętli while w run(). Jeśli
     * usuwam pasażera zmieniam na false oraz usuwam obiekt.
     */
    private boolean alive;
    /**
     * Zmienna określająca czy pasażer jest gotowy wyjechać z danego punktu
     * podróży
     */
    private boolean ready;

    /**
     * Konstruktor klasy Pasażera
     */
    public Passenger() {
        this.ready = true;
        this.alive = true;
        this.needsChange = false;
        this.needsTarget = true;
        this.moving = true;
        this.path = new LinkedList();
        this.setState(3);
        World.passengers.add(this);
        Random rand1 = new Random();
        int luckn = rand1.nextInt(Names.values().length);
        int i = 0;
        for (Names c : Names.values()) {
            if (i == luckn) {
                this.name = c.toString();
            }
            i++;
        }
        Random rand2 = new Random();
        int lucks = rand2.nextInt(Surnames.values().length);
        i = 0;
        for (Surnames c : Surnames.values()) {
            if (i == lucks) {
                this.surname = c.toString();
            }
            i++;
        }
        Random rand3 = new Random();
        this.age = rand3.nextInt(99) + 3;
        this.personalID = World.nextPersonalID();

    }

    /**
     * Funkcja losująca cel podróży pasażera oraz ustalająca typ podróży
     */
    public void setTarget() {
        this.path.clear();
        Random rand1 = new Random();
        int rnd = rand1.nextInt(2);
        if (rnd == 0) {
            this.tripType = true;
        } else {
            this.tripType = false;
        }
        Random rand2 = new Random();
        int luck = rand2.nextInt(World.airports.size() + World.ports.size());
        if (luck >= World.airports.size()) {
            luck = luck - World.airports.size();
            this.target = World.ports.get(luck);
        } else {
            this.target = World.airports.get(luck);
        }
    }

    /**
     * Funkcja sprawdzająca czy pasażer musi się przesiąść, aby dotrzeć do celu
     * na podstawie porównania typów miast domu i celu.
     */
    public void needsChange() {
        if (this.in.getType() != this.target.getType()) {
            this.setNeedsChange(true);
        }
    }

    /**
     * Funkcja wykonująca przesiadkę. Sprawdza czy miasto dom ma możliwość
     * przesiadki, jeśli tak wykonuje ją. Natomiast jeśli nie sprawdza, które z
     * sąsiednich miast oferuje taką możliwość.
     */
    public void change() {
        if (this.in.isCanChange()) {
            this.in = this.in.getClosest();
        } else {
            for (int i = 0; i < this.in.connected.size(); i++) {
                if (this.in.connected.get(i).isCanChange()) {
                    this.in = this.in.connected.get(i);
                    break;
                }
            }
        }

    }

    /**
     * Funckja szukająca ścieżki do celu.
     *
     * @param current Miasto z którego szukamy ścieżki
     */
    public void findPath(City current) {

        do {
            this.path.clear();
            City a, b;
            path.add(current);
            for (int i = 0; i < 10; i++) {
                a = this.path.get(i).connected.get((int) (Math.random() * this.path.get(i).connected.size()));
                b = a.connected.get((int) (Math.random() * a.connected.size()));
                if (this.path.contains(b)) {
                    break;
                }
                this.path.add(b);
            }
            this.path.removeFirst();
        } while (this.path.size() <= 3);
    }
    
    /**
     * Funkcja odwraca listę tworząc nową i dodając do niej kolejne elementy ze
     * starej listy od końca o napisuje starą listę.
     * @param list Lista do odwrócenia
     */
    public void reverseList(LinkedList list) {

        LinkedList copy = new LinkedList();
        for (int i = 0; i < list.size(); i++) {
            copy.add(list.getLast());
            list.removeLast();
        }
        list = copy;
    }

    /**
     * Funkcja "zarządzająca" pasażerem. W zależności od stanu wykonuje: "0" -
     * jest w domu - nie jest gotowy, jeśli potrzebuje celu losuje nowy, jest
     * gotowy. "2" - doleciał do celu, nie jest gotowy, jeśli podróż jest
     * służbowa zostaje zawieszony na 5 sek., ustawia na cel dom i jest gotowy
     * do powrotu.
     */
    private void live() {
        switch (this.getState()) {
            case (0):
                this.ready = false;
                if (this.needsTarget) {
                    //this.setTarget();
                    findPath(this.getIn());
                    this.target = this.path.getLast();
                    this.midTarget = this.path.getFirst();
                    this.setNeedsTarget(false);
                }
                this.ready = true;
                break;
            case (1):
                this.ready = false;
                this.path.addLast(this.path.pop());
                this.midTarget = this.path.getFirst();
                this.ready = true;
                break;
            case (2):
                this.ready = false;
                if (this.tripType) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.reverseList(path);
                this.target = this.home;
                //jakaś funckja obracająca ścieżkę
                this.ready = true;
                break;
            case (3):
                break;
        }
    }

    /**
     * Funkcja usuwająca pasażera. Kończy pętlę while w funkcji run() oraz usuwa
     * obiekt z listy pasażerów
     */
    public void delete() {
        this.setAlive(false);
        World.passengers.remove(this);
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            this.live();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPersonalID() {
        return personalID;
    }

    public void setPersonalID(int personalID) {
        this.personalID = personalID;
    }

    /**
     * @return the home
     */
    public City getHome() {
        return home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(City home) {
        this.home = home;
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
     * @return the needsTarget
     */
    public boolean isNeedsTarget() {
        return needsTarget;
    }

    /**
     * @param needsTarget the needTarget to set
     */
    public void setNeedsTarget(boolean needsTarget) {
        this.needsTarget = needsTarget;
    }

    /**
     * @return the needsChange
     */
    public boolean isNeedsChange() {
        return needsChange;
    }

    /**
     * @param needChange the needChange to set
     */
    public void setNeedsChange(boolean needsChange) {
        this.needsChange = needsChange;
    }

    /**
     * @return the moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * @param moving the moving to set
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * @return the tripType
     */
    public boolean isTripType() {
        return tripType;
    }

    /**
     * @param tripType the tripType to set
     */
    public void setTripType(boolean tripType) {
        this.tripType = tripType;
    }

    /**
     * @return the in
     */
    public City getIn() {
        return in;
    }

    /**
     * @param in the in to set
     */
    public void setIn(City in) {
        this.in = in;
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
     * @return the ready
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * @param ready the ready to set
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * @return the path
     */
    public LinkedList<City> getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(LinkedList<City> path) {
        this.path = path;
    }

}
