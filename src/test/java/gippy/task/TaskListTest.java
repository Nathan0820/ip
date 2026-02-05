package gippy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void addTask_test() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_test() {
        TaskList taskList = new TaskList();
        Task task = new Todo("read book");
        taskList.addTask(task);
        taskList.deleteTask(task);
        assertEquals(0, taskList.size());
    }

    @Test
    public void getTask_test() {
        TaskList taskList = new TaskList();
        Task task = new Todo("read book");
        taskList.addTask(task);
        Task get = taskList.getTask(0);
        assertEquals(task, get);
    }

    @Test
    public void isEmpty_test() {
        TaskList taskList = new TaskList();
        boolean isEmpty = taskList.isEmpty();
        assertTrue(isEmpty);
    }

    @Test
    public void size_test() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        int size = taskList.size();
        assertEquals(1, size);
    }


}
