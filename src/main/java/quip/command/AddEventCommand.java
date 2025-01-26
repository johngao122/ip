package quip.command;


import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.Event;
import quip.task.TaskList;
import quip.ui.Ui;

public class AddEventCommand extends Command {
    private final String args;

    public AddEventCommand(String args) {
        this.args = args;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws QuipException {
        String[] eventParts = args.split(" /from ", 2);
        if (eventParts.length < 2 || eventParts[0].isBlank() || !eventParts[1].contains(" /to ")) {
            throw new QuipException("Invalid event format. Use: <description> /from <start> /to <end>");
        }
        String[] eventTime = eventParts[1].split(" /to ", 2);
        Event event = new Event(eventParts[0], eventTime[0], eventTime[1]);
        tasks.addTask(event);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(event, tasks.size());
    }
}