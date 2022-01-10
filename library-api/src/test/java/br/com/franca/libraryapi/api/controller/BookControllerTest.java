package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.helper.MockHelper;
import br.com.franca.libraryapi.api.service.IBookService;
import br.com.franca.libraryapi.domain.ApiError;
import br.com.franca.libraryapi.domain.MessageError;
import br.com.franca.libraryapi.dtos.BookDTO;
import br.com.franca.libraryapi.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Comparator;
import java.util.List;


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

        var bookDTORequest = MockHelper.oneBookDTO();

        String request = MockHelper.of(bookDTORequest);

        BDDMockito.given(bookService.save(Mockito.any(BookDTO.class)))
                .willReturn(MockHelper.savedBookDTO());

        var requestBuilder = getRequestBuilder(request);

        var response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.getHeader("Location").contains("books/1"));

        Mockito.verify(bookService, Mockito.times(1))
                .save(Mockito.any(BookDTO.class));
    }

    @Test
    @DisplayName("Should throw exception when is invalid book")
    public void save_shouldThrowExceptionWhenIsInvalidBook() throws Exception {

        String request = MockHelper.of(new BookDTO());

        var apiErrorExpected = new ApiError(
                new MessageError("title", "The field title must not be blank"),
                new MessageError("author", "The field author must not be blank"),
                new MessageError("isbn", "The field isbn must not be blank"));

        sort(apiErrorExpected.getErrors());

        var requestBuilder = getRequestBuilder(request);

        var response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        var apiErrorResponse = (ApiError) MockHelper.of(response.getContentAsString(), ApiError.class);

        sort(apiErrorResponse.getErrors());

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(apiErrorResponse.getErrors().size()).isEqualTo(apiErrorExpected.getErrors().size());
        Assertions.assertThat(apiErrorResponse).isEqualTo(apiErrorExpected);

        Mockito.verify(bookService, Mockito.never())
                .save(Mockito.any(BookDTO.class));

    }

    @Test
    @DisplayName("Should throw exception when title is blank ")
    public void save_shouldThrowExceptionWhenTitleIsBlank() throws Exception {

        var bookDTO = MockHelper.oneBookDTO();
        bookDTO.setTitle("");

        String request = MockHelper.of(bookDTO);

        var apiErrorExpected = new ApiError(
                new MessageError("title", "The field title must not be blank"));

        var requestBuilder = getRequestBuilder(request);

        var response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // converter o expected em json e comparar com o response json
        String responseExpected = MockHelper.of(apiErrorExpected);
        AssertionsForClassTypes.assertThat(response.getContentAsString()).isEqualTo(responseExpected);

        // converter o response em objeto java e comparar com o expected objeto java
        var apiErrorResponse = (ApiError) MockHelper.of(response.getContentAsString(), ApiError.class);
        Assertions.assertThat(apiErrorResponse.getErrors().size()).isEqualTo(apiErrorExpected.getErrors().size());

        Mockito.verify(bookService, Mockito.never())
                .save(Mockito.any(BookDTO.class));
    }

    @Test
    @DisplayName("Should throw exception when author is blank ")
    public void save_shouldThrowExceptionWhenAuthorIsBlank() throws Exception {

        var bookDTO = MockHelper.oneBookDTO();
        bookDTO.setAuthor("");

        String request = MockHelper.of(bookDTO);

        var apiErrorExpected = new ApiError(
                new MessageError("author", "The field author must not be blank"));

        var requestBuilder = getRequestBuilder(request);

        var response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        var apiErrorResponse = (ApiError) MockHelper.of(response.getContentAsString(), ApiError.class);

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(apiErrorResponse).isEqualTo(apiErrorExpected);
        Assertions.assertThat(apiErrorResponse.getErrors().size()).isEqualTo(apiErrorExpected.getErrors().size());

        Mockito.verify(bookService, Mockito.never())
                .save(Mockito.any(BookDTO.class));
    }

    @Test
    @DisplayName("Should throw exception when isbn is blank ")
    public void save_shouldThrowExceptionWhenIsbnIsBlank() throws Exception {

        var bookDTO = MockHelper.oneBookDTO();
        bookDTO.setIsbn("");

        String request = MockHelper.of(bookDTO);

        var apiErrorExpected = new ApiError(
                new MessageError("isbn", "The field isbn must not be blank"));

        var requestBuilder = getRequestBuilder(request);

        var response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        ApiError apiErrorResponse = (ApiError) MockHelper.of(response.getContentAsString(), ApiError.class);

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(apiErrorResponse).isEqualTo(apiErrorExpected);
        Assertions.assertThat(apiErrorResponse.getErrors().size()).isEqualTo(apiErrorExpected.getErrors().size());

        Mockito.verify(bookService, Mockito.never())
                .save(Mockito.any(BookDTO.class));
    }

    @Test
    @DisplayName("Should throw exception when isbn already registered")
    public void save_shouldThrowExceptionWhenIsbnAlreadyRegistered() throws Exception {

        var apiErrorExpected = new ApiError(
                new MessageError("isbn", "The field isbn already registered"));

        BDDMockito.given(bookService.save(Mockito.any(BookDTO.class)))
                .willThrow(new BusinessException(apiErrorExpected.getErrors().get(0)));

        var requestBuilder = getRequestBuilder(MockHelper.of(MockHelper.oneBookDTO()));

        var response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        var apiErrorResponse = (ApiError) MockHelper.of(response.getContentAsString(), ApiError.class);

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        Assertions.assertThat(apiErrorResponse.getErrors().size()).isEqualTo(apiErrorExpected.getErrors().size());

        Assertions.assertThat(apiErrorResponse).isEqualTo(apiErrorExpected);

        Mockito.verify(bookService, Mockito.times(1))
                .save(Mockito.any(BookDTO.class));

    }

    private MockHttpServletRequestBuilder getRequestBuilder(String request) {
        return MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(request);
    }

    private void sort(List<MessageError> errors) {
        errors.sort(Comparator.comparing(MessageError::getField));
        // errors.sort(Comparator.comparing(e->e.getField()));
        // errors.sort((e1, e2) -> e1.getField().compareTo(e2.getField()));
    }

    /**
     * Adicionar os demais métodos do CRUD
     */

}