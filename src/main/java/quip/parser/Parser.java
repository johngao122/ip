package quip.parser;

import quip.command.AddDeadlineCommand;
import quip.command.AddEventCommand;
import quip.command.AddTodoCommand;
import quip.command.Command;
import quip.command.DeleteCommand;
import quip.command.ExitCommand;
import quip.command.FindCommand;
import quip.command.ListCommand;
import quip.command.ListDateCommand;
import quip.command.MarkCommand;
import quip.command.UnmarkCommand;
import quip.exception.QuipException;

/**
 * Parser for command-line input in the Quip application.
 * Converts user input strings into executable Command objects.
 */
public final class Parser {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private Parser() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Parses a user input string into a Command object.
     * @param input The raw input string from the user
     * @return A Command object representing the parsed input
     * @throws QuipException if the input format is invalid or unrecognized
     */
    public static Command parse(String input) throws QuipException {
        assert input != null : "Input should not be null";
        String[] parts = input.trim().split(" ", 2);
        assert parts.length >= 1 : "Input should have at least one part";
        String commandType = parts[0];
        String args = parts.length > 1 ? parts[1].trim() : "";

        return switch (commandType.toLowerCase()) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "delete" -> new DeleteCommand(parseIndex(args));
            case "mark" -> new MarkCommand(parseIndex(args));
            case "unmark" -> new UnmarkCommand(parseIndex(args));
            case "todo" -> new AddTodoCommand(args);
            case "deadline" -> {
                if (!args.contains("/by")) {
                    throw new QuipException("Invalid deadline format. Use: <description> /by <time>");
                }
                yield new AddDeadlineCommand(args);
            }
            case "event" -> {
                if (!args.contains("/from") || !args.contains("/to")) {
                    throw new QuipException("Invalid event format. Use: <description> /from <start> /to <end>");
                }
                yield new AddEventCommand(args);
            }
            case "on" -> new ListDateCommand(args);
            case "find" -> new FindCommand(args);
            default -> throw new QuipException("I'm sorry, I don't understand that command.");
        };
    }

    private static int parseIndex(String args) throws QuipException {
        assert args != null : "Args should not be null";
        try {
            return Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new QuipException("Please provide a valid task number.");
        }
    }
}