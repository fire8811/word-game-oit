import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Random;

public class WordBrain {
    private int lives = 7;
    private int hintsRemaining = 1;
    private boolean easy;
    private String word;
    private ArrayList<String> prevGuesses = new ArrayList<>();
    private ArrayList<String> wordDisplay = new ArrayList<>(); //displays the correct guesses made by the user (starts as blank spaces)

    public WordBrain(String mode) {
        easy = mode.equals("easy");

        this.word = getRandomWord();
        wordDisplay = getBlankWord();
    }

    public String processGuess(String guess){
        //correct guess
        if(word.contains(guess) && !prevGuesses.contains(guess)){
            prevGuesses.add(guess);
            return correctGuess(guess);
        }
        //bad guesses
        if (prevGuesses.contains(guess)){
            return ("You already guessed " + guess + "!");
        }
        else if(!word.contains(guess)){ //incorrect guess
            lives--;
            prevGuesses.add(guess);

            if (lives == 0){ //end game
                return "\nGAME OVER! The word was " + word + ".";
            }

            return "\nINCORRECT";
        }

        return "ERROR";
    }

    //update the wordDisplay to store the correctly guessed letter
    //finds the index of the correct letter and updates the corresponding index in wordDisplay
    private String correctGuess(String guess) {
        updateWordDisplay(guess);

        return "\nCORRECT";
    }

    private void updateWordDisplay(String guess) {
        for(int i = 0; i < wordDisplay.size(); i++){
            if (guess.equals(String.valueOf(word.charAt(i)))){
                wordDisplay.set(i, guess);
            }
        }
    }

    private ArrayList<String> getBlankWord() {
        ArrayList<String> arrayList = new ArrayList<>();
        int wordSize = easy ? 4 : 6;

        for(int i=0; i < wordSize; i++){
            arrayList.add("_");
        }
        return arrayList;
    }

    private String getRandomWord() {
        String json = sendRequest();
        ArrayList<String> wordList = getWordList(json);

        return wordList.get(new Random().nextInt(wordList.size())); //select random word from list
    }

    //fetch 1000 words from API and return String of the response JSON
    private String sendRequest()  {
        try {
            URL url = (new URI(getUrlString()).toURL());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            int responseCode = http.getResponseCode();

            if (responseCode == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuffer jsonResponseData = new StringBuffer();
                String readLine;

                while ((readLine = in.readLine()) != null){
                    jsonResponseData.append(readLine);
                }
                in.close();

                //convert StringBuffer to String
                return jsonResponseData.toString();

            } else {
                System.out.println(responseCode);
            }
        } catch (IOException | URISyntaxException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    private String getUrlString() {
        if(easy == true){
            return "https://api.datamuse.com/words?sp=????&max=1000";
        }
        else {
            return  "https://api.datamuse.com/words?sp=??????&max=1000";
        }
    }

    //extract words from JSON into arrayList of words
    private static ArrayList<String> getWordList(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray(); //Turn JSON string into Array

        ArrayList<String> words = new ArrayList<>(); //Arraylist of words

        //add words to ArrayList
        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject l = jsonArray.get(i).getAsJsonObject();
            words.add(l.get("word").getAsString());
        }

        return words;
    }

    public void newGame() {
        word = getRandomWord();
        wordDisplay = getBlankWord();
        prevGuesses = new ArrayList<String>();
        lives = 7;
        hintsRemaining = 1;
    }

    public String printWordDisplay() {
        String returnString = "";
        for(int i = 0; i < wordDisplay.size(); i++){
            returnString += (wordDisplay.get(i) + " ");
        }

        return returnString;
    }

    //update wordDisplay to show one of the word's letters as a hint
    public String showHint() {
        if (hintsRemaining == 0) {
            return "You already used your hint!";
        }

        String hint = null;
        while (hint == null){
            String letter = String.valueOf(word.charAt(new Random().nextInt(wordDisplay.size())));
            if (!prevGuesses.contains(letter)){
                hint = letter;
                updateWordDisplay(hint);
                prevGuesses.add(hint);
                hintsRemaining = 0;
            }
        }

        return "Here's a hint for you";
    }

    //game has been won if no blank spaces are in the wordDisplay ArrayList
    public boolean gameWon(){
        return !wordDisplay.contains("_");
    }

    public String getWord() {
        return word;
    }

    public int getLives() {
        return lives;
    }
}
