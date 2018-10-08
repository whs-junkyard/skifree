package skifree;

import java.io.IOException;

import jline.ConsoleReader;


public class Configure {
    public void configure() throws IOException, InterruptedException {
        ConsoleReader inp = new ConsoleReader();
        if ((inp.getTermwidth() == 80) && (inp.getTermheight() == 25)) {
            return;
        }
        System.out.println(repeat("=", 80));
        int i = 0;
        while (i < 23) {
            String msg = "";
            if (i == 0) {
                msg = "SkiFree!";
            } else if (i == 1) {
                msg = repeat("-", 76);
            } else if (i == 2) {
                msg = "How to play:";
            } else if (i == 3) {
                msg = "Run around, don't hit #, collect L.";
            } else if (i == 5) {
                msg = "Controls:";
            } else if (i == 6) {
                msg = "A to steer left. D to steer right.";
            } else if (i == 11) {
                msg = "Please set window size to 80x25 character";
            } else if (i == 12) {
                msg = "so that all borders fit on screen.";
            }
            System.out.println("| " + msg + repeat(" ", 77 - msg.length()) + "|");
            i++;
        }
        System.out.print(repeat("=", 80));
        while ((inp.getTermwidth() != 80) || (inp.getTermheight() != 25)) {
            Thread.sleep(50L);
        }
    }


    private String repeat(String str, int count) {
        return String.format(String.format("%%0%dd", count), 0).replace("0", str);
    }
}
