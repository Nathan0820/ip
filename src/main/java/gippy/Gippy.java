package gippy;

import gippy.exception.GippyException;
import gippy.parser.Parser;
import gippy.storage.Storage;
import gippy.task.Task;
import gippy.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class Gippy {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;

    public Gippy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.getTasks());
        } catch (FileNotFoundException e) {
            ui.printError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Handles marking of tasks to be done or not done.
     * @param input Input message to be processed
     * @param isDone Boolean value to show whether a task is done or not
     */
    public void handleMark(String input, boolean isDone) throws GippyException {
        String[] processedInput = input.split(" "); //split string to extract task name and index

        if (processedInput.length < 2) {
            throw new GippyException("Please give me a task number.");
        }

        try {
            int index = Integer.parseInt(processedInput[1]);
            if (index < 1 || index > tasks.size()) {
                throw new GippyException("Invalid task number!! Please try a different number");
            }
            Task task = tasks.getTask(index - 1);
            ui.printLine();

            if (isDone) {
                task.markDone();
                System.out.println("    Nice! I've marked this task as done:");
            } else {
                task.markUndone();
                System.out.println("    OK, I've marked this task as not done yet:");
            }

            System.out.println("      " + task);
            ui.printLine();
        } catch (NumberFormatException e) {
            throw new GippyException("Please use a number");
        }
    }

    /**
     * Adds tasks to the tasklist.
     * @param input Input to be processed
     */
    public void handleAdd(String input) throws GippyException {
        Task task;

        if (input.startsWith("todo")) {
            String description = input.length() > 5 ? input.substring(5).trim() : "";
            if (description.isEmpty()) {
                throw new GippyException("gippy.task.Task description required! Please try again.");
            }
            task = new Todo(description);
            tasks.addTask(task);
        } else if (input.startsWith("deadline")) {
            if (!input.contains("/by")) {
                throw new GippyException("gippy.task.Deadline required! Please include /by.");
            }
            int separator = input.indexOf("/by");
            if (separator <= 9) {
                throw new GippyException("gippy.task.Task description required! Please try again.");
            }
            String description = input.substring(9, separator).trim();
            String deadline = input.substring(separator + 4).trim();

            task = new Deadline(description, deadline);
            tasks.addTask(task);
        } else if (input.startsWith("event")) {
            if (!input.contains("/from") || !input.contains("/to")) {
                throw new GippyException("gippy.task.Task from and to date required! Please include /from and /to.");
            }
            int from = input.indexOf("/from");
            int to = input.indexOf("/to");
            if (from <= 6) {
                throw new GippyException("gippy.task.Task description required! Please try again.");
            }
            String description = input.substring(6, from).trim();
            String fromDate = input.substring(from + 6, to).trim();
            String toDate = input.substring(to + 4).trim();
            task = new Event(description, fromDate, toDate);
            tasks.addTask(task);
        } else {
            throw new GippyException("I'm sorry, I don't know what this means. Please try again.");
        }
        
        ui.printLine();
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
        ui.printLine();
    }

    /**
     * Deletes tasks that the user wish to delete
     * @param input Input to be processed
     */
    public void handleDelete(String input) throws GippyException {
        String[] processedInput = input.split(" "); //split string to extract task name and index
        if (processedInput.length < 2) {
            throw new GippyException("gippy.task.Task description required! Please try again.");
        }
        int index = Integer.parseInt(processedInput[1]);
        if (index < 1 || index > tasks.size()) {
            throw new GippyException("Invalid task number!! Please try a different number");
        }
        Task task = tasks.getTask(index - 1);
        ui.printLine();
        tasks.deleteTask(task);
        System.out.println("      Noted. I've removed this task:");
        System.out.println("      " + task);
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        ui.printLine();
    }

    /**
     * Runs the whole Gippy program with handling exceptions
     */
    public void run() {
        ui.printHello();
        boolean isRunning = true;
        while (isRunning){
            try {
                String input = ui.processInput();
                String command = Parser.getCommand(input);
                switch (command) {
                case "bye":
                    ui.printBye();
                    isRunning = false;
                    break;
                case "list":
                    ui.handleList(tasks);
                    break;
                case "mark":
                    handleMark(input, true);
                    storage.saveTasks(tasks.getAllTasks());
                    break;
                case "unmark":
                    handleMark(input, false);
                    storage.saveTasks(tasks.getAllTasks());
                    break;
                case "delete":
                    handleDelete(input);
                    storage.saveTasks(tasks.getAllTasks());
                    break;
                case "todo":
                case "deadline":
                case "event":
                    handleAdd(input);
                    storage.saveTasks(tasks.getAllTasks());
                    break;
                default:
                    throw new GippyException("Sorry, I don't understand your input. Please try again.");
                }
            } catch (GippyException | IOException e) {
                ui.printLine();
                ui.printError(e.getMessage());
                ui.printLine();
            }
        }
    }

    public static void main(String[] args) {
        String filePath = Paths.get("data", "gippy.txt").toString();
        new Gippy(filePath).run();
    }
}
