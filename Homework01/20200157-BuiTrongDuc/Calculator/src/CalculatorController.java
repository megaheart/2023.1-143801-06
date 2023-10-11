import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CalculatorController {

    private CalculatorView theView;
    private CalculatorModel theModel;

    public CalculatorController(CalculatorView theView, CalculatorModel theModel) {
        this.theView = theView;
        this.theModel = theModel;


        this.theView.addCalculateListener(new CalculateListener());
        this.theView.addSubtractListener(new SubtractListener());
        this.theView.addMultiplyListener(new MultiplyListener());
        this.theView.addDivideListener(new DivideListener());
        this.theView.addModuloListener(new ModuloListener());
        this.theView.addClearListener(new ClearListener());

    }

    class CalculateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            performOperation((first, second) -> theModel.addTwoNumbers(first, second));
        }
    }

    class SubtractListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            performOperation((first, second) -> theModel.subtractTwoNumbers(first, second));
        }
    }

    class MultiplyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            performOperation((first, second) -> theModel.multiplyTwoNumbers(first, second));
        }
    }

    class DivideListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            performOperation((first, second) -> theModel.divideTwoNumbers(first, second));
        }
    }

    class ModuloListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            performOperation((first, second) -> theModel.moduloTwoNumbers(first, second));
        }
    }

    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            theView.setClearSolution(theModel.clear());
            theView.setFirstNumber(theModel.clear());
            theView.setSecondNumberNumber(theModel.clear());
        }
    }

    private void performOperation(Operation operation) {
        try {
            int firstNumber = theView.getFirstNumber();
            int secondNumber = theView.getSecondNumber();
            operation.execute(firstNumber, secondNumber);
            theView.setCalcSolution(theModel.getCalculationValue());
        } catch (NumberFormatException ex) {
            theView.displayErrorMessage("Enter 2 Integers");
        }
    }

    interface Operation {
        void execute(int first, int second);
    }
}
