package quip.parser;

import quip.command.*;
import quip.exception.QuipException;

/**
 * Parser for command-line input in the Quip application.
 * Converts user input strings into executable Command objects.
 */

public class Parser {
    /**
     * Parses a user input string into a Command object.
     * @param input The raw input string from the user
     * @return A Command object representing the parsed input
     * @throws QuipException if the input format is invalid or unrecognized
     */
    public static Command parse(String input) throws QuipException {
        String[] parts = input.trim().split(" ", 2);
        String commandType = parts[0];
        String args = parts.length > 1 ? parts[1].trim() : "";

        return switch (commandType.toLowerCase()) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "delete" -> new DeleteCommand(parseIndex(args));
            case "mark" -> new MarkCommand(parseIndex(args));
            case "unmark" -> new UnmarkCommand(parseIndex(args));
            case "todo" -> new AddTodoCommand(args);
            case "deadline" -> new AddDeadlineCommand(args);
            case "event" -> new AddEventCommand(args);
            case "on" -> new ListDateCommand(args);
            case "find" -> new FindCommand(args);
            default -> throw new QuipException("I'm sorry, I don't understand that command.");
        };
    }

    private static int parseIndex(String args) throws QuipException {
        try {
            return Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new QuipException("Please provide a valid task number.");
        }
    }
}