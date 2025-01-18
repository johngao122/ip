public class Task {
    private final String task;
    private final TaskType type;
    private boolean done;

    Task(String task, TaskType type) {
        this.task = task;
        this.type = type;
        this.done = false;
    }


    void markAsDone() {
        this.done = true;
    }

    void markAsUndone() {
        this.done = false;
    }

    boolean isDone() {
        return this.done;
    }


    @Override
    public String toString() {
        return type.getMarker() + (this.done ? "[X] " : "[ ] ") + this.task;
    }
}


