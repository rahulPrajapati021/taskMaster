package com.pranton.TaskMaster.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pranton.TaskMaster.models.Task;
import com.pranton.TaskMaster.services.TaskService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping("/add")
    public String createTask(HttpServletRequest request, Task task) {
        String referer = request.getHeader("Referer");
        if(task.getDate() == null) {
            task.setDate(LocalDate.now());
        }
        else {
            System.out.println(task.getDate());
        }
        taskService.create(task);
        return "redirect:" + referer;
    }
    @PostMapping("/toggleStatus/{id}")
    public String toggleStatus(HttpServletRequest request, @PathVariable long id) {
        String referer = request.getHeader("Referer");
        try {
            Task existingTask = taskService.getTask(id);
            existingTask.setDone(true);
            taskService.update(id, existingTask);
        } catch (Exception e) {
            // TODO: handle exception
            // not able to toggle Status
            e.printStackTrace();
        }
        return "redirect:" + referer;
    }
    @PostMapping("/delete/{id}")
    public String deleteTask(HttpServletRequest request, @PathVariable long id) {
        String referer = request.getHeader("Referer");
        try {
            taskService.delete(id);
        } catch (Exception e) {
            // TODO: handle exception
            // not able to handle it 
            e.printStackTrace();
        }
        return "redirect:" + referer;
    }


    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int insertedRows = readExcel(file.getInputStream());
            redirectAttributes.addFlashAttribute("message", ("total " + insertedRows + " inserted"));
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", ("failed to uplaod file + " + e.getMessage()));
        }
        return "redirect:/fileUpload";
    }

    public int readExcel(InputStream inputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter dataFormatter = new DataFormatter();
        int insertedCount = 0;
        for (Row row : sheet) {
            if(row.getRowNum() == 0) {
                continue;
            }
            String content = dataFormatter.formatCellValue(row.getCell(1));
            String dateString = dataFormatter.formatCellValue(row.getCell(2));
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("M/d/yy");
            LocalDate date = LocalDate.parse(dateString, pattern);
            Task task = new Task();
            task.setContent(content);
            task.setDate(date);
            task.setDone(false);
            taskService.create(task);
            insertedCount++;
        }
        workbook.close();
        return insertedCount;
    }
}
