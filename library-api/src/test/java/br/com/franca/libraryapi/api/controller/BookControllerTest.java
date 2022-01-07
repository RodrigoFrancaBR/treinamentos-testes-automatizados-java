package br.com.franca.libraryapi.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureMockMvc
@WebMvcTest
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    @Test
    @DisplayName("Should create book when is valid")
    public void createBook_shouldCreateBookWhenIsValid() {
    }

    @Test
    @DisplayName("Should throw exception when book is invalid")
    public void createInvalidBook_shouldThrowExceptionWhenIsInvalid() {

    }
}
