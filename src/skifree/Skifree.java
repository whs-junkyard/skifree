package skifree;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Skifree {
    public static void main(String[] args) throws IOException, InterruptedException {
        Configure c = new Configure();
        c.configure();

        Game g = new Game();
        try {
            g.play();
        } catch (Exception ex) {
            Logger.getLogger(Skifree.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }
}
