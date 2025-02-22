package quip.task;

import quip.exception.QuipException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Map;

public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd h:mm")
                    .appendPattern(" ")
                    .appendText(ChronoField.AMPM_OF_DAY,
                            Map.of(0L, "am", 1L, "pm"))
                    .toFormatter(Locale.US);
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime from;
    private final LocalDateTime to;


    public Event(String task, String from, String to) throws QuipException {
        super(task, TaskType.EVENT);
        try {
            this.from = LocalDateTime.parse(from, STORAGE_FORMATTER);
            this.to = LocalDateTime.parse(to, STORAGE_FORMATTER);
            if (this.from.isAfter(this.to)) {
                throw new QuipException("End time cannot be before start time");
            }
        } catch (DateTimeParseException e) {
            throw new QuipException("Please use this format: yyyy-MM-dd HH:mm");
        }
    }

    public String getFrom() {
        return this.from.format(STORAGE_FORMATTER);
    }

    public String getTo() {
        return this.to.format(STORAGE_FORMATTER);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(DISPLAY_FORMATTER)
                + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}
