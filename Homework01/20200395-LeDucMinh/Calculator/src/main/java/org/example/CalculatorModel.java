package org.example;

public class CalculatorModel {
    private int calculationValue;

    public CalculatorModel() {
    }

    public void addTwoNumbers(int firstNumber, int secondNumber) {

        calculationValue = firstNumber + secondNumber;
    }

    public void subtractTwoNumbers(int firstNumber, int secondNumber) {
        calculationValue = firstNumber - secondNumber;
    }

    public void multiplyTwoNumbers(int firstNumber, int secondNumber) {
        calculationValue = firstNumber * secondNumber;
    }

    public void divideTwoNumbers(int firstNumber, int secondNumber) {
        calculationValue = firstNumber / secondNumber;
    }

    public void moduloTwoNumbers(int firstNumber, int secondNumber) {
        calculationValue = firstNumber % secondNumber;
    }

    public int getCalculationValue() {
        return calculationValue;
    }
}
