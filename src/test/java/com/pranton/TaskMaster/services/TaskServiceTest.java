package com.pranton.TaskMaster.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pranton.TaskMaster.models.Task;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @Test
    public void createTask() {
        Task task = new Task();
        task.setContent("This is test task");
        task.setDate(LocalDate.now());
        task.setDone(false);
        assertTrue(taskService.create(task));
    }

}
