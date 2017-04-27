
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ja
 */
public class MilitaryAirport extends City{
    
    public MilitaryAirport() throws IOException{
        this.setType(4);
        this.getImage("lotniskow.gif");
    }
}
