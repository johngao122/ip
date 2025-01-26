class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        tasks.unmarkTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskUnmarked(tasks.getTask(taskIndex));
    }
}