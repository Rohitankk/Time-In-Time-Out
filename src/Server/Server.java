package Server;

import Server.GUIs.AdminHomepage;
import Server.GUIs.AdminLogin;
import Server.GUIs.ReportGeneration;
import Server.ReadersAndWriters.AccountRAWs;
import Server.ReadersAndWriters.LogsRAWs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Server implements ActionListener {
    private AdminLogin loginInterface;
    private static AdminHomepage homepageInterface;
    private ReportGeneration generationInterface;
    private static HashMap<String, String> userStatus = new HashMap<>();

    public void initializeServer(){
        try{
            Servant servant = new Servant();
            Registry reg = LocateRegistry.createRegistry(8888);
            reg.bind("server", servant);
            System.out.println("RMI has been bound");

            userStatus = LogsRAWs.checkLastTimeOut();

            loginInterface = new AdminLogin();
            loginInterface.displayLogin();
            addLoginActionListeners();

        }catch(RemoteException | AlreadyBoundException e){
            e.printStackTrace();
        }
    }

    public void addLoginActionListeners(){
        loginInterface.getLoginButton().addActionListener(this);
    }

    public void addHomepageActionListeners(){
        homepageInterface.getAcceptButton().addActionListener(this);
        homepageInterface.getGenerateReportButton().addActionListener(this);
        homepageInterface.getRejectButton().addActionListener(this);
    }

    public void addGenerationActionListeners(){
        generationInterface.getGenerateReportButton().addActionListener(this);
    }

    public static String[][] toArray(HashMap<String,String> userStat){
        int i = 0;

        Set<String> keySet1 = userStat.keySet();
        Set<String> keySet2 = AccountRAWs.readUsers().keySet();

        Set<String> commonKeys = new HashSet<>(keySet1);
        commonKeys.retainAll(keySet2);
        Set<String> differentKeys = new HashSet<>(keySet1);
        differentKeys.addAll(keySet2);
        differentKeys.removeAll(commonKeys);

        String[][] values = new String[differentKeys.size() + commonKeys.size()][2];

        for (String key : commonKeys) {
            values[i][0] = key;
            values[i][1] = userStat.get(key);
            i++;
        }
        for (String key : differentKeys) {
            values[i][0] = key;
            values[i][1] = "On Break";
            i++;
        }
        return values;
    }

    public void showServerView(){
        homepageInterface = new AdminHomepage();
        homepageInterface.fillTable(toArray(userStatus));
        homepageInterface.displayHomepage();
        addHomepageActionListeners();
        generationInterface = new ReportGeneration();
        addGenerationActionListeners();
    }

    public void actionPerformed(ActionEvent e) {
        // LOGIN
        if (e.getSource().equals(loginInterface.getLoginButton())) {
            String username = loginInterface.getUsernameField().getText();
            String password = new String(loginInterface.getPasswordField().getPassword());
            if (Objects.equals(username, "admin") && password.equals("admin")) { //USERNAME AND PASSWORD CAN HAVE ITS OWN JSON FILE
                showServerView();
                loginInterface.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username and/or password is incorrect");
            }
        }

        //ACCEPT ACCOUNT REQUESTS
        else if (e.getSource().equals(homepageInterface.getAcceptButton())) {
            String username = homepageInterface.getRequestList().getSelectedValue().toString();
            HashMap<String, String> requests = AccountRAWs.readRequests();

            for (String user : requests.keySet()) {
                if (user.equals(username)) {
                    String password = requests.get(user);
                    AccountRAWs.addUser(username,password);
                    AccountRAWs.deleteRequest(username);
                    userStatus.put(username,"On Break");
                    break;
                }
            }
            refreshRequestList();

            homepageInterface.fillTable(toArray(userStatus));
            homepageInterface.repaint();
            homepageInterface.revalidate();
        }

        //REJECT ACCOUNT REQUESTS
        else if (e.getSource().equals(homepageInterface.getRejectButton())){
            String username = homepageInterface.getRequestList().getSelectedValue().toString();
            HashMap<String, String> requests = AccountRAWs.readRequests();
            for (String user : requests.keySet()) {
                if (user.equals(username)) {
                    AccountRAWs.deleteRequest(username);

                }
            }
            refreshRequestList();
        }

        else if (e.getSource().equals(homepageInterface.getGenerateReportButton())){
            generationInterface.displayReportGeneration();
        }
        else if (e.getSource().equals(generationInterface.getGenerateReportButton())){
            try {
                ArrayList<ArrayList<String>> logsInRange = LogsRAWs.getLogsInRange(generationInterface.getDateStartField().
                        getText(), generationInterface.getDateEndField().getText());
                HashMap<String, Double> hours = LogsRAWs.calculateHoursRendered(logsInRange);
                System.out.println(hours);
                generationInterface.fillTable(logsInRange);
                generationInterface.fillTable(hours);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            generationInterface.repaint();
            generationInterface.revalidate();
        }
    }

    public static void refreshRequestList(){
        homepageInterface.refreshRequestList();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.initializeServer();
    }

    public static HashMap<String, String> getUserStatus() {
        return userStatus;
    }

    public static void setUserStatus(HashMap<String, String> userStatus) {
        Server.userStatus = userStatus;
    }

    public static AdminHomepage getHomepageInterface() {
        return homepageInterface;
    }
}
