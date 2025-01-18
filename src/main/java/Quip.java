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

    private static void addTodoTask(String name) throws QuipException {
        if (name.isBlank()) {
            throw new QuipException("The description of a todo cannot be empty.");
        }
        Todo todo = new Todo(name);
        tasks.add(todo);
        System.out.println(LINE);
        System.out.println("I’ve added this little nugget to your to-do list:");
        System.out.println(todo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void addDeadlineTask(String command) throws QuipException {
        String[] deadlineParts = command.split(" /by ", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].isBlank() || deadlineParts[1].isBlank()) {
            throw new QuipException("Invalid deadline format. Use: <description> /by <time>");
        }
        Deadline deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
        tasks.add(deadline);
        System.out.println(LINE);
        System.out.println("Deadline? Challenge accepted! I’ve added this:");
        System.out.println(deadline);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void addEventTask(String command) throws QuipException {
        String[] eventParts = command.split(" /from ", 2);
        if (eventParts.length < 2 || eventParts[0].isBlank() || !eventParts[1].contains(" /to ")) {
            throw new QuipException("Invalid event format. Use: <description> /from <start> /to <end>");
        }
        String[] eventTime = eventParts[1].split(" /to ", 2);
        Event event = new Event(eventParts[0], eventTime[0], eventTime[1]);
        tasks.add(event);
        System.out.println(LINE);
        System.out.println("Event scheduled! Here's the scoop:");
        System.out.println(event);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println(LINE);
            System.out.println("Your task list is empty! Time to add some tasks.");
            System.out.println(LINE);
            return;
        }
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            String message = (i + 1) + ". " + tasks.get(i);
            System.out.println(message);
        }
        System.out.println(LINE);
    }

    private static void markTask(int taskToMark) throws QuipException {
        if (taskToMark < 1 || taskToMark > tasks.size()) {
            throw new QuipException("Invalid task number. Please try again.");
        }
        tasks.get(taskToMark - 1).markAsDone();
        System.out.println(LINE);
        System.out.println("Another one bites the dust: " + tasks.get(taskToMark - 1));
        System.out.println(LINE);
    }

    private static void unmarkTask(int taskToUnmark) throws QuipException {
        if (taskToUnmark < 1 || taskToUnmark > tasks.size()) {
            throw new QuipException("Invalid task number. Please try again.");
        }
        tasks.get(taskToUnmark - 1).markAsUndone();
        System.out.println(LINE);
        System.out.println("Let’s pretend that task wasn’t done yet:");
        System.out.println(tasks.get(taskToUnmark - 1));
        System.out.println(LINE);
    }

    private static void deleteTask(int taskToDelete) throws QuipException {
        if (taskToDelete < 1 || taskToDelete > tasks.size()) {
            throw new QuipException("Invalid task number. Please try again.");
        }
        Task task = tasks.remove(taskToDelete - 1);
        System.out.println(LINE);
        System.out.println("That task has vanished faster than your weekend plans. It’s gone, mortal!");
        System.out.println(task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void processCommands() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                String input = sc.nextLine();
                String[] commandParts = input.split(" ");
                Command command = Command.fromString(commandParts[0]);

                switch (command) {
                    case BYE:
                        exit();
                        return;
                    case LIST:
                        listTasks();
                        break;
                    case MARK:
                        markTask(Integer.parseInt(commandParts[1]));
                        break;
                    case UNMARK:
                        unmarkTask(Integer.parseInt(commandParts[1]));
                        break;
                    case TODO:
                        addTodoTask(String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)));
                        break;
                    case DEADLINE:
                        addDeadlineTask(String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)));
                        break;
                    case EVENT:
                        addEventTask(String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length)));
                        break;
                    case DELETE:
                        deleteTask(Integer.parseInt(commandParts[1]));
                        break;
                    default:
                        throw new QuipException("I’m sorry, I don’t know what that means.");
                }
            } catch (QuipException e) {
                System.out.println(LINE);
                System.out.println(e.getMessage());
                System.out.println(LINE);
            }
        }
    }

    public static void main(String[] args) {
        greet();
        processCommands();
    }
}