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
	
	private String newPassword;
	private String confirmPassword;
	private String userID;
	private boolean fail;

	public Admin(){
		
		try{
			rs = db.query("SELECT * from users");
		
		while(rs.next()){
			
			User user = new User();
			
			user.setUserID(rs.getString("user_id"));
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

		if(newPassword.equals("")||confirmPassword.equals("")){
			setFail(true);
			setNewPassword(null);
			setConfirmPassword(null);
			return "failed";
		}
		else if(newPassword.equals(confirmPassword)){
			try{
				db.insert("UPDATE `shopdb`.`users` SET `user_password`='"+getConfirmPassword()+"' WHERE `user_id`='"+getUserID()+"'");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (User user : userArray){
				
				if(user.getUserID().equals(getUserID())){
					
					user.setPassword(confirmPassword);
				}	
			}
			setFail(false);
			return "saved";
		}
		else{
			setFail(true);
			setNewPassword(null);
			setConfirmPassword(null);
			return "failed";
		}
		
	}
	
	public void setUserID(String userID) {
		
		this.userID = userID;
	}
	
	public String getUserID() {
		
		return userID;
	}

	public String confirmUserDelete(String userID) {
		
		for (User user : userArray){
			
			if(user.getUserID().equals(userID)){
				try {
					db.insert("DELETE FROM users WHERE user_id='"+userID+"'");
					user.setDeletable(false);
					userArray.remove(user);
					break;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public String deleteAction(User user) {
	    
		user.setDeletable(true);
		return null;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isFail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}
	
}

