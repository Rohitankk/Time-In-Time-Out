package Server.GUIs;

import Server.ReadersAndWriters.AccountRAWs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class AdminHomepage extends JFrame{
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable statusTable;
    private JButton generateReportButton;
    private JLabel statusLabel;
    private JList requestList;
    private JButton acceptButton;
    private JButton rejectButton;
    private JLabel requestsLabel;
    private JScrollPane statusScrollPane;
    private DefaultListModel<String>listModel;
    private HashMap<String, String> requests;

    private DefaultTableModel tableModel;
    private String[] columnNames = {"Name", "Status"};

    public void fillTable(String[][] status){
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);
        for(String[] item: status){
            tableModel.addRow(item);
        }
        statusTable.setModel(tableModel);
        repaint();
        revalidate();
    }

    public void displayHomepage(){
        setName("Homepage");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(821, 561);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        requests = AccountRAWs.readRequests();
        listModel = new DefaultListModel();
        for (String request: requests.keySet()) {
            listModel.addElement(request);
        }
        requestList.setModel(listModel);

        validate();

    }

    public static void main(String[] args) {
        AdminHomepage ui = new AdminHomepage();
        ui.displayHomepage();
    }

    public void refreshRequestList(){
        requests = AccountRAWs.readRequests();
        listModel = new DefaultListModel();
        for (String request: requests.keySet()) {
            listModel.addElement(request);
        }
        requestList.setModel(listModel);
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }

    public JButton getGenerateReportButton() {
        return generateReportButton;
    }

    public JButton getRejectButton() {
        return rejectButton;
    }

    public JList getRequestList() {
        return requestList;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

}
