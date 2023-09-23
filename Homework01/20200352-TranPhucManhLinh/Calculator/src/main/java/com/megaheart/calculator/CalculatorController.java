package com.megaheart.calculator;

public class CalculatorController {
    private CalculatorView theView;
    private CalculatorModel theModel;

    public CalculatorController(CalculatorView theView, CalculatorModel theModel) {
        this.theView = theView;
        this.theModel = theModel;

        this.theView.addClearListener(e -> {
            this.theView.clearAll();
        });
        this.theView.addAddListener(e -> {
            try {
                this.theModel.addTwoNumbers(this.theView.getFirstNumber(), this.theView.getSecondNumber());
                this.theView.setCalcSolution(this.theModel.getCalculationValue());
            } catch (NumberFormatException ex) {
                this.theView.displayErrorMessage("You need to enter 2 integers");
            }
        });
        this.theView.addSubtractListener(e -> {
            try {
                this.theModel.subtractTwoNumbers(this.theView.getFirstNumber(), this.theView.getSecondNumber());
                this.theView.setCalcSolution(this.theModel.getCalculationValue());
            } catch (NumberFormatException ex) {
                this.theView.displayErrorMessage("You need to enter 2 integers");
            }
        });
        this.theView.addMultiplyListener(e -> {
            try {
                this.theModel.multiplyTwoNumbers(this.theView.getFirstNumber(), this.theView.getSecondNumber());
                this.theView.setCalcSolution(this.theModel.getCalculationValue());
            } catch (NumberFormatException ex) {
                this.theView.displayErrorMessage("You need to enter 2 integers");
            }
        });
        this.theView.addDivideListener(e -> {
            try {
                this.theModel.divideTwoNumbers(this.theView.getFirstNumber(), this.theView.getSecondNumber());
                this.theView.setCalcSolution(this.theModel.getCalculationValue());
            } catch (NumberFormatException ex) {
                this.theView.displayErrorMessage("You need to enter 2 integers");
            }
        });
        this.theView.addModulusListener(e -> {
            try {
                this.theModel.modulusTwoNumbers(this.theView.getFirstNumber(), this.theView.getSecondNumber());
                this.theView.setCalcSolution(this.theModel.getCalculationValue());
            } catch (NumberFormatException ex) {
                this.theView.displayErrorMessage("You need to enter 2 integers");
            }
        });

    }

}
