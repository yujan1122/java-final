/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafinal_1129;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Jhen
 */
public class myMethod extends AbstractTableModel{
    private final Connection connection;
    private final Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private boolean connectedToDatabase = false;
    
    public myMethod(String url, String username, String password, String query)throws SQLException{
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(
          ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_READ_ONLY);
          connectedToDatabase = true;
          setQuery(query);      
    }
    public void setQuery(String query)throws SQLException, IllegalStateException{
        if (!connectedToDatabase) 
          throw new IllegalStateException("Not Connected to Database");
        resultSet = statement.executeQuery(query);
        metaData = resultSet.getMetaData();
        resultSet.last(); // move to last row
        numberOfRows = resultSet.getRow(); // get row number     
        
        // notify JTable that model has changed
        fireTableStructureChanged();
    }
    
    
    
    
    public Object getValueAt(int row, int column) 
       throws IllegalStateException
    {
       // ensure database connection is available
       if (!connectedToDatabase) 
          throw new IllegalStateException("Not Connected to Database");

       // obtain a value at specified ResultSet row and column
       try 
       {
          resultSet.absolute(row + 1);
          return resultSet.getObject(column + 1);
       }
       catch (SQLException sqlException) 
       {
          sqlException.printStackTrace();
       } 

       return ""; // if problems, return empty string object
    }
    
    public int getColumnCount() throws IllegalStateException
    {   
       // ensure database connection is available
       if (!connectedToDatabase) 
          throw new IllegalStateException("Not Connected to Database");

       // determine number of columns
       try 
       {
          return metaData.getColumnCount(); 
       }
       catch (SQLException sqlException) 
       {
          sqlException.printStackTrace();
       } 

       return 0; // if problems occur above, return 0 for number of columns
    } 
    
    public int getRowCount() throws IllegalStateException
    {      
       // ensure database connection is available
       if (!connectedToDatabase) 
          throw new IllegalStateException("Not Connected to Database");
       return numberOfRows;
    }
    
    public String getColumnName(int column) throws IllegalStateException
    {    
       // ensure database connection is available
       if (!connectedToDatabase) 
          throw new IllegalStateException("Not Connected to Database");

       // determine column name
       try 
       {
          return metaData.getColumnName(column + 1);  
       } 
       catch (SQLException sqlException) 
       {
          sqlException.printStackTrace();
       } 

       return ""; // if problems, return empty string for column name
    }
    
    
    
}
