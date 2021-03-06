/**
 * 
 */
package com.techphive.beans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.Mail;
import com.techphive.supportclasses.MysqlConnect;
import com.techphive.supportclasses.Order;
import com.techphive.supportclasses.Product;
import com.techphive.supportclasses.ShoppingCart;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author songhokun
 * A bean class which holds a shopping cart class.
 */
@ManagedBean(name="shoppingcartbean")
@SessionScoped
public class ShoppingCartBean implements Serializable{
	private static final long serialVersionUID = -1625821307853226223L;
	
	private ShoppingCart cart;
	private Order order = new Order();
	
	/**
	 * Constructor of shopping cart bean. register cart to session.
	 */
	public ShoppingCartBean(){
		HttpSession session = SessionBean.getSession();
		cart = (ShoppingCart) session.getAttribute("CART");
		 if(cart==null){
			 cart = new ShoppingCart();
		 }
		 session.setAttribute("CART",cart);
	}

	public String proceedOrder(){
		FacesContext context = FacesContext.getCurrentInstance();
		boolean failed=false;
		
		try {
			MysqlConnect db = new MysqlConnect();
			
			//Check actual product quantity is enough for purchase. It prevents negative quantity - Songho
			for(Product p: cart.getProducts())
			{
				p.getProductID();
				ResultSet rs = db.query("SELECT products.product_quantity FROM shopdb.products where product_id="+p.getProductID()+";");
				rs.next();
				int actualQuantity = rs.getInt("product_quantity");
				if(actualQuantity-cart.getSelectedQuantity().get(p.getProductID()) < 0){
					context.addMessage(null, new FacesMessage("Fail!",  "We only have "+actualQuantity+"items in stock for "+p.getName()) );
					failed=true;
					p.setQuantity(actualQuantity);
					
					//If problem becomes unavailable while a customer tries to place an order, it should be removed from the shopping cart. 
					//This part is not tested. is going to be tested in testing phrase. - Songho
					while(cart.getSelectedQuantity().get(p.getProductID()) > actualQuantity){
						cart.decrease(p);
					}
				}
				
			}
			
			//If quantities in db are safe and ready to go
			if(!failed){
				
				//Generating Order ID
				DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
				Date date = new Date();
				String orderID=dateFormat.format(date)+"-";
				ResultSet rs = db.query("SELECT COUNT(*) customer_order_ID FROM customers WHERE customer_order_ID LIKE '"+orderID+"%'");
				rs.next();
				orderID+=String.format("%03X", rs.getInt(1)+1);
				
				order.setOrderID(orderID);
				order.setOrderstatus("neworder");
				order.setProductarray(cart.getProducts());
				order.setTotalcost((cart.getTotalCost()));
				
				//Safeguarding db by using escaping letters for input.
				order.setCity(db.escapeString(order.getCity()));
				order.setCustomerAddress(db.escapeString(order.getCustomerAddress()));
				order.setCustomerName(db.escapeString(order.getCustomerName()));
				order.setEmail(db.escapeString(order.getEmail()));
				order.setPhone(db.escapeString(order.getPhone()));
				order.setZipCode(db.escapeString(order.getZipCode()));
				
				
				Mail sendmail = new Mail();
				
				//Send order confirmation e-mail
				StringBuilder content = new StringBuilder();
				content.append("<p>Thank you for your order from TechPhive!<br />");
				content.append("Your order number is "+orderID);
				content.append(", and you have ordered following products:</p>");
				content.append("<table id=\"ordersummary:productstable\" class=\"table table-striped\"><thead>");
				content.append("<tr><th scope=\"col\"></th><th style=\"text-align: left\">Product Name</th><th style=\"text-align: left\">Category</th><th style=\"text-align: right\">Price</th>");
				content.append("<th style=\"text-align: right\">Quantity</th></tr></thead><tbody>");

				// deduct db quantity;
				
				db.insert("INSERT into customers (customer_order_ID,customer_name,customer_streetaddress,customer_zipcode,customer_city,customer_phone,customer_email,customer_orderstatus) "
						+ "VALUES ('"+orderID+"','"+order.getCustomerName()+"','"+order.getCustomerAddress()+"','"+order.getZipCode()+"','"+order.getCity()+"','"+order.getPhone()+"','"+order.getEmail()+"','neworder');");
				for(Product p: order.getProductarray())
				{
					int selectedq = cart.getSelectedQuantity().get(p.getProductID());
					db.insert("INSERT into ordereditems (ordereditems_order_ID,ordereditems_product,ordereditems_quantity,ordereditems_price) "+
				"VALUES ('"+orderID+"','"+p.getProductID()+"','"+selectedq+"','"+p.getPrice()*selectedq+"');");
					
					content.append("<tr> <td style=\"text-align: center\"><img src=\"http://songhohem.ddns.net/TechPhive/productImageServlet?id="+p.getProductID()+"\" width=\"100\" /></td>");
					content.append("<td style=\"text-align: left\">"+p.getName()+"</td>"+"<td style=\"text-align: left\">"+p.getCategoryName()+"</td>"+"<td style=\"text-align: right\">"+p.getPrice()+"</td><td style=\"text-align: right\">"+selectedq+"</td></tr>");
					//Save temporary quantity of the product to update the database. 
					p.setQuantity(p.getQuantity()-selectedq);
					db.insert("UPDATE products SET `product_quantity`='"+p.getQuantity()+"' WHERE `product_ID`='"+p.getProductID()+"'");
					
					//replace that temporary quantity into user selected quantity to store into array of products in order.
					p.setQuantity(selectedq);
					
				}
				
				content.append("</tbody></table><br /><br />Your order will normally processed within three days.</p>");
				content.append("<p>If you have further questions, please feel free to contact us.</p>");
				content.append("<p>Yours sincerely,</p><p><img src=\"http://homepage.lnu.se/student/sl222xk/logo.png\" alt=\"Logo\" width=\"200\" height=\"60\" /></p>");
				
				
				sendmail.send(order.getEmail(), "Order ID "+order.getOrderID()+" from TechPhive", content.toString());
				sendmail = null;
				
				//Emptying shopping cart.
				HttpSession session = SessionBean.getSession();
				this.cart = new ShoppingCart();
				session.setAttribute("CART",cart);
				session.setAttribute("selectedOrder", order);
				
				this.order = new Order();
			}
			db.close();
			db = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(failed)
			return "checkout";
		else
			return "orderregistered";
	}
	
	//Here comes getters and setters.
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public ShoppingCart getCart() {
		return cart;
	}
	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
	
	
}
