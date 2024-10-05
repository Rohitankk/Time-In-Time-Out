package Client.GUIs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ClientSummary extends JFrame{
    private JPanel mainPanel;
    private JLabel summaryName;
    private JTable summaryTable;
    private JLabel logo;
    private JScrollPane summaryScrollPane;
    private DefaultTableModel tableModel;
    private String[] columnNames = {"Date", "Time In", "Time Out"};

    public void fillTable(ArrayList<ArrayList<String>> logsData) {
        Object[][] data = new Object[logsData.size()][];
        for (int i = 0; i < logsData.size(); i++) {
            ArrayList<String> row = logsData.get(i);
            data[i] = row.toArray(new String[0]);
        }
        tableModel = new DefaultTableModel(data, columnNames);
        summaryTable.setModel(tableModel);
        repaint();
        revalidate();
    }

    public void displaySummary(){
        setName("Summary");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(682, 627);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        validate();
    }

    public static void main(String[] args) {
        ClientSummary ui = new ClientSummary();
        ui.displaySummary();
    }

    public JLabel getSummaryName() {
        return summaryName;
    }
}
