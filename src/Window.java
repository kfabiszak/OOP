/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.LinkedList;
import javax.swing.JLabel;

/**
 *
 * @author Krzysztof Fabiszak
 */
public class Window extends JPanel implements MouseListener {

    /**
     * Obraz tła
     */
    private BufferedImage bground;
    /**
     * Obraz ikony
     */
    private static BufferedImage icon;
    /**
     * Zmienna przechowujące punkt, w którym naciśnięto przycisk myszy
     */
    private int mX, mY;

    /**
     * Metoda główna programu.
     *
     * @param args
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws InterruptedException,
            IOException {
        JFrame frame = new JFrame("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        World.World();
        Window window = new Window();
        frame.add(window);
        frame.setSize(1250, 625);
        frame.setIconImage(icon);
        frame.setTitle("Symulator świata");
        frame.setVisible(true);

//        for (int i = 0; i < 20; i++) {
//            Plane plane = new Plane();
//            Thread t = new Thread(plane);
//            World.threads.add(t);
//            t.start();
//            Panel panel = new Panel(plane);
//            Thread p = new Thread(panel);
//            World.threads.add(p);
//            p.start();
//        }
        for (int i = 0; i < 10; i++) {
            Cruiser cruiser = new Cruiser();
            Thread t = new Thread(cruiser);
            World.threads.add(t);
            t.start();
        }
        for (int i = 0; i < 1; i++) {
            Carrier carrier = new Carrier();
            Thread t = new Thread(carrier);
            World.threads.add(t);
            t.start();
        }

        Info info = new Info();
        Thread tInfo = new Thread(info);
        tInfo.start();

        while (true) {
            Thread.sleep(40);
            window.repaint();
        }
    }

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g
    ) {
        int x, y;
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(bground, 0, 0, null);
        for (int i = 0; i < World.pcount; i++) {
            g.drawImage(World.ports.get(i).getImg(), (int) World.ports.get(i).getPosition().getX(), (int) World.ports.get(i).getPosition().getY(), null);
        }
        for (int i = 0; i < World.acount; i++) {
            g.drawImage(World.airports.get(i).getImg(), (int) World.airports.get(i).getPosition().getX(), (int) World.airports.get(i).getPosition().getY(), null);
        }
        for (int i = 0; i < World.wccount; i++) {
            g.drawImage(World.wintersections.get(i).getImg(), (int) World.wintersections.get(i).getPosition().getX(), (int) World.wintersections.get(i).getPosition().getY(), null);
        }
        for (int i = 0; i < World.account; i++) {
            g.drawImage(World.aintersections.get(i).getImg(), (int) World.aintersections.get(i).getPosition().getX(), (int) World.aintersections.get(i).getPosition().getY(), null);
        }
        for (int i = 0; i < World.macount; i++) {
            g.drawImage(World.mairports.get(i).getImg(), (int) World.mairports.get(i).getPosition().getX(), (int) World.mairports.get(i).getPosition().getY(), null);
        }

        //g.drawLine(airports.get(0).getPosition().getX(), airports.get(0).getPosition().getY(), airports.get(1).getPosition().getX(), airports.get(1).getPosition().getY());
        //g.drawImage(samolot, (x += (routes.getFirst().getXdistance()/100)) % airports.get(1).getPosition().getX(), (y -= 1) % (airports.getFirst().getPosition().getY() - airports.get(1).getPosition().getY()), null);
        //g.drawImage(samolot, (x += (routes.getFirst().getXdistance()/100)) % airports.get(1).getPosition().getX(), (y += (routes.getFirst().getYdistance()/100)) % airports.get(1).getPosition().getY(), null);
        //g.drawImage(plane, (x += (World.routes.getFirst().getXdistance()/100)) % World.airports.get(1).getPosition().getX(), y, null);
//        for(int i = 0; i < this.vehicles.size(); i++){
//                 
//                x = (int)Math.round(this.vehicles.get(i).coor.getX());
//                y = (int)Math.round(this.vehicles.get(i).coor.getY());
//                buf = this.vehicles.get(i).buffor;
//                 
//                if(this.vehicles.get(i).getSelected()){
//                    g.drawImage(select, x-10, y-10, 20, 20, null);
//                    panel.fillTextArea(this.vehicleSelected.toString());
//                }
//                if(!this.vehicles.get(i).getSuspend()){
//                    g.drawImage(buf, x-8, y-8, 16, 16, null);
//                }else{
//                    g.drawImage(buf, x-5, y-5, 10, 10, null);
//                } 
//            }
        for (int i = 0; i < World.planes.size(); i++) {
            x = (int) World.planes.get(i).getPosition().getX();
            y = (int) World.planes.get(i).getPosition().getY();
            g.drawImage(World.planes.get(i).getImg(), x, y, 20, 20, null);
        }
        for (int i = 0; i < World.cruisers.size(); i++) {
            x = (int) World.cruisers.get(i).getPosition().getX();
            y = (int) World.cruisers.get(i).getPosition().getY();
            g.drawImage(World.cruisers.get(i).getImg(), x, y, 20, 20, null);
        }
        for (int i = 0; i < World.carriers.size(); i++) {
            x = (int) World.carriers.get(i).getPosition().getX();
            y = (int) World.carriers.get(i).getPosition().getY();
            g.drawImage(World.carriers.get(i).getImg(), x, y, 20, 20, null);
        }
        for (int i = 0; i < World.militaryAircrafts.size(); i++) {
            x = (int) World.militaryAircrafts.get(i).getPosition().getX();
            y = (int) World.militaryAircrafts.get(i).getPosition().getY();
            g.drawImage(World.militaryAircrafts.get(i).getImg(), x, y, 20, 20, null);
        }
    }

    /**
     *
     * @throws IOException
     */
    public Window() throws IOException {

        File image = new File("lotnisko.gif");
        File back = new File("mapa.jpg");
        bground = ImageIO.read(back);
        icon = ImageIO.read(image);

        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setMX(e.getX());
        this.setMY(e.getY());
        for (int i = 0; i < World.planes.size(); i++) {
            if (mX > World.planes.get(i).getPosition().getX() - 20
                    && mX < World.planes.get(i).getPosition().getX() + 20
                    && mY > World.planes.get(i).getPosition().getY() - 20
                    && mY < World.planes.get(i).getPosition().getY() + 20) {
                Panel panel = new Panel(World.planes.get(i));
                Thread p = new Thread(panel);
                World.threads.add(p);
                p.start();
            }
        }
        for (int i = 0; i < World.cities.size(); i++) {
            if (World.cities.get(i).getType() == 0 || World.cities.get(i).getType() == 1) {
                if (mX > World.cities.get(i).getPosition().getX() - 20
                        && mX < World.cities.get(i).getPosition().getX() + 20
                        && mY > World.cities.get(i).getPosition().getY() - 20
                        && mY < World.cities.get(i).getPosition().getY() + 20) {
                    Panel panel = new Panel(World.cities.get(i));
                    Thread p = new Thread(panel);
                    World.threads.add(p);
                    p.start();
                }
            }
        }
        for (int i = 0; i < World.cruisers.size(); i++) {
                if (mX > World.cruisers.get(i).getPosition().getX() - 20
                        && mX < World.cruisers.get(i).getPosition().getX() + 20
                        && mY > World.cruisers.get(i).getPosition().getY() - 20
                        && mY < World.cruisers.get(i).getPosition().getY() + 20) {
                    Panel panel = new Panel(World.cruisers.get(i));
                    Thread p = new Thread(panel);
                    World.threads.add(p);
                    p.start();
                }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * @return the x
     */
    public int getMX() {
        return mX;
    }

    /**
     * @param x the x to set
     */
    public void setMX(int mX) {
        this.mX = mX;
    }

    /**
     * @return the y
     */
    public int getMY() {
        return mY;
    }

    /**
     * @param y the y to set
     */
    public void setMY(int mY) {
        this.mY = mY;
    }
}
