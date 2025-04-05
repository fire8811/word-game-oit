import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.*;

import com.google.gson.Gson;
import java.util.ArrayList;

public class WordBrain {
    private int lives = 7;
    private final String word;
    //private static final String URL = "https://api.datamuse.com/words?sp=?????&max=5";

    public WordBrain() {
        this.word = getWord();
        System.out.println("WORDS: " + word);
    }

    private String getWord() {
       return sendRequest();
    }

    private String sendRequest()  {
        try {
            URL url = (new URI("https://api.datamuse.com/words?sp=?????&max=5").toURL());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            int responseCode = http.getResponseCode();

            if (responseCode == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuffer jsonResponseData = new StringBuffer();
                String readLine = null;

                while ((readLine = in.readLine()) != null){
                    jsonResponseData.append(readLine);
                }

                in.close();
                System.out.println(jsonResponseData);
            } else {
                System.out.println(responseCode);
            }
        } catch (IOException | URISyntaxException e){
            System.out.println(e.getMessage());
        }


        return "";
    }
}
