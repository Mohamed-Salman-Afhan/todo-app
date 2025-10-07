package com.coveragex.todoapp.service;

import com.coveragex.todoapp.entity.Task;
import com.coveragex.todoapp.exception.TaskNotFoundException;
import com.coveragex.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getIncompleteTasks() {
        return taskRepository.findTop5ByIsCompletedOrderByCreatedAtDesc(false);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task markTaskAsComplete(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        task.setCompleted(true);

        return taskRepository.save(task);
    }

}
