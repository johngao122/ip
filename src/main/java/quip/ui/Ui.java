package quip.ui;

import quip.task.Task;

import java.util.List;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "Quip";

    public void showWelcome() {
        String logo = "________        .__        \n" +
                "\\_____  \\  __ __|__|_____  \n" +
                " /  / \\  \\|  |  \\  \\____ \\ \n" +
                "/   \\_/.  \\  |  /  |  |_> >\n" +
                "\\_____\\ \\_/____/|__|   __/ \n" +
                "       \\__>        |__|    \n";
        System.out.println(logo);
        System.out.println("Hi there mortal! I'm " + NAME + "!");
        System.out.println("What shenanigans can I help you with today?");
        showLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showLoadingError() {
        showLine();
        System.out.println("Oops! couldn't load your tasks.");
        System.out.println("Starting with an empty list.");
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            showLine();
            System.out.println("Your task list is empty! Time to add some tasks.");
            showLine();
            return;
        }
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            String message = (i + 1) + ". " + tasks.get(i);
            System.out.println(message);
        }
        showLine();
    }

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("I've added this little nugget to your to-do list:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        showLine();
    }

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Another one bites the dust: " + task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("Let's pretend that task wasn't done yet: " + task);
        showLine();
    }

    public void showTasksOnDate(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for this date");
        } else {
            System.out.println("Here are the tasks for this date:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        showLine();
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        showLine();
        System.out.println("That task has vanished faster than your weekend plans. It's gone, mortal!");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        showLine();
    }

    public void showExit() {
        showLine();
        System.out.println("Aww, you're leaving already? \uD83D\uDE22 Bye for now!");
        showLine();
    }
}