package com.coveragex.todoapp.controller;

import com.coveragex.todoapp.entity.Task;
import com.coveragex.todoapp.exception.TaskNotFoundException;
import com.coveragex.todoapp.repository.TaskRepository;
import com.coveragex.todoapp.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the TaskService.
 * This class tests the core business logic ("normal flow") of the application.
 * The TaskRepository is mocked to isolate the service layer from the database.
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;

    @BeforeEach
    void setUp() {

        task1 = new Task(1L, "Buy groceries", "Milk, Bread, Cheese", false, LocalDateTime.now());
    }

    @Test
    void whenGetIncompleteTasks_shouldReturnListOfTasks() {
        // Arrange (Given)
        Task task2 = new Task(2L, "Clean home", "Need to clean the bed room", false, LocalDateTime.now().minusHours(1));
        List<Task> expectedTasks = Arrays.asList(task1, task2);
        // Define the behavior of the mock repository
        when(taskRepository.findTop5ByIsCompletedOrderByCreatedAtDesc(false)).thenReturn(expectedTasks);

        // Act (When)
        List<Task> actualTasks = taskService.getIncompleteTasks();

        // Assert (Then)
        assertNotNull(actualTasks);
        assertEquals(2, actualTasks.size());
        assertEquals(expectedTasks, actualTasks);
        // Verify that the repository method was called exactly once
        verify(taskRepository, times(1)).findTop5ByIsCompletedOrderByCreatedAtDesc(false);
    }

    @Test
    void whenCreateTask_shouldSaveAndReturnTask() {
        // Arrange
        Task newTask = new Task(null, "New Task", "Description", false, null);
        // When the save method is called with any Task object, return our sample task with an ID.
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        // Act
        Task createdTask = taskService.createTask(newTask);

        // Assert
        assertNotNull(createdTask);
        assertEquals(task1.getTitle(), createdTask.getTitle());
        // Verify that the save method was called exactly once
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    void whenMarkTaskAsComplete_withValidId_shouldUpdateAndReturnTask() {
        // Arrange
        // Mock the repository to find the task first
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task1));
        // Mock the save operation
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task updatedTask = taskService.markTaskAsComplete(1L);

        // Assert
        assertNotNull(updatedTask);
        assertTrue(updatedTask.isCompleted()); // The most important check!
        assertEquals(1L, updatedTask.getId());
        // Verify findById and save were both called once
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void whenMarkTaskAsComplete_withInvalidId_shouldThrowException() {
        // Arrange
        Long invalidId = 99L;
        // Mock the repository to return an empty Optional, simulating "not found"
        when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        // We assert that executing this service call throws the expected exception
        Exception exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.markTaskAsComplete(invalidId);
        });

        assertEquals("Task not found with id: " + invalidId, exception.getMessage());

        // Verify that findById was called, but save was never called
        verify(taskRepository, times(1)).findById(invalidId);
        verify(taskRepository, never()).save(any(Task.class));
    }
}
