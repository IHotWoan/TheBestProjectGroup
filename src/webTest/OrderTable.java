/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

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
	
	//These fields below are used for 'selected products' in ordernumbers page.
	private String selected;
	private Order selectedOrder;
	private Status selectedStatus;
	
	public SelectItem[] getStatusValues(){
		  SelectItem[] items = new SelectItem[Status.values().length];
		  int i = 0;
		  for(Status s: Status.values())
			  items[i++] = new SelectItem(s, s.getLabel());
		  
		  return items;
	}
	
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	@PostConstruct
	public void retrieveOrders(){
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
				
				if(temp.getOrderstatus() == Status.delivered)
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
	public String display(){
		return "ordernumbers";
	}
	/*
	public String display(Order inOrder){
		this.selected = inOrder.getOrderID();
		this.selectedOrder = inOrder;
		return "ordernumbers";
	}
	*/

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

	/**
	 * @return the selectedStatus
	 */
	public Status getSelectedStatus() {
		return selectedStatus;
	}

	/**
	 * @param selectedStatus the selectedStatus to set
	 */
	public void setSelectedStatus(Status selectedStatus) {
		this.selectedStatus = selectedStatus;
		
	}
	
	public String updateOrderStatus(){
		this.selectedOrder.setOrderstatus(selectedStatus);
		try {
			MysqlConnect db = new MysqlConnect();
			String query = "UPDATE customers SET customer_orderstatus='"+selectedStatus.getLabel()+
					"' WHERE customer_order_ID='"+this.selected+"';";
			db.insert(query);
			db.close();
			db = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		deliveredOrderarray.clear();
		undeliveredOrderarray.clear();
		retrieveOrders();
		
		return "updated";
	}
}
