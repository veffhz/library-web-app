package ru.otus.librarywebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.otus.librarywebapp.integration.ValidateTask;

import java.util.Objects;

@Slf4j
@Controller
public class TaskController {

    private static final String SCHEDULED_TASKS = "validateTask";

    private final ScheduledAnnotationBeanPostProcessor postProcessor;
    private final ValidateTask scheduledTask;

    @Autowired
    public TaskController(ScheduledAnnotationBeanPostProcessor postProcessor, @Nullable ValidateTask scheduledTask) {
        this.postProcessor = postProcessor;
        this.scheduledTask = scheduledTask;
    }

    @PutMapping(value = "/task/stop")
    @ResponseBody
    public ResponseEntity<String> stopSchedule(){

        if (Objects.isNull(scheduledTask)) {
            log.warn("Task disabled!");
            return ResponseEntity.badRequest().body("Task disabled!");
        }

        log.info("Task stop.");

        postProcessor.postProcessBeforeDestruction(scheduledTask, SCHEDULED_TASKS);
        return ResponseEntity.ok("OK");
    }

    @PutMapping(value = "/task/start")
    @ResponseBody
    public ResponseEntity<String> startSchedule(){

        if (Objects.isNull(scheduledTask)) {
            log.warn("Task disabled!");
            return ResponseEntity.badRequest().body("Task disabled!");
        }

        log.info("Task start.");

        postProcessor.postProcessAfterInitialization(scheduledTask, SCHEDULED_TASKS);
        return ResponseEntity.ok("OK");
    }

}
