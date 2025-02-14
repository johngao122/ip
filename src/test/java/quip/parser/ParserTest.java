package quip.parser;

import org.junit.jupiter.api.Test;
import quip.command.Command;
import quip.command.AddDeadlineCommand;
import quip.command.AddEventCommand;
import quip.command.AddTodoCommand;
import quip.command.DeleteCommand;
import quip.command.ExitCommand;
import quip.command.ListCommand;
import quip.command.ListDateCommand;
import quip.command.MarkCommand;
import quip.command.UnmarkCommand;
import quip.exception.QuipException;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void parseTodoCommandReturnsAddTodoCommand() throws QuipException {
        Command command = Parser.parse("todo read book");
        assertInstanceOf(AddTodoCommand.class, command);
    }

    @Test
    void parseEmptyTodoCommandThrowsException() {
        assertThrows(QuipException.class, () -> Parser.parse("todo"));
    }

    @Test
    void parseDeadlineCommandReturnsAddDeadlineCommand() throws QuipException {
        Command command = Parser.parse("deadline submit report /by 2024-01-28 14:00");
        assertInstanceOf(AddDeadlineCommand.class, command);
    }

    @Test
    void parseInvalidDeadlineFormatThrowsException() {
        assertThrows(QuipException.class, () -> Parser.parse("deadline submit report"));
    }

    @Test
    void parseEventCommandReturnsAddEventCommand() throws QuipException {
        Command command = Parser.parse("event meeting /from 2024-01-28 14:00 /to 2024-01-28 15:00");
        assertInstanceOf(AddEventCommand.class, command);
    }

    @Test
    void parseInvalidEventFormatThrowsException() {
        assertThrows(QuipException.class, () -> Parser.parse("event meeting /from 2024-01-28 14:00"));
    }

    @Test
    void parseListCommandReturnsListCommand() throws QuipException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parseListDateCommandReturnsListDateCommand() throws QuipException {
        Command command = Parser.parse("on 2024-01-28");
        assertInstanceOf(ListDateCommand.class, command);
    }

    @Test
    void parseDeleteCommandReturnsDeleteCommand() throws QuipException {
        Command command = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parseInvalidDeleteIndexThrowsException() {
        assertThrows(QuipException.class, () -> Parser.parse("delete abc"));
    }

    @Test
    void parseMarkCommandReturnsMarkCommand() throws QuipException {
        Command command = Parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void parseUnmarkCommandReturnsUnmarkCommand() throws QuipException {
        Command command = Parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    void parseExitCommandReturnsExitCommand() throws QuipException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void parseInvalidCommandThrowsException() {
        assertThrows(QuipException.class, () -> Parser.parse("invalid"));
    }
}