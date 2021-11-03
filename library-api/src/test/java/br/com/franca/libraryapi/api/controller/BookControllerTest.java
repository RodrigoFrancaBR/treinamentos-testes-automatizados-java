package br.com.franca.libraryapi.api.controller;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.helper.mock.MockHelper;
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


    /**
     * 1 - Cenário, criar um Objeto para a requisição (dto e json) e
     * um para a resposta (dto de saida).
     * 2 - Definir a URI que será consumida.
     * 3 - Invocar o método desejado (post, get, etc..)
     * da classe MockMvcRequestBuilder (construtor de request) passando a URI,
     * (acho que simula um cliente http).
     * 4 - Add os cabeçalhos da requisição (contentType, acept etc..).
     * 5 - Informar o conteúdo da requisição (content(json)).
     * 6 - Fase de Execução do teste, invocar o método perform(request)
     *  passando a requisição que é fornecedida pelo construtor de request.
     * 7- Verificar se o id do DTO de resposta vem preenchido, uma vez que o dto de entrada não vai com id
     *  e o o controller não está preenchendo esse ID.
     * 8 - Rodar os testes e ver falhar, pois não existe a API de verdade.
     * 9 - Criar API de verdade com parãmetros de request(dto de entrada) e response (dto de saida)
     *  usar um mapper para converter dto para entidade e depois a entidade para dto novamente.
     *  retornar o dto baseado no cenário de teste acima.
     *  10 Rodar testes e ver falhar pois o dto de retorno é identico ao dto de entrada,
     *  e não possui um id preenchido.
     * 11 - Simular uma chamada do método save da classe de servico
     * que recebe como parametro qualquer coisa (não importa o argumento nesse momento)
     * mas retorne uma entidade com id preenchido (BDDMockito.given).
     * 12 - Rodar teste e ver falhar pois nao existe serviço de verdade
     * 13 - Criar uma interface com método save conforme cenário acima.
     * 15 - Injetar o servico no controller e delegar para o service a entidade ou o DTO
     * fazer o mapper do DTO na controlle ou no service...
     *
     */

    static String URI = "/api/books/";

    @Autowired
    MockMvc mockMvc; // injeta um objeto que sabe simular requisições.

    @MockBean
    BookService service;

    /**
     cria uma instancia mockada da interface BookService,
     caso não exista uma implementacao concreta dessa classe ou
     se vc não quiser executar o comportamento real dessa classe
     para simular chamadas dessa classe
     e injeta no contexto trazido pelo @ExtendWith(SpringExtension.class)
     */

    @Test
    @DisplayName("Should save book When it is valid")
    public void saveBookTest() throws Exception {

        // cenário
        BookDTO bookDtoIn = MockHelper.oneBookDtoIn();
        String json = new ObjectMapper().writeValueAsString(bookDtoIn);

        BookDTO saveBookDTO = MockHelper.oneBookDtoOut();
        String jsonResponse = new ObjectMapper().writeValueAsString(saveBookDTO);

        /**
         Para que a simulação do método funcione, passamos uma entidade sem id preenchido e
         esperamos outra entidade com id preenchido como resosta do método
         lembrando que o servico de verdade é apenas uma interface, sem uma implementação concreta
         que se chamado vai disparar null pointer, e por isso existe a necessidade de
         simular essa implementação e seu resultado conforme abaixo.
         esse comportamento vai ocorrer dentro do controller ou seja depois que a requisição chegar na controller
         */

        Book saveBook = MockHelper.oneBook();
        BDDMockito.given(service.save(ArgumentMatchers.any()))
                .willReturn(saveBook);

        // execução verificação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())

                .andExpect(MockMvcResultMatchers.jsonPath("id").value(saveBookDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(saveBookDTO.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(saveBookDTO.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(saveBookDTO.getIsbn()))

                // informações que vem do saveBookDTO
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("As aventuras"))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Artur"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value("123456"));
    }

    @Test
    @DisplayName("Should throw exception When save invalid book")
    public void saveInvalidBookTest(){
    }
}
