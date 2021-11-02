package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    static String URI = "/api/books/";

    @Autowired
    MockMvc mockMvc; //simula uma requisição

    //@Autowired
    //ModelMapper modelMapper;

    @MockBean
    BookService service; // cria uma instancia mockada da interface BookService
    // e injeta no contexto trazido pelo @ExtendWith(SpringExtension.class)

    @Test
    @DisplayName("Should save book When book is valid")
    public void saveBookTest() throws Exception {
        // cenário

        /**
         * 1 - Cenário, criar um Objeto para a requisição (dto e json) e
         * um para a resposta.
         * 2 - Definir a URI que será consumida.
         * 3 - Invocar o método desejado (post, get, etc..)
         * da classe MockMvcRequestBuilder (construtor de request) passando a URI,
         * (acho que simula um cliente http).
         * 4 - Add os cabeçalhos da requisição (contentType, acept etc..).
         * 5 - Informar o conteúdo da requisição (content(json)).
         * 6 - Fase de Execução do teste, invocar o método perform(request)
         *  passando a requisição que é fornecedida pelo construtor de request.
         * 7- Fazer as verificaçõs desejadas.
         * 8 - Rodar os testes e ver falhar, por falta da API.
         * 9 - Criar API de verdade com parãmetros de request e response (DTO)
         *  baseado no cenário de teste acima.
         *  11 Rodar testes e ver sucesso.
         * 12 - Simular uma chamada para o serviço salvar a entidade de negócio
         *  Dado que uma service invoca o método save passando qualquer coisa
         *  espera que se retorne um dto salvo (BDDMockito.given).
         * 13 - Rodar teste e ver falhar pois nao existe serviço.
         * 14 - Criar um serviço (interface) de verdade com método save,
         * que recebe uma entidade de negocio e devolve uma entidade de negocio.
         * 15 - Injetar o servico no controller e delegar para o service a entidade ou o DTO
         * fazer o mapper do DTO na controlle ou no service...
         *
         */

        BookDTO bookDTO = BookDTO.builder()
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();
        String json = new ObjectMapper().writeValueAsString(bookDTO);

        Book saveBook = Book.builder()
                .id(10l)
                .title("As aventuras")
                .author("Artur")
                .isbn("123456")
                .build();

        BDDMockito.given(service.save(Mockito.any()))
                .willReturn(saveBook);

        // execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // verificação

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(saveBook.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(saveBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(saveBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(saveBook.getIsbn()));
    }

    @Test
    @DisplayName("Should throw exception When save invalid book")
    public void saveInvalidBookTest(){
    }
}
