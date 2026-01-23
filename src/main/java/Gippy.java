import java.util.ArrayList;
import java.util.Scanner;

public class Gippy {
    public static void main(String[] args) {
        //initialize variables
        Scanner scanner = new Scanner(System.in);
        String line = "    ____________________________________________________________";
        String logo = """
                   ▄▄▄▄▄▄ ▄▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄ 
                  █      █   █       █       █  █ █  █
                  █  ▄▄▄▄█   █    ▄  █    ▄  █  █▄█  █
                  █ █  ▄▄█   █   █▄█ █   █▄█ █       █
                  █ █▄▄  █   █    ▄▄▄█    ▄▄▄█▄     ▄█
                  █      █   █   █   █   █     █   █  
                  █▄▄▄▄▄▄█▄▄▄█▄▄▄█   █▄▄▄█     █▄▄▄█  
            """;
        ArrayList<Task> tasks = new ArrayList<>();
        //greet
        System.out.println(line);
        System.out.println("      Hello! I'm");
        System.out.println(logo);
        System.out.println("      How can I help you?");
        System.out.println(line);

        //store task in arraylist and list them out
        while (true){
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("    " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println(line);
            } else if (input.startsWith("mark")) {
                handleMark(input, tasks, line, true);
            } else if (input.startsWith("unmark")) {
                handleMark(input, tasks, line, false);
            } else {
                addTask(input, tasks, line);
            }
        }

        //exit
        System.out.println(line);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    //function to handle mark or unmark
    public static void handleMark(String input, ArrayList<Task> tasks, String line, boolean markDone) {
        try {
            String[] processedInput = input.split(" "); //split string to extract task name and index
            int index = Integer.parseInt(processedInput[1]);
            if (index < 1 || index > tasks.size()) {
                throw new GippyException("      Invalid task number!! Please try a different number");
            }
            Task task = tasks.get(index - 1);
            System.out.println(line);
            if (markDone) {
                task.markDone();
                System.out.println("    Nice! I've marked this task as done:");
            } else {
                task.markUndone();
                System.out.println("    OK, I've marked this task as not done yet:");
            }
            System.out.println("      " + task);
            System.out.println(line);
        } catch (GippyException e) {
            System.out.println(line);
            System.out.println(e.getMessage());
            System.out.println(line);
        }

    }

    public static void addTask(String input, ArrayList<Task> tasks, String line) {
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
}
