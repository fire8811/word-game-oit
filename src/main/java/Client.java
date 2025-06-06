public class Client {
    private WordBrain wordBrain;
    private GameStatus gameStatus;

    public enum GameStatus {
        PREGAME,
        GAME,
        POSTGAME;
    }

    public Client(){
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

    //pregame UI
    private String evalPregame(String command) {
        if (command.equals("easy")){
            gameStatus = GameStatus.GAME;
            wordBrain = new WordBrain("easy");
            return "Game starts now!";
        }
        else if (command.equals("hard")){
            gameStatus = GameStatus.GAME;
            wordBrain = new WordBrain("hard");
            return "Game starts now!";
        }
        else {
            return "I'm not sure what that means. Type 'easy'/'hard' to start or 'quit' to quit!";
        }
    }

    //ui for gameplay
    private String evalGame(String command) {
        if (command.equals("hint")) {
            return wordBrain.showHint();
        }
        else if (command.matches("[a-z]")) {
            String result = processGuess(command);

            if(wordBrain.getLives() == 0){ //game ends in loss
                gameStatus = GameStatus.POSTGAME;
            }
            else if (wordBrain.gameWon()) {
                result += "\nYOU WON!\nWord was " + wordBrain.getWord() + ".";
                gameStatus = GameStatus.POSTGAME;
            }
            return result;
        }
        else {
            return "Only single letters, 'hint', and 'quit' are valid commands!";
        }
    }

    //postgame ui
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

    private String processGuess(String guess){
        return wordBrain.processGuess(guess);
    }

    public int getLives() {
        return wordBrain.getLives();
    }

    //ends the repl loop when returned
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
