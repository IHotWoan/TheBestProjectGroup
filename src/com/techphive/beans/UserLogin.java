package com.techphive.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.MysqlConnect;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


@Named
@SessionScoped
public class UserLogin implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3690339744194800891L;
	private String userName;
	private String password;
	
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
	 * 
	 * @return "success" if id&pw matches. otherwise returns "failure"
	 */
	public String verifyUser(){
		if (isValidIDPW()) {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("username", userName);
            
            //Refresh all products (remove filter)
            SuperCategory supercat = (SuperCategory) session.getAttribute("supercategory");
            if(supercat!=null){
            	supercat.refreshAllProducts();
            	supercat.setSearchString("");
            }
            
            return "success";
        } else {
			FacesContext.getCurrentInstance().addMessage("loginform",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            
            return "index";
        }
	}
	private boolean isValidIDPW(){
		MysqlConnect db = new MysqlConnect();
		ResultSet rs;
		
		userName=db.escapeString(userName);
		password=db.escapeString(password);
		try {
			rs = db.query("SELECT user_username, user_password from users where user_username='"+userName+"' and user_password = '"+password+"' is TRUE");
			while(rs.next()){
				if ((rs.getString("user_username").equals(userName)) && (rs.getString("user_password").equals(password))){
					return true;
				}
			}
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		db=null;
		
		return false;
	}
	public String getMessage() throws SQLException{
		return verifyUser();
	}
	public String logout(){
		 HttpSession session = SessionBean.getSession();
		 session.invalidate();
		 
		 return "index";
	}
}
