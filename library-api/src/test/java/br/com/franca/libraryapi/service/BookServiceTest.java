package br.com.franca.libraryapi.service;

import br.com.franca.libraryapi.helper.mock.MockHelper;
import br.com.franca.libraryapi.model.entity.Book;
import br.com.franca.libraryapi.repository.BookRepository;
import br.com.franca.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@Profile("test")
public class BookServiceTest {

    /**
     * 1 - Cenário, criar dois objetos, um que vai representar a entidade de negocio que o método
     * vai receber e outro que vai representar uma entidade do banco de dados que ele vai devolver.
     * 2 - instanciar a classe de servico  antes da chamada de cada metodo de teste (@BeforeEach).
     * 3 - Invocar o método save da classe de servico passando a entidade de negócio.
     * 4 - Verificar se o objeto de retorno é igual ao objeto simulado.
     * 5 - Rodas os testes e ver falhar pois nao existe classe concreta de servico.
     * 6 - Criar classe concreta para implementar a interface de servico.
     * 7 - Rodar testes e ver que vai falhar uma vez que ainda não estamos retornando  nenhuma entidade,
     * do banco de dados.
     * 8 - Simular uma chamada ao banco de dados (repository) (Mockito.when).
     * 9 - Rodar testes e ver falhar pois nao existe a interface repository.
     * 10 - Criar interface Repository e injetar na camada de servico e fazer uso do repository.
     * 11 - Rodar teste e ver passar
     */

    // @MockBean // apenas se eu quiser simular o serviço.
    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setup(){
        // aqui estou fazendo questão de executar os metodos de verdade da classe de serviço.
        // diferente de quando usamos @MockBean.. que simula os resultados dos métodos da classe de servico
    service = new BookServiceImpl(repository);
    }

    @DisplayName("Should save book when it is valid")
    @Test
    public void saveBookTest(){

        // cenário
        Book bookIn = MockHelper.oneBook();
        Book saveBookDataBase = MockHelper.oneBook();
        Mockito.when(repository.save(any())).thenReturn(saveBookDataBase);
        // execução
        Book saveBook = service.save(bookIn);

        // verificação
        assertThat(saveBook).isEqualTo(saveBookDataBase);
    }
}
