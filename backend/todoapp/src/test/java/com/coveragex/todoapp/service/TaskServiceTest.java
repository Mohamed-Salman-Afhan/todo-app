package com.coveragex.todoapp.service;

import com.coveragex.todoapp.entity.Task;
import com.coveragex.todoapp.exception.TaskNotFoundException;
import com.coveragex.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getIncompleteTasks_shouldReturnListOfTasks() {
        // 1. Arrange
        List<Task> mockTaskList = List.of(new Task(), new Task());
        when(taskRepository.findTop5ByIsCompletedOrderByCreatedAtDesc(false))
                .thenReturn(mockTaskList);

        // 2. Act
        List<Task> result = taskService.getIncompleteTasks();

        // 3. Assert
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findTop5ByIsCompletedOrderByCreatedAtDesc(false);
    }

    @Test
    void createTask() {
        // 1. Arrange

        Task taskToSave = new Task();
        taskToSave.setTitle("Practice Java");
        taskToSave.setDescription("Building real world Projects");

        Task savedTask = new Task(1L,"Practice Java", "Building real world Projects", false, LocalDateTime.now());
        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        // 2. Act
        Task result = taskService.createTask(taskToSave);

        // 3. Assert
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void markTaskAsComplete() {
        // 1. Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setCompleted(false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act
        Task result = taskService.markTaskAsComplete(taskId);

        // 3. Assert
        assertTrue(result.isCompleted());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);

    }

    @Test
    void markTaskAsCompleteFailed() {
        // 1. Arrange
        Long nonExistentId = 50L;
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // 2. Act & Assert
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.markTaskAsComplete(nonExistentId);
        });

    }
}