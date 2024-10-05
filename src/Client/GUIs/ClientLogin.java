package Client.GUIs;

import javax.swing.*;

public class ClientLogin extends JFrame{
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel icon;
    private JPanel midPanel;
    private JLabel usernameLabel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel passwordLabel;

    public void displayLogin(){
        setName("Login");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(417, 548);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        validate();
    }

    public static void main(String[] args) {
        ClientLogin ui = new ClientLogin();
        ui.displayLogin();
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
