import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("Hi there mortal! I'm " + NAME + "!");
        System.out.println("What shenanigans can I help you with today?");
        System.out.println(LINE);
    }

    private static void exit() {
        System.out.println(LINE);
        System.out.println("Aww, you’re leaving already? \uD83D\uDE22 Bye for now!");
        System.out.println(LINE);
    }

    private static void addTodoTask(String name) {
        Todo todo = new Todo(name);
        tasks.add(todo);
        System.out.println(LINE);
        System.out.println("I’ve added this little nugget to your to-do list:");
        System.out.println(todo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void addDeadlineTask(String command) {
        String[] deadlineParts = command.split(" /by ");
        Deadline deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
        tasks.add(deadline);
        System.out.println(LINE);
        System.out.println("Deadline? Challenge accepted! I’ve added this:");
        System.out.println(deadline);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void addEventTask(String command) {
        String[] eventParts = command.split(" /from ");
        String eventName = eventParts[0];
        String[] eventTime = eventParts[1].split(" /to ");
        Event event = new Event(eventName, eventTime[0], eventTime[1]);
        tasks.add(event);
        System.out.println(LINE);
        System.out.println("Event scheduled! Here's the scoop:");
        System.out.println(event);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void listTasks() {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            String message = (i + 1) + ". ";
            message += tasks.get(i);
            System.out.println(message);
        }
        System.out.println(LINE);
    }

    private static void markTask(int taskToMark) {
        tasks.get(taskToMark - 1).markAsDone();
        System.out.println(LINE);
        System.out.println("Another one bites the dust: " + tasks.get(taskToMark - 1));
        System.out.println(tasks.get(taskToMark - 1));
        System.out.println(LINE);
    }

    private static void unmarkTask(int taskToUnmark) {
        tasks.get(taskToUnmark - 1).markAsUndone();
        System.out.println(LINE);
        System.out.println("Let’s pretend that task wasn’t done yet:");
        System.out.println(tasks.get(taskToUnmark - 1));
        System.out.println(LINE);
    }

    private static void processCommands() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            String[] commandParts = command.split(" ");
            switch (commandParts[0]) {
                case "bye":
                    exit();
                    return;
                case "list":
                    listTasks();
                    break;
                case "mark":
                    markTask(Integer.parseInt(commandParts[1]));
                    break;
                case "unmark":
                    unmarkTask(Integer.parseInt(commandParts[1]));
                    break;
                case "todo":
                    addTodoTask(String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)));
                    break;
                case "deadline":
                    addDeadlineTask(String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)));
                    break;
                case "event":
                    addEventTask(String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)));
                    break;
                default:
                    System.out.println("I'm sorry, I don't understand that command.");
                    break;
            }
        }
    }


    public static void main(String[] args) {
        greet();
        processCommands();
    }
}
