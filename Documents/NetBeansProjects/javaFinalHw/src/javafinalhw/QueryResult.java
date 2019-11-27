/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinalhw;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
/**
 *
 * @author Jhen
 */
public class QueryResult  extends JFrame{
    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/books";
    private static final String USERNAME = "app";
    private static final String PASSWORD = "app";
    private static final String DEFAULT_QUERY = "SELECT * FROM authors";
    private static ResultModel tableModel;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            tableModel = new ResultModel(
            DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
            final JTextArea queryArea = new JTextArea(DEFAULT_QUERY, 3, 100);
            queryArea.setWrapStyleWord(true);
            queryArea.setLineWrap(true);
            
            JScrollPane scrollPane = new JScrollPane(queryArea,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            
            JButton submitButton = new JButton("Submit");            
            JTable resultJTable = new JTable(tableModel);
            
            JFrame window = new JFrame("JavaDB Query Results");
            
            Box box1 = Box.createHorizontalBox();
            box1.add(scrollPane);
            box1.add(submitButton);
            //window.add(scrollPane,BorderLayout.NORTH);
            //window.add(submitButton,BorderLayout.NORTH);
            window.add(box1,BorderLayout.NORTH);
            //window.add(resultJTable, BorderLayout.SOUTH);
            window.add(new JScrollPane(resultJTable), BorderLayout.CENTER);

            
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try 
                  {
                     tableModel.setQuery(queryArea.getText());
                  }
                  catch (SQLException sqlException) 
                  {
                     JOptionPane.showMessageDialog(null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE);
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery(DEFAULT_QUERY);
                        queryArea.setText(DEFAULT_QUERY);
                     } 
                     catch (SQLException sqlException2) 
                     {
                        JOptionPane.showMessageDialog(null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE);
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit(1); // terminate application
                     }                 
                  }
                }
            });
            
            final TableRowSorter<TableModel> sorter = 
            new TableRowSorter<TableModel>(tableModel);
            resultJTable.setRowSorter(sorter);       
            
            window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            window.setSize(500, 250); 
            window.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                "Database error", JOptionPane.ERROR_MESSAGE);
             tableModel.disconnectFromDatabase();
             System.exit(1); // terminate application
        }
    }
    
}
