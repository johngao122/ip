abstract class Command {
    abstract void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException;

    boolean isExit() {
        return false;
    }
}