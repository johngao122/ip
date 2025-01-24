class Task {
    private final String task;
    private final TaskType type;
    private boolean isDone;

    Task(String task, TaskType type) {
        this.task = task;
        this.type = type;
        this.isDone = false;
    }


    void markAsDone() {
        this.isDone = true;
    }

    void markAsUndone() {
        this.isDone = false;
    }

    boolean isDone() {
        return this.isDone;
    }


    @Override
    public String toString() {
        return type.getMarker() + (this.isDone ? "[X] " : "[ ] ") + this.task;
    }
}


