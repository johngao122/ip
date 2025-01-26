import java.util.Scanner;

public class Quip {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Quip() {
        TaskList tempTasks;
        ui = new Ui();
        storage = new Storage();
        try {
            tempTasks = new TaskList(storage.load());
        } catch (QuipException e) {
            ui.showLoadingError();
            tempTasks = new TaskList();
        }
        tasks = tempTasks;
    }

    public static void main(String[] args) {
        new Quip().run();
    }

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = scanner.nextLine();
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (QuipException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}