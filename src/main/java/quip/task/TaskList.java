package quip.task;

import quip.exception.QuipException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a collection of tasks and provides operations to manipulate them.
 * Supports adding, deleting, marking, and filtering tasks.
 */

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks.isEmpty() : "Task list should be empty";
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the list.
     * @param task The task to be added
     */

    public void addTask(Task task) {
        assert task != null : "Task cannot be null";
        int sizeBefore = tasks.size();
        tasks.add(task);
        assert tasks.size() == sizeBefore + 1 : "Task should be added to the list";
    }

    /**
     * Adds multiple tasks to the list at once.
     * @param tasksToAdd Variable number of tasks to be added
     */
    public void addTasks(Task... tasksToAdd) {
        Collections.addAll(tasks, tasksToAdd);
    }

    /**
     * Deletes a task at the specified index.
     * @param index Zero-based index of the task to delete
     * @return The deleted task
     * @throws QuipException if the index is invalid
     */

    public Task deleteTask(int index) throws QuipException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "Invalid index";
        int sizeBefore = tasks.size();
        Task deletedTask = tasks.remove(index);
        assert tasks.size() == sizeBefore - 1 : "Task should be deleted from the list";
        assert deletedTask != null : "Deleted task should not be null";
        return deletedTask;
    }

    /**
     * Marks a task at the specified index as done.
     * @param index Zero-based index of the task to mark
     * @throws QuipException if the index is invalid
     */
    public void markTask(int index) throws QuipException {
        validateIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task at the specified index.
     *
     * @param index Zero-based index of the task to unmark
     * @throws QuipException if the index is invalid
     */
    public void unmarkTask(int index) throws QuipException {
        validateIndex(index);
        tasks.get(index).markAsUndone();
    }

    /**
     * Retrieves a task at the specified index.
     * @param index Zero-based index of the task
     * @return The task at the specified index
     * @throws QuipException if the index is invalid
     */
    public Task getTask(int index) throws QuipException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Retrieves tasks scheduled for a specific date.
     * @param dateStr Date in yyyy-MM-dd format
     * @return List of tasks scheduled for the specified date
     * @throws QuipException if the date format is invalid
     */

    public List<Task> getTasksOnDate(String dateStr) throws QuipException {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return tasks.stream()
                    .filter(task -> {
                        LocalDate taskDate = null;
                        if (task instanceof Deadline) {
                            taskDate = LocalDateTime.parse(((Deadline) task).getDeadline(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toLocalDate();
                        } else if (task instanceof Event) {
                            taskDate = LocalDateTime.parse(((Event) task).getFrom(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toLocalDate();
                        }
                        return taskDate != null && taskDate.equals(date);
                    })
                    .toList();
        } catch (DateTimeException e) {
            throw new QuipException("Please use this format: yyyy-MM-dd");
        }
    }

    /**
     * Validates the index of a task.
     * @param index Zero-based index of the task
     * @throws QuipException if the index is invalid
     */
    private void validateIndex(int index) throws QuipException {
        if (index < 0 || index >= tasks.size()) {
            throw new QuipException("Invalid task number. Please try again.");
        }
        assert index >= 0 && index < tasks.size() : "Invalid index";
    }

    /**
     * Retrieves all tasks in the list.
     * @return List of tasks
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}