package com.pranton.TaskMaster.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pranton.TaskMaster.models.Task;
import com.pranton.TaskMaster.services.TaskService;

@Controller
public class MainController {
    @Autowired
    private TaskService taskService;
    @GetMapping("/")
    public String home(Model model) {
        List<Task> todayList = taskService.getByDate(LocalDate.now());
        System.out.println(todayList.size());
        model.addAttribute("tasks", todayList);
        return "index";
    }

    @GetMapping("/allTasks") 
    public String getTasks(Model model) {
        List<Task> list = taskService.getTaskList();
        model.addAttribute("tasks", list);
        return "allTasks";
    }
    @GetMapping("/fileUpload")
    public String fileUpload() {
        return "fileUpload";
    }
}
