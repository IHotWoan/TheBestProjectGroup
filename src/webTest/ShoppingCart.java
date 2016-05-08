/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * @author songhokun
 *
 */
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = -7602876105709743261L;
	private String cartID;
	private String productID;
	private double totalCost;
	private ArrayList<Product> products = new ArrayList<Product>();
	private Order order;
	
	/**
	 * Constructor class of shopping cart
	 */
	public ShoppingCart(){
		
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
	public void addProduct(Product inProduct){
		this.products.add(inProduct);
	}
	
	public void add(Product p){
		products.add(p);
		
	}
	public void remove(Product p){
		products.remove(p);
	}
	public void getCartCount(){
		products.size();
	}
	
	public Map<Product, Integer> getCartContents(){
		Map<Product, Integer> cartContents = new HashMap<>();
		for (Product obj: products){
			if(cartContents.containsKey(obj)){
				cartContents.put(obj, cartContents.get(obj) + 1);
			}
			else{
				cartContents.put(obj, 1);
			}
		}
		return cartContents;
	}

}
