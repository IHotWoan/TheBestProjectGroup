/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author songhokun
 *
 */
@ManagedBean(name="shoppingcartbean")
@SessionScoped
public class ShoppingCartBean implements Serializable{
	private static final long serialVersionUID = -1625821307853226223L;
	
	private ShoppingCart cart;
	private Order order = new Order();
	
	public ShoppingCartBean(){
		HttpSession session = SessionBean.getSession();
		cart = (ShoppingCart) session.getAttribute("CART");
		 if(cart==null){
			 cart = new ShoppingCart();
		 }
		 session.setAttribute("CART",cart);
	}
	public ShoppingCart getCart() {
		return cart;
	}
	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
	public String proceedOrder(){
		FacesContext context = FacesContext.getCurrentInstance();
		boolean failed=false;
		
		try {
			MysqlConnect db = new MysqlConnect();
			for(Product p: cart.getProducts())
			{
				p.getProductID();
				ResultSet rs = db.query("SELECT products.product_quantity FROM shopdb.products where product_id="+p.getProductID()+";");
				rs.next();
				int actualQuantity = rs.getInt("product_quantity");
				if(actualQuantity-cart.getSelectedQuantity().get(p) < 0){
					context.addMessage(null, new FacesMessage("Fail!",  "We only have "+actualQuantity+"items in stock for "+p.getName()) );
					failed=true;
				}
				
			}
			
			if(!failed){
				
				DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
				Date date = new Date();
				String orderID=dateFormat.format(date)+"-";
				ResultSet rs = db.query("SELECT COUNT(*) customer_order_ID FROM customers WHERE customer_order_ID LIKE '"+orderID+"%'");
				rs.next();
				orderID+=String.format("%03X", rs.getInt(1)+1);
				
				order.setOrderID(orderID);
				order.setOrderstatus("neworder");
				
				db.insert("INSERT into customers (customer_order_ID,customer_name,customer_streetaddress,customer_zipcode,customer_city,customer_phone,customer_email,customer_orderstatus) "
						+ "VALUES ('"+orderID+"','"+order.getCustomerName()+"','"+order.getCustomerAddress()+"','"+order.getZipCode()+"','"+order.getCity()+"','"+order.getPhone()+"','"+order.getEmail()+"','neworder');");
				for(Product p: cart.getProducts())
				{
					int selectedq = cart.getSelectedQuantity().get(p);
					db.insert("INSERT into ordereditems (ordereditems_order_ID,ordereditems_product,ordereditems_quantity,ordereditems_price) "+
				"VALUES ('"+orderID+"','"+p.getProductID()+"','"+selectedq+"','"+p.getPrice()*selectedq+"');");
					p.setQuantity(p.getQuantity()-selectedq);
					db.insert("UPDATE products SET `product_quantity`='"+p.getQuantity()+"' WHERE `product_ID`='"+p.getProductID()+"'");
					
				}
				
				//Emptpying shopping cart.
				HttpSession session = SessionBean.getSession();
				this.cart =null;
				session.setAttribute("CART",null);
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
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
