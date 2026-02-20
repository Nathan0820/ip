package gippy.task;

// Uses Github Copilot to assist in writing test cases for TaskList class.

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import gippy.exception.GippyException;

public class TaskListTest {
    @Test
    public void constructor_emptyList_createsEmptyTaskList() {
        TaskList taskList = new TaskList();
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());
    }

    @Test
    public void constructor_withExistingTasks_copiesTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write essay"));

        TaskList taskList = new TaskList(tasks);
        assertEquals(2, taskList.size());
    }

    @Test
    public void getTask_validIndex_returnsCorrectTask() {
        TaskList taskList = new TaskList();
        Task task = new Todo("read book");
        taskList.addTask(task);
        Task retrieved = taskList.getTask(0);
        assertEquals(task, retrieved);
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        TaskList taskList = new TaskList();
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void size_emptyList_returnsZero() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.size());
    }

    @Test
    public void size_withTasks_returnsCorrectCount() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write essay"));
        assertEquals(2, taskList.size());
    }

    @Test
    public void getAllTasks_returnsAllTasks() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));

        ArrayList<Task> tasks = taskList.getAllTasks();
        assertEquals(2, tasks.size());
    }

    @Test
    public void findTasks_matchingKeyword_returnsMatchingTasks() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write book report"));
        taskList.addTask(new Todo("buy groceries"));

        TaskList found = taskList.findTasks("book");
        assertEquals(2, found.size());
    }

    @Test
    public void findTasks_noMatchingKeyword_returnsEmptyList() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write essay"));

        TaskList found = taskList.findTasks("groceries");
        assertTrue(found.isEmpty());
    }

    @Test
    public void findTasks_partialMatch_returnsMatchingTasks() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("reading assignment"));
        taskList.addTask(new Todo("read more books"));
        taskList.addTask(new Todo("already read"));

        TaskList found = taskList.findTasks("read");
        assertEquals(3, found.size());
    }

    @Test
    public void findTasks_caseSensitive_exactMatchOnly() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("Read Book"));
        taskList.addTask(new Todo("read book"));

        TaskList found = taskList.findTasks("Read");
        assertEquals(1, found.size());
    }

    @Test
    public void sortTask_tasksWithDates_sort() throws GippyException {
        TaskList taskList = new TaskList();
        taskList.addTask(new Deadline("task 3", "2026-03-01"));
        taskList.addTask(new Deadline("task 1", "2026-01-01"));
        taskList.addTask(new Deadline("task 2", "2026-02-01"));

        // Tasks should be sorted by date after adding
        assertEquals("task 1", taskList.getTask(0).getDescription());
        assertEquals("task 2", taskList.getTask(1).getDescription());
        assertEquals("task 3", taskList.getTask(2).getDescription());
    }

    @Test
    public void sortTask_mixedTaskTypes_todosAtEnd() throws GippyException {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("todo task"));
        taskList.addTask(new Deadline("deadline task", "2026-01-15"));

        // Deadline should come first (has date), Todo should be at end (null date)
        assertEquals("deadline task", taskList.getTask(0).getDescription());
        assertEquals("todo task", taskList.getTask(1).getDescription());
    }
}
