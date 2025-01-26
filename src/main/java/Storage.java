import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Storage {
    private static final String DELIMITER = ",";
    private final Path PATH;
    private final Path FILE_NAME;

    public Storage() {
        this.PATH = Path.of("tasks");
        this.FILE_NAME = PATH.resolve("tasks.csv");
    }

    public Storage(Path path) {
        this.PATH = path;
        this.FILE_NAME = PATH.resolve("tasks.csv");
    }

    public List<Task> load() throws QuipException {
        createDirectoryIfMissing();
        List<Task> tasks = new ArrayList<>();

        try {
            if (!Files.exists(FILE_NAME)) {
                Files.createFile(FILE_NAME);
                return tasks;
            }

            List<String> lines = Files.readAllLines(FILE_NAME);
            for (String line : lines) {
                tasks.add(createTaskFromLine(line));
            }
            return tasks;
        } catch (IOException e) {
            throw new QuipException("Unable to read file");
        }
    }

    public void save(List<Task> tasks) throws QuipException {
        try {
            createDirectoryIfMissing();
            List<String> lines = new ArrayList<>();

            for (Task task : tasks) {
                lines.add(createLineFromTask(task));
            }

            Files.write(FILE_NAME, lines);
        } catch (IOException e) {
            throw new QuipException("Unable to write file");
        }
    }

    private void createDirectoryIfMissing() throws QuipException {
        try {
            if (!Files.exists(PATH)) {
                Files.createDirectory(PATH);
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
