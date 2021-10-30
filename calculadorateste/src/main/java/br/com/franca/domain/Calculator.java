package br.com.franca.domain;

public class Calculator {
    public int sum(int valor1, int valor2) {
        if (valor1 < 0 || valor2 < 0){
            throw new IllegalArgumentException("Valor informado deve ser positivo");
        }
        return valor1+valor2;
    }

    public int subtract(int valor1, int valor2) {
        if (valor1 < 0 || valor2 < 0){
            throw new IllegalArgumentException("Valor informado deve ser positivo");
        }
        return valor1-valor2;
    }

    public float multiplication(int valor1, int valor2) {
        if (valor1 < 0 || valor2 < 0){
            throw new IllegalArgumentException("Valor informado deve ser positivo");
        }
        return valor1*valor2;
    }

    public float division(int valor1, int valor2) {
        if (valor2 == 0){
            throw new ArithmeticException("Valor informado deve ser diferente de zero");
        }

        if (valor1 < 0 || valor2 < 0 ){
            throw new IllegalArgumentException("Valor informado deve ser positivo");
        }

       return valor1/valor2;
    }
}
