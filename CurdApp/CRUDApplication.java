package CurdApp;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDApplication extends JFrame {
    private List<Person> personList = new ArrayList<>();
    private JTextField idField, nameField, ageField, phoneField, emailField, addressField;
    private JComboBox<String> professionalComboBox;
    private JTextArea outputArea;

    private int idCounter = 1; // Counter for generating unique IDs

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CRUDApplication app = new CRUDApplication();
            app.setSize(600, 400); // Adjusted size for the new fields
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        });
    }

    public CRUDApplication() {
        super("CRUD Application");

        // Initialize UI components
        idField = new JTextField(10);
        nameField = new JTextField(20);
        ageField = new JTextField(5);
        phoneField = new JTextField(15);
        emailField = new JTextField(30);
        addressField = new JTextField(30);

        String[] professions = {"Choose Your Profession","Student", "Engineer", "Doctor", "Teacher", "Artist", "Other"};
        professionalComboBox = new JComboBox<>(professions);

        outputArea = new JTextArea(10, 50); // Adjusted size for the new fields
        outputArea.setEditable(false);

        // Create buttons
        JButton createButton = new JButton("Create");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(ageField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(phoneField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(addressField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Profession:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(professionalComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        createButton.addActionListener(e -> createPerson());
        readButton.addActionListener(e -> readPersons());
        updateButton.addActionListener(e -> updatePerson());
        deleteButton.addActionListener(e -> deletePerson());
         AbstractDocument phoneDoc = (AbstractDocument) phoneField.getDocument();
        phoneDoc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        // Age Field Input Validation
        AbstractDocument ageDoc = (AbstractDocument) ageField.getDocument();
        ageDoc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+") && isValidAge(fb.getDocument().getText(0, offset) + string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+") && isValidAge(fb.getDocument().getText(0, offset) + text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidAge(String ageText) {
                try {
                    int age = Integer.parseInt(ageText);
                    return age >= 0 && age <= 150; // Set a reasonable age range
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
    }
    private void createPerson() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String profession = (String) professionalComboBox.getSelectedItem();
    
           // Check for duplicate values based on all fields
for (Person person : personList) {
    if (person.getName().equals(name) && 
        person.getAge() == age && 
        person.getPhone().equals(phone) && 
        person.getEmail().equals(email) && 
        person.getAddress().equals(address) && 
        person.getProfession().equals(profession)) {
        JOptionPane.showMessageDialog(this, "Duplicate value not allowed.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
        return;
    }
}

             Person person = new Person(idCounter++, name, age, phone, email, address, profession);
            personList.add(person);
    
            // Set the generated ID in the idField
            idField.setText(String.valueOf(person.getId()));
    
            outputArea.setText("Person created: " + person.toString() + "\n");
    
            // Display all persons' details
            readPersons();
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter valid values for Age.");
        }
    }
    
    private void readPersons() {
        outputArea.setText("Person List:\n");
        for (Person person : personList) {
            outputArea.append(person.toString() + "\n");
        }
    }

    private void updatePerson() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String profession = (String) professionalComboBox.getSelectedItem();

            for (Person person : personList) {
                if (person.getId() == id) {
                    person.setName(name);
                    person.setAge(age);
                    person.setPhone(phone);
                    person.setEmail(email);
                    person.setAddress(address);
                    person.setProfession(profession);

                    outputArea.setText("Person updated: " + person.toString());
                    return;
                }
            }

            outputArea.setText("Person with ID " + id + " not found.");
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter valid values for ID and Age.");
        }
    }

    private void deletePerson() {
        try {
            int id = Integer.parseInt(idField.getText());

            for (Person person : personList) {
                if (person.getId() == id) {
                    personList.remove(person);
                    outputArea.setText("Person deleted: " + person.toString());
                    return;
                }
            }

            outputArea.setText("Person with ID " + id + " not found.");
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid input. Please enter a valid ID.");
        }
    }

    class Person {
        private int id;
        private String name;
        private int age;
        private String phone;
        private String email;
        private String address;
        private String profession;

        public Person(int id, String name, int age, String phone, String email, String address, String profession) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.phone = phone;
            this.email = email;
            this.address = address;
            this.profession = profession;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Age: " + age +
                    ", Phone: " + phone + ", Email: " + email +
                    ", Address: " + address + ", Profession: " + profession;
        }
    }
}
