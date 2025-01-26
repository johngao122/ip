import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    private static final Path TEST_PATH = Path.of("test");
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
    void loadEmptyFile_returnsEmptyList() throws QuipException {
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void saveAndLoadTasks_returnsSameTasks() throws QuipException {
        List<Task> tasks = Arrays.asList(
                new Todo("Read book"),
                new Deadline("Return book", "June 6th"),
                new Event("Meeting", "2023-11-20 00:00", "2023-11-20 12:00")
        );

        storage.save(tasks);
        List<Task> loadedTasks = storage.load();

        assertEquals(tasks.size(), loadedTasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).toString(), loadedTasks.get(i).toString());
        }
    }

    @Test
    void loadCorruptedFile_throwsException() throws IOException {
        Files.createDirectories(TEST_PATH);
        Files.write(TEST_FILE, List.of("Invalid,line"));

        assertThrows(QuipException.class, () -> storage.load());
    }
}
