/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import webTest.Order.Status;

/**
 * @author songhokun
 *
 */
public class Order implements Serializable{
	public enum Status{neworder, delivered, delayed, cancelled, rejected};
	
	private ArrayList<OrderedProduct> productarray = new ArrayList<OrderedProduct>();
	private String cartID;
	private String orderID;
	private String customerName;
	private String customerAddress;
	private String city;
	private String zipCode;
	private String phone;
	private String email;
	//private Status orderstatus;
	private String orderstatus;
	private String summary;
	
	public ArrayList<OrderedProduct> getProductarray() {
		return productarray;
	}
	public void setProductarray(ArrayList<OrderedProduct> productarray) {
		this.productarray = productarray;
	}
	
	
	//private Date orderPlaced;
	
	public Order(){
		
	}
	public Order(String inOrderID){
		this.orderID = inOrderID;
		
		try {
			MysqlConnect db = new MysqlConnect();
			ResultSet rs1;
			
			rs1 = db.query("SELECT products.product_name, brands.brand_name, category.category_name, ordereditems.ordereditems_quantity, ordereditems.ordereditems_price FROM ordereditems "
					+ "inner join products on ordereditems.ordereditems_product = products.product_id " +
				"inner join brands on products.product_brand = brands.brand_ID " +
				"inner join category on products.product_category = category.category_ID " +
				"where ordereditems.ordereditems_order_ID = '"+this.orderID+"'");
			while(rs1.next()){
				OrderedProduct temp = new OrderedProduct();
				
				temp.setProductName(rs1.getString("product_name"));
				temp.setCategoryName(rs1.getString("category_name"));
				temp.setBrandName(rs1.getString("brand_name"));
				temp.setQuantity(rs1.getInt("ordereditems_quantity"));
				temp.setPrice(rs1.getDouble("ordereditems_price"));
				
				productarray.add(temp);
			}
			db.close();
			db = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	/*
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
	*/
	public String getSummary(){
		int n = productarray.size();
		if(n>3)
			n=3;
		
		StringBuilder toReturn = new StringBuilder();
		for(int i=0;i<n;i++)
			toReturn.append(productarray.get(i).getProductName()+", ");
		
		summary = toReturn.toString();
		return summary;
	}
	public void setOrderstatus(String inOrderstatus) {
		this.orderstatus=inOrderstatus;
		
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	
}
