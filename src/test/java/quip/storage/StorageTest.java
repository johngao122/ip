package quip.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quip.exception.QuipException;
import quip.task.Deadline;
import quip.task.Event;
import quip.task.Task;
import quip.task.Todo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageTest {
    private static final Path TEST_PATH = Path.of("test-storage");
    private static final Path TEST_FILE = TEST_PATH.resolve("tasks.csv");
    private Storage storage;

    @BeforeEach
    void setUpStorage() {
        storage = new Storage(TEST_PATH);
        cleanTestFiles();
    }

    @AfterEach
    void tearDownStorage() {
        cleanTestFiles();
    }

    private void cleanTestFiles() {
        try {
            Files.deleteIfExists(TEST_FILE);
            Files.deleteIfExists(TEST_PATH);
        } catch (IOException e) {
            fail("Test cleanup failed");
        }
    }

    @Test
    void loadNewStorageReturnsEmptyList() throws QuipException {
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void saveMultipleTaskTypesSavesCorrectly() throws QuipException {
        Todo todo = new Todo("Read book");
        Deadline deadline = new Deadline("Submit report", "2024-01-28 14:00");
        Event event = new Event("Meeting", "2024-01-28 14:00", "2024-01-28 15:00");
        List<Task> tasks = Arrays.asList(todo, deadline, event);

        storage.save(tasks);
        List<Task> loadedTasks = storage.load();

        assertEquals(3, loadedTasks.size());
        assertEquals(todo.toString(), loadedTasks.get(0).toString());
        assertEquals(deadline.toString(), loadedTasks.get(1).toString());
        assertEquals(event.toString(), loadedTasks.get(2).toString());
    }

    @Test
    void saveMarkedTasksPreservesStatus() throws QuipException {
        Todo todo = new Todo("Read book");
        todo.markAsDone();

        storage.save(List.of(todo));
        List<Task> loadedTasks = storage.load();

        assertTrue(loadedTasks.get(0).isDone());
    }

    @Test
    void loadInvalidFormatThrowsException() throws IOException {
        Files.createDirectories(TEST_PATH);
        Files.write(TEST_FILE, List.of("Invalid,line"));

        assertThrows(QuipException.class, () -> storage.load());
    }

    @Test
    void loadInvalidTaskTypeThrowsException() throws IOException {
        Files.createDirectories(TEST_PATH);
        Files.write(TEST_FILE, List.of("X,false,Invalid task"));

        assertThrows(QuipException.class, () -> storage.load());
    }
}