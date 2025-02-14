package quip.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quip.exception.QuipException;
import quip.storage.Storage;
import quip.task.Deadline;
import quip.task.Event;
import quip.task.TaskList;
import quip.task.Todo;
import quip.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AddTodoCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
    }

    @Test
    void executeValidTodoAddsTodoTask() throws QuipException {
        AddTodoCommand command = new AddTodoCommand("read book");
        command.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertInstanceOf(Todo.class, tasks.getTask(0));
        assertEquals("[T][ ] read book", tasks.getTask(0).toString());
    }

    @Test
    void constructorEmptyDescriptionThrowsException() {
        assertThrows(QuipException.class, () -> new AddTodoCommand(""));
    }
}

class AddDeadlineCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
    }

    @Test
    void executeValidDeadlineAddsDeadlineTask() throws QuipException {
        AddDeadlineCommand command = new AddDeadlineCommand("submit report /by 2024-01-28 14:00");
        command.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertInstanceOf(Deadline.class, tasks.getTask(0));
        assertEquals("[D][ ] submit report (by: 2024-01-28 2:00 pm)", tasks.getTask(0).toString());
    }

    @Test
    void executeInvalidFormatThrowsException() {
        AddDeadlineCommand command = new AddDeadlineCommand("submit report without deadline");
        assertThrows(QuipException.class, () -> command.execute(tasks, ui, storage));
    }
}

class AddEventCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
    }

    @Test
    void executeValidEventAddsEventTask() throws QuipException {
        AddEventCommand command = new AddEventCommand("meeting /from 2024-01-28 14:00 /to 2024-01-28 15:00");
        command.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertInstanceOf(Event.class, tasks.getTask(0));
        assertEquals("[E][ ] meeting (from: 2024-01-28 2:00 pm to: 2024-01-28 3:00 pm)",
                tasks.getTask(0).toString());
    }

    @Test
    void executeInvalidFormatThrowsException() {
        AddEventCommand command = new AddEventCommand("meeting without proper format");
        assertThrows(QuipException.class, () -> command.execute(tasks, ui, storage));
    }
}

class DeleteCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() throws QuipException {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
        Todo todo = new Todo("test task");
        tasks.addTask(todo);
    }

    @Test
    void executeValidIndexDeletesTask() throws QuipException {
        DeleteCommand command = new DeleteCommand(0);
        command.execute(tasks, ui, storage);
        assertEquals(0, tasks.size());
    }

    @Test
    void executeInvalidIndexThrowsException() {
        DeleteCommand command = new DeleteCommand(1);
        assertThrows(QuipException.class, () -> command.execute(tasks, ui, storage));
    }
}

class ExitCommandTest {
    @Test
    void isExitReturnsTrue() {
        ExitCommand command = new ExitCommand();
        assertTrue(command.isExit());
    }
}

class ListCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() throws QuipException {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
        tasks.addTask(new Todo("test task"));
    }

    @Test
    void executeWithTasksShowsList() throws QuipException {
        ListCommand command = new ListCommand();
        command.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
    }
}

class MarkCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() throws QuipException {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
        tasks.addTask(new Todo("test task"));
    }

    @Test
    void executeValidIndexMarksTask() throws QuipException {
        MarkCommand command = new MarkCommand(0);
        command.execute(tasks, ui, storage);
        assertTrue(tasks.getTask(0).isDone());
    }

    @Test
    void executeInvalidIndexThrowsException() {
        MarkCommand command = new MarkCommand(1);
        assertThrows(QuipException.class, () -> command.execute(tasks, ui, storage));
    }
}

class UnmarkCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() throws QuipException {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage();
        Todo todo = new Todo("test task");
        todo.markAsDone();
        tasks.addTask(todo);
    }

    @Test
    void executeValidIndexUnmarksTask() throws QuipException {
        UnmarkCommand command = new UnmarkCommand(0);
        command.execute(tasks, ui, storage);
        assertFalse(tasks.getTask(0).isDone());
    }

    @Test
    void executeInvalidIndexThrowsException() {
        UnmarkCommand command = new UnmarkCommand(1);
        assertThrows(QuipException.class, () -> command.execute(tasks, ui, storage));
    }
}