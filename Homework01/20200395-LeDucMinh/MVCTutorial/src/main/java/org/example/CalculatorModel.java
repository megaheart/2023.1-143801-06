package org.example;

public class CalculatorModel {
    private int calculationValue;

    public CalculatorModel() {
    }

    public void addTwoNumbers(int firstNumber, int secondNumber) {
        calculationValue = firstNumber + secondNumber;
    }

    public int getCalculationValue() {
        return calculationValue;
    }
}
