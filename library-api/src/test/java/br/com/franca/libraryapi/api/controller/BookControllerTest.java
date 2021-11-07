package br.com.franca.libraryapi.api.controller;

import static  org.hamcrest.Matchers.hasSize ;
import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.exception.BusinessException;
import br.com.franca.libraryapi.helper.mock.MockHelper;
import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
     *  não possui um id preenchido.
     * 11 - Simular uma chamada do método save da classe de servico
     * que recebe como parametro qualquer coisa (não importa o argumento nesse momento)
     * mas retorne uma entidade com id preenchido (BDDMockito.given).
     * 12 - Rodar teste e ver falhar pois nao existe serviço de verdade
     * 13 - Criar uma interface com método save conforme cenário acima.
     * 15 - Injetar o servico no controller e delegar para o service a entidade ou o DTO
     * fazer o mapper do DTO na controlle ou no service...
     * 16 - Rodar teste e ver passar.
     */

    static String URI = "/api/books/";

    @Autowired
    MockMvc mockMvc; // injeta um objeto que sabe simular requisições.

    @MockBean
    BookService service;

    @MockBean
    ModelMapper mapper;

    @Test
    @DisplayName("Should save book When it is valid")
    public void saveBookTest() throws Exception {

        BookDTO bookDTO = MockHelper.oneBookDTO();
        String json = new ObjectMapper().writeValueAsString(bookDTO);

        Book book = MockHelper.oneBook();
        given(mapper.map(bookDTO, Book.class)).willReturn(book);

        Book saveBook = MockHelper.oneBook();
        saveBook.setId(1L);
        given(service.save(Mockito.any(Book.class)))
                .willReturn(saveBook);

        BookDTO saveBookDTO = MockHelper.oneBookDTO();
        saveBookDTO.setId(1L);

        given(mapper.map(saveBook, BookDTO.class))
                .willReturn(saveBookDTO);
        String jsonResponse = new ObjectMapper().writeValueAsString(saveBookDTO);

        MockHttpServletRequestBuilder request = post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        InOrder inOrder = inOrder(mapper, service);
        inOrder.verify(mapper).map(any(), any());
        inOrder.verify(service).save(any());
        inOrder.verify(mapper).map(any(), any());

        Mockito.verify(mapper, times(2)).map(any(),any());
        Mockito.verify(service, times(1)).save(any());
    }

    /**
     fazer uma requisição passando um dto vazio
     validar se retorna 404
     rodar teste e ver falhar por conta do mapper, não vai ser possível
     converter obj dto null para obj entidade
     implemar a expecificação bean validations no DTO
     (pom) anotaçoes no DTO e @valid no parâmetro do método da controller
     rodar teste e ver passar.
     validar se está retornando além do status 404 um erro customizado
     criar um centralizador de exception para capturar todas as exceptios
     e retornar um erro customizado
     *
     */

    @Test
    @DisplayName("Should throw exception When save invalid book")
    public void saveInvalidBookTest() throws Exception {

        BookDTO bookDTO = new BookDTO();
        String jsonRequest = new ObjectMapper().writeValueAsString(bookDTO);

        // esse teste não precisa simular o mapper nem o service
        // pois a requisição será interceptada (RestControllerAdvice)
        // pq não está passando os parametros obrigatorios no request.

        MockHttpServletResponse response = mockMvc.perform(post(URI)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("errors");
        Mockito.verify(mapper, never()).map(any(),any());
        Mockito.verify(service, never()).save(any());

    // Assertions.assertThat(response.getContentAsString()).isEqualTo("{\"errors\":[\"must not be empty\",\"must not be empty\",\"must not be empty\"]}");
    }

    // depois de simular um erro de negócio é hora de criar esse cenário na camada de servico
    @DisplayName("should throw exception when isbn in use by another book")
    @Test
    public void saveBookWithDuplicatedIsbn () throws Exception {
        BookDTO bookDTO = MockHelper.oneBookDTO();
        bookDTO.setIsbn("123");
        String jsonRequest = new ObjectMapper().writeValueAsString(bookDTO);

        Book book = MockHelper.oneBook();
        book.setIsbn("123");
        given(mapper.map(bookDTO, Book.class)).willReturn(book);

        String errorMenssage = "isbn already registered";
        given(service.save(Mockito.any(Book.class))).willThrow(new BusinessException(errorMenssage));

        MockHttpServletRequestBuilder request = post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize (1)))
                .andExpect(jsonPath("errors[0]").value(errorMenssage));

        InOrder inOrder = inOrder(mapper, service);
        inOrder.verify(mapper).map(any(), any());
        inOrder.verify(service).save(any());

        Mockito.verify(mapper, times(1)).map(any(),any());
        Mockito.verify(service, times(1)).save(any());

    }

}
