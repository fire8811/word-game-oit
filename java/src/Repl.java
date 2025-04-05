import java.util.Scanner;

public class Repl {
    private final String url;
    private final Client client;

    public Repl(String url){
        this.url = url;
        client = new Client(url);
    }

    public void run(){
        System.out.println("======================");
        System.out.println("Welcome to Not Wordle!");
        System.out.println("======================");
        System.out.println("Guess a letter up to 7 times to find the hidden word!");
        System.out.println("Quit anytime with 'quit'. Reveal 1 letter with 'hint'. Game starts now!");

        Scanner scanner = new Scanner(System.in);
        var result = "";

        while(!result.equals("GOODBYE!")) {
            System.out.println("\nWORD: _ _ _ _ _");
            System.out.print("INPUT: ");
            String input = scanner.nextLine();

            result = client.eval(input);

            System.out.println(result);


        }
    }
}
