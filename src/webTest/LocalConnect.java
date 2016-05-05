package webTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalConnect {
	
    public Connection conn;
    private Statement statement;
    public LocalConnect() {
    	
    	
        String url= "jdbc:mysql://songhohem.ddns.net:3306/";
        String dbName = "shopdb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "1DV508Grupp5";
        
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }

    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
    	
        statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
    	
    	statement = conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
        
    }
    public void close() throws SQLException{
    	conn.close();
    }
    
}