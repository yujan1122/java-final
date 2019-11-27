/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinal_1129;
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
import java.awt.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 *
 * @author Jhen
 */
public class JavaFinal_1129 {

    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/books";
    private static final String USERNAME = "app";
    private static final String PASSWORD = "app";
    private static final String DEFAULT_QUERY = "SELECT * FROM authors";
    private static  myMethod tableset;
    
    
    static JFrame window = new JFrame("JavaDB Query Results");
    static Menu menu1 = new Menu("File");
    static MenuBar mb = new MenuBar();
    static MenuItem mI1 = new MenuItem("Exit");
    
    static class myListenr implements ActionListener{
        public void actionPerformed(ActionEvent e){
            MenuItem item = (MenuItem) e.getSource();
            if(item == mI1){
                window.dispose();                
            }
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            
            tableset = new myMethod(DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
            final JTextArea keyInArea = new JTextArea(DEFAULT_QUERY,3,100);
            keyInArea.setWrapStyleWord(true);
            keyInArea.setLineWrap(true);
            mb.add(menu1);
            menu1.add(mI1);
            window.setMenuBar(mb);            
            mI1.addActionListener(new myListenr());
             
            JScrollPane scv = new JScrollPane(keyInArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            
            JButton submit = new JButton("Submit command");
            JTable resultTable = new JTable(tableset);
             
            Box box1 = Box.createHorizontalBox();
            box1.add(scv);
            box1.add(submit);             
            window.add(box1,BorderLayout.NORTH);
            window.add(new JScrollPane(resultTable), BorderLayout.CENTER);
            
            //final TableRowSorter<TableModel> sorter = 
            //new TableRowSorter<TableModel>(tableset);
            //resultTable.setRowSorter(sorter); 
            
            window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            window.setSize(500, 250); 
            window.setVisible(true);
            
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        tableset.setQuery(keyInArea.getText());
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, 
                        e1.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE);                        
                    }
                }
            });        
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                "Database error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
}


