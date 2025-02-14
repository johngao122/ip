package quip.ui;

import javafx.application.Platform;
import quip.task.Reminder;
import quip.task.TaskList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Service to periodically check and display reminders for upcoming tasks.
 */
public class ReminderService {
    private static final int INITIAL_DELAY = 0;
    private static final int CHECK_INTERVAL = 15;
    private final ScheduledExecutorService scheduler;
    private final TaskList taskList;
    private final JavaFxUi ui;

    /**
     * Constructs a ReminderService with the specified task list and UI.
     *
     * @param taskList the list of tasks to check for reminders
     * @param ui the UI component to display reminders
     */
    public ReminderService(TaskList taskList, JavaFxUi ui) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Starts the reminder service to periodically check for upcoming tasks.
     */
    public void start() {
        scheduler.scheduleAtFixedRate(this::checkReminders,
                INITIAL_DELAY,
                CHECK_INTERVAL,
                TimeUnit.MINUTES);
    }

    /**
     * Stops the reminder service gracefully.
     */
    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    /**
     * Checks for upcoming task reminders and updates the UI if any are found.
     */
    private void checkReminders() {
        Reminder reminder = new Reminder(taskList);
        List<String> reminders = reminder.getUpcomingTaskReminders();

        if (!reminders.isEmpty()) {
            Platform.runLater(() -> ui.showReminders(reminders));
        }
    }
}