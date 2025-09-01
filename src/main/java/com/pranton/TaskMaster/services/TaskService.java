package com.pranton.TaskMaster.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.pranton.TaskMaster.models.Task;
import com.pranton.TaskMaster.repos.TaskRepo;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    public boolean create(Task task) {
        try {
            taskRepo.save(task);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
    public List<Task> getTaskList() {
        try {
            return taskRepo.findAll();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    public List<Task> getByDate(LocalDate date) {
        try {
            Task task = new Task();
            task.setDate(LocalDate.now());
            ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withIgnorePaths("id", "isDone");
            Example<Task> ex = Example.of(task, matcher);
            return taskRepo.findAll(ex);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public Task getTask(long id) {
        try {
            return taskRepo.findById(id).get();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    } 
    @Transactional
    public boolean update(long id, Task task) {
        try {
            Optional<Task> existingTask = taskRepo.findById(id);
            if (existingTask.isPresent()) {   
                existingTask.get().setContent(task.getContent());
                existingTask.get().setDate(task.getDate());
                existingTask.get().setDone(task.isDone());
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    };
    public boolean delete(long id) {
        try {
            taskRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
}
