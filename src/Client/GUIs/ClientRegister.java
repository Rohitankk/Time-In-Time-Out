package Client.GUIs;

import javax.swing.*;

public class ClientRegister extends JFrame{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel bottomPanel;
    private JLabel icon;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel passwordLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel usernameLabel;
    private JPasswordField confirmPasswordTF;
    private JLabel confirmPasswordLabel;
    private JTextField textField1;

    public void displayRegister(){
        setName("Register");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(417, 548);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        validate();
    }

    public static void main(String[] args) {
        ClientRegister ui = new ClientRegister();
        ui.displayRegister();
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JPasswordField getConfirmPasswordTF() {
        return confirmPasswordTF;
    }
}
