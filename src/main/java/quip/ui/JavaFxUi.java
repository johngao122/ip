package quip.ui;

import javafx.scene.layout.VBox;
import quip.task.Task;
import java.util.List;

/**
 * JavaFX's implementation of the UI component.
 * Handles displaying chat messages in the GUI.
 */
public class JavaFxUi extends Ui {
    private final VBox dialogContainer;

    public JavaFxUi(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    @Override
    public void showWelcome() {
        addQuipMessage("Hi there mortal! I'm Quip!\n" +
                "What shenanigans can I help you with today?");
    }

    @Override
    public void showLoadingError() {
        addErrorMessage("Oops! couldn't load your tasks.\n" +
                "Starting with an empty list.");
    }

    @Override
    public void showError(String message) {
        addErrorMessage(message);
    }

    @Override
    public void showMatchingTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            addQuipMessage("No matching tasks found.");
        } else {
            StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                message.append(String.format("%d. %s\n", i + 1, tasks.get(i)));
            }
            addQuipMessage(message.toString().trim());
        }
    }

    @Override
    public void showReminders(List<String> reminders) {
        if (reminders.isEmpty()) {
            addQuipMessage("Got nothing to do in the next 24 hours");
            return;
        }

        StringBuilder message = new StringBuilder("Here are your upcoming tasks:\n");
        for (String reminder : reminders) {
            message.append("  • ").append(reminder).append("\n");
        }
        addQuipMessage(message.toString().trim());
    }

    @Override
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            addQuipMessage("Wow there is nothing in your list! Time to add some tasks.");
            return;
        }
        StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            message.append(String.format("%d. %s\n", i + 1, tasks.get(i)));
        }
        addQuipMessage(message.toString().trim());
    }

    @Override
    public void showTaskAdded(Task task, int totalTasks) {
        addQuipMessage(String.format("""
                I've added this little nugget to your to-do list:
                %s
                Now you have %d tasks in the list.""", task, totalTasks));
    }

    @Override
    public void showTaskMarked(Task task) {
        addQuipMessage("Another one bites the dust: " + task);
    }

    @Override
    public void showTaskUnmarked(Task task) {
        addQuipMessage("Let's pretend that task wasn't done yet: " + task);
    }

    @Override
    public void showTasksOnDate(List<Task> tasks) {
        if (tasks.isEmpty()) {
            addQuipMessage("No tasks found for this date");
        } else {
            StringBuilder message = new StringBuilder("Here are the tasks for this date:\n");
            for (int i = 0; i < tasks.size(); i++) {
                message.append(String.format("%d. %s\n", i + 1, tasks.get(i)));
            }
            addQuipMessage(message.toString().trim());
        }
    }

    @Override
    public void showTaskDeleted(Task task, int totalTasks) {
        addQuipMessage(String.format("""
                That task has vanished faster than your weekend plans. It's gone, mortal!
                %s
                Now you have %d tasks in the list.""", task, totalTasks));
    }

    @Override
    public void showExit() {
        addQuipMessage("Aww, you're leaving already? 😢 Bye for now!");
    }

    private void addQuipMessage(String message) {
        dialogContainer.getChildren().add(new QuipDialogBox(message));
    }

    private void addErrorMessage(String errorMessage){
        dialogContainer.getChildren().add(new ErrorDialogBox(errorMessage));
    }
}