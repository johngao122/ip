class Event extends Task {
    private final String from;
    private final String to;

    Event(String task, String from, String to) {
        super(task, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
