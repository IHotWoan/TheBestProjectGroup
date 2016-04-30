/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
/**
 * @author songhokun
 *
 */
@ManagedBean(name="ordertable")
@SessionScoped
public class OrderTable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Order> orderarray = new ArrayList<Order>();

	public OrderTable(){
		retrieveOrders();
	}
	
	public ArrayList<Order> getOrderarray() {
		return orderarray;
	}

	public void setOrderarray(ArrayList<Order> orderarray) {
		this.orderarray = orderarray;
	}
	
	private void retrieveOrders(){
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
				
				orderarray.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
