public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete");

    private final String commandText;

    Command(String commandText) {
        this.commandText = commandText;
    }

    public static Command fromString(String text) throws QuipException {
        for (Command command : Command.values()) {
            if (command.getCommandText().equalsIgnoreCase(text)) {
                return command;
            }
        }
        throw new QuipException("I'm sorry, I don't understand that command.");
    }

    public String getCommandText() {
        return commandText;
    }
}