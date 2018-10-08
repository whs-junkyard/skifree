package skifree;

import java.util.Random;


public class Mapgen {
    public Random gen = new Random();

    public String nextLine() {
        String out = "#";

        int width = 77;
        int i = 0;

        while (i < width) {
            int rnd = gen.nextInt(10000);
            String spawn = " ";

            if ((i < width / 8) || (i > width / 8 * 7)) {
                if (rnd <= 500) {
                    spawn = "#";
                } else if (rnd >= 9950) {
                    spawn = "L";
                } else if ((rnd >= 9900) && (rnd <= 9920)) {
                    spawn = "S";
                } else if ((rnd >= 5000) && (rnd <= 5050)) {
                    spawn = "s";
                }
            } else if (rnd <= 200) {
                spawn = "#";
            } else if (rnd >= 9990) {
                spawn = "L";
            } else if ((rnd >= 9800) && (rnd <= 9820)) {
                spawn = "S";
            } else if ((rnd >= 5000) && (rnd <= 5010)) {
                spawn = "s";
            }

            out = out + spawn;
            i++;
        }

        out = out + "#";
        return out;
    }
}
