import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Gippy {
    private static Storage storage;
    public static void main(String[] args) {
        //initialize variables
        String filePath = Paths.get("data", "gippy.txt").toString();
        storage = new Storage(filePath);
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks;
        String line = "    ____________________________________________________________";

        //load task from storage, initialize a new arraylist if not found
        try {
            tasks = storage.getTasks();
        } catch (FileNotFoundException e) {
            tasks = new ArrayList<>();
        }

        printHello(line);

        while (true){
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                handleList(tasks, line);
            } else if (input.startsWith("mark")) {
                handleMark(input, tasks, line, true);
            } else if (input.startsWith("unmark")) {
                handleMark(input, tasks, line, false);
            } else if (input.startsWith("delete")) {
                handleDelete(input, tasks, line);
            } else {
                handleAdd(input, tasks, line);
            }
        }
        printBye(line);
    }

    /**
     * Print greeting message when chatbot is first activated
     * @param line Line to separate messages
     */
    private static void printHello(String line) {
        String logo = """
                   ▄▄▄▄▄▄ ▄▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄ 
                  █      █   █       █       █  █ █  █
                  █  ▄▄▄▄█   █    ▄  █    ▄  █  █▄█  █
                  █ █  ▄▄█   █   █▄█ █   █▄█ █       █
                  █ █▄▄  █   █    ▄▄▄█    ▄▄▄█▄     ▄█
                  █      █   █   █   █   █     █   █  
                  █▄▄▄▄▄▄█▄▄▄█▄▄▄█   █▄▄▄█     █▄▄▄█  
            """;

        System.out.println(line);
        System.out.println("      Hello! I'm");
        System.out.println(logo);
        System.out.println("      How can I help you?");
        System.out.println(line);
    }

    /**
     * Prints closing message when user closes the program
     * @param line Line to separate messages
     */
    private static void printBye(String line) {
        System.out.println(line);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    /**
     * Saves all existing tasks to the storage file
     * @param tasks Arraylist that stores tasks
     */
    private static void saveToFile(ArrayList<Task> tasks) {
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            System.out.println("    Error saving tasks to file");
        }
    }

    /**
     * Lists out all tasks stored in the list.
     * @param tasks Arraylist to store tasks
     * @param line Line to separate messages
     */
    private static void handleList(ArrayList<Task> tasks, String line) {
        if (tasks.isEmpty()) {
            System.out.println(line);
            System.out.println("    No tasks found, add one to start!");
            System.out.println(line);
            return;
        }
        System.out.println(line);
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(line);
    }

    /**
     * Handles marking of tasks to be done or not done.
     * @param input Input message to be processed
     * @param tasks Arraylist to store tasks
     * @param line Line to separate messages
     * @param isDone Boolean value to show whether a task is done or not
     */
    public static void handleMark(String input, ArrayList<Task> tasks, String line, boolean isDone) {
        try {
            String[] processedInput = input.split(" "); //split string to extract task name and index
            int index = Integer.parseInt(processedInput[1]);
            if (index < 1 || index > tasks.size()) {
                throw new GippyException("      Invalid task number!! Please try a different number");
            }
            Task task = tasks.get(index - 1);
            System.out.println(line);
            if (isDone) {
                task.markDone();
                System.out.println("    Nice! I've marked this task as done:");
            } else {
                task.markUndone();
                System.out.println("    OK, I've marked this task as not done yet:");
            }
            saveToFile(tasks);
            System.out.println("      " + task);
            System.out.println(line);
        } catch (GippyException e) {
            System.out.println(line);
            System.out.println(e.getMessage());
            System.out.println(line);
        }
    }

    /**
     * Adds tasks to the arraylist.
     * @param input Input to be processed
     * @param tasks Arraylist to store tasks
     * @param line Line to separate messages
     */
    public static void handleAdd(String input, ArrayList<Task> tasks, String line) {
        try {
            Task task;

            if (input.startsWith("todo")) {
                String description = input.length() > 5 ? input.substring(5).trim() : "";
                if (description.isEmpty()) {
                    throw new GippyException("      Task description required! Please try again.");
                }
                task = new Todo(description);
                tasks.add(task);
            } else if (input.startsWith("deadline")) {
                if (!input.contains("/by")) {
                    throw new GippyException("      Deadline required! Please include /by.");
                }
                int separator = input.indexOf("/by");
                if (separator <= 9) {
                    throw new GippyException("      Task description required! Please try again.");
                }
                String description = input.substring(9, separator).trim();
                String deadline = input.substring(separator + 4).trim();

                task = new Deadline(description, deadline);
                tasks.add(task);
            } else if (input.startsWith("event")) {
                if (!input.contains("/from") || !input.contains("/to")) {
                    throw new GippyException("      Task from and to date required! Please include /from and /to.");
                }
                int from = input.indexOf("/from");
                int to = input.indexOf("/to");
                if (from <= 6) {
                    throw new GippyException("      Task description required! Please try again.");
                }
                String description = input.substring(6, from).trim();
                String fromDate = input.substring(from + 6, to).trim();
                String toDate = input.substring(to + 4).trim();
                task = new Event(description, fromDate, toDate);
                tasks.add(task);
            } else {
                throw new GippyException("      I'm sorry, I don't know what this means. Please try again.");
            }
            saveToFile(tasks);
            System.out.println(line);
            System.out.println("     Got it. I've added this task:");
            System.out.println("       " + task);
            System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(line);
        } catch (GippyException e) {
            System.out.println(line);
            System.out.println(e.getMessage());
            System.out.println(line);
        }
    }

    /**
     * Deletes tasks that the user wish to delete
     * @param input Input to be processed
     * @param tasks Arraylist to store tasks
     * @param line Line to separate messages
     */
    public static void handleDelete(String input, ArrayList<Task> tasks, String line) {
        try {
            String[] processedInput = input.split(" "); //split string to extract task name and index
            if (processedInput.length < 2) {
                throw new GippyException("      Task description required! Please try again.");
            }
            int index = Integer.parseInt(processedInput[1]);
            if (index < 1 || index > tasks.size()) {
                throw new GippyException("      Invalid task number!! Please try a different number");
            }
            Task task = tasks.get(index - 1);
            System.out.println(line);
            tasks.remove(task);
            saveToFile(tasks);
            System.out.println("      Noted. I've removed this task:");
            System.out.println("      " + task);
            System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(line);
        } catch (GippyException e) {
            System.out.println(line);
            System.out.println(e.getMessage());
            System.out.println(line);
        }
    }
}
