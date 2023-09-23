package org.example;


public class Calculator {

    public static void main(String[] args) {
        CalculatorModel calculatorModel = new CalculatorModel();
        CalculatorView calculatorView = new CalculatorView();
        CalculatorController calculatorController = new CalculatorController(calculatorView, calculatorModel);

        calculatorView.setVisible(true);
    }
}
