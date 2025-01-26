class MarkCommand extends Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        tasks.markTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskMarked(tasks.getTask(taskIndex));
    }
}