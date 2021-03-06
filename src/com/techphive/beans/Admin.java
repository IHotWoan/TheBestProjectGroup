package com.techphive.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.MysqlConnect;
import com.techphive.supportclasses.User;

/**
 * @author Jonathan
 *
 */

@ManagedBean(name="admin")
@SessionScoped
public class Admin {

	private ArrayList<User> userArray = new ArrayList<User>();
	private MysqlConnect db;
	private ResultSet rs;
	
	private String userName;
	private String userPassword;
	private User currentUser;
	private boolean permitted=false;
	
	private String newPassword;
	private String confirmPassword;
	private String userID;
	private boolean fail;
	private boolean newuserstatus=false;

	public Admin(){
		
		db = new MysqlConnect();
		try{
			rs = db.query("SELECT * from users");
		
		while(rs.next()){
			
			User user = new User();
			
			user.setUserID(rs.getString("user_id"));
			user.setUserName(rs.getString("user_username"));
			user.setPassword(rs.getString("user_password"));
			user.setSuperuser(rs.getBoolean("user_isSuperUser"));
			userArray.add(user);
			
		}
		
		db.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		db=null;
		
		//adding current user
		this.setCurrentUser();
		
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
			db = new MysqlConnect();
			try{
				db.insert("UPDATE `shopdb`.`users` SET `user_password`='"+getConfirmPassword()+"' WHERE `user_id`='"+getUserID()+"'");
				db.close();
				db = null;
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
		db = new MysqlConnect();
		for (User user : userArray){
			
			if(user.getUserID().equals(userID)){
				try {
					db.insert("DELETE FROM users WHERE user_id='"+userID+"'");
					user.setDeletable(false);
					userArray.remove(user);
					db.close();
					db = null;
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
		if(!this.currentUser.equals(user))
			if(user.isDeletable()){
				user.setDeletable(false);
			}
			else{
				user.setDeletable(true);
			}
		
		return null;
	}

	public String addUser(){
		db = new MysqlConnect();
		try {
			db.insert("INSERT into `shopdb`.`users` (user_username,user_password,user_isSuperUser) "
					+ "VALUES ('"+getUserName()+"','"+getUserPassword()+"',"+isNewuserstatus()+")");
			
			User user = new User();
			user.setUserName(getUserName());
			user.setPassword(getUserPassword());
		
			rs = db.query("SELECT user_ID from users order by user_ID desc limit 1");
			rs.next();
			user.setUserID(rs.getString(1));
			
			userArray.add(user);
			db.close();
			db = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public boolean hasPermission(User anotherUser){
		if(this.currentUser==null)
			this.setCurrentUser();
		
		return currentUser.isSuperuser() || (!currentUser.isSuperuser() && !anotherUser.isSuperuser());
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public boolean isPermitted(){
		return permitted;
	}
	public User getCurrentUser(){
		return currentUser;
	}
	public void setCurrentUser(){
		HttpSession session = SessionBean.getSession();
		String currentUserName = (String) session.getAttribute("username");
		for(User u: userArray){
			if(u.getUserName().equals(currentUserName))
			{
				currentUser = u;
				break;
			}
		}
		if(currentUser.isSuperuser())
			this.permitted=true;
	}

	public boolean isNewuserstatus() {
		return newuserstatus;
	}

	public void setNewuserstatus(boolean newuserstatus) {
		this.newuserstatus = newuserstatus;
	}
	
}

