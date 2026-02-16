package gippy.storage;

import gippy.exception.GippyException;
import gippy.task.Deadline;
import gippy.task.Event;
import gippy.task.Task;
import gippy.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Storage class to handle loading and saving tasks to a txt file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from storage file to be used by the main program
     * @return Arraylist to be used by the main program
     * @throws FileNotFoundException if the file is not found
     */
    public ArrayList<Task> getTasks() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            tasks.add(stringToTask(line));
        }
        return tasks;
    }

    /**
     * Processes lines from storage file to a gippy.task.Task class
     * @param line Line from each row in the storage file
     * @return Arraylist of tasks from storage
     */
    public Task stringToTask(String line) {
        String[] input = line.split(" \\| ");
        String taskType = input[0];
        assert taskType.equals("T") || taskType.equals("D") || taskType.equals("E") 
            : "Invalid task type";
        boolean isDone = input[1].equals("1");
        String description = input[2];

        Task task;
        try {
            task = createTask(taskType, description, input);
        } catch (GippyException e) {
            System.out.println("    Error: " + e.getMessage());
            return null;
        }
        if (task != null && isDone) {
            task.markDone();
        }
        return task;
    }

    /**
     * Creates a task based on the task type and description
     * @param taskType Type of task (T, D, E)
     * @param description Description of the task
     * @param inputs Array of inputs from the storage file line
     * @return The task created based on the task type and description
     * @throws GippyException Handles unknown task type error
     */
    private Task createTask(String taskType, String description, String[] inputs) throws GippyException {
        switch (taskType) {
        case "T":
            return new Todo(description);
        case "D":
            return new Deadline(description, inputs[3]);
        case "E":
            return new Event(description, inputs[3], inputs[4]);
        default:
            throw new GippyException("Unknown task type, please try again");
        }
    }

    /**
     * Saves back the tasks in the arraylist back to the storage file
     * @param tasks ArrayList that stores the tasks
     * @throws IOException Handles errors saving to storage file
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter fileWriter = new FileWriter(filePath);

        for (Task task : tasks) {
            fileWriter.write(task.taskToString() + System.lineSeparator());
        }
        fileWriter.close();
    }
}
