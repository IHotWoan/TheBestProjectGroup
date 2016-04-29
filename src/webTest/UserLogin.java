package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@SessionScoped
public class UserLogin implements Serializable {
	
	private String userName;
	private String password;
	
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
	 * 
	 * @return "success" if id&pw matches. otherwise returns "failure"
	 */
	public String verifyUser(){
		if (isValidIDPW()) {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("username", userName);
            return "success";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            
            return "index";
        }
	}
	private boolean isValidIDPW(){
		MysqlConnect db = new MysqlConnect();
		ResultSet rs;
		
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
