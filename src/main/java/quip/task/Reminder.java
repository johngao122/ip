package quip.task;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages reminders for tasks with deadlines or events.
 * Checks for upcoming tasks and generates reminder messages.
 */
public class Reminder {
    private static final int DEFAULT_REMINDER_HOURS = 24;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final TaskList taskList;

    public Reminder(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns a list of reminder messages for upcoming tasks.
     * Tasks are considered upcoming if they are within the next 24 hours.
     *
     * @return List of reminder messages for upcoming tasks
     */
    public List<String> getUpcomingTaskReminders() {
        List<String> reminders = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Task task : taskList.getTasks()) {
            if (task instanceof Deadline) {
                addDeadlineReminder((Deadline) task, now, reminders);
            } else if (task instanceof Event) {
                addEventReminder((Event) task, now, reminders);
            }
        }

        return reminders;
    }

    private void addDeadlineReminder(Deadline deadline, LocalDateTime now, List<String> reminders) {
        try {
            LocalDateTime deadlineTime = LocalDateTime.parse(deadline.getDeadline(),
                    DATE_FORMATTER);

            if (isWithinReminderWindow(now, deadlineTime)) {
                String timeLeft = formatDuration(Duration.between(now, deadlineTime));
                reminders.add(String.format("Deadline approaching: %s (in %s)",
                        deadline.getTask(), timeLeft));
            }
        } catch (Exception e) {
            // skip invalid date formats
        }
    }

    private void addEventReminder(Event event, LocalDateTime now, List<String> reminders) {
        try {
            LocalDateTime eventTime = LocalDateTime.parse(event.getFrom(),
                    DATE_FORMATTER);

            if (isWithinReminderWindow(now, eventTime)) {
                String timeLeft = formatDuration(Duration.between(now, eventTime));
                reminders.add(String.format("Upcoming event: %s (in %s)",
                        event.getTask(), timeLeft));
            }
        } catch (Exception e) {
            // skip invalid date formats
        }
    }

    private boolean isWithinReminderWindow(LocalDateTime now, LocalDateTime taskTime) {
        Duration timeUntilTask = Duration.between(now, taskTime);
        return !timeUntilTask.isNegative() &&
                timeUntilTask.toHours() <= DEFAULT_REMINDER_HOURS;
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        if (hours > 0) {
            return String.format("%d hour%s %d minute%s",
                    hours, hours != 1 ? "s" : "",
                    minutes, minutes != 1 ? "s" : "");
        } else {
            return String.format("%d minute%s",
                    minutes, minutes != 1 ? "s" : "");
        }
    }
}