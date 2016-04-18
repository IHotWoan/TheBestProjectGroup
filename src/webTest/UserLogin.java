package webTest;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Named
@SessionScoped
public class UserLogin implements Serializable {
	
	private String userName;
	private String password;
	private String message;
	
	//@Resource(name="java:/MySqlDS")
	//private DataSource ds;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//THIS METHOD IS NOT WORKING AND I DONT KNOW WHY IT WORKS FINE IN THE ConnectionTest CLASS AND ITS DRIVING ME CRAZY! HELP!
	public String getMessage() throws SQLException{
		
		message = "Login Failed";
		
		 String DATASOURCE_CONTEXT = "java:/MySqlDS";
		 Context initialContext;
		try {
			initialContext = new InitialContext();
			DataSource ds;
			
			ds = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
			if(ds==null)
				throw new SQLException("Can't get data source");
			
			Connection con = ds.getConnection();
			if(con==null)
				throw new SQLException("Can't get database connection");
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users;"); 
			ResultSet rs =  ps.executeQuery();
			
			while(rs.next()){
				if ((rs.getString("username").equals(userName)) && (rs.getString("password").equals(password))){

					return message = "Login Successful";
				}
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		
		/*
		MysqlConnect db = new MysqlConnect();
		message = "Login Failed";
		
		ResultSet rs = db.query("SELECT * FROM users");
	
			while(rs.next()){
				if ((rs.getString("username").equals(userName)) && (rs.getString("password").equals(password))){

					return message = "Login Successful";
				}
			}
			//java:jboss/datasources
		*/
		return message;
	}
	
}
