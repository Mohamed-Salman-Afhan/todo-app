package com.coveragex.todoapp.controller;

import com.coveragex.todoapp.entity.Task;
import com.coveragex.todoapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
       List<Task> tasksList = taskService.getIncompleteTasks();
       return new ResponseEntity<>(tasksList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<Task> markTaskAsComplete (@PathVariable Long id){
      Task updatedTask = taskService.markTaskAsComplete(id);
      return new ResponseEntity<>(updatedTask,HttpStatus.OK);
    }
}
