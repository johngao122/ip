import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quip {
    private static final String NAME = "Quip";
    private static final String LINE = "____________________________________________________________";
    private static List<Task> tasks = new ArrayList<>();

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

    private static void addTask(String task) {
        tasks.add(new Task(task));
        System.out.println(LINE);
        System.out.println("Gotcha. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void listTasks() {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            String message = (i + 1) + ". ";
            message += tasks.get(i).isDone() ? "[X] " : "[ ] ";
            message += tasks.get(i);
            System.out.println(message);
        }
        System.out.println(LINE);
    }

    private static void processCommands() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            if (command.startsWith("mark")) {
                String[] parts = command.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                tasks.get(index).markAsDone();
                System.out.println("Nice! So you have finished this task: " + tasks.get(index));
            } else if (command.startsWith("unmark")) {
                String[] parts = command.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                tasks.get(index).markAsUndone();
                System.out.println("Alright! I've marked this task as undone: " + tasks.get(index));
            } else {
                switch (command) {
                    case "bye":
                        exit();
                        return;
                    case "list":
                        listTasks();
                        break;
                    default:
                        addTask(command);
                        break;
                }
            }
        }
    }


    public static void main(String[] args) {
        greet();
        processCommands();
    }
}
