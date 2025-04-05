public class Client {
    private String url;
    private WordBrain wordBrain;

    public Client(String url){
        this.url = url;
    }

    //eval the input and call proper method
    public String eval(String input){
        var command = input.toLowerCase();

        if (command.equals("quit")) {
            return endGame();
        } else if (command.equals("hint")) {
            return showHint();
        } else if (command.matches("[a-z]")) {
            return processGuess(command);
        } else {
            return "Only letters, 'hint', and 'quit' are valid commands!";
        }

    }

    private String processGuess(String guess){
        return guess;
    }

    private String showHint(){
        return "SHOW HINT";
    }

    private String endGame(){
        return "GOODBYE!";
    }
}
