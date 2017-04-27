
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Created by Ja on 2015-10-26.
 */
public class Info extends JFrame implements Runnable, ActionListener {

    private JLabel lPlanes;
    private JLabel lMAircrafts;
    private JLabel lCarriers;
    private JLabel lCruisers;
    private JLabel lPassengers;
    private JLabel lMoving;
    private JLabel nPlanes;
    private JLabel nMAircrafts;
    private JLabel nCarriers;
    private JLabel nCruisers;
    private JLabel nPassengers;
    private JLabel nMoving;
    private JButton sPlane;
    private JButton sCruiser;
    private JButton sCarrier;
    /**
     * Zmienna przechowująca liczbę pasażerów w ruchu
     */
    private int move;

    public Info() {

        setVisible(true);
        setLocation(1250, 0);
        setLayout(null);
        setSize(210, 625);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informacje");
        lPlanes = new JLabel("Liczba samolotów pasażerskich");
        lMAircrafts = new JLabel("Liczba samolotów wojskowych");
        lCarriers = new JLabel("Liczba lotniskowców");
        lCruisers = new JLabel("Liczba statków wycieczkowych");
        lPassengers = new JLabel("Ludność");
        lMoving = new JLabel("Ludność w ruchu");
        nPlanes = new JLabel();
        nMAircrafts = new JLabel();
        nCarriers = new JLabel();
        nCruisers = new JLabel();
        nPassengers = new JLabel();
        nMoving = new JLabel();
        sPlane = new JButton("Samolot");
        sCruiser = new JButton("Wycieczkowiec");
        sCarrier = new JButton("Lotniskowiec");
        this.update();
        lPlanes.setBounds(0, 0, 210, 25);
        nPlanes.setBounds(0, 25, 210, 25);
        lMAircrafts.setBounds(0, 50, 210, 25);
        nMAircrafts.setBounds(0, 75, 210, 25);
        lCarriers.setBounds(0, 100, 210, 25);
        nCarriers.setBounds(0, 125, 210, 25);
        lCruisers.setBounds(0, 150, 210, 25);
        nCruisers.setBounds(0, 175, 210, 25);
        lPassengers.setBounds(0, 200, 210, 25);
        nPassengers.setBounds(0, 225, 210, 25);
        lMoving.setBounds(0, 250, 210, 25);
        nMoving.setBounds(0, 275, 210, 25);
        sPlane.setBounds(0, 300, 200, 25);
        sCruiser.setBounds(0, 325, 200, 25);
        sCarrier.setBounds(0, 350, 200, 25);
        add(lPlanes);
        add(nPlanes);
        add(lMAircrafts);
        add(nMAircrafts);
        add(lCarriers);
        add(nCarriers);
        add(lCruisers);
        add(nCruisers);
        add(lPassengers);
        add(nPassengers);
        add(lMoving);
        add(nMoving);
        add(sPlane);
        add(sCruiser);
        add(sCarrier);
        sPlane.addActionListener(this);
        sCruiser.addActionListener(this);
        sCarrier.addActionListener(this);
    }

    private void update() {
        this.move = 0;
        for (int i = 0; i < World.passengers.size(); i++) {
            if (World.passengers.get(i).isMoving()) {
                move++;
            }
        }
        this.nPlanes.setText(Integer.toString(World.planes.size()));
        this.nMAircrafts.setText(Integer.toString(World.militaryAircrafts.size()));
        this.nCarriers.setText(Integer.toString(World.carriers.size()));
        this.nCruisers.setText(Integer.toString(World.cruisers.size()));
        this.nPassengers.setText(Integer.toString(World.passengers.size()));
        this.nMoving.setText(Integer.toString(this.move));

    }

    private void changeRoute() {

    }

    private void emergency() {

    }

    @Override
    public void run() {
        while (true) {
            this.update();
            this.repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Info.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == sPlane){
            Plane plane = null;
            try {
                plane = new Plane();
            } catch (IOException ex) {
                Logger.getLogger(Info.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread t = new Thread(plane);
            World.threads.add(t);
            t.start();      
        }else if (source == sCruiser){
            Cruiser cruiser = null;
            try {
                cruiser = new Cruiser();
            } catch (IOException ex) {
                Logger.getLogger(Info.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread t = new Thread(cruiser);
            World.threads.add(t);
            t.start();  
        }else if (source == sCarrier){
            Carrier carrier = null;
            try {
                carrier = new Carrier();
            } catch (IOException ex) {
                Logger.getLogger(Info.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread t = new Thread(carrier);
            World.threads.add(t);
            t.start();  
        }
    }

}
