package br.com.franca;

import org.junit.Assert;
import org.junit.Test;

public class PrimeiroTeste {

    @Test
    public void estruturaDeUmTeste(){

        /**
         * cenário
         * Testar o resultado da soma de 2 valores
         * */

        int valor1 = 5 , valor2 = 10;

        // exeção
        int resultado = valor1+valor2;

        // verificação
        Assert.assertEquals(15,resultado);
    }

    @Test
    public void estruturaDeUmTeste1(){

        /**
         * cenário
         * Testar o resultado da soma de 2 valores
         * */

        int valor1 = 5 , valor2 = 10;

        // exeção
        int resultado = valor1+valor2;

        // verificação
        Assert.assertEquals(valor1+valor2,resultado);
    }
}
