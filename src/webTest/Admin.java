package webTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Jonathan
 *
 */

@ManagedBean(name="admin")
@SessionScoped
public class Admin {

	private ArrayList<User> userArray = new ArrayList<User>();
	private MysqlConnect db = new MysqlConnect();
	private ResultSet rs;
	
	private String userName;
	private String password;
	private long userID;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Admin(){
		
		try{
			rs = db.query("SELECT * from users");
		
		while(rs.next()){
			
			User user = new User();
			
			user.setUserID(rs.getLong("user_id"));
			user.setUserName(rs.getString("user_username"));
			user.setPassword(rs.getString("user_password"));
			
			userArray.add(user);
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
	}
	
	public ArrayList<User> getUserArray(){
		
		return userArray;
		
	}

	public String changePassword(){

		try{
			db.insert("UPDATE `shopdb`.`users` SET `user_password`='"+getPassword()+"' WHERE `user_username`='"+getUserName()+"'");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "saved";
	}

	public String deleteUser(){

		try{
			db.insert("DELETE FROM users WHERE user_id='"+ getUserID() +"'");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "saved";
	}
	
	

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
	
}

