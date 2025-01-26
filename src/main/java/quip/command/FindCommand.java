package quip.command;

import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.Task;
import quip.task.TaskList;
import quip.ui.Ui;

import java.util.List;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) throws QuipException {
        if (keyword.isBlank()) {
            throw new QuipException("The search keyword cannot be empty.");
        }
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matchingTasks = tasks.getTasks().stream()
                .filter(task -> task.getTask().toLowerCase().contains(keyword))
                .toList();
        ui.showMatchingTasks(matchingTasks);
    }
}