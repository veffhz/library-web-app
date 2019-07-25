package ru.otus.librarywebapp.endpoints;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ru.otus.librarywebapp.integration.ValidateTask;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
@RestControllerEndpoint(id="task")
public class TaskEndPoint {

    private static final String SCHEDULED_TASKS = "validateTask";

    private final ScheduledAnnotationBeanPostProcessor postProcessor;
    private final ValidateTask scheduledTask;

    @Autowired
    public TaskEndPoint(ScheduledAnnotationBeanPostProcessor postProcessor, @Nullable ValidateTask scheduledTask) {
        this.postProcessor = postProcessor;
        this.scheduledTask = scheduledTask;
    }

    @GetMapping(value = "/stop")
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

    @GetMapping(value = "/start")
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

    @GetMapping
    @ResponseBody
    public Mono<ResponseEntity<Map>> task(ServerHttpRequest serverHttpRequest) {
        Map<String, Link> links = new HashMap<>();
        links.put("stop", new Link(newPath(serverHttpRequest.getURI(), "/stop")));
        links.put("start", new Link(newPath(serverHttpRequest.getURI(), "/start")));
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("_links", links)));
    }

    private String newPath(URI uri, String path) {
        return UriComponentsBuilder.fromUri(uri).path(path).toUriString();
    }

}
