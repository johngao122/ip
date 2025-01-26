package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.TaskList;
import quip.ui.Ui;

public class ListDateCommand extends Command {
    private final String date;

    public ListDateCommand(String date) throws QuipException {
        if (date.isBlank()) {
            throw new QuipException("Please specify a date.");
        }
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        ui.showTasksOnDate(tasks.getTasksOnDate(date));
    }
}