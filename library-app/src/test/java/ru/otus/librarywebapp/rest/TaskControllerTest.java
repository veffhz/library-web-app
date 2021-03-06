package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import ru.otus.librarywebapp.controller.TaskController;
import ru.otus.librarywebapp.integration.ValidateTask;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@DisplayName("Test for Task Controller")
@WebFluxTest(controllers = TaskController.class)
@Import(TaskController.class)
class TaskControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ValidateTask validateTask;

    @SpyBean
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

    @Test
    @DisplayName("Test task forbidden /task/** ")
    void shouldTaskForbidden() {
        this.webClient.put()
                .uri("/task/start")
                .exchange()
                .expectStatus().isForbidden();

        this.webClient.put()
                .uri("/task/stop")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("Test task start on /task/start ")
    @WithMockUser
    void shouldTaskStart() {
        this.webClient.mutateWith(csrf())
                .put()
                .uri("/task/start")
                .exchange()
                .expectStatus().isOk();

        verify(this.scheduledAnnotationBeanPostProcessor,
                times(1)).postProcessAfterInitialization(any(ValidateTask.class), anyString());
    }

    @Test
    @DisplayName("Test task stop on /task/stop ")
    @WithMockUser
    void shouldTaskStop() {
        this.webClient.mutateWith(csrf())
                .put()
                .uri("/task/stop")
                .exchange()
                .expectStatus().isOk();

        verify(this.scheduledAnnotationBeanPostProcessor,
                times(1)).postProcessBeforeDestruction(any(ValidateTask.class), anyString());
    }

}