/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
/**
 * @author songhokun
 *
 */
@Named
@SessionScoped
public class OrderBean implements Serializable{
	private Order[] orderarray;
	
	private void retrieveOrders(){
		ArrayList<Order> orders = new ArrayList<Order>();
		
		MysqlConnect db = new MysqlConnect();
		ResultSet rs;
		
		try {
			rs = db.query("SELECT * FROM orders;");
			while(rs.next()){
				Order temp = new Order();
				
				temp.setOrderID(rs.getString("order_ID"));
				temp.setCustomerName(rs.getString("CustomerName"));
				temp.setCustomerAddress(rs.getString("streetaddress"));
				temp.setZipCode(rs.getString("zipcode"));
				temp.setCity(rs.getString("city"));
				temp.setPhone(rs.getString("phone"));
				temp.setEmail(rs.getString("email"));
				
				orders.add(temp);
			}
			
			orderarray = orders.toArray(new Order[orders.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Order[] getOrderarray() {
		retrieveOrders();
		
		return orderarray;
	}

	public void setOrderarray(Order[] orderarray) {
		this.orderarray = orderarray;
	}
}
