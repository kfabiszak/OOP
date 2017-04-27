
import java.awt.Graphics;
import java.io.IOException;

/**
 * Created by Ja on 2015-10-26.
 */
public class Port extends City {

    public Port() throws IOException {
        this.setType(1);
        this.getImage("port.gif");
    }
}
