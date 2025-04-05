public class Client {
    private final WordBrain wordBrain;
    private GameStatus gameStatus;

    public enum GameStatus {
        PREGAME,
        GAME,
        POSTGAME;
    }

    public Client(){
        wordBrain = new WordBrain();
        gameStatus = GameStatus.PREGAME;
    }

    //eval the input and call proper method
    public String eval(String input){
        var command = input.toLowerCase();

        if (command.equals("quit")) {
            return endGame();
        }

        if (gameStatus == GameStatus.PREGAME){
            return evalPregame(command);
        }
        else if (gameStatus == GameStatus.GAME){
            return evalGame(command);
        }
        else if (gameStatus == GameStatus.POSTGAME){
            return evalPostgame(command);
        }

        return "ERROR: unknown game state! (something broke)";
    }

    private String evalPostgame(String command) {
        if (command.equals("y")){
            gameStatus = GameStatus.GAME;
            wordBrain.newGame();
            return "Game starts again!";
        }
        else if (command.equals("n")){
            return endGame();
        }

        return "I'm not sure what that means...";
    }

    private String evalGame(String command) {
        if (command.equals("hint")) {
            return showHint();
        }
        else if (command.matches("[a-z]")) {
            String result = processGuess(command);

            if(wordBrain.getLives() == 0){ //game ends in loss
                gameStatus = GameStatus.POSTGAME;
            }
            return result;
        }
        else {
            return "Only single letters, 'hint', and 'quit' are valid commands!";
        }
    }

    private String evalPregame(String command) {
        if (command.equals("go")){
            gameStatus = GameStatus.GAME;
            return "Game starts now!";
        }
        else {
            return "I'm not sure what that means. Type 'go' to start or 'quit' to quit!";
        }
    }

    private String processGuess(String guess){

        return wordBrain.processGuess(guess);
    }

    private String showHint(){
        return "SHOW HINT";
    }

    public int getLives() {
        return wordBrain.getLives();
    }

    private String endGame() {
        return "GOODBYE!";
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    public String printWordDisplay(){
        return wordBrain.printWordDisplay();
    }

}
