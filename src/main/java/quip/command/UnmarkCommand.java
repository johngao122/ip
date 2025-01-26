package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.TaskList;
import quip.ui.Ui;

public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        tasks.unmarkTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskUnmarked(tasks.getTask(taskIndex));
    }
}