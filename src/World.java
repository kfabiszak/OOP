
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Ja on 2015-10-26.
 */
public class World {

//    private Container container;
    /**
     * Zmienna do tworzenia identyfikatorów pojazdów. Przydielana do nowo
     * tworzonego pojazdu i zwiększana o 1.
     */
    public static int vehicleID;
    /**
     * Zmienna do tworzenia indetyfikatorów pasażerów
     */
    public static int passengerID;
    /**
     * Zmienna do tworzenia identyfiaktorów miast
     */
    public static int citiesID;
    /**
     * Lista wszystkich samolotów pasażerskich
     */
    public static LinkedList<Plane> planes;
    /**
     * Lista wszystkich samolotów wojskowych
     */
    public static LinkedList<MilitaryAircraft> militaryAircrafts;
    /**
     * Lista wszystich statków pasażerskich
     */
    public static LinkedList<Cruiser> cruisers;
    /**
     * Lista wszystkich lotniskowców
     */
    public static LinkedList<Carrier> carriers;
    /**
     * Lista wszystkich portów
     */
    public static LinkedList<Port> ports;
    /**
     * Lista wszystkich lotnisk pasażerskich
     */
    public static LinkedList<Airport> airports;
    /**
     * Lista lotnisk wojskowych
     */
    public static LinkedList<MilitaryAirport> mairports;
    /**
     * Lista dróg
     */
    public static LinkedList<Route> routes;
    /**
     * Lista skrzyżowań dla statków
     */
    public static LinkedList<Intersection> wintersections;
    /**
     * Lista skrzyżowań dla samolotów
     */
    public static LinkedList<Intersection> aintersections;
    /**
     * Lista wszystkich miast (lotnisk, portów, skrzyżowań dla samolotów oraz
     * skrzyżowań dla statków)
     */
    public static LinkedList<City> cities;
    /**
     * Spis ludności (wszystkich pasażerów)
     */
    public static LinkedList<Passenger> passengers;
    /**
     * Lista wątków
     */
    public static LinkedList<Thread> threads;
    /**
     * Liczba lotnisk pasażerskich
     */
    public static int acount;
    /**
     * Liczba portów
     */
    public static int pcount;
    /**
     * Liczba skrzyżowań dla statków
     */
    public static int wccount;
    /**
     * Liczba skrzyżowań dla samolotów
     */
    public static int account;
    /**
     * Liczba lotnisk wojskowych
     */
    public static int macount;
    /**
     * Dwuwymiarowa tablica połączeń między miastami. W wierszach miasto
     * ("dom"), w kolumnach miasta, z którymi istnieją połączenia.
     */
    public static int[][] connections;
    /**
     * Tablica miast do przesiadek. -1 oznacza brak możliwości przesiadki,
     * indeks innego miasta oznacza możliwość przesiadki do tamtego miasta.
     */
    public static int[] changes;

    /**
     * Konstruktor statycznej klasy World
     *
     * @throws IOException
     */
    public static void World() throws IOException {

        World.vehicleID = 0;
        World.passengerID = 0;
        World.citiesID = 0;
        World.ports = new LinkedList();
        World.airports = new LinkedList();
        World.planes = new LinkedList();
        World.militaryAircrafts = new LinkedList();
        World.cruisers = new LinkedList();
        World.carriers = new LinkedList();
        World.routes = new LinkedList();
        World.aintersections = new LinkedList();
        World.wintersections = new LinkedList();
        World.cities = new LinkedList();
        World.passengers = new LinkedList();
        World.threads = new LinkedList();
        World.mairports = new LinkedList();
        World.connections = new int[][]{
            {10, 14, -1, -1, -1, -1},
            {10, 13, 14, -1, -1, -1},
            {12, -1, -1, -1, -1, -1},
            {10, 11, -1, -1, -1, -1},
            {11, 12, -1, -1, -1, -1},
            {13, -1, -1, -1, -1, -1},
            {14, -1, -1, -1, -1, -1},
            {12, 13, -1, -1, -1, -1},
            {13, 14, -1, -1, -1, -1},
            {10, 11, 13, -1, -1, -1},
            {0, 1, 3, 9, 23, -1},
            {3, 4, 9, 24, -1, -1},
            {2, 4, 7, 25, -1, -1},
            {1, 5, 7, 8, 9, 25},
            {0, 1, 6, 8, 23, -1},
            {21, 22, -1, -1, -1, -1},
            {20, -1, -1, -1, -1, -1},
            {22, -1, -1, -1, -1, -1},
            {21, 22, -1, -1, -1, -1},
            {20, -1, -1, -1, -1, -1},
            {16, 19, 21, -1, -1, -1},
            {15, 18, 20, 22, -1, -1},
            {15, 17, 18, 21, -1, -1},
            {10, 14, -1, -1, -1, -1},
            {11, -1, -1, -1, -1, -1},
            {12, 13, -1, -1, -1, -1},
        };
        World.changes = new int[]{
            17, -1, 16, 18, -1, -1, -1, 19, -1, 15, -1, -1, -1, -1, -1, 9, 2, 0, 3, 7, -1, -1, -1, -1, -1, -1
        };

        File a = new File("airports.txt");
        File p = new File("ports.txt");
        File w = new File("mairports.txt");
        File fw = new File("watercross.txt");
        File fa = new File("aircross.txt");

        Scanner re1 = new Scanner(a);
        World.acount = re1.nextInt();
        for (int i = 0; i < World.acount; i++) {
            Airport nAirport = new Airport();
            World.airports.add(i, nAirport);
            World.cities.add(nAirport.getCityID(), nAirport);
            Position position = new Position();
            position.setX(re1.nextInt());
            position.setY(re1.nextInt());
            World.airports.get(i).setPosition(position);
        }

        Scanner re2 = new Scanner(fa);
        World.account = re2.nextInt();
        for (int i = 0; i < World.account; i++) {
            Intersection nAIntersection = new Intersection(0);
            World.aintersections.add(i, nAIntersection);
            World.cities.add(nAIntersection.getCityID(), nAIntersection);
            Position position = new Position();
            position.setX(re2.nextInt());
            position.setY(re2.nextInt());
            World.aintersections.get(i).setPosition(position);
        }

        Scanner re3 = new Scanner(p);
        World.pcount = re3.nextInt();
        for (int i = 0; i < World.pcount; i++) {
            Port nPort = new Port();
            World.ports.add(i, nPort);
            World.cities.add(nPort.getCityID(), nPort);
            Position position = new Position();
            position.setX(re3.nextInt());
            position.setY(re3.nextInt());
            World.ports.get(i).setPosition(position);
        }

        Scanner re4 = new Scanner(fw);
        World.wccount = re4.nextInt();
        for (int i = 0; i < World.wccount; i++) {
            Intersection nWIntersection = new Intersection(1);
            World.wintersections.add(i, nWIntersection);
            World.cities.add(nWIntersection.getCityID(), nWIntersection);
            Position position = new Position();
            position.setX(re4.nextInt());
            position.setY(re4.nextInt());
            World.wintersections.get(i).setPosition(position);
        }

        Scanner re5 = new Scanner(w);
        World.macount = re5.nextInt();
        for (int i = 0; i < World.macount; i++){
            MilitaryAirport mAirport = new MilitaryAirport();
            World.mairports.add(i, mAirport);
            World.cities.add(mAirport.getCityID(), mAirport);
            Position position = new Position();
            position.setX(re5.nextInt());
            position.setY(re5.nextInt());
            World.mairports.get(i).setPosition(position);
        }
        
        for (int i = 0; i < World.cities.size(); i++) {
            World.cities.get(i).connect();
            World.cities.get(i).changes();
        }

    }

    public static int nextID() {
        int give = World.vehicleID;
        World.vehicleID += 1;
        return give;
    }

    public static int nextPersonalID() {
        int give = World.passengerID;
        World.passengerID += 1;
        return give;
    }

    public static int nextCityID() {
        int give = World.citiesID;
        World.citiesID += 1;
        return give;
    }

//    public Size getSize() {
//        return size;
//    }
//
//    public void setSize(Size size) {
//        this.size = size;
//    }
//    /**
//     * @return the id
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     * @param id the id to set
//     */
//    public void setId(int id) {
//        this.id = id;
//    }
//    /**
//     * Funkcja do zwiększania licznika id po przydzieleniu go jakiemuś pojazdowi
//     */
//    public void afterId() {
//        this.id = id + 1;
//    }
}
