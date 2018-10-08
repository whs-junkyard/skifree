package skifree;

import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;


public class Highscore {
    String appsecret = "sdjflkefklwsdklfsklfjsd";
    int appid = 1001;

    public JsonReader getScore() throws Exception {
        URL url = new URL("https://gamehighscore.appspot.com/?id=" + appid);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        JsonReader reader = new JsonReader(in);
        reader.setLenient(true);
        reader.beginArray();
        return reader;
    }

    public Integer sendScore(String name, int score) throws Exception {
        if (name.equals("")) throw new Exception("No name");
        String sig = DigestUtils.sha256Hex(score + appsecret);
        URL url = new URL("https://gamehighscore.appspot.com/register?id=" + appid + "&score=" + score + "&name=" + name + "&signature=" + sig);

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        Integer pos = Integer.valueOf(Integer.parseInt(in.readLine().toString()));
        return pos;
    }
}
