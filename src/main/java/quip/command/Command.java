package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.TaskList;
import quip.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException;

    public boolean isExit() {
        return false;
    }
}