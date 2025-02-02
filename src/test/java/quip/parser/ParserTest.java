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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    @Test
    void parse_todoCommand_returnsAddTodoCommand() throws QuipException {
        Command command = Parser.parse("todo read book");
        assertTrue(command instanceof AddTodoCommand);
    }

    @Test
    void parse_emptyTodoCommand_throwsException() {
        assertThrows(QuipException.class, () -> Parser.parse("todo"));
    }

    @Test
    void parse_deadlineCommand_returnsAddDeadlineCommand() throws QuipException {
        Command command = Parser.parse("deadline submit report /by 2024-01-28 14:00");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    @Test
    void parse_invalidDeadlineFormat_throwsException() {
        assertThrows(QuipException.class, () -> Parser.parse("deadline submit report"));
    }

    @Test
    void parse_eventCommand_returnsAddEventCommand() throws QuipException {
        Command command = Parser.parse("event meeting /from 2024-01-28 14:00 /to 2024-01-28 15:00");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    void parse_invalidEventFormat_throwsException() {
        assertThrows(QuipException.class, () -> Parser.parse("event meeting /from 2024-01-28 14:00"));
    }

    @Test
    void parse_listCommand_returnsListCommand() throws QuipException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    void parse_listDateCommand_returnsListDateCommand() throws QuipException {
        Command command = Parser.parse("on 2024-01-28");
        assertTrue(command instanceof ListDateCommand);
    }

    @Test
    void parse_deleteCommand_returnsDeleteCommand() throws QuipException {
        Command command = Parser.parse("delete 1");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    void parse_invalidDeleteIndex_throwsException() {
        assertThrows(QuipException.class, () -> Parser.parse("delete abc"));
    }

    @Test
    void parse_markCommand_returnsMarkCommand() throws QuipException {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    void parse_unmarkCommand_returnsUnmarkCommand() throws QuipException {
        Command command = Parser.parse("unmark 1");
        assertTrue(command instanceof UnmarkCommand);
    }

    @Test
    void parse_exitCommand_returnsExitCommand() throws QuipException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    void parse_invalidCommand_throwsException() {
        assertThrows(QuipException.class, () -> Parser.parse("invalid"));
    }
}