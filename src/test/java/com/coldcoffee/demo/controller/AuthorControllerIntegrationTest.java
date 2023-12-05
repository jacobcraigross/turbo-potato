package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.AuthorEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThat__CreateAuthorReturns201() throws Exception {
        AuthorEntity testAuthorOne = TestDataUtility.createTestAuthorOne();
        testAuthorOne.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorOne);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType("application/json")
                .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThat__CreateAuthorReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorOne = TestDataUtility.createTestAuthorOne();
        testAuthorOne.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorOne);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20)
        );
    }
}