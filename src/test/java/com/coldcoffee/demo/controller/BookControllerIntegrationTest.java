package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.DTO.BookDTO;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.model.BookEntity;
import com.coldcoffee.demo.service.BookService;
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
public class BookControllerIntegrationTest {

    /*
    these tests are primarily checking the behavior of the Spring Boot application (especially the MVC components)
    in a controlled test environment. It is not directly testing against the real database but rather against the
    application's logic and potentially an in-memory database or mocked data layer.
    */

    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }


    // ---------- TEST THAT CREATE BOOK RETURNS 201 --------------------------------------------------------------------
    @Test
    public void testThat__CreateBookReturns201() throws Exception {
        BookDTO testBookDTO = TestDataUtility.createTestBookDTO(null);
        String bookJson = objectMapper.writeValueAsString(testBookDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBookDTO.getIsbn()).contentType("application/json").content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // ---------- TEST THAT CREATE BOOK RETURNS SAVED BOOK -------------------------------------------------------------
    @Test
    public void testThat__CreateBookReturnsSavedBook() throws Exception {
        BookDTO testBookDTO = TestDataUtility.createTestBookDTO(null);
        String bookJson = objectMapper.writeValueAsString(testBookDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBookDTO.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDTO.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testBookDTO.getTitle()));
    }

    // ---------- TEST THAT GET ALL BOOKS RETURNS 200 ------------------------------------------------------------------
    @Test
    public void testThat__GetAllBooksReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // ---------- TEST THAT GET ALL BOOKS RETURNS LIST OF BOOKS --------------------------------------------------------
    @Test
    public void testThat__GetAllBooksReturnsListOfBooks() throws Exception {
        BookEntity testBookEntity = TestDataUtility.createTestBookEntityOne(null);
        bookService.createBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Jake"));
    }

    // ---------- TEST THAT GET BOOK BY ISBN RETURNS 200 ---------------------------------------------------------------
    @Test
    public void testThat__GetBookByIsbnReturns200() throws Exception {
        BookEntity testBookEntity = TestDataUtility.createTestBookEntityOne(null);
        bookService.createBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn()).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    // ---------- TEST THAT GET BOOK BY ISBN RETURNS 404 WHEN BOOK DOES NOT EXIST --------------------------------------
    @Test
    public void testThat__GetBookByIsbnReturns404WhenBookDoesntExist() throws Exception {
        BookEntity testBookEntity = TestDataUtility.createTestBookEntityOne(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn()).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}





