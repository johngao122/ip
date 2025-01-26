package quip.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import quip.exception.QuipException;
import quip.task.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private static final Path TEST_PATH = Path.of("test-storage");
    private static final Path TEST_FILE = TEST_PATH.resolve("tasks.csv");
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(TEST_PATH);
        cleanTestFiles();
    }

    @AfterEach
    void tearDown() {
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
    void load_newStorage_returnsEmptyList() throws QuipException {
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void save_multipleTaskTypes_savesCorrectly() throws QuipException {
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
    void save_markedTasks_preservesStatus() throws QuipException {
        Todo todo = new Todo("Read book");
        todo.markAsDone();

        storage.save(List.of(todo));
        List<Task> loadedTasks = storage.load();

        assertTrue(loadedTasks.get(0).isDone());
    }

    @Test
    void load_invalidFormat_throwsException() throws IOException {
        Files.createDirectories(TEST_PATH);
        Files.write(TEST_FILE, List.of("Invalid,line"));

        assertThrows(QuipException.class, () -> storage.load());
    }

    @Test
    void load_invalidTaskType_throwsException() throws IOException {
        Files.createDirectories(TEST_PATH);
        Files.write(TEST_FILE, List.of("X,false,Invalid task"));

        assertThrows(QuipException.class, () -> storage.load());
    }
}