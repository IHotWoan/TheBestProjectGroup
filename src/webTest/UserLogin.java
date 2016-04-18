package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class UserLogin implements Serializable {
	
	private String userName;
	private String password;
	private String message;
	
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
		MysqlConnect db = new MysqlConnect();
		
		ResultSet rs = db.query("SELECT * FROM users");
	
			while(rs.next()){
				if ((rs.getString("username").equals(userName)) && (rs.getString("password").equals(password))){

					return message = "Login Successful";
				}
			}
		
		message = "Login Failed";
		return message;
	}
	
}
