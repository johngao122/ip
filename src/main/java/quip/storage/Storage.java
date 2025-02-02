package quip.storage;

import quip.exception.QuipException;
import quip.task.Deadline;
import quip.task.Event;
import quip.task.Task;
import quip.task.Todo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the moving of tasks to and from storage.
 */
public class Storage {
    private static final String DELIMITER = ",";
    private final Path filePath;
    private final Path fileName;

    /**
     * Creates a Storage instance with default file location.
     */
    public Storage() {
        this.filePath = Path.of("tasks");
        this.fileName = filePath.resolve("tasks.csv");
    }

    /**
     * Creates a Storage instance with specified file location.
     *
     * @param path The directory path for storing tasks
     */
    public Storage(Path path) {
        this.filePath = path;
        this.fileName = filePath.resolve("tasks.csv");
    }

    /**
     * Loads tasks from storage.
     *
     * @return List of tasks loaded from storage
     * @throws QuipException if there's an error reading from storage
     */
    public List<Task> load() throws QuipException {
        createDirectoryIfMissing();
        List<Task> tasks = new ArrayList<>();

        try {
            if (!Files.exists(fileName)) {
                Files.createFile(fileName);
                return tasks;
            }

            List<String> lines = Files.readAllLines(fileName);
            for (String line : lines) {
                tasks.add(createTaskFromLine(line));
            }
            return tasks;
        } catch (IOException e) {
            throw new QuipException("Unable to read file");
        }
    }

    /**
     * Saves tasks to storage.
     *
     * @param tasks List of tasks to save
     * @throws QuipException if there's an error writing to storage
     */
    public void save(List<Task> tasks) throws QuipException {
        try {
            createDirectoryIfMissing();
            List<String> lines = new ArrayList<>();

            for (Task task : tasks) {
                lines.add(createLineFromTask(task));
            }

            Files.write(fileName, lines);
        } catch (IOException e) {
            throw new QuipException("Unable to write file");
        }
    }

    private void createDirectoryIfMissing() throws QuipException {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectory(filePath);
            }
        } catch (Exception e) {
            throw new QuipException("Unable to create directory");
        }
    }

    private Task createTaskFromLine(String line) throws QuipException {
        String[] parts = line.split(DELIMITER);
        if (parts.length < 3) {
            throw new QuipException("Invalid task format");
        }

        String type = parts[0];
        boolean status = Boolean.parseBoolean(parts[1]);
        String description = parts[2];

        Task task = switch (type) {
            case "T" -> new Todo(description);
            case "D" -> {
                if (parts.length < 4) {
                    throw new QuipException("Invalid task format");
                }
                yield new Deadline(description, parts[3]);
            }
            case "E" -> {
                if (parts.length < 5) {
                    throw new QuipException("Invalid task format");
                }
                yield new Event(description, parts[3], parts[4]);
            }
            default -> throw new QuipException("Invalid task format");
        };

        if (status) {
            task.markAsDone();
        }
        return task;
    }

    private String createLineFromTask(Task task) {
        StringBuilder line = new StringBuilder();

        if (task instanceof Todo) {
            line.append("T");
        } else if (task instanceof Deadline) {
            line.append("D");
        } else if (task instanceof Event) {
            line.append("E");
        }

        line.append(DELIMITER)
                .append(task.isDone())
                .append(DELIMITER)
                .append(task.getTask());

        if (task instanceof Deadline) {
            line.append(DELIMITER)
                    .append(((Deadline) task).getDeadline());
        } else if (task instanceof Event) {
            line.append(DELIMITER)
                    .append(((Event) task).getFrom())
                    .append(DELIMITER)
                    .append(((Event) task).getTo());
        }

        return line.toString();
    }
}