/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author songhokun
 *
 */
public class Order implements Serializable{
	public enum Status{neworder, delivered, delayed, cancelled, rejected};
	
	private String cartID;
	private String orderID;
	private String customerName;
	private String customerAddress;
	private String city;
	private String zipCode;
	private String phone;
	private String email;
	private Status orderstatus;
	
	public ArrayList<Product> getProductarray() {
		return productarray;
	}
	public void setProductarray(ArrayList<Product> productarray) {
		this.productarray = productarray;
	}
	private ArrayList<Product> productarray = new ArrayList<Product>();
	
	//private Date orderPlaced;
	
	public Order(){
		
	}
	public Order(String inOrderID){
		this.orderID = inOrderID;
	}

	public String getCartID() {
		return cartID;
	}

	public void setCartID(String cartID) {
		this.cartID = cartID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/*
	public Date getOrderPlaced() {
		return orderPlaced;
	}

	public void setOrderPlaced(Date orderPlaced) {
		this.orderPlaced = orderPlaced;
	}
	*/
	public Status getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(Status orderstatus) {
		this.orderstatus = orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		if(orderstatus.equals("neworder"))
			this.orderstatus = Status.neworder;
		else if(orderstatus.equals("delivered"))
			this.orderstatus = Status.delivered;
		else if(orderstatus.equals("delayed"))
			this.orderstatus = Status.delayed;
		else if(orderstatus.equals("cancelled"))
			this.orderstatus = Status.cancelled;
		else if(orderstatus.equals("rejected"))
			this.orderstatus = Status.rejected;
		
	}
	
}
