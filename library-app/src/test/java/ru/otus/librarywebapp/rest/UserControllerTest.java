package ru.otus.librarywebapp.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for user api")
@WebMvcTest(controllers = UserApi.class)
@Import(UserApi.class)
class UserControllerTest extends BaseTest {

    @Test
    @DisplayName("Test get on /api/user")
    void shouldGetUser() throws Exception {
        String responseBody = "[\"usr\",null,{\"password\":\"password\",\"username\":\"usr\"," +
                "\"authorities\":[{\"authority\":\"ROLE_USER\"}],\"accountNonExpired\":true,\"accountNonLocked\":true," +
                "\"credentialsNonExpired\":true,\"enabled\":true},[{\"authority\":\"ROLE_USER\"}]]";

        this.webClient.perform(get("/api/user")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));
    }

    @Test
    @DisplayName("Test post on /api/user")
    void shouldPostUser() throws Exception {
        String responseBody = "[\"usr\",[{\"authority\":\"ROLE_USER\"}],\"Test\"]";

        this.webClient.perform(post("/api/user").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("Test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseBody));
    }

}