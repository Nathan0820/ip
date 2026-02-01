package gippy.ui;

import gippy.task.TaskList;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui(){
        scanner = new Scanner(System.in);
    }

    /**
     * Prints the line that separate messages.
     */
    public void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Print greeting message when chatbot is first activated
     */
    public void printHello() {
        String logo = """
                   ▄▄▄▄▄▄ ▄▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄ 
                  █      █   █       █       █  █ █  █
                  █  ▄▄▄▄█   █    ▄  █    ▄  █  █▄█  █
                  █ █  ▄▄█   █   █▄█ █   █▄█ █       █
                  █ █▄▄  █   █    ▄▄▄█    ▄▄▄█▄     ▄█
                  █      █   █   █   █   █     █   █  
                  █▄▄▄▄▄▄█▄▄▄█▄▄▄█   █▄▄▄█     █▄▄▄█  
            """;

        printLine();
        System.out.println("      Hello! I'm");
        System.out.println(logo);
        System.out.println("      How can I help you?");
        printLine();
    }

    /**
     * Prints closing message when user closes the program
     */
    public void printBye() {
        printLine();
        System.out.println("     Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * returns the input
     * @return input
     */
    public String processInput() {
        return scanner.nextLine().toLowerCase();
    }

    /**
     * Prints error messages to user with an error message
     * @param message error message
     */
    public void printError(String message) {
        System.out.println("    " + message);
    }

    /**
     * Handles the listing of task when user entered "list" as input
     * @param tasks the TaskList that stores all tasks
     */
    public void handleList(TaskList tasks) {
        if (tasks.isEmpty()) {
            printLine();
            System.out.println("    No tasks found, add one to start!");
            printLine();
            return;
        }
        printLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.getTask(i));
        }
        printLine();
    }
}
