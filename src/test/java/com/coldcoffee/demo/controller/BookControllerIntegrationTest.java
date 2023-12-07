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

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThat__CreateBookReturns201() throws Exception {
        BookDTO testBookDTOOne = TestDataUtility.createTestBookDTO(null);
        String bookJson = objectMapper.writeValueAsString(testBookDTOOne);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBookDTOOne.getIsbn()).contentType("application/json").content(bookJson))
                        .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThat__CreateBookReturnsSavedBook() throws Exception {
        BookDTO testBookDTOOne = TestDataUtility.createTestBookDTO(null);
        String bookJson = objectMapper.writeValueAsString(testBookDTOOne);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBookDTOOne.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDTOOne.getIsbn()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testBookDTOOne.getTitle()));
    }

    @Test
    public void testThat__GetAllBooksReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThat__GetAllBooksReturnsListOfBooks() throws Exception {
        BookEntity testBookEntity = TestDataUtility.createTestBookOne(null);
        bookService.createBook(testBookEntity.getIsbn(), testBookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Jake"));
    }





}
