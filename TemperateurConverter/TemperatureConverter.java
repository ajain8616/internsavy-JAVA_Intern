package TemperateurConverter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TemperatureConverter extends JFrame {
    private JTextField temperatureField;
    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;
    private JTextField resultField;

    private String[] temperatureTypes = {
        "Choose the Temperature",
        "Celsius (°C)",
        "Fahrenheit (°F)",
        "Kelvin (°K)",
        "Rankine (°R or °Ra)"
    };

    public TemperatureConverter() {
        super("Temperature Converter");

        // Create the Temperature label and text field
        JLabel temperatureLabel = new JLabel("Temperature:");
        temperatureField = new JTextField(10);

        // Create the From and To labels and dropdowns
        JLabel fromLabel = new JLabel("From:");
        fromComboBox = new JComboBox<>(temperatureTypes);

        JLabel toLabel = new JLabel("To:");
        toComboBox = new JComboBox<>(temperatureTypes);

        // Create the Result label and text field
        JLabel resultLabel = new JLabel("Result:");
        resultField = new JTextField(10);
        resultField.setEditable(false);

        // Create the Convert button
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });

        // Create the layout using GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(temperatureLabel, gbc);
        gbc.gridx = 1;
        add(temperatureField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(fromLabel, gbc);
        gbc.gridx = 1;
        add(fromComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(toLabel, gbc);
        gbc.gridx = 1;
        add(toComboBox, gbc);
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

    private void convertTemperature() {
        try {
            // Get the input temperature and selected units
            double temperature = Double.parseDouble(temperatureField.getText());
            String fromUnit = (String) fromComboBox.getSelectedItem();
            String toUnit = (String) toComboBox.getSelectedItem();

            // Perform temperature conversion based on selected units
            double convertedTemperature = convert(temperature, fromUnit, toUnit);

            // Set the converted temperature in the result field along with the symbol
            resultField.setText(String.format("%.2f %s", convertedTemperature, getTemperatureSymbol(toUnit)));
        } catch (NumberFormatException e) {
            // Show an error message if the input is not a valid number
            JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double convert(double value, String fromUnit, String toUnit) {
        // Define conversion formulas here for different temperature units
        if (fromUnit.equals("Celsius (°C)")) {
            if (toUnit.equals("Fahrenheit (°F)")) {
                return (value * 9 / 5) + 32;
            } else if (toUnit.equals("Kelvin (°K)")) {
                return value + 273.15;
            } else if (toUnit.equals("Rankine (°R or °Ra)")) {
                return (value + 273.15) * 9 / 5;
            }
        } else if (fromUnit.equals("Fahrenheit (°F)")) {
            if (toUnit.equals("Celsius (°C)")) {
                return (value - 32) * 5 / 9;
            } else if (toUnit.equals("Kelvin (°K)")) {
                return (value + 459.67) * 5 / 9;
            } else if (toUnit.equals("Rankine (°R or °Ra)")) {
                return value + 459.67;
            }
        }
        
        // Implement other conversion cases similarly

        // If no conversion applies, return the same value
        return value;
    }
    
    private String getTemperatureSymbol(String unit) {
        if (unit.contains("Celsius")) {
            return "°C";
        } else if (unit.contains("Fahrenheit")) {
            return "°F";
        } else if (unit.contains("Kelvin")) {
            return "°K";
        } else if (unit.contains("Rankine")) {
            return "°R or °Ra";
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        // Create and show the temperature converter window
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TemperatureConverter().setVisible(true);
            }
        });
    }
}
