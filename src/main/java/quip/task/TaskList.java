package quip.task;

import quip.exception.QuipException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws QuipException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public void markTask(int index) throws QuipException {
        validateIndex(index);
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) throws QuipException {
        validateIndex(index);
        tasks.get(index).markAsUndone();
    }

    public Task getTask(int index) throws QuipException {
        validateIndex(index);
        return tasks.get(index);
    }

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

    private void validateIndex(int index) throws QuipException {
        if (index < 0 || index >= tasks.size()) {
            throw new QuipException("Invalid task number. Please try again.");
        }
    }

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