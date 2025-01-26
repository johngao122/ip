package quip.task;

public class Task {
    private final String task;
    private final TaskType type;
    private boolean isDone;

    public Task(String task, TaskType type) {
        this.task = task;
        this.type = type;
        this.isDone = false;
    }


    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }


    public boolean isDone() {
        return this.isDone;
    }

    public String getType() {
        return this.type.getMarker();
    }

    public String getTask() {
        return this.task;
    }


    @Override
    public String toString() {
        return type.getMarker() + (this.isDone ? "[X] " : "[ ] ") + this.task;
    }
}


