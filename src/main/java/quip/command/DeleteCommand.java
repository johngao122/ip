package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.Task;
import quip.task.TaskList;
import quip.ui.Ui;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        Task deletedTask = tasks.deleteTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(deletedTask, tasks.size());
    }
}