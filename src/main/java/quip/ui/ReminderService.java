package quip.ui;

import javafx.application.Platform;
import quip.task.Reminder;
import quip.task.TaskList;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ReminderService {
    private static final int INITIAL_DELAY = 0;
    private static final int CHECK_INTERVAL = 15;
    private final ScheduledExecutorService scheduler;
    private final TaskList taskList;
    private final JavaFxUi ui;

    public ReminderService(TaskList taskList, JavaFxUi ui) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.taskList = taskList;
        this.ui = ui;
    }


    public void start() {
        scheduler.scheduleAtFixedRate(this::checkReminders,
                INITIAL_DELAY,
                CHECK_INTERVAL,
                TimeUnit.MINUTES);
    }


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

    private void checkReminders() {
        Reminder reminder = new Reminder(taskList);
        List<String> reminders = reminder.getUpcomingTaskReminders();

        if (!reminders.isEmpty()) {
            Platform.runLater(() -> ui.showReminders(reminders));
        }
    }
}