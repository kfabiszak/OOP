
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Created by Ja on 2015-10-26.
 */
public abstract class City {

    
    /**
     * Obraz miasta
     */
    private BufferedImage img;
    /**
     * Współrzędne
     */
    private Position position;
    /**
     * Pojemność (ile pojazdów moze przebywać)
     */
    private int capacity;
    /**
     * Aktualna liczba pojazdów przebywających w mieście
     */
    private int vehicleCount;
    /**
     * Unikalny identyfikator miasta
     */
    private int cityID;
    /**
     * Zmienna ustawiana, jeśli miasto jest przepełnione.
     */
    private boolean full;
    /**
     * "Kontener" do przechowywania pasażerów przebywających w mieście
     */
    private Container container;
    /**
     * Lista miast, z którymi dane miasto ma połączenie Miasta to również
     * skrzyżowania
     */
    public LinkedList<City> connected;
    /**
     * Typ miasta: "0" - lotnisko pasażerskie, "1" - port, "2" - skrzyżowanie dla samolotów,
     * "3" - skrzyżowanie dla statków, "4" - lotnisko wojskowe.
     */
    private int type;
    /**
     * Pokazuje czy w tym mieście można się przesiąść, czy można zmienić z
     * lotniska na port i z portu na lotnisko
     */
    private boolean canChange;
    /**
     * Najbliższe "przeciwne" miasto. Dla lotniska - najbliższy port do
     * przesiadki, dla portu, lotnisko.
     */
    private City closest;
    
    /**
     * Pobiera obraz miasta z pliku
     *
     * @param name Nazwa pliku z obrazem
     * @throws IOException
     */
    protected void getImage(String name) throws IOException {
        File file = new File(name);
        this.setImg(ImageIO.read(file));
    }

    /**
     * Konstruktor klasy miasta
     */
    public City() {
        this.cityID = World.nextCityID();
        container = new Container();
        this.connected = new LinkedList();
    }

    /**
     * Funkcja odczytująca z tablicy dwuwymiarowej zadeklarowanej w klasie
     * "World" (macierzy sąsiedztwa) z jakimi miastami ma dane miasto
     * połączenia. Funkcja zapisuje miasta w tablicy "connected"
     */
    public void connect() {
        for (int i = 0; i < 6; i++) {
            if (World.connections[this.cityID][i] == -1) {
                break;
            }
            this.connected.add(World.cities.get(World.connections[this.cityID][i]));
        }
    }

    /**
     * Funkcja odczytująca z tablicy zadeklarowanej w klasie "World" czy w danym
     * mieście jest możliwość przesiadki oraz do jakiego miasta jeśli tak.
     * Funkcja ustala "Closest" na miasto do jakiego można sie przesiąść jeśli
     * można
     */
    public void changes() {
        if (World.changes[this.cityID] == -1) {
            setCanChange(false);
        } else {
            setCanChange(true);
            this.setClosest(World.cities.get(World.changes[this.cityID]));
        }
    }

    /**
     * Funkcja losująca cel podróży z tablicy miast z jakimi dane miasto ma
     * połączenia
     *
     * @return Losowe miasto z talicy połączeń
     */
    public City randTarget() {
        City point;
        Random rand = new Random();
        int luck = rand.nextInt(this.connected.size());
        point = this.connected.get(luck);
        return point;
    }

//    /**
//     * Funkcja zwraca "typ" miasta: "0" - lotnisko "1" - skrzyżowanie dla
//     * samolotów "2" - port "3" - skrzyżowanie dla statków
//     *
//     * @return
//     */
//    private int whatIsThat() {
//        int what;
//        if (this.cityID < 10) {
//            what = 0;
//        } else if ((this.cityID >= 10) && (this.cityID < 15)) {
//            what = 1;
//        } else if ((this.cityID >= 15) && (this.cityID < 20)) {
//            what = 2;
//        } else {
//            what = 3;
//        }
//        return what;
//    }

    /**
     * Funkcja wykonwyana przy wpuszczaniu pojazdu do miasta, zwięszka liczbę
     * pojazdów aktualnie przebywających w mieście o 1
     */
    public void land() {
        this.vehicleCount++;
    }

    /**
     * Funcja wykonwywana przy wypuszczaniu pojazdu z miasta, zmniejsza liczbę
     * pojazdów aktualnie przebywających w mieście o 1
     */
    public void takeOff() {
        this.vehicleCount--;
    }
    
    /**
     * Funckja obliczająca dystans między miastem a punktem "position"
     * @param position Współrzędne punktu
     * @return Dystans między miastem a punktem
     */
    public double distance(Position position){
        double x = Math.abs(this.getPosition().getX() - position.getX());
        double y = Math.abs(this.getPosition().getX() - position.getY());
        double dist = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return dist;
    }

    /**
     * Dodaje do listy pasażerów w mieście listę pasażerów "przywieźionych"
     *
     * @param list Lista pasażerów z pojazdu
     */
    public void addToContainer(LinkedList<Passenger> list) {
        this.container.passengers.addAll(list);
    }

    /**
     * Podaje liczbę pasażerów w mieście
     *
     * @return Liczba pasażerów w mieście
     */
    public int getPassengersCount() {
        int count = this.container.passengers.size();
        return count;
    }

    /**
     * Zwraca pasażera z listy w mieście o indeksie i
     *
     * @param i Indeks pasażera na liście miasta
     * @return Pasażer na liście miasta o indeksie i
     */
    public Passenger getPassenger(int i) {
        Passenger passenger = this.container.passengers.get(i);
        return passenger;
    }
    
    /**
     * Usuwa pasażera z listy w mieście o indeksie i
     *
     * @param i Indeks pasażera na liście miasta
     */
    public void removePassenger(int i) {
        this.container.passengers.remove(i);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the cityID
     */
    public int getCityID() {
        return cityID;
    }

    /**
     * @param cityID the cityID to set
     */
    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the canChange
     */
    public boolean isCanChange() {
        return canChange;
    }

    /**
     * @param canChange the canChange to set
     */
    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }

    /**
     * @return the closest
     */
    public City getClosest() {
        return closest;
    }

    /**
     * @param closest the closest to set
     */
    public void setClosest(City closest) {
        this.closest = closest;
    }

    /**
     * @return the vehicleCount
     */
    public int getVehicleCount() {
        return vehicleCount;
    }

    /**
     * @param vehicleCount the vehicleCount to set
     */
    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    /**
     * @return the full
     */
    public boolean isFull() {
        return full;
    }

    /**
     * @param full the full to set
     */
    public void setFull(boolean full) {
        this.full = full;
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
}
