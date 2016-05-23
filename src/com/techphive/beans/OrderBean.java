/**
 * 
 */
package com.techphive.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.MysqlConnect;
import com.techphive.supportclasses.Order;

/**
 * @author songhokun
 *
 */
@ManagedBean(name="orderbean")
@SessionScoped
public class OrderBean {
	private Order selectedOrder = new Order();
	private ArrayList<Order> foundOrders= new ArrayList<Order>();
	private String ordernumber;
	private String email;;

	public OrderBean(){
		HttpSession session = SessionBean.getSession();
		selectedOrder = (Order) session.getAttribute("selectedOrder");
		 if(selectedOrder==null){
			 selectedOrder = new Order();
		 }
		 session.setAttribute("selectedOrder",selectedOrder);
	}
	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public ArrayList<Order> getFoundOrders() {
		return foundOrders;
	}
	public void setFoundOrders(ArrayList<Order> foundOrders) {
		this.foundOrders = foundOrders;
	}
	public String checkExisitingOrder(){
		try {
			MysqlConnect db = new MysqlConnect();
			ResultSet rs;

			String sendquery = "SELECT * FROM customers where customer_order_ID = '"+ordernumber+"' and customer_email = '"+email+"'";
			rs = db.query(sendquery);
			while(rs.next()){
				if(rs.getString("customer_order_ID").equals(ordernumber)) {
					Order test = new Order(rs.getString("customer_order_ID"));

					//test.setOrderID(rs.getString("customer_order_ID"));
					test.setCustomerName(rs.getString("customer_name"));
					test.setCustomerAddress(rs.getString("customer_streetaddress"));
					test.setZipCode(rs.getString("customer_zipcode"));
					test.setCity(rs.getString("customer_city"));
					test.setPhone(rs.getString("customer_phone"));
					test.setEmail(rs.getString("customer_email"));
					test.setOrderstatus(rs.getString("customer_orderstatus"));

					selectedOrder = test;
					db.close();
					return "vieworder";
				}
			}

			db.close();
			db = null;
		} catch (SQLException e) {
			System.err.println("An exception happened during querying SQL for tracking order functions");
		}
		FacesContext.getCurrentInstance().addMessage("checkout-order-form",
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Provided order number does not exist in our system.",
                        "Please check your number and email again."));
		
		return "checkorder";
	}
	public String orderIdByMail(){
		boolean found = false;
		foundOrders.clear();
		
		try {
			MysqlConnect db = new MysqlConnect();
			ResultSet rs;


			rs = db.query("SELECT * FROM customers where customer_email = '"+email+"'");
			while(rs.next()){
				if(rs.getString("customer_email").equals(email)) {
					Order test = new Order(rs.getString("customer_order_ID"));

					//test.setOrderID(rs.getString("customer_order_ID"));
					test.setCustomerName(rs.getString("customer_name"));
					test.setCustomerAddress(rs.getString("customer_streetaddress"));
					test.setZipCode(rs.getString("customer_zipcode"));
					test.setCity(rs.getString("customer_city"));
					test.setPhone(rs.getString("customer_phone"));
					test.setEmail(rs.getString("customer_email"));
					test.setOrderstatus(rs.getString("customer_orderstatus"));
					foundOrders.add(test);
					found = true;
				}
			}

			db.close();
			db = null;
		} catch (SQLException e) {
			System.err.println("An exception happened during querying SQL for tracking order functions");
		}
		if(found)
			return "vieworders";
		else
		{
			FacesContext.getCurrentInstance().addMessage("searchOrderNumbers",
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Provided e-mail address does not exist in our system.",
                        "Please check your email address again."));
			
			return "checkorder";
		}
	}
}