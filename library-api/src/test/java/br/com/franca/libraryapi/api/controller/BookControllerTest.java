package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
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

@AutoConfigureMockMvc // disponibiliza um objeto para fazer requisicções
@WebMvcTest //
@ActiveProfiles("test") // quero rodar os testes com o profile test
@ExtendWith(SpringExtension.class) // cria um mini contexto com injeção de dependencia para executar os testes
public class BookControllerTest {

    /** 1 - Definir a URI que será consumida
     * 2 - Definir um método com um construtor de requisicoes fake (MockMvcRequestBuilder)
     * 3 - Chamar o método conforme o verbo http da chamada (get, post, delete etc..) passando a URI
     * 4 - definir cabeçalhos
     * 5 - definir um conteudo da requisição
    */
    static String URI = "/api/books/";

    @Autowired
    MockMvc mockMvc; //simula uma requisição

    @MockBean
    BookService service; // cria uma instancia mockada da interface BookService
    // e injeta no contexto trazido pelo @ExtendWith(SpringExtension.class)

    @Test
    @DisplayName("Should save book When book is valid")
    public void saveBookTest() throws Exception {
        // cenário
        BookDTO bookDTO = BookDTO.builder()
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();

        Book saveBook = Book.builder()
                .id(10l)
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();

        BDDMockito.given(service.save(Mockito.any(Book.class)))
                .willReturn(saveBook);

        String json = new ObjectMapper().writeValueAsString(bookDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(bookDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(bookDTO.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(bookDTO.getIsbn()));
    }

    @Test
    @DisplayName("Should throw exception When save invalid book")
    public void saveInvalidBookTest(){
    }
}
