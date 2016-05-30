package com.techphive.supportclasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MysqlConnect {
	
	private final String DATASOURCE_CONTEXT = "java:/MySqlDS";
    public Connection conn;
    private Statement statement;
    public MysqlConnect() {
    	
		Context initialContext;
		try {
			initialContext = new InitialContext();
			DataSource ds;
			
			ds = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
			
			if(ds==null)
				throw new SQLException("Can't get data source");
			
			conn = ds.getConnection();
			if(conn==null)
				throw new SQLException("Can't get database connection");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    
    public String escapeString(String in){
    	StringBuilder make = new StringBuilder (in);
    	for(int i=0;i<make.length();i++)
		{
			if(make.charAt(i)=='\\' || make.charAt(i)=='\''|| make.charAt(i)=='\"'
					|| make.charAt(i)=='%' || make.charAt(i)=='_'){
				if((i-1>0 && make.charAt(i-1)!='\\') || i==0)
					make.insert(i++,'\\');
			}
				
		}
    	return make.toString();
    }
}
