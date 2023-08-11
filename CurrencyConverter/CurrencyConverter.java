package CurrencyConverter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> fromCountryComboBox;
    private JComboBox<String> toCountryComboBox;
    private JTextField amountField;
    private JTextField resultField;

    private String[] currencyUnits = {
        "Select One..",
        "Indian Rupee (₹)",
        "Pakistani Rupee (Rs.)",
        "US Dollar ($)",
        "Canadian Dollar ($)",
        "Kenyan Shilling (KSh)",
        "Nigerian Naira (₦)",
        "Brazilian Real (R$)",
        "Indonesian Rupiah (Rp)",
        "Philippine Pisco (₱)"
    };

    public CurrencyConverter() {
        super("Currency Converter");

        // Create the From and To labels and dropdowns
        JLabel fromLabel = new JLabel("From:");
        fromCountryComboBox = new JComboBox<>(currencyUnits);

        JLabel toLabel = new JLabel("To:");
        toCountryComboBox = new JComboBox<>(currencyUnits);

        // Create the Amount label and text field
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);

        // Create the Result label and text field
        JLabel resultLabel = new JLabel("Result:");
        resultField = new JTextField(29);
        resultField.setEditable(false);

        // Create the Convert button
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Create the layout using GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(fromLabel, gbc);
        gbc.gridx = 1;
        add(fromCountryComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(toLabel, gbc);
        gbc.gridx = 1;
        add(toCountryComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(amountLabel, gbc);
        gbc.gridx = 1;
        add(amountField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(resultLabel, gbc);
        gbc.gridx = 1;
        add(resultField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(convertButton, gbc);

        // Set the default close operation and pack the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void convertCurrency() {
        try {
            // Get the input amount and selected units
            double amount = Double.parseDouble(amountField.getText());
            int fromIndex = fromCountryComboBox.getSelectedIndex();
            int toIndex = toCountryComboBox.getSelectedIndex();

            // Perform currency conversion based on selected units
            double convertedAmount = convert(amount, fromIndex, toIndex);
            String fromCurrency = currencyUnits[fromIndex];
            String toCurrency = currencyUnits[toIndex];

            // Set the converted amount and currency names/symbols in the result field
            String convertedResult = String.format("%.2f %s = %.2f %s",
                amount, fromCurrency, convertedAmount, toCurrency);
            resultField.setText(convertedResult);
        } catch (NumberFormatException e) {
            // Show an error message if the input is not a valid number
            JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double convert(double amount, int fromIndex, int toIndex) {
        // Define conversion rates here for different currencies
        double[] conversionRates = {
            0.0, // Placeholder for "Select One.."
            95.21,  // Indian Rupee
            162.74, // Pakistani Rupee
            1.31,   // US Dollar
            1.71,   // Canadian Dollar
            132.53, // Kenyan Shilling
            476.57, // Nigerian Naira
            5.47,   // Brazilian Real
            19554.94, // Indonesian Rupiah
            71.17   // Philippine Pisco
        };

        double fromRate = conversionRates[fromIndex];
        double toRate = conversionRates[toIndex];

        return amount * (toRate / fromRate);
    }

    public static void main(String[] args) {
        // Create and show the currency converter window
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CurrencyConverter().setVisible(true);
            }
        });
    }
}
