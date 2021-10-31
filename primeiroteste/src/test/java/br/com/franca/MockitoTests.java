package br.com.franca;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MockitoTests {

    @Test
    public void getSize_shouldReturnZero_whenListIsEmpty(){
        // cenário
        List<String> myList = Mockito.mock(List.class);
        // Mockito.when(myList.size())
//                .thenReturn(20);
        // execução
        int size = myList.size();
        Assertions.assertThat(size).isEqualTo(0);
        // verificação
    }

    @Test
    public void getSize_shouldReturnValueGreaterThanZero_whenListIsNotEmpty(){
        // cenário
        /** Para garantir que o resultado do size seja maior que zero
         preciso criar uma lista e add 1 elemento qualquer na lista */
        List<String> myList = new ArrayList<>();
        myList.add("");

        // execução
        /** Executando o método size*/
        int size = myList.size();

        // verificação
        /** Testando se o retorno do size deu maior que zero*/
        Assertions.assertThat(size).isGreaterThan(0);
    }

    /** Quando usamos mockito podemos manipular a chamada do método
        Se eu posso controlar o retorno do size,
        Não preciso fazer a chamada para o add */

    @Test
    public void getSizeUsingMockito_shouldReturnValueGreaterThanZero_whenListIsNotEmpty(){
        // cenário
        /** Para garantir que o resultado do size seja maior que zero
         preciso criar uma lista e add algum valor na lista */
        List<String> myList = Mockito.mock(List.class);

        /** Manipulando o método size, para retornar 1 */
        Mockito.when(myList.size()).thenReturn(1);

        /** Quando o metodo size for chamado ele vai retornar 1*/
        int size = myList.size();

        // verificação
        /** Testando se o retorno do size é maior que zero, igual a 1 etc..*/
        Assertions.assertThat(size)
                .isGreaterThan(0)
                // .isEqualTo(1)
        ;
    }
}
