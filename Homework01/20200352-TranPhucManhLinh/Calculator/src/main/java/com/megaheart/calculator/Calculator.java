package com.megaheart.calculator;


/**
 *
 * @author linh2
 */
public class Calculator {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        CalculatorModel calculatorModel = new CalculatorModel();
        CalculatorView calculatorView = new CalculatorView();
        CalculatorController calculatorController = new CalculatorController(calculatorView, calculatorModel);

        calculatorView.setVisible(true);
    }
}
