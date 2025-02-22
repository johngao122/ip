package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.Deadline;
import quip.task.TaskList;
import quip.ui.Ui;

public class AddDeadlineCommand extends Command {
    private final String args;

    public AddDeadlineCommand(String args) {
        this.args = args;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        String[] parts = args.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new QuipException("Invalid deadline format. Use: <description> /by <time>");
        }
        Deadline deadline = new Deadline(parts[0], parts[1]);
        tasks.addTask(deadline);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(deadline, tasks.size());
    }
}