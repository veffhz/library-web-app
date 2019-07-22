package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.librarywebapp.controller.TaskController;
import ru.otus.librarywebapp.integration.ValidateTask;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for Task Controller")
@WebMvcTest(controllers = TaskController.class)
@Import(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc webClient;

    @MockBean
    private ValidateTask validateTask;

    @SpyBean
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

    @Test
    @DisplayName("Test task forbidden /task/** ")
    void shouldTaskForbidden() throws Exception {
        this.webClient.perform(put("/task/start"))
                .andExpect(status().isForbidden());

        this.webClient.perform(put("/task/stopt"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test task start on /task/start ")
    @WithMockUser
    void shouldTaskStart() throws Exception {
        this.webClient.perform(put("/task/start").with(csrf()))
                .andExpect(status().isOk());

        verify(this.scheduledAnnotationBeanPostProcessor,
                times(1)).postProcessAfterInitialization(any(ValidateTask.class), anyString());
    }

    @Test
    @DisplayName("Test task stop on /task/stop ")
    @WithMockUser
    void shouldTaskStop() throws Exception {
        this.webClient.perform(put("/task/stop").with(csrf()))
                .andExpect(status().isOk());

        verify(this.scheduledAnnotationBeanPostProcessor,
                times(1)).postProcessBeforeDestruction(any(ValidateTask.class), anyString());
    }

}