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
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    public void addTask(Task task) throws QuipException {
        assert task != null : "Task cannot be null";
        int sizeBefore = tasks.size();
        if (hasDuplicate(task)) {
            throw new QuipException("Task already exists");
        }
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
            LocalDate targetDate = LocalDate.parse(dateStr);
            return tasks.stream()
                    .filter(task -> isTaskOnDate(task, targetDate))
                    .toList();
        } catch (DateTimeException e) {
            throw new QuipException("Please use this format: yyyy-MM-dd");
        }
    }

    /**
     * Checks if a task is scheduled for a specific date.
     *
     * @param task     The task to be checked
     * @param targetDate The target date
     * @return true if the task is scheduled for the target date, false otherwise
     */
    private boolean isTaskOnDate(Task task, LocalDate targetDate) {
        if (task instanceof Deadline deadline) {
            return getTaskDate(deadline.getDeadline()).equals(targetDate);
        } else if (task instanceof Event event) {
            return getTaskDate(event.getFrom()).equals(targetDate);
        }
        return false;
    }

    /**
     * Parses a date-time string and returns the local date component.
     *
     * @param dateTimeStr The date-time string to be parsed
     * @return The local date component extracted from the date-time string
     * @throws DateTimeException if the date-time string cannot be parsed
     */
    private LocalDate getTaskDate(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER).toLocalDate();
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
     * Checks if a task is a duplicate of any existing task.
     * Two tasks are considered duplicates if they have:
     * 1. The same task type
     * 2. The same description
     * 3. The same deadline/event times (if applicable)
     *
     * @param newTask The task to check for duplicates
     * @return true if a duplicate exists, false otherwise
     */
    public boolean hasDuplicate(Task newTask) {
        return tasks.stream().anyMatch(existingTask -> isDuplicate(existingTask, newTask));
    }

    /**
     * Compares two tasks to determine if they are duplicates.
     *
     * @param task1 First task to compare
     * @param task2 Second task to compare
     * @return true if the tasks are duplicates, false otherwise
     */
    private boolean isDuplicate(Task task1, Task task2) {

        if (!task1.getType().equals(task2.getType()) ||
                !task1.getTask().equals(task2.getTask())) {
            return false;
        }

        if (task1 instanceof Deadline && task2 instanceof Deadline) {
            return ((Deadline) task1).getDeadline()
                    .equals(((Deadline) task2).getDeadline());
        }

        if (task1 instanceof Event && task2 instanceof Event) {
            Event event1 = (Event) task1;
            Event event2 = (Event) task2;
            return event1.getFrom().equals(event2.getFrom()) &&
                    event1.getTo().equals(event2.getTo());
        }

        return true;
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