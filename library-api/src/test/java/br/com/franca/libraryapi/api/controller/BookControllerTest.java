package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.service.IBookService;
import br.com.franca.libraryapi.domain.model.Book;
import br.com.franca.libraryapi.dtos.BookDTO;
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
    @DisplayName("Should create book when is valid")
    public void createBook_shouldCreateBookWhenIsValid() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();

        String json = new ObjectMapper().writeValueAsString(bookDTO);

        Book savedBook = Book.builder().id(1l)
                .title("Meu Livro")
                .author("Autor")
                .isbn("121212")
                .build();

        given(bookService.save(Mockito.any(Book.class)))
                .willReturn(savedBook);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(savedBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Meu Livro"))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Autor"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value("121212"));
    }

    @Test
    @DisplayName("Should throw exception when book is invalid")
    public void createInvalidBook_shouldThrowExceptionWhenIsInvalid() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", hasSize(3)));
//                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Meu Livro"))
//                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Autor"))
//                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value("121212"));

    }
}
