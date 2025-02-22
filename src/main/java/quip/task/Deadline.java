package quip.task;

import quip.exception.QuipException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Map;

public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd h:mm")
                    .appendPattern(" ")
                    .appendText(ChronoField.AMPM_OF_DAY,
                            Map.of(0L, "am", 1L, "pm"))
                    .toFormatter(Locale.US);
    private static final DateTimeFormatter FILE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime deadline;

    public Deadline(String task, String deadline) throws QuipException {
        super(task, TaskType.DEADLINE);
        try {
            this.deadline = LocalDateTime.parse(deadline, FILE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new QuipException("Please use this format: yyyy-MM-dd HH:mm");
        }
    }

    public String getDeadline() {
        return this.deadline.format(FILE_FORMATTER);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline.format(DISPLAY_FORMATTER) + ")";
    }
}
