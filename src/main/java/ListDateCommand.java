class ListDateCommand extends Command {
    private final String date;

    public ListDateCommand(String date) throws QuipException {
        if (date.isBlank()) {
            throw new QuipException("Please specify a date.");
        }
        this.date = date;
    }

    @Override
    void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        ui.showTasksOnDate(tasks.getTasksOnDate(date));
    }
}