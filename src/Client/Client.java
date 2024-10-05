package Client;

import Client.GUIs.ClientHomepage;
import Client.GUIs.ClientLogin;
import Client.GUIs.ClientRegister;
import Client.GUIs.ClientSummary;
import Server.ClientFunctions;
import Server.ReadersAndWriters.Exceptions;


import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Client implements ActionListener {
    private ClientFunctions remote;
    private ClientLogin loginInterface;
    private ClientRegister registerInterface;
    private ClientHomepage homepageInterface;
    private ClientSummary summaryInterface;
    private User user;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    public void initializeClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 8888);
        remote = (ClientFunctions) registry.lookup("server");
        loginInterface = new ClientLogin();
        registerInterface = new ClientRegister();
        loginInterface.displayLogin();
        addLoginActionListeners();
    }

    public void addLoginActionListeners(){
        loginInterface.getLoginButton().addActionListener(this);
        loginInterface.getRegisterButton().addActionListener(this);
    }

    public void addHomepageActionListeners(){
        homepageInterface.getSummaryButton().addActionListener(this);
        homepageInterface.getTimeInButton().addActionListener(this);
        homepageInterface.getTimeOutButton().addActionListener(this);
    }

    public void addRegisterActionListeners(){
        registerInterface.getRegisterButton().addActionListener(this);
    }

    public void showClientView(){
        homepageInterface = new ClientHomepage();
        homepageInterface.displayClientHomepage();
        addHomepageActionListeners();
        summaryInterface = new ClientSummary();
    }
    public void showRegisterView(){
        loginInterface.dispose();
//        registerInterface = new ClientRegister();
        registerInterface.displayRegister();
        addRegisterActionListeners();
    }

    public void actionPerformed(ActionEvent e){
        // EMPLOYEE LOGIN //
        if(e.getSource().equals(loginInterface.getLoginButton())){
            String username = loginInterface.getUsernameField().getText();
            String password = new String(loginInterface.getPasswordField().getPassword());
            try {
                if(remote.login(username, password)){
                    user = new User(username, password);
                    showClientView();
                    if(remote.getStatus(username)){
                        homepageInterface.getClientStatusLabel().setText("You are currently: Timed In");
                        homepageInterface.getTimeInButton().setEnabled(false);
                    }
                    else{
                        homepageInterface.getClientStatusLabel().setText("You are currently: Timed Out");
                        homepageInterface.getTimeOutButton().setEnabled(false);
                    }
                    homepageInterface.getWelcomeLabel().setText("Welcome " + username);
                    loginInterface.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Username and/or password is incorrect");
                }
            } catch (RemoteException | Exceptions ex) {
                throw new RuntimeException(ex);
            }

            //DISPLAY REGISTER INTERFACE //
        }else if(e.getSource().equals(loginInterface.getRegisterButton())){
            showRegisterView();

            // REGISTER ACCOUNT //
        }else if (e.getSource().equals(registerInterface.getRegisterButton())){
            String username = registerInterface.getUsernameField().getText();
            String password = new String(registerInterface.getPasswordField().getPassword());
            String ConfirmPassword = new String(registerInterface.getConfirmPasswordTF().getPassword());

            if (!password.equals(ConfirmPassword)){
                JOptionPane.showMessageDialog(new Frame(), "Password does not match","Error", JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    if(remote.register(username,password,ConfirmPassword)){
                        JOptionPane.showMessageDialog(new Frame(), "Registration is still in progress... \n " +
                                "Please proceed to login page", "Registration processing",JOptionPane.INFORMATION_MESSAGE);
                        registerInterface.dispose();
                        loginInterface.displayLogin();
                    }else{
                        JOptionPane.showMessageDialog(new Frame(), "Sorry the username already exists", "user found",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException | Exceptions remoteException) {
                    remoteException.printStackTrace();
                }
            }

            // DISPLAY SUMMARY PAGE //
        }else if (e.getSource().equals(homepageInterface.getSummaryButton())){
            summaryInterface.getSummaryName().setText(user.getUsername() + "'s Summary Page");
            fillSummaryTable();
            summaryInterface.displaySummary();


        }else if(e.getSource().equals(homepageInterface.getTimeInButton())){
            try {
                homepageInterface.getClientStatusLabel().setText("You are currently: Timed In");
                homepageInterface.getTimeInButton().setEnabled(false);
                homepageInterface.getTimeOutButton().setEnabled(true);
                remote.timeIn(user.getUsername(), formatter.format(new Date()));
                fillSummaryTable();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource().equals(homepageInterface.getTimeOutButton())) {
            try {
                homepageInterface.getClientStatusLabel().setText("You are currently: Timed Out");
                homepageInterface.getTimeOutButton().setEnabled(false);
                homepageInterface.getTimeInButton().setEnabled(true);
                remote.timeOut(user.getUsername(), formatter.format(new Date()));
                fillSummaryTable();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void fillSummaryTable(){
        SimpleDateFormat formatByDay = new SimpleDateFormat("yy-MM-dd");
        String currDate = formatByDay.format(new Date());
        try {
            ArrayList<ArrayList<String>> temp = remote.getSummary(user.getUsername());
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            if(temp != null){
                for(ArrayList<String> log : temp){
                    if(log.get(0).equals(currDate)){
                        data.add(log);
                    }
                }
            }

            if(data != null){
                summaryInterface.fillTable(data);
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Client client = new Client();
        client.initializeClient();
    }
}
