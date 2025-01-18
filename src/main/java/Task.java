public class Task {
    private final String task;
    private boolean done;

    Task(String task) {
        this.task = task;
        this.done = false;
    }


    void markAsDone(){
        this.done = true;
    }

    void markAsUndone(){
        this.done = false;
    }

    boolean isDone(){
        return this.done;
    }


    @Override
    public String toString(){
        return this.done ? "[X] " + this.task : "[ ] " + this.task;
    }
}
