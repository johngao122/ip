import java.util.Scanner;

public class Quip {
    private static final String NAME = "Quip";
    private static final String LINE = "____________________________________________________________";

    private static void greet() {
        String logo = "________        .__        \n" +
                "\\_____  \\  __ __|__|_____  \n" +
                " /  / \\  \\|  |  \\  \\____ \\ \n" +
                "/   \\_/.  \\  |  /  |  |_> >\n" +
                "\\_____\\ \\_/____/|__|   __/ \n" +
                "       \\__>        |__|    \n";
        System.out.println(logo);
        System.out.println("Hi there human! I'm " + NAME + "!");
        System.out.println("What shenanigans can I help you with today?");
        System.out.println(LINE);
    }

    private static void exit() {
        System.out.println(LINE);
        System.out.println("Aww, you’re leaving already? \uD83D\uDE22 Bye for now!");
        System.out.println(LINE);
    }

    private static void processCommands(){
        while(true){
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            if(command.equals("bye")){
                exit();
                break;
            } else {
                System.out.println(LINE);
                System.out.println(command);
                System.out.println(LINE);
            }
        }
    }


    public static void main(String[] args) {
        greet();
        processCommands();
    }
}
