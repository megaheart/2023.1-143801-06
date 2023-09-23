package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {
    private JLabel firstNumberLabel = new JLabel("First Number", JLabel.LEFT);
    private JTextField firstNumber = new JTextField(1);
    private JLabel secondNumberLabel = new JLabel("Second Number", JLabel.LEFT);
    private JTextField secondNumber = new JTextField(1);
    private JLabel resultLabel = new JLabel("Result", JLabel.LEFT);
    private JTextField result = new JTextField(1);
    private JButton plusButton = new JButton("+");
    private JButton minusButton = new JButton("-");
    private JButton multiplyButton = new JButton("*");
    private JButton divideButton = new JButton("/");
    private JButton moduloButton = new JButton("%");
    private JButton clearButton = new JButton("Clear");

    CalculatorView(){
        // Sets up the view and adds the components
        Container cp = getContentPane();
        result.setEditable(false);

        // Add components to the panel
        cp.add(firstNumberLabel);
        cp.add(firstNumber);
        cp.add(secondNumberLabel);
        cp.add(secondNumber);
        cp.add(resultLabel);
        cp.add(result);
        cp.add(plusButton);
        cp.add(minusButton);
        cp.add(multiplyButton);
        cp.add(divideButton);
        cp.add(moduloButton);
        cp.add(clearButton);

        cp.setLayout(new GridLayout(6, 2, 10, 5));
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Arithmetics");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // End of setting up the components --------

    }
    public int getFirstNumber(){

        return Integer.parseInt(firstNumber.getText());

    }

    public int getSecondNumber(){

        return Integer.parseInt(secondNumber.getText());

    }

    public void setCalcSolution(int solution){

        result.setText(Integer.toString(solution));
    }

    // If the calculateButton is clicked execute a method
    // in the Controller named actionPerformed

    void addPlusListener(ActionListener listenForCalcButton){
        plusButton.addActionListener(listenForCalcButton);
    }

    void addMinusListener(ActionListener listenForCalcButton){
        minusButton.addActionListener(listenForCalcButton);
    }

    void addMultiplyListener(ActionListener listenForCalcButton){
        multiplyButton.addActionListener(listenForCalcButton);
    }

    void addDivideListener(ActionListener listenForCalcButton){
        divideButton.addActionListener(listenForCalcButton);
    }

    void addModuloListener(ActionListener listenForCalcButton){
        moduloButton.addActionListener(listenForCalcButton);
    }

    void addClearListener(ActionListener listenForClearButton){
        clearButton.addActionListener(listenForClearButton);
    }

    // Open a popup that contains the error message passed
    void displayErrorMessage(String errorMessage){

        JOptionPane.showMessageDialog(this, errorMessage);

    }

    void clear(){
        firstNumber.setText("");
        secondNumber.setText("");
        result.setText("");
    }
}
