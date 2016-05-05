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
	
	public Admin(){
		
		try{
			rs = db.query("SELECT * from users");
		
		while(rs.next()){
			
			User user = new User();
			
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
	
}
