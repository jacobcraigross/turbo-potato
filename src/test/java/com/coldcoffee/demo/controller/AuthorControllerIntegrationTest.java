package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.service.AuthorService;
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

    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final AuthorService authorService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }


    // ---------- TEST THAT CREATE AUTHOR RETURNS 201 ------------------------------------------------------------------
    @Test
    public void testThat__CreateAuthorReturns201() throws Exception {
        AuthorEntity testAuthorOne = TestDataUtility.createTestAuthorOne();
        testAuthorOne.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorOne);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").contentType("application/json").content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // ---------- TEST THAT CREATE AUTHOR RETURNS SAVED AUTHOR ---------------------------------------------------------
    @Test
    public void testThat__CreateAuthorReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorOne = TestDataUtility.createTestAuthorOne();
        testAuthorOne.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorOne);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").contentType(MediaType.APPLICATION_JSON).content(authorJson))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jake"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20));
    }


    // ---------- TEST THAT GET ALL AUTHORS RETURNS 200 ----------------------------------------------------------------
    @Test
    public void testThat__GetAllAuthorsReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors").contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // ---------- TEST THAT GET ALL AUTHORS RETURNS LIST OF AUTHORS ----------------------------------------------------
    @Test
    public void testThat__GetAllAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorOne();
        authorService.createAuthor(testAuthorEntityOne);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20));
    }

    // ---------- TEST THAT GET AUTHOR BY ID RETURNS 200 WHEN AUTHOR EXISTS --------------------------------------------
    @Test
    public void testThat__GetAuthorByIdReturns200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorOne();
        authorService.createAuthor(testAuthorEntityOne);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // ---------- TEST THAT GET AUTHOR BY ID RETURNS 404 WHEN AUTHOR DOESNT EXIST --------------------------------------
    @Test
    public void testThat__GetAuthorByIdReturns404WhenAuthorDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/99").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
