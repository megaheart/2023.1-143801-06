package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
    private CalculatorView theView;
    private CalculatorModel theModel;

    public CalculatorController(CalculatorView theView, CalculatorModel theModel){
        this.theView = theView;
        this.theModel = theModel;
        this.theView.addPlusListener(new CalculateListener("+"));
        this.theView.addMinusListener(new CalculateListener("-"));
        this.theView.addMultiplyListener(new CalculateListener("*"));
        this.theView.addDivideListener(new CalculateListener("/"));
        this.theView.addModuloListener(new CalculateListener("%"));
        this.theView.addClearListener(new ClearListener());
    }

    class CalculateListener implements ActionListener {
        private String operation;
        CalculateListener(String operation){
            this.operation = operation;
        }

        public String getOperation(String operation){
            return this.operation;
        }


        public void actionPerformed(ActionEvent e){
            int firstNumber, secondNumber;

            try{
                firstNumber = theView.getFirstNumber();
                secondNumber = theView.getSecondNumber();

                // Perform the operation
                if (this.operation.equals("+"))
                    theModel.addTwoNumbers(firstNumber, secondNumber);
                else if (this.operation.equals("-"))
                    theModel.subtractTwoNumbers(firstNumber, secondNumber);
                else if (this.operation.equals("*"))
                    theModel.multiplyTwoNumbers(firstNumber, secondNumber);
                else if (this.operation.equals("/"))
                    theModel.divideTwoNumbers(firstNumber, secondNumber);
                else if (this.operation.equals("%"))
                    theModel.moduloTwoNumbers(firstNumber, secondNumber);

                // Update the view with the result
                theView.setCalcSolution(theModel.getCalculationValue());
            }catch(NumberFormatException ex){
                System.out.println(ex);
                theView.displayErrorMessage("You Need to Enter 2 Integers");
            }catch (ArithmeticException ex){
                System.out.println(ex);
                theView.displayErrorMessage("You Can't Divide by Zero");
            }
        }
    }

    class ClearListener implements ActionListener {
        ClearListener(){
        }
        public void actionPerformed(ActionEvent e){
            theView.clear();
        }
    }
}
