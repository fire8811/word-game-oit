import java.util.Scanner;

public class Repl {
    private final Client client;

    public Repl(){
        client = new Client();
    }

    public void run(){
        System.out.println("======================");
        System.out.println("Welcome to Not Wordle!");
        System.out.println("======================");
        System.out.println("Guess a letter up to 7 times to find the hidden word!");
        System.out.println("Quit anytime with 'quit'. Reveal 1 letter with 'hint'. Type 'go' to start!");

        Scanner scanner = new Scanner(System.in);
        var result = "";

        while(!result.equals("GOODBYE!")) {
            if(client.getGameStatus() == Client.GameStatus.GAME){
                System.out.println("\n _ _ _ _ _");
                System.out.println("LIVES: " + client.getLives());
            }
            else if(client.getGameStatus() == Client.GameStatus.POSTGAME){
                System.out.println("Play again? (y/n)");
            }

            System.out.print("\nINPUT: ");
            String input = scanner.nextLine();

            result = client.eval(input);
            System.out.println(result);




        }
    }
}
