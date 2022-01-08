package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.helper.MockHelper;
import br.com.franca.libraryapi.api.service.IBookService;
import br.com.franca.libraryapi.domain.MessageError;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
import br.com.franca.libraryapi.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    static String URI = "/api/books";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IBookService bookService;

    @Test
    @DisplayName("Should save book when is valid")
    public void save_shouldSaveBookWhenIsValid() throws Exception {

        String json = MockHelper.of(MockHelper.oneBookDTO());

        Book savedBook = MockHelper.oneBook();

        given(bookService.save(Mockito.any(Book.class)))
                .willReturn(savedBook);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(savedBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(savedBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(savedBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(savedBook.getIsbn()));
    }

    @Test
    @DisplayName("Should throw exception when is invalid book")
    public void save_shouldThrowExceptionWhenIsInvalidBook() throws Exception {
        String json = MockHelper.of(new BookDTO());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", hasSize(3)));

    }

    @Test
    @DisplayName("Should throw exception when isbn already registered")
    public void save_shouldThrowExceptionWhenIsbnAlreadyRegistered() throws Exception {

        MessageError messageError = new MessageError("isbn", "The field isbn already registered");

        given(bookService.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(messageError));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(MockHelper.of(MockHelper.oneBookDTO()));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0].field").value(messageError.getField()))
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0].message").value(messageError.getMessage()));

    }
}
