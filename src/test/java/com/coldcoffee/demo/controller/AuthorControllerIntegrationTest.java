package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.DTO.AuthorDTO;
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

    /*
    these tests are primarily checking the behavior of the Spring Boot application (especially the MVC components)
    in a controlled test environment. It is not directly testing against the real database but rather against the
    application's logic and potentially an in-memory database or mocked data layer.
    */

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
        AuthorEntity testAuthorOne = TestDataUtility.createTestAuthorEntityOne();
        testAuthorOne.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorOne);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").contentType("application/json").content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // ---------- TEST THAT CREATE AUTHOR RETURNS SAVED AUTHOR ---------------------------------------------------------
    @Test
    public void testThat__CreateAuthorReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorOne = TestDataUtility.createTestAuthorEntityOne();
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
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorService.createAuthor(testAuthorEntityOne);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20));
    }

    // ---------- TEST THAT GET AUTHOR BY ID RETURNS 200 WHEN AUTHOR EXISTS --------------------------------------------
    @Test
    public void testThat__GetAuthorByIdReturns200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorService.createAuthor(testAuthorEntityOne);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // ---------- TEST THAT GET AUTHOR BY ID RETURNS 404 WHEN AUTHOR DOESNT EXIST --------------------------------------
    @Test
    public void testThat__GetAuthorByIdReturns404WhenAuthorDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // ---------- TEST THAT GET AUTHOR BY ID RETURNS AUTHOR WHEN AUTHOR EXISTS -----------------------------------------
    @Test
    public void testThat__GetAuthorByIdReturnsAuthorWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorService.createAuthor(testAuthorEntityOne);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20));
    }

    // ---------- TEST THAT UPDATE AUTHOR BY ID RETURNS 404 WHEN AUTHOR DOESNT EXIST -----------------------------------
    @Test
    public void testThat__UpdateAuthorByIdReturns404WhenAuthorDoesntExist() throws Exception {
        AuthorDTO testAuthorDTO = TestDataUtility.createTestAuthorDTO(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/857575758").contentType("application/json").content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // ---------- TEST THAT UPDATE AUTHOR BY ID RETURNS 200 WHEN AUTHOR EXISTS -----------------------------------------
    @Test
    public void testThat__UpdateAuthorByIdReturns200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntityOne);
        AuthorDTO testAuthorDTO = TestDataUtility.createTestAuthorDTO(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId()).contentType("application/json").content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // ---------- TEST THAT UPDATE AUTHOR BY ID RETURNS UPDATED AUTHOR -------------------------------------------------
    @Test
    public void testThat__UpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntityOne);
        AuthorEntity testAuthor = TestDataUtility.createTestAuthorEntityTwo();
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId()).contentType(MediaType.APPLICATION_JSON).content(authorJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge()));
    }

    // ---------- TEST THAT UPDATE AUTHOR BY ID (PARTIAL) RETURNS 200 WHEN AUTHOR EXISTS -------------------------------
    @Test
    public void testThat__UpdateAuthorByIdPartialReturns200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntityOne);
        AuthorDTO testAuthorDTO = TestDataUtility.createTestAuthorDTO(null);
        testAuthorDTO.setName("UPDATED NAME");
        String authorJson = objectMapper.writeValueAsString(testAuthorDTO);
        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId()).contentType("application/json").content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    // ---------- TEST THAT UPDATE AUTHOR BY ID (PARTIAL) RETURNS UPDATED AUTHOR ---------------------------------------
    @Test
    public void testThat__UpdateAuthorByIdPartialReturnsPartiallyUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntityOne);
        AuthorDTO testAuthorDTO = TestDataUtility.createTestAuthorDTO(null);
        testAuthorDTO.setName("UPDATED NAME");
        String authorJson = objectMapper.writeValueAsString(testAuthorDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId()).contentType(MediaType.APPLICATION_JSON).content(authorJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UPDATED NAME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDTO.getAge()));
    }

    // DELETE TEST --> 200 OK
    @Test
    public void testThat__DeleteAuthorReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // ADD ANOTHER DELETE TEST HERE LATER POSSIBLY....







}
