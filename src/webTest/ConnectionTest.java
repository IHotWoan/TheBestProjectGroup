package webTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;


public class ConnectionTest {
	
	public static void main(String[] args) throws SQLException {
		MysqlConnect db = new MysqlConnect();
		
		ResultSet rs = db.query("SELECT * FROM users");
			
			while(rs.next()){
				System.err.println(rs.getString("password"));
			}
	}
}
