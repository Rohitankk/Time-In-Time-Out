package Server.GUIs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportGeneration extends JFrame{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel bottomPanel;
    private JTextField dateStartField;
    private JTextField dateEndField;
    private JButton generateReportButton;
    private JTable logsTable;
    private JTable hoursTable;
    private JLabel generationLabel;
    private JLabel dateStartLabel;
    private JLabel dateEndLabel;
    private JLabel logsLabel;
    private JLabel hoursLable;
    private DefaultTableModel logsTableModel;
    private DefaultTableModel hoursTableModel;
    private String[] logsColumnNames = {"Name", "Time In", "Time Out"};
    private String[] hoursColumnNames = {"Name", "Hours Rendered"};

    public void fillTable(ArrayList<ArrayList<String>> logsData) {
        Object[][] data = new Object[logsData.size()][];
        for (int i = 0; i < logsData.size(); i++) {
            ArrayList<String> row = logsData.get(i);
            data[i] = row.toArray(new String[0]);
        }
        logsTableModel = new DefaultTableModel(data, logsColumnNames);
        logsTable.setModel(logsTableModel);
        repaint();
        revalidate();
    }

    public void fillTable(HashMap<String, Double> hours) {
        Object[][] rowData = new String[hours.size()][2];
        int i = 0;
        for (String key : hours.keySet()) {
            rowData[i][0] = key;
            rowData[i][1] = hours.get(key).toString();
            i++;
        }
        hoursTableModel = new DefaultTableModel(rowData, hoursColumnNames);
        hoursTable.setModel(hoursTableModel);
        repaint();
        revalidate();
    }

    public void displayReportGeneration(){
        setName("Report Generation");
        setContentPane(mainPanel);
        setVisible(true);
        setSize(698, 575);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        validate();
    }

    public static void main(String[] args) {
        ReportGeneration ui = new ReportGeneration();
        ui.displayReportGeneration();
    }

    public JButton getGenerateReportButton() {
        return generateReportButton;
    }

    public JLabel getDateEndLabel() {
        return dateEndLabel;
    }

    public JLabel getDateStartLabel() {
        return dateStartLabel;
    }

    public JTable getHoursTable() {
        return hoursTable;
    }

    public JTable getLogsTable() {
        return logsTable;
    }

    public JTextField getDateEndField() {
        return dateEndField;
    }

    public JTextField getDateStartField() {
        return dateStartField;
    }
}
