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
public class OrderTable implements Serializable{
	private Order[] orderarray;
	
	private void retrieveOrders(){
		ArrayList<Order> orders = new ArrayList<Order>();
		
		MysqlConnect db = new MysqlConnect();
		ResultSet rs;
		
		try {
			rs = db.query("SELECT * FROM customers;");
			while(rs.next()){
				Order temp = new Order();
				
				temp.setOrderID(rs.getString("customer_order_ID"));
				temp.setCustomerName(rs.getString("customer_name"));
				temp.setCustomerAddress(rs.getString("customer_streetaddress"));
				temp.setZipCode(rs.getString("customer_zipcode"));
				temp.setCity(rs.getString("customer_city"));
				temp.setPhone(rs.getString("customer_phone"));
				temp.setEmail(rs.getString("customer_email"));
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
