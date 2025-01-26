package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.TaskList;
import quip.ui.Ui;

public class MarkCommand extends Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        tasks.markTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskMarked(tasks.getTask(taskIndex));
    }
}