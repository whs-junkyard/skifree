package skifree;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import jline.ANSIBuffer;
import jline.ConsoleReader;
import jline.Terminal;


public class Game {
    int framerate = 10;
    int defframerate = 10;

    int boostEnd = 0;

    ArrayList buf = new ArrayList(25);
    int curPos = 41;

    int life = 3;
    long time = 0;

    ConsoleReader inp;
    Terminal term;

    public void play() throws Exception {
        inp = new ConsoleReader();
        term = inp.getTerminal();

        time = new Date().getTime();
        Mapgen map = new Mapgen();

        term.disableEcho();

        while (buf.size() < 25) {
            buf.add(map.nextLine());
        }

        cls();

        try {
            drawBuffer();
        } catch (Dead ex) {
        }

        InputStream ins = inp.getInput();

        for (; ; ) {
            Thread.sleep(1000 / framerate);

            buf.remove(0);
            buf.add(map.nextLine());

            if ((framerate != defframerate) && (boostEnd < (int) new Date().getTime())) {
                framerate = defframerate;
            }

            cls();
            try {
                drawBuffer();
            } catch (Dead ex) {
                int td = timePassed();

                if (td > 5) {
                    inp.beep();
                    life -= 1;
                    if (life < 0) {
                        deadScene();
                        return;
                    }
                }
            }

            if (ins.available() > 0) {
                int key = inp.readVirtualKey();
                if ((key == 97) || (key == 65)) {
                    curPos -= 1;
                    if (curPos < 0) curPos = 0;
                } else if ((key == 100) || (key == 68)) {
                    curPos += 1;
                    if (curPos > 80) {
                        curPos = 80;
                    }
                }
                int i = 0;
                while (i < ins.available()) {
                    inp.readVirtualKey();
                    i++;
                }
            }
        }
    }


    public void cls() {
        if (term.isANSISupported()) {
            System.out.println(ANSIBuffer.ANSICodes.clrscr());
        } else {
            System.out.print(repeat("\n", 25));
        }
    }

    public int timePassed() {
        long ntime = new Date().getTime();
        return (int) Math.floor((ntime - time) / 1000);
    }


    public void drawBuffer() throws Dead, IOException {
        int i = 0;
        int td = timePassed();

        for (Object line : buf.toArray()) {
            if (i == 0) {
                String info = "LIFE: " + life;
                info = info + " TIME: " + String.format("%d", td);
                String nline = line.toString();

                nline = nline.substring(0, nline.length() - info.length()) + info;
                line = nline;
            } else if (i == 10) {
                String oline = "";
                int pos = 1;
                for (String s : line.toString().split("")) {
                    if (pos == curPos) {
                        oline = oline + "v";

                        if (s.equals("#")) {
                            if (new Random().nextBoolean()) {
                                curPos += 1;
                                if (curPos > 80) curPos -= 2;
                            } else {
                                curPos -= 1;
                                if (curPos < 0) curPos += 2;
                            }
                            throw new Dead();
                        }
                        if (s.equals("L")) {
                            life += 1;
                        } else if (s.equals("S")) {
                            framerate += 10;
                            boostEnd = ((int) (new Date().getTime() + 5000));
                        } else if (s.equals("s")) {
                            framerate -= 3;
                            if (framerate <= 0) framerate = 1;
                            boostEnd = ((int) (new Date().getTime() + 5000));
                        }
                    } else {
                        oline = oline + s;
                    }
                    pos++;
                }
                line = oline;
            }

            if (term.isANSISupported()) {
                inp.printNewline();
                for (String s : line.toString().split("")) {
                    System.out.print(ANSIBuffer.ANSICodes.attrib(0));
                    System.out.print(ANSIBuffer.ANSICodes.attrib(37));
                    if (s.equals("#")) {
                        System.out.print(ANSIBuffer.ANSICodes.attrib(42));
                    } else if (s.equals("L")) {
                        System.out.print(ANSIBuffer.ANSICodes.attrib(35));
                    } else if (s.equals("S")) {
                        System.out.print(ANSIBuffer.ANSICodes.attrib(34));
                    } else if (s.equals("s")) {
                        System.out.print(ANSIBuffer.ANSICodes.attrib(36));
                    } else if (s.equals("v")) {
                        System.out.print(ANSIBuffer.ANSICodes.attrib(43));

                        if (td < 5) {
                            System.out.print(ANSIBuffer.ANSICodes.attrib(5));
                        }
                    }
                    System.out.print(s);
                }
            } else {
                System.out.print("\n" + line);
            }
            i++;
        }
    }


    private String repeat(String str, int count) {
        return String.format(String.format("%%0%dd", count), 0).replace("0", str);
    }

    public void deadScene() throws Exception {
        Highscore hs = new Highscore();

        cls();

        System.out.print(ANSIBuffer.ANSICodes.gotoxy(1, 0));

        int td = timePassed();

        System.out.print(ANSIBuffer.ANSICodes.attrib(41));
        System.out.print(ANSIBuffer.ANSICodes.attrib(37));
        System.out.println(repeat(" ", 34) + "YOU ARE DEAD" + repeat(" ", 34));
        System.out.print(ANSIBuffer.ANSICodes.attrib(0));
        System.out.println("Time: " + String.format("%s", td));

        JsonReader score = hs.getScore();
        System.out.println("\nHighscore:");

        int rank = 1;
        while (score.hasNext()) {
            score.beginObject();
            String hisName = "";
            Integer hisScore = Integer.valueOf(0);

            while (score.hasNext()) {
                String name = score.nextName();
                if (name.equals("name")) {
                    hisName = score.nextString();
                } else if (name.equals("score")) {
                    hisScore = Integer.valueOf(score.nextInt());
                } else
                    score.skipValue();
            }

            System.out.println(rank + ". " + hisName + "\t\t" + hisScore);
            score.endObject();
            rank++;
        }

        score.endArray();

        String name = inp.readLine("Enter name: ");
        if (name.equals("")) return;
        int pos = hs.sendScore(name, td).intValue();
        System.out.println(pos + ". " + name + "\t\t" + td);
    }
}
