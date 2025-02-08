package quip.command;

import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.Reminder;
import quip.task.TaskList;
import quip.ui.Ui;

import java.util.List;

public class RemindCommand extends Command{
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        Reminder reminder = new Reminder(tasks);
        List<String> reminders = reminder.getUpcomingTaskReminders();
        ui.showReminders(reminders);
    }
}
