package com.megaheart.calculator;

// The Model performs all the calculations needed
// and that is it. It doesn't know the View
// exists

/**
 * The CalculatorModel class represents the model of the calculator application.
 * It holds the value of the sum of the numbers entered in the view and performs
 * arithmetic operations on them.
 */
public class CalculatorModel {

    // Holds the value of the sum of the numbers
    // entered in the view

    private int calculationValue;

    /**
     * Adds two numbers and sets the result as the calculation value.
     * @param firstNumber the first number to be added
     * @param secondNumber the second number to be added
     */
    public void addTwoNumbers(int firstNumber, int secondNumber){
        calculationValue = firstNumber + secondNumber;
    }

    /**
     * Subtracts the second number from the first number and stores the result in the calculationValue field.
     * @param firstNumber the first number to be subtracted from
     * @param secondNumber the second number to subtract from the first number
     */
    public void subtractTwoNumbers(int firstNumber, int secondNumber){
        calculationValue = firstNumber - secondNumber;
    }

    /**
     * Multiplies two numbers and sets the result as the calculation value.
     * @param firstNumber the first number to be multiplied
     * @param secondNumber the second number to be multiplied
     */
    public void multiplyTwoNumbers(int firstNumber, int secondNumber){
        calculationValue = firstNumber * secondNumber;
    }

    /**
     * Divides two numbers and stores the result in the calculationValue field.
     * @param firstNumber the first number to be divided
     * @param secondNumber the second number to be divided
     */
    public void divideTwoNumbers(int firstNumber, int secondNumber){
        calculationValue = firstNumber / secondNumber;
    }

    /**
     * Calculates the modulo of two given integers.
     * 
     * @param firstNumber the first integer
     * @param secondNumber the second integer
     */
    public void modulusTwoNumbers(int firstNumber, int secondNumber){
        calculationValue = firstNumber % secondNumber;
    }

    /**
     * Returns the calculation value of the calculator model.
     *
     * @return the calculation value of the calculator model.
     */
    public int getCalculationValue(){
        return calculationValue;
    }

}
