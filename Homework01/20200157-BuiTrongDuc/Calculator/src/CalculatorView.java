// This is the View
// Its only job is to display what the user sees
// It performs no calculations, but instead passes
// information entered by the user to whomever needs
// it. 

import java.awt.event.ActionListener;
import java.awt.Insets;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class CalculatorView extends JFrame {

    private JTextField firstNumber = new JTextField(10);
    // private JLabel additionLabel = new JLabel("+");
    private JTextField secondNumber = new JTextField(10);
    private JButton calculateButton = new JButton("+");
    private JTextField calcSolution = new JTextField(10);
    private JButton addButton = new JButton("+");
    private JButton subtractButton = new JButton("-");
    private JButton multiplyButton = new JButton("*");
    private JButton divideButton = new JButton("/");
    private JButton moduloButton = new JButton("%");
    private JButton clearButton = new JButton("Clear");

    CalculatorView() {
        this.setTitle("Calculator");

        setLayout(new GridBagLayout());
        GridBagConstraints calcPanel = new GridBagConstraints();
        calcPanel.gridwidth = 1;
        calcPanel.fill = GridBagConstraints.HORIZONTAL;
        calcPanel.insets = new Insets(5, 5, 5, 5); // Padding

        // Row 1
        calcPanel.gridx = 0;
        calcPanel.gridy = 0;
        add(new JLabel("First number:"), calcPanel);
        calcPanel.gridx = 1;
        calcPanel.gridy = 0;
        add(firstNumber, calcPanel);

        // Row 2
        calcPanel.gridx = 0;
        calcPanel.gridy = 1;
        add(new JLabel("Second number:"), calcPanel);
        calcPanel.gridx = 1;
        calcPanel.gridy = 1;
        add(secondNumber, calcPanel);

        // Row 3
        calcPanel.gridx = 0;
        calcPanel.gridy = 2;
        add(new JLabel("Result:"), calcPanel);
        calcPanel.gridx = 1;
        calcPanel.gridy = 2;
        add(calcSolution, calcPanel);

        // Row 4
        calcPanel.gridx = 0;
        calcPanel.gridy = 3;
        add(calculateButton, calcPanel);
        calcPanel.gridx = 1;
        calcPanel.gridy = 3;
        add(subtractButton, calcPanel);

        // Row 5
        calcPanel.gridx = 0;
        calcPanel.gridy = 4;
        add(multiplyButton, calcPanel);
        calcPanel.gridx = 1;
        calcPanel.gridy = 4;
        add(divideButton, calcPanel);

        // Row 6
        calcPanel.gridx = 0;
        calcPanel.gridy = 5;
        add(moduloButton, calcPanel);
        calcPanel.gridx = 1;
        calcPanel.gridy = 5;
        add(clearButton, calcPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(600, 200);

        // End of setting up the components --------

    }

    public int getFirstNumber() {

        return Integer.parseInt(firstNumber.getText());

    }

    public int getSecondNumber() {

        return Integer.parseInt(secondNumber.getText());

    }

    public int getCalcSolution() {

        return Integer.parseInt(calcSolution.getText());

    }

    public void setCalcSolution(int solution) {

        calcSolution.setText(Integer.toString(solution));

    }
    public void setFirstNumber(String name){
        firstNumber.setText(name);
    }
    public void setSecondNumberNumber(String name){
        secondNumber.setText(name);
    }
    public void setClearSolution(String name){
        calcSolution.setText(name);
    }
    void addCalculateListener(ActionListener listenForCalcButton) {

        calculateButton.addActionListener(listenForCalcButton);

    }
    void addSubtractListener(ActionListener listenForSubtractButton) {
        subtractButton.addActionListener(listenForSubtractButton);
    }

    void addMultiplyListener(ActionListener listenForMultiplyButton) {
        multiplyButton.addActionListener(listenForMultiplyButton);
    }

    void addDivideListener(ActionListener listenForDivideButton) {
        divideButton.addActionListener(listenForDivideButton);
    }

    void addModuloListener(ActionListener listenForModuloButton) {
        moduloButton.addActionListener(listenForModuloButton);
    }

    void addClearListener(ActionListener listenForClearButton) {
        clearButton.addActionListener(listenForClearButton);
    }

    void displayErrorMessage(String errorMessage) {

        JOptionPane.showMessageDialog(this, errorMessage);

    }

}