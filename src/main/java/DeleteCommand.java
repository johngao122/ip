class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        Task deletedTask = tasks.deleteTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(deletedTask, tasks.size());
    }
}