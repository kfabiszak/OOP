
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Created by Ja on 2015-10-26.
 */
public class Panel extends JFrame  implements Runnable, ActionListener{

    private JLabel lPlanes;
    private JLabel nPlanes;
    private JLabel lPassengers;
    private JLabel nPassengers;
    private JLabel lPassengersC;
    private JLabel nPassengersC;
    private JLabel lPass;
    private JLabel nPass;
    private JLabel lStart;
    private JLabel nStart;
    private JLabel lTarget;
    private JTextField nTarget;
    private JLabel lStartC;
    private JLabel nStartC;
    private JLabel lTargetC;
    private JTextField nTargetC;
    private JLabel lFuel;
    private JLabel nFuel;
    private JLabel lName;
    private JTextField nName;
    private JLabel lSurname;
    private JTextField nSurname;
    private JLabel lAge;
    private JTextField nAge;
    private JLabel lPath;
    private JLabel nPath;
    private JLabel lHome;
    private JLabel nHome;
    private JLabel lIn;
    private JLabel nIn;
    private JLabel lTar;
    private JLabel nTar;
    private JButton dPlane;
    private JButton dPass;
    private JButton dCruiser;
    private JLabel lCompany;
    private JTextField nCompany;
    private JComboBox lPassP;
    private JComboBox lPassC;
    private JComboBox lPassCity;

    /**
     * Samolot pasażerski, do którego należy panel
     */
    private Plane plane;
    /**
     * Statek wycieczkowy, do którego należy panel
     */
    private Cruiser cruiser;
    /**
     * Miasto, do którego należy panel
     */
    private City city;
    /**
     * Pasażer, do którego należy panel
     */
    private Passenger passenger;
    /**
     * Typ panelu (0 - dla samolotu pasażerskiego, 1 - dla miasta, 2 - dla statku wycieczkowego, 3 - dla pasażera)
     */
    private int type;

    /**
     * Konstruktor do tworzenia panelu dla samolotu
     *
     * @param vehicle Samolot, dla którego wywoływany jest panel
     */
    public Panel(Plane vehicle) {

        this.type = 0;
        this.plane = vehicle;
        setVisible(true);
        setLocation(0, 625);
        setLayout(null);
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pojazd nr " + Integer.toString(this.plane.getId() + 1));
        lPassengers = new JLabel("Ilość pasażerów");
        lStart = new JLabel("Start");
        lTarget = new JLabel("Cel");
        lFuel = new JLabel("Paliwo");
        nPassengers = new JLabel();
        nStart = new JLabel();
        nTarget = new JTextField();
        nFuel = new JLabel();
        dPlane = new JButton("Usuń");
        lPassP = new JComboBox();
        this.update();
        lPassengers.setBounds(0, 0, 300, 25);
        nPassengers.setBounds(0, 25, 300, 25);
        lStart.setBounds(0, 50, 300, 25);
        nStart.setBounds(0, 75, 300, 25);
        lTarget.setBounds(0, 100, 300, 25);
        nTarget.setBounds(0, 125, 300, 25);
        lFuel.setBounds(0, 150, 300, 25);
        nFuel.setBounds(0, 175, 300, 25);
        dPlane.setBounds(0, 200, 250, 25);
        lPassP.setBounds(0, 225, 300, 25);
        add(nPassengers);
        add(lStart);
        add(nStart);
        add(lTarget);
        add(nTarget);
        add(lFuel);
        add(nFuel);
        add(dPlane);
        add(lPassP);
        dPlane.addActionListener(this);
    }

    /**
     * Konstruktor do tworzenia panelu dla statków wycieczkowych
     *
     * @param vehicle Statek, dla którego wywoływany jest panel
     */
    public Panel(Cruiser vehicle) {

        this.type = 2;
        this.cruiser = vehicle;
        setVisible(true);
        setLocation(900, 625);
        setLayout(null);
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pojazd nr " + Integer.toString(this.cruiser.getId() + 1));
        lPassengersC = new JLabel("Ilość pasażerów");
        lStartC = new JLabel("Start");
        lTargetC = new JLabel("Cel");
        lCompany = new JLabel("Firma");
        nPassengersC = new JLabel();
        nStartC = new JLabel();
        nTargetC = new JTextField();
        nCompany = new JTextField();
        dCruiser = new JButton("Usuń");
        lPassC = new JComboBox();
        this.update();
        lCompany.setBounds(0, 0, 300, 25);
        nCompany.setBounds(0, 25, 300, 25);
        lPassengersC.setBounds(0, 50, 300, 25);
        nPassengersC.setBounds(0, 75, 300, 25);
        lStartC.setBounds(0, 100, 300, 25);
        nStartC.setBounds(0, 125, 300, 25);
        lTargetC.setBounds(0, 150, 300, 25);
        nTargetC.setBounds(0, 175, 300, 25);
        dCruiser.setBounds(0, 200, 250, 25);
        lPassC.setBounds(0, 225, 300, 25);
        add(lPassengersC);
        add(nPassengersC);
        add(lStartC);
        add(nStartC);
        add(lTargetC);
        add(nTargetC);
        add(dCruiser);
        add(lCompany);
        add(nCompany);
        add(lPassC);
        dCruiser.addActionListener(this);
    }

    /**
     * Konstruktor do tworzenia panelu dla miasta
     *
     * @param c Miasto, dla którego wywoływany jest panel
     */
    public Panel(City c) {

        this.type = 1;
        this.city = c;
        setVisible(true);
        setLocation(300, 625);
        setLayout(null);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Miasto nr " + Integer.toString(this.city.getCityID() + 1));
        lPass = new JLabel("Ilość pasażerów");
        lPlanes = new JLabel("Ilość samolotów");
        lPassCity = new JComboBox();
        nPass = new JLabel();
        nPlanes = new JLabel();
        this.update();
        lPass.setBounds(0, 0, 300, 25);
        nPass.setBounds(0, 25, 300, 25);
        lPlanes.setBounds(0, 50, 300, 25);
        nPlanes.setBounds(0, 75, 300, 25);
        lPassP.setBounds(0, 100, 300, 25);
        add(lPass);
        add(nPass);
        add(lPlanes);
        add(nPlanes);
        add(lPassCity);
    }

    /**
     * Konstruktor do tworzenia panelu dla pasażera
     *
     * @param pass Pasażer, dla którego wywoływany jest panel
     */
    public Panel(Passenger pass) {

        this.type = 3;
        this.passenger = pass;
        setVisible(true);
        setLocation(600, 625);
        setLayout(null);
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pasażer nr " + Integer.toString(this.passenger.getPersonalID() + 1));
        lName = new JLabel("Imię");
        lSurname = new JLabel("Nazwisko");
        lAge = new JLabel("Wiek");
        lPath = new JLabel("Ścieżka");
        lHome = new JLabel("Dom");
        lIn = new JLabel("Aktualnie w");
        lTar = new JLabel("Cel");
        dPass = new JButton("Usuń");
        nName = new JTextField();
        nSurname = new JTextField();
        nAge = new JTextField();
        nPath = new JLabel();
        nHome = new JLabel();
        nIn = new JLabel();
        nTar = new JLabel();
        this.update();
        lName.setBounds(0, 0, 300, 25);
        nName.setBounds(0, 25, 300, 25);
        lSurname.setBounds(0, 50, 300, 25);
        nSurname.setBounds(0, 75, 300, 25);
        lAge.setBounds(0, 100, 300, 25);
        nAge.setBounds(0, 125, 300, 25);
        lPath.setBounds(0, 150, 300, 25);
        nPath.setBounds(0, 175, 300, 25);
        lHome.setBounds(0, 200, 300, 25);
        nHome.setBounds(0, 225, 300, 25);
        lIn.setBounds(0, 250, 300, 25);
        nIn.setBounds(0, 275, 300, 25);
        lTar.setBounds(0, 300, 300, 25);
        nTar.setBounds(0, 325, 300, 25);
        dPass.setBounds(0, 250, 300, 25);
        add(lName);
        add(nName);
        add(lSurname);
        add(nSurname);
        add(lAge);
        add(nAge);
        add(lPath);
        add(nPath);
        add(lHome);
        add(nHome);
        add(lIn);
        add(nIn);
        add(lTar);
        add(nTar);
        add(dPass);
        dPass.addActionListener(this);
    }

    /**
     * Uaktualnia panel zależnie od typu właściciela (Nadaje wartości polom).
     */
    private void update() {
        switch (this.type) {
            case 0:
                this.nPassengers.setText(Integer.toString(this.plane.getPassengerCount()));
                this.nStart.setText(Integer.toString(this.plane.getStartPoint().getCityID()));
                this.nTarget.setText(Integer.toString(this.plane.getTarget().getCityID()));
                this.nFuel.setText(Double.toString(this.plane.getPerFuel() * 100) + "%");
                this.lPassP.removeAllItems();
                for (int i = 0; i < this.plane.getPassengerCount(); i++){
                    this.lPassP.addItem(this.plane.container.passengers.get(i).getName() + " " + this.plane.container.passengers.get(i).getSurname());
                }
                break;
            case 1:
                this.nPass.setText(Integer.toString(this.city.getPassengersCount()));
                this.nPlanes.setText(Integer.toString(this.city.getVehicleCount()));
                break;
            case 2:
                this.nCompany.setText(this.cruiser.getCompany());
                this.nPassengersC.setText(Integer.toString(this.cruiser.getPassengerCount()));
                this.nStartC.setText(Integer.toString(this.cruiser.getStartPoint().getCityID()));
                this.nTargetC.setText(Integer.toString(this.cruiser.getTarget().getCityID()));
                break;
            case 3:
                this.nName.setText(this.passenger.getName());
                this.nSurname.setText(this.passenger.getSurname());
                this.nAge.setText(Integer.toString(this.passenger.getAge()));
                this.nHome.setText(Integer.toString(this.passenger.getHome().getCityID()));
                this.nIn.setText(Integer.toString(this.passenger.getIn().getCityID()));
                this.nTar.setText(Integer.toString(this.passenger.getTarget().getCityID()));
                break;
            default:
                break;
        }
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
//        Object source = e.getSource();
//        if (source == this.lPassP){
//            
//        }
//        if (source == dPass){
//            this.passenger.delete();
//        }else if (source == dPlane){
//            this.plane.delete();
//        }else if (source == dCruiser){
//            this.cruiser.delete();
//        }
    }
}
