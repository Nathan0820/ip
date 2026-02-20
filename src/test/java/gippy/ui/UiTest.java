package gippy.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gippy.task.TaskList;
import gippy.task.Todo;

public class UiTest {

    @Test
    public void printBye_returnsGoodbyeMessage() {
        Ui ui = new Ui();
        String result = ui.printBye();
        assertEquals("Bye. Hope to see you again soon!", result);
    }

    @Test
    public void printError_returnsMessageWithNewline() {
        Ui ui = new Ui();
        String result = ui.printError("Test error message");
        assertEquals("Test error message\n", result);
    }

    @Test
    public void handleList_emptyTaskList_returnsNoTasksMessage() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();

        String result = ui.handleList(taskList);
        assertEquals("No tasks found, add one to start!", result);
    }

    @Test
    public void handleList_withTasks_returnsFormattedList() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write essay"));

        String result = ui.handleList(taskList);

        assertTrue(result.contains("Here are the tasks in your list:"));
        assertTrue(result.contains("1. [T][ ] read book"));
        assertTrue(result.contains("2. [T][ ] write essay"));
    }

    @Test
    public void handleList_markedTask_showsCorrectStatus() {
        Ui ui = new Ui();
        TaskList taskList = new TaskList();
        Todo todo = new Todo("read book");
        todo.markDone();
        taskList.addTask(todo);

        String result = ui.handleList(taskList);

        assertTrue(result.contains("[T][X] read book"));
    }
}
