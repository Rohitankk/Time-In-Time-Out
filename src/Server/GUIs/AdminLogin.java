package Server.GUIs;

import javax.swing.*;

public class AdminLogin extends JFrame{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel bottomPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JLabel icon;
    private JLabel passwordLabel;
    private JLabel usernameLabel;

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
        AdminLogin ui = new AdminLogin();
        ui.displayLogin();
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
