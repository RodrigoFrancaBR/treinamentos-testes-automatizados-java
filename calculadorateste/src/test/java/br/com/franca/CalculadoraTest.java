package br.com.franca;

import br.com.franca.domain.Calculator;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {
    private Calculator calculator;

    // roda sempre antes de cada método
    @Before
    public void setup(){
        calculator = new Calculator();
    }

    @Test
    public void sum_shouldReturnSum_whenTwoPositiveNumbersAreEntered(){
        // cenário
        int valor1 = 2, valor2 = 3;
        // execução
        int result = calculator.sum(valor1,valor2);
        // verificação
        Assertions.assertThat(result).isEqualTo(valor1+valor2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionSum_shouldThrowException_whenANegativeNumberIsEntered(){
        // cenario
        int valor1 =-1, valor2 = 1;
        // execução
        calculator.sum(valor1, valor2);
        // verificação
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionSum_shouldThrowException_whenTwoNegativeNumberIsEntered(){
        // cenario
        int valor1 =-1, valor2 = -1;
        // execução
        calculator.sum(valor1, valor2);
        // verificação
    }

    @Test
    public void subtract_shouldReturnSubtract_whenTwoPositiveNumbersAreEntered(){
        // cenario
        int valor1=2, valor2=2;
        // execução
        int result = calculator.subtract(valor1, valor2);
        // verificação
        Assertions.assertThat(result).isEqualTo(valor1-valor2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionSubtract_shouldThrowException_whenAnNegativeNumberEntered(){
        // cenário
        int valor1 = -1, valor2 = 1;
        // execução
        calculator.subtract(valor1, valor2);
        // verificação
    }

    @Test
    public void multiply_shouldReturnMultiplication_whenTwoNumbersEntered(){
        // cenário
        int valor1 = 2, valor2 =2;
        // execução
        float result = calculator.multiplication(valor1, valor2);
        // verificação
        Assertions.assertThat(result).isEqualTo(valor1*valor2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionMultiply_shouldThrowException_whenAnNegativeNumberEntered(){
        // cenário
        int valor1 =-1, valor2 =1;
        // execução
        calculator.multiplication(valor1, valor2);
        // verificação
    }

    @Test
    public void division_shouldReturnDivision_whenTwoPositiveNumbersEntered(){
        // cenário
        int valor1 = 3, valor2 = 2;
        // execução
        float result = calculator.division(valor1, valor2);
        // verificação
        Assertions.assertThat(result).isEqualTo(valor1/valor2);
    }

    @Test(expected = ArithmeticException.class)
    public void exceptionDivision_shouldThrowException_whenAnNumberDivisionByZero(){
        // cenário
        int valor1 =2, valor2 =0;
        // execução
        calculator.division(valor1, valor2);
        // verificação
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionDivision_whenAnNegativeNumbersEntered(){
        // cenário
        int valor1=-1, valor2 =2;
        // execução
        calculator.division(valor1, valor2);
        // verificação
    }

}
