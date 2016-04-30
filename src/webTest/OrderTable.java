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

import webTest.Order.Status;
/**
 * @author songhokun
 *
 */
@ManagedBean(name="ordertable")
@SessionScoped
public class OrderTable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Order> undeliveredOrderarray = new ArrayList<Order>();
	private ArrayList<Order> deliveredOrderarray = new ArrayList<Order>();

	public OrderTable(){
		retrieveOrders();
	}

	private void retrieveOrders(){
		try {
			MysqlConnect db = new MysqlConnect();
			ResultSet rs;
			
			rs = db.query("SELECT * FROM customers;");
			while(rs.next()){
				Order temp = new Order(rs.getString("customer_order_ID"));
				
				//temp.setOrderID(rs.getString("customer_order_ID"));
				temp.setCustomerName(rs.getString("customer_name"));
				temp.setCustomerAddress(rs.getString("customer_streetaddress"));
				temp.setZipCode(rs.getString("customer_zipcode"));
				temp.setCity(rs.getString("customer_city"));
				temp.setPhone(rs.getString("customer_phone"));
				temp.setEmail(rs.getString("customer_email"));
				temp.setOrderstatus(rs.getString("customer_orderstatus"));
				
				//if(temp.getOrderstatus() == Status.delivered)
				if(temp.getOrderstatus().equals("delivered"))
					deliveredOrderarray.add(temp);
				else
					undeliveredOrderarray.add(temp);
			}
			
			db.close();
			db = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<Order> getUndeliveredOrderarray() {
		return undeliveredOrderarray;
	}

	public void setUndeliveredOrderarray(ArrayList<Order> undeliveredOrderarray) {
		this.undeliveredOrderarray = undeliveredOrderarray;
	}

	public ArrayList<Order> getDeliveredOrderarray() {
		return deliveredOrderarray;
	}

	public void setDeliveredOrderarray(ArrayList<Order> deliveredOrderarray) {
		this.deliveredOrderarray = deliveredOrderarray;
	}
}
