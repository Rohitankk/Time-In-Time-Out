package Client.GUIs;

import javax.swing.*;

public class ClientHomepage extends JFrame{
    private JPanel mainPanel;
    private JPanel midPanel;
    private JLabel icon;
    private JLabel welcomeLabel;
    private JLabel clientStatusLabel;
    private JButton timeInButton;
    private JButton timeOutButton;
    private JButton summaryButton;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel line;

    public void displayClientHomepage(){
        setName("Homepage");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(417, 548);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        validate();
    }

    public static void main(String[] args) {
        ClientHomepage ui = new ClientHomepage();
        ui.displayClientHomepage();
    }

    public JButton getSummaryButton() {
        return summaryButton;
    }

    public JLabel getClientStatusLabel() {
        return clientStatusLabel;
    }

    public JButton getTimeInButton() {
        return timeInButton;
    }

    public JButton getTimeOutButton() {
        return timeOutButton;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }
}
