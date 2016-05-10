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
	private Map<Product, Integer> selectedQuantity = new HashMap<Product, Integer>();

	
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
	public boolean add(Product p){
		
		
		if(selectedQuantity.containsKey(p)){
			if(selectedQuantity.get(p)+1 <= p.getQuantity())
				selectedQuantity.put(p, selectedQuantity.get(p)+1);
			else
				return false;
		}
			
		else{
			if(p.getQuantity()<1)
				return false;
			
			products.add(p);
			selectedQuantity.put(p, 1);
		}
		totalCost+=p.getPrice();
		return true;
		
	}
	public void remove(Product p){
		totalCost-=p.getPrice()*selectedQuantity.get(p);
		products.remove(p);
		selectedQuantity.remove(p);
	}
	public int getQuantity(Product p){
		return selectedQuantity.get(p);
	}

	public Map<Product, Integer> getSelectedQuantity() {
		return selectedQuantity;
	}

	public void setSelectedQuantity(Map<Product, Integer> selectedQuantity) {
		this.selectedQuantity = selectedQuantity;
	}
	

}
