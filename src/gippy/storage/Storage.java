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

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load Tasks from storage file to be used by the main program
     * @return Arraylist to be used by the main program
     * @throws FileNotFoundException Initialize a new arraylist if there is no data
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
     * Process lines from storage file to a gippy.task.Task class
     * @param line Line from each row in the storage file
     * @return Arraylist of tasks from storage
     */
    public Task stringToTask(String line) {
        String[] input = line.split(" \\| ");
        String taskType = input[0];
        boolean isDone = input[1].equals("1");
        String description = input[2];

        Task task;
        try {
            switch (taskType) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    task = new Deadline(description, input[3]);
                    break;
                case "E":
                    task = new Event(description, input[3], input[4]);
                    break;
                default:
                    task = null;
                    System.out.println("Unknown task type, please try again");
            }

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
