/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author songhokun
 *
 */
//@ManagedBean(name="shoppingcart")
//@SessionScoped
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = -7602876105709743261L;
	private String cartID;
	private String productID;
	private double totalCost;
	private ArrayList<Product> products = new ArrayList<Product>();
	
	/**
	 * Constructor class of shopping cart
	 */
	public ShoppingCart(){
		
	}
	public String getCartID() {
		return cartID;
	}
	public void setCartID(String cartID) {
		this.cartID = cartID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public String addProduct(Product inProduct){
		this.products.add(inProduct);
		return "viewcart";
	}
}
