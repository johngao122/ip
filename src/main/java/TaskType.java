public enum TaskType {
    TODO("[T]"),
    DEADLINE("[D]"),
    EVENT("[E]");

    private final String marker;

    TaskType(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
}