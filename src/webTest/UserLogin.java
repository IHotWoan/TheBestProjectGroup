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
	private boolean loginStatus;
	
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
	/**
	 * Ask songho if there is a problem in this part;
	 * 
	 * @return "success" if id&pw matches. otherwise returns "failure"
	 */
	public String verifyUser(){
		MysqlConnect db = new MysqlConnect();
		ResultSet rs;
		
		try {
			rs = db.query("SELECT username, password from users where username='"+userName+"' and password = '"+password+"' is TRUE");
			while(rs.next()){
				if ((rs.getString("username").equals(userName)) && (rs.getString("password").equals(password))){
					loginStatus=true;
					return "success";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "failure";
		}

		return "failure";
	}
	public String getMessage() throws SQLException{
		return verifyUser();
	}
	public boolean getLoginStatus(){
		return loginStatus;
	}
}
