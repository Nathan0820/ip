package gippy.ui;

import gippy.task.TaskList;

/**
 * Ui class to handle interactions with the user.
 */
public class Ui {
    /**
     * Prints closing message when user closes the program
     */
    public String printBye() {
        return "Bye. Hope to see you again soon!";
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
