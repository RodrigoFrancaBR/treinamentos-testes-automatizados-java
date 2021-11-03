package br.com.franca.libraryapi.service;

import br.com.franca.libraryapi.api.dto.BookDTO;
import br.com.franca.libraryapi.helper.mock.MockHelper;
import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Profile("test")
public class BookServiceTest {

    // @MockBean // apenas se eu quiser simular o serviço.
    BookService service;


    @BeforeEach
    public void setup(){
        // aqui estou fazendo questão de executar os metodos de verdade da classe de serviço.
        // diferente de quando usamos @MockBean.. que simulam os resultados dos métodos da classe de servico
    //service = new BookServiceImpl();
    }

//    private BookService service;

    @DisplayName("Should save book when it is valid")
    @Test
    public void saveBookTest(){

        // cenário
        Book book = MockHelper.oneBook();

        // execução
        Book saveBook = service.save(book);

        // verificação
        assertThat(saveBook).isNotNull();
        assertThat(saveBook.getId()).isNotNull();
    }
}
