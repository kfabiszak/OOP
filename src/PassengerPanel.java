
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ja
 */
public class PassengerPanel extends JFrame implements ActionListener{
    
    /**
     * Etykieta "Imię"
     */
    private JLabel lName;
    /**
     * Etykieta "Nazwisko"
     */
    private JLabel lSurname;
    /**
     * Etykieta "Wiek"
     */
    private JLabel lAge;
    /**
     * Etykieta "Identyfikator"
     */
    private JLabel lID;
    /**
     * Etykieta "Dom"
     */
    private JLabel lHome;
    /**
     * Etykieta "Cel"
     */
    private JLabel lTarget;
    /**
     * Imię pasażera
     */
    private JTextField sName;
    /**
     * Nazwisko pasażera
     */
    private JTextField sSurname;
    /**
     * Wiek pasażera
     */
    private JTextField sAge;
    /**
     * Identyfikator pasażera
     */
    private JTextField sID;
    /**
     * Identyfikator miasta - domu pasażera
     */
    private JTextField sHome;
    /**
     * Identyfikator miasta - celu pasażera
     */
    private JTextField sTarget;
    /**
     * Przycisk do zapisywania zmian w panelu pasażera
     */
    private JButton sav;
    
    
    public PassengerPanel(Passenger p){
        
        setVisible(true);
        setLocation(0, 625);
        setLayout(null);
        setSize(150, 325);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informacje");
        lName = new JLabel("Imię");
        lSurname = new JLabel("Nazwisko");
        lAge = new JLabel("Wiek");
        lID = new JLabel("Identyfikator");
        lHome = new JLabel("Dom");
        lTarget = new JLabel("Cel");
        sName = new JTextField(p.getName());
        sSurname = new JTextField(p.getSurname());
        sAge = new JTextField(Integer.toString(p.getAge()));
        sID = new JTextField(Integer.toString(p.getPersonalID()));
        sHome = new JTextField(p.getHome().getCityID());
        sTarget = new JTextField(p.getTarget().getCityID());
        sav = new JButton("Zapisz");
        lName.setBounds(0, 0, 150, 25);
        sName.setBounds(0, 25, 150, 25);
        lSurname.setBounds(0, 50, 150, 25);
        sSurname.setBounds(0, 75, 150, 25);
        lAge.setBounds(0, 100, 150, 25);
        sAge.setBounds(0, 125, 150, 25);
        lID.setBounds(0, 150, 150, 25);
        sID.setBounds(0, 175, 150, 25);
        lHome.setBounds(0, 200, 150, 25);
        sHome.setBounds(0, 225, 150, 25);
        lTarget.setBounds(0, 250, 150, 25);
        sTarget.setBounds(0, 275, 150, 25);
        sav.setBounds(0, 300, 150, 25);
        sav.addActionListener(this);
        add(lName);
        add(sName);
        add(lSurname);
        add(sSurname);
        add(lAge);
        add(sAge);
        add(lID);
        add(sID);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sav){
            
        }
    }
}
