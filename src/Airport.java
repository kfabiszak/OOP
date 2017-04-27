
import java.awt.Graphics;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Ja on 2015-10-26.
 */
public class Airport extends City {

    public Airport() throws IOException {
        this.setType(0);
        this.getImage("lotnisko.gif");
    }


}
