package org.example;

import javax.swing.*;

public class SwingCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CalculatorView theView = new CalculatorView();
                CalculatorModel theModel = new CalculatorModel();
                CalculatorController theController = new CalculatorController(theView, theModel);
            }
        });
    }
}