package gippy.ui;

import java.util.Scanner;

import gippy.task.TaskList;

/**
 * Ui class to handle interactions with the user.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints the line that separate messages.
     */
    public void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Prints greeting message when chatbot is first activated
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
    public String printBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns the input
     * @return input
     */
    public String processInput() {
        return scanner.nextLine().toLowerCase();
    }

    /**
     * Prints error messages to user with an error message
     * @param message error message
     */
    public String printError(String message) {
        return message + "\n";
    }

    /**
     * Handles the listing of task when user entered "list" as input
     * @param tasks the TaskList that stores all tasks
     */
    public String handleList(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "No tasks found, add one to start!";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list: \n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.getTask(i)).append("\n");
        }
        
        return sb.toString();
    }
}
