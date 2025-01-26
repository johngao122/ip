class Deadline extends Task {
    private final String deadline;

    public Deadline(String task, String deadline) {
        super(task, TaskType.DEADLINE);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline + ")";
    }
}
