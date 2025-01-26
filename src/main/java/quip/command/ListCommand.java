package quip.command;


import quip.storage.Storage;
import quip.task.TaskList;
import quip.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}