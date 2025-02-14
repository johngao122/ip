package quip.task;

import org.junit.jupiter.api.Test;
import quip.exception.QuipException;
import org.junit.jupiter.api.BeforeEach;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Todo("Test task");
    }

    @Test
    void markAsDoneUnmarkedTaskMarksCorrectly() {
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    void markAsUndoneMarkedTaskUnmarksCorrectly() {
        task.markAsDone();
        task.markAsUndone();
        assertFalse(task.isDone());
    }

    @Test
    void toStringFormatsCorrectly() {
        assertEquals("[T][ ] Test task", task.toString());
        task.markAsDone();
        assertEquals("[T][X] Test task", task.toString());
    }
}

class DeadlineTest {
    @Test
    void constructorValidDateTimeCreatesDeadline() throws QuipException {
        Deadline deadline = new Deadline("Test", "2024-01-28 14:00");
        String expected = "[D][ ] Test (by: 2024-01-28 2:00 pm)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    void constructorInvalidDateTimeThrowsException() {
        assertThrows(QuipException.class, () -> new Deadline("Test", "invalid"));
    }

    @Test
    void getDeadlineReturnsCorrectFormat() throws QuipException {
        Deadline deadline = new Deadline("Test", "2024-01-28 14:00");
        assertEquals("2024-01-28 14:00", deadline.getDeadline());
    }

}

class EventTest {
    @Test
    void constructorValidDateTimeCreatesEvent() throws QuipException {
        Event event = new Event("Test", "2024-01-28 14:00", "2024-01-28 15:00");
        String expected = "[E][ ] Test (from: 2024-01-28 2:00 pm to: 2024-01-28 3:00 pm)";
        assertEquals(expected, event.toString());
    }

    @Test
    void constructorEndBeforeStartThrowsException() {
        assertThrows(QuipException.class, () ->
                new Event("Test", "2024-01-28 15:00", "2024-01-28 14:00"));
    }

    @Test
    void getFromAndToReturnsCorrectFormat() throws QuipException {
        Event event = new Event("Test", "2024-01-28 14:00", "2024-01-28 15:00");
        assertEquals("2024-01-28 14:00", event.getFrom());
        assertEquals("2024-01-28 15:00", event.getTo());
    }
}

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void addTaskValidTaskAddsSuccessfully() throws QuipException {
        Task task = new Todo("Test");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
    }

    @Test
    void deleteTaskValidIndexRemovesTask() throws QuipException {
        Task task = new Todo("Test");
        taskList.addTask(task);
        Task deleted = taskList.deleteTask(0);
        assertEquals(task, deleted);
        assertTrue(taskList.isEmpty());
    }

    @Test
    void markTaskValidIndexMarksTask() throws QuipException {
        taskList.addTask(new Todo("Test"));
        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());
    }

    @Test
    void getTasksOnDateMatchingDateReturnsCorrectTasks() throws QuipException {
        Deadline deadline = new Deadline("Test1", "2024-01-28 14:00");
        Event event = new Event("Test2", "2024-01-28 14:00", "2024-01-28 15:00");
        Todo todo = new Todo("Test3");

        taskList.addTask(deadline);
        taskList.addTask(event);
        taskList.addTask(todo);

        List<Task> tasksOnDate = taskList.getTasksOnDate("2024-01-28");
        assertEquals(2, tasksOnDate.size());
    }

    @Test
    void getTasksOnDateInvalidFormatThrowsException() {
        assertThrows(QuipException.class, () ->
                taskList.getTasksOnDate("invalid-date"));
    }
}