/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Boolean> checked = new HashMap<String, Boolean>();
	
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
		deliveredOrderarray.clear();
		undeliveredOrderarray.clear();
		
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
		retrieveOrders();
		
		this.selected=null;
		this.selectedOrder=null;
		
		return "updated";
	}
	
	public String updateQuick(String toWhich){
		
		ArrayList<String> ordernumbersToUpdate = new ArrayList<String>();

		for(Order i : undeliveredOrderarray)
			if(checked.get(i.getOrderID()))
				ordernumbersToUpdate.add(i.getOrderID());
		
		/*
		for(int i=0;i<undeliveredOrderarray.size();i++)
			if(undeliveredOrderarray.get(i).isQuickselected())
				ordernumbersToUpdate.add(undeliveredOrderarray.get(i).getOrderID());
		*/
		try {
			MysqlConnect db = new MysqlConnect();
			for(int i=0;i<ordernumbersToUpdate.size();i++){
				String query="";
				if(toWhich.equals("delayed")){
					query = "UPDATE customers SET customer_orderstatus='"+Status.delayed.getLabel()+
						"' WHERE customer_order_ID='"+ordernumbersToUpdate.get(i)+"';";
				}
				else if(toWhich.equals("rejected")){
					query = "UPDATE customers SET customer_orderstatus='"+Status.rejected.getLabel()+
							"' WHERE customer_order_ID='"+ordernumbersToUpdate.get(i)+"';";
				}
				else if(toWhich.equals("delivered"))
				{
					query = "UPDATE customers SET customer_orderstatus='"+Status.delivered.getLabel()+
							"' WHERE customer_order_ID='"+ordernumbersToUpdate.get(i)+"';";
				}
				db.insert(query);
			}
			db.close();
			db = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		retrieveOrders();
		checked.clear();
		return "manageorders";
	}
	public Map<String, Boolean> getChecked() {
		return checked;
	}

	public void setChecked(Map<String, Boolean> checked) {
		this.checked = checked;
	}
	/**
	 * The method overwrites the current array with queried order.
	 * @param whereToNavigateAfterwards
	 * @return
	 */
	public String searchOrder(String whereToNavigateAfterwards){
		retrieveOrders();
		
		if(whereToNavigateAfterwards.equals("deliveredorders")){
			for(Order i : deliveredOrderarray)
			{
				if(i.getOrderID().equals(selected))
				{
					deliveredOrderarray.clear();
					deliveredOrderarray.add(i);
					return "deliveredorders";
				}
			}
			//items not found
			deliveredOrderarray.clear();
		}
		else
		{
			for(Order i : undeliveredOrderarray)
			{
				if(i.getOrderID().equals(selected))
				{
					undeliveredOrderarray.clear();
					undeliveredOrderarray.add(i);
					return "manageorders";
				}
			}
			//items not found
			undeliveredOrderarray.clear();
		}
		return whereToNavigateAfterwards;
	}
	public String clearSearch(String whereToNavigateAfterwards){
		
		retrieveOrders();
		return whereToNavigateAfterwards;
	}
}
