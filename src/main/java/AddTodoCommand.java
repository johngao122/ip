class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) throws QuipException {
        if (description.isBlank()) {
            throw new QuipException("The description of a todo cannot be empty.");
        }
        this.description = description;
    }

    @Override
    void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        Todo todo = new Todo(description);
        tasks.addTask(todo);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(todo, tasks.size());
    }
}