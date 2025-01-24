enum TaskType {
    TODO("[T]"),
    DEADLINE("[D]"),
    EVENT("[E]");

    private final String marker;

    TaskType(String marker) {
        this.marker = marker;
    }

    String getMarker() {
        return marker;
    }
}