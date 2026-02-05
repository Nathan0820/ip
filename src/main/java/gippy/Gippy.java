package gippy;

import gippy.exception.GippyException;
import gippy.parser.Parser;
import gippy.storage.Storage;
import gippy.task.Deadline;
import gippy.task.Task;
import gippy.task.Event;
import gippy.task.TaskList;
import gippy.task.Todo;
import gippy.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Main Gippy class for the chatbot to operate
 */
public class Gippy {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;

    /**
     * Constructor for Gippy
     *
     * @param filePath Filepath
     */
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
     *
     * @param input  Input message to be processed
     * @param isDone Boolean value to show whether a task is done or not
     */
    public String handleMark(String input, boolean isDone) throws GippyException {
        //split string to extract task name and index
        String[] processedInput = input.split(" ");

        if (processedInput.length < 2) {
            throw new GippyException("Please give me a task number.");
        }

        try {
            int index = Integer.parseInt(processedInput[1]);
            if (index < 1 || index > tasks.size()) {
                throw new GippyException("Invalid task number!! Please try a different number");
            }
            Task task = tasks.getTask(index - 1);
            StringBuilder sb = new StringBuilder();

            if (isDone) {
                task.markDone();
                sb.append("Nice! I've marked this task as done: \n");
            } else {
                task.markUndone();
                sb.append("OK, I've marked this task as not done yet: \n");
            }

            sb.append(task);
            return sb.toString();
        } catch (NumberFormatException e) {
            throw new GippyException("Please use a number");
        }
    }

    /**
     * Adds tasks to the tasklist.
     *
     * @param input Input to be processed
     */
    public String handleAdd(String input) throws GippyException {
        Task task;

        if (input.startsWith("todo")) {
            String description = input.length() > 5 ? input.substring(5).trim() : "";
            if (description.isEmpty()) {
                throw new GippyException("Task description required! Please try again.");
            }
            task = new Todo(description);
            tasks.addTask(task);
        } else if (input.startsWith("deadline")) {
            if (!input.contains("/by")) {
                throw new GippyException("Deadline required! Please include /by.");
            }
            int separator = input.indexOf("/by");
            if (separator <= 9) {
                throw new GippyException("Task description required! Please try again.");
            }
            String description = input.substring(9, separator).trim();
            String deadline = input.substring(separator + 4).trim();

            task = new Deadline(description, deadline);
            tasks.addTask(task);
        } else if (input.startsWith("event")) {
            if (!input.contains("/from") || !input.contains("/to")) {
                throw new GippyException("Task from and to date required! Please include /from and /to.");
            }
            int from = input.indexOf("/from");
            int to = input.indexOf("/to");
            if (from <= 6) {
                throw new GippyException("Task description required! Please try again.");
            }
            String description = input.substring(6, from).trim();
            String fromDate = input.substring(from + 6, to).trim();
            String toDate = input.substring(to + 4).trim();
            task = new Event(description, fromDate, toDate);
            tasks.addTask(task);
        } else {
            throw new GippyException("I'm sorry, I don't know what this means. Please try again.");
        }
        return "Got it. I've added this task:" + "\n" +
                task + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Deletes tasks that the user wish to delete
     *
     * @param input Input to be processed
     */
    public String handleDelete(String input) throws GippyException {
        String[] processedInput = input.split(" "); //split string to extract task name and index
        if (processedInput.length < 2) {
            throw new GippyException("Task description required! Please try again.");
        }
        int index = Integer.parseInt(processedInput[1]);
        if (index < 1 || index > tasks.size()) {
            throw new GippyException("Invalid task number!! Please try a different number");
        }
        Task task = tasks.getTask(index - 1);
        StringBuilder sb = new StringBuilder();
        tasks.deleteTask(task);
        sb.append("Noted. I've removed this task: \n");
        sb.append(task).append("\n");
        sb.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
        return sb.toString();
    }

    /**
     * Finds tasks that match the keyword given by the user.
     *
     * @param input User input to be processed
     * @throws GippyException Exception thrown
     */
    public String handleFind(String input) throws GippyException {
        String[] processedInput = input.split(" ");
        if (processedInput.length < 2) {
            throw new GippyException("Please provide a keyword to search for.");
        }
        String keyword = processedInput[1].trim();
        TaskList foundTasks = tasks.findTasks(keyword);
        StringBuilder sb = new StringBuilder();
        if (foundTasks.isEmpty()) {
            sb.append("No tasks found with the keyword: \n").append(keyword);
        } else {
            sb.append("Here are the matching tasks in your list: \n");
            for (int i = 0; i < foundTasks.size(); i++) {
                sb.append(i + 1).append(".").append(foundTasks.getTask(i)).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Runs the Gippy chatbot. For CLI only
     */
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
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
                    case "find":
                        handleFind(input);
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

    /**
     * Same logic as run method. For GUI only. Handles input and provide bot output
     * @param input Input from user
     * @return Responses from bot
     */
    public String handleResponse(String input) {
        String response;
        try {
            String command = Parser.getCommand(input);
            switch (command) {
            case "bye":
                return ui.printBye();
            case "list":
                return ui.handleList(tasks);
            case "mark":
                response = handleMark(input, true);
                storage.saveTasks(tasks.getAllTasks());
                return response;
            case "unmark":
                response = handleMark(input, false);
                storage.saveTasks(tasks.getAllTasks());
                return response;
            case "delete":
                response = handleDelete(input);
                storage.saveTasks(tasks.getAllTasks());
                return response;
            case "todo":
            case "deadline":
            case "event":
                response = handleAdd(input);
                storage.saveTasks(tasks.getAllTasks());
                return response;
            case "find":
                return handleFind(input);
            default:
                throw new GippyException("Sorry, I don't understand your input. Please try again.");
            }
        } catch (GippyException | IOException e) {
            return ui.printError(e.getMessage());
        }
    }

    //For CLI
    public static void main(String[] args) {
        String filePath = Paths.get("data", "gippy.txt").toString();
        new Gippy(filePath).run();
    }

    /**
     * Print hello message in GUI only.
     * @return Hello message
     */
    public String printHello() {
        return "Hello, I'm Gippy! How can I help you?";
    }
}
