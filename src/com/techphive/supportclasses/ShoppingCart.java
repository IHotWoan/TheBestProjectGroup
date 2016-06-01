/**
 * 
 */
package com.techphive.supportclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
	private Map<String, Integer> selectedQuantity = new HashMap<String, Integer>();
	private int itemsCounter=0;

	
	/**
	 * Constructor class of shopping cart
	 */
	public ShoppingCart(){
		
	}
	//actual functional methods: add /decrease/remove;
	public boolean add(Product p){
		if(selectedQuantity.containsKey(p.getProductID())){
			if(selectedQuantity.get(p.getProductID())+1 <= p.getQuantity()){
				selectedQuantity.put(p.getProductID(), selectedQuantity.get(p.getProductID())+1);
				itemsCounter++;
			}
			else{
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Fail!",  "We only have "+p.getQuantity()+" items in stock for "+p.getName()) );
				return false;
			}
				
		}
			
		else{
			if(p.getQuantity()<1){
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Fail!",p.getName()+" is sold out!") );
				return false;
			}
				
			
			products.add(p);
			selectedQuantity.put(p.getProductID(), 1);
			itemsCounter++;
		}
		totalCost+=p.getPrice();
		return true;
		
	}
	public boolean decrease(Product p){
		if(!selectedQuantity.containsKey(p.getProductID())){
			return false;
		}
		if(selectedQuantity.get(p.getProductID())-1 <= 0)
		{
			this.remove(p);
			return true;
		}
		selectedQuantity.put(p.getProductID(), selectedQuantity.get(p.getProductID())-1);	
		totalCost-=p.getPrice();
		itemsCounter--;
		return true;	
	}
	public void remove(Product p){
		totalCost-=p.getPrice()*selectedQuantity.get(p.getProductID());
		itemsCounter-=selectedQuantity.get(p.getProductID());
		products.remove(p);
		selectedQuantity.remove(p.getProductID());
	}
	public String checkout(){
		if(products.size()!=0)
			return "checkout";
		else
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("There is no item in the cart!") );
			return "viewcart";
		}
	}
	
	//Now getters and setters begin.
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
	public int getQuantity(Product p){
		return selectedQuantity.get(p.getProductID());
	}

	public Map<String, Integer> getSelectedQuantity() {
		return selectedQuantity;
	}

	public void setSelectedQuantity(Map<String, Integer> selectedQuantity) {
		this.selectedQuantity = selectedQuantity;
	}
	public int getItemsCounter() {
		return itemsCounter;
	}
	

}
