package br.com.franca.libraryapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc // disponibiliza um objeto para fazer requisicções
@WebMvcTest //
@ActiveProfiles("test") // quero rodar os testes com o profile test
@ExtendWith(SpringExtension.class) // cria um mini contexto com injeção de dependencia para executar os testes
public class BookControllerTest {

    /** 1 - definir a api que será consumida
     * 2 - definir um construtor de requisicoes fake
     * 3 - definir o verbo e informar a uri
     * 4 - definir cabeçalhos
     * 5 - definir um conteudo da requisição
    */
    static String URI = "/api/books/";

    @Autowired
    MockMvc mockMvc; //simula uma requisição

    @Test
    @DisplayName("Should save book When book is valid")
    public void saveBookTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("My Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value("123456"));

    }

    @Test
    @DisplayName("Should throw exception When save invalid book")
    public void saveInvalidBookTest(){
    }
}
