package ru.otus.librarywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.otus.librarywebapp.integration.ValidateTask;

import java.util.Objects;

@Controller
public class TaskController {

    private static final String SCHEDULED_TASKS = "validateTask";

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired(required = false)
    private ValidateTask scheduledTask;

    @PutMapping(value = "/task/stop")
    @ResponseBody
    public ResponseEntity<String> stopSchedule(){

        if (Objects.isNull(scheduledTask)) {
            return ResponseEntity.badRequest().body("Task disabled!");
        }

        postProcessor.postProcessBeforeDestruction(scheduledTask, SCHEDULED_TASKS);
        return ResponseEntity.ok("OK");
    }

    @PutMapping(value = "/task/start")
    @ResponseBody
    public ResponseEntity<String> startSchedule(){

        if (Objects.isNull(scheduledTask)) {
            return ResponseEntity.badRequest().body("Task disabled!");
        }

        postProcessor.postProcessAfterInitialization(scheduledTask, SCHEDULED_TASKS);
        return ResponseEntity.ok("OK");
    }

}
