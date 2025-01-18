public class Task {
    private final String task;
    private boolean done;

    public Task(String task){
        this.task = task;
        this.done = false;
    }

    private void markAsDone(){
        this.done = true;
    }

    @Override
    public String toString(){
        return this.task;
    }
}
