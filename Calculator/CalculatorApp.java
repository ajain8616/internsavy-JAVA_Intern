package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField inputField;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JPanel buttonPanel;
    private String currentInput;
    private String currentOperation;

    public CalculatorApp() {
        setTitle("Calculator App");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentInput = "";
        currentOperation = "";

        inputField = new JTextField(20);
        inputField.setEditable(false);
        inputField.setFont(new Font("Arial", Font.PLAIN, 20));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", ".", "=", "/",
            "C", "√", "x²", "ln"
        };

        numberButtons = new JButton[10];
        operationButtons = new JButton[buttonLabels.length - 10];

        for (int i = 0; i < buttonLabels.length; i++) {
            if (i < 10) {
                numberButtons[i] = new JButton(buttonLabels[i]);
                numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
                numberButtons[i].addActionListener(this);
                buttonPanel.add(numberButtons[i]);
            } else {
                operationButtons[i - 10] = new JButton(buttonLabels[i]);
                operationButtons[i - 10].setFont(new Font("Arial", Font.PLAIN, 18));
                operationButtons[i - 10].addActionListener(this);
                buttonPanel.add(operationButtons[i - 10]);
            }
        }

        add(inputField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
            currentInput += command;
            inputField.setText(currentInput);
        } else if (command.equals("C")) {
            currentInput = "";
            currentOperation = "";
            inputField.setText("");
        } else if (command.equals("=")) {
            BigDecimal result = calculateResult();
            inputField.setText(result.toString());
            currentInput = result.toString();
            currentOperation = "";
        } else if (command.equals("√")) {
            BigDecimal value = new BigDecimal(currentInput);
            BigDecimal result = value.sqrt(MathContext.DECIMAL32);
            inputField.setText(result.toString());
            currentInput = result.toString();
            currentOperation = "";
        } else if (command.equals("x²")) {
            BigDecimal value = new BigDecimal(currentInput);
            BigDecimal result = value.multiply(value);
            inputField.setText(result.toString());
            currentInput = result.toString();
            currentOperation = "";
        } else if (command.equals("ln")) {
            BigDecimal value = new BigDecimal(currentInput);
            BigDecimal result = new BigDecimal(Math.log(value.doubleValue()));
            inputField.setText(result.toString());
            currentInput = result.toString();
            currentOperation = "";
        } else {
            currentOperation = command;
            currentInput = "";
        }
    }

    private BigDecimal calculateResult() {
        BigDecimal operand1 = new BigDecimal(currentInput);
        BigDecimal operand2 = new BigDecimal(inputField.getText());

        switch (currentOperation) {
            case "+":
                return operand1.add(operand2);
            case "-":
                return operand1.subtract(operand2);
            case "*":
                return operand1.multiply(operand2);
            case "/":
                if (operand2.compareTo(BigDecimal.ZERO) != 0) {
                    return operand1.divide(operand2, MathContext.DECIMAL32);
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                    return BigDecimal.ZERO;
                }
            default:
                return BigDecimal.ZERO;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorApp calculator = new CalculatorApp();
            calculator.setVisible(true);
        });
    }
}
