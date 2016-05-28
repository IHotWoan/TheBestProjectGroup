/**
 * 
 */
package com.techphive.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.Product;
import com.techphive.supportclasses.ShoppingCart;

import java.io.Serializable;


/**
 * @author songhokun
 * Product bean is the bean class which holds Product class.
 * This holds product class picked up from the list of products inside supercategory class,
 * and show detailed information of a selected product.
 * 
 * This class is made to reduce dependency of SuperCategoryClass.
 * Mainly intended to use on customer mode.
 */
@ManagedBean(name="productbean")
@SessionScoped
public class ProductBean implements Serializable{
	private static final long serialVersionUID = -1463081015373942774L;
	private Product selectedProduct;

	/**
	 * @return the selectedProduct
	 */
	public Product getSelectedProduct() {
		return selectedProduct;
	}
	public String stockStatus(){
		if(selectedProduct.getQuantity()<=10 && selectedProduct.getQuantity()>0)
			return "only "+ selectedProduct.getQuantity() + " are left in our stock. Hurry!";
		else if(selectedProduct.getQuantity()==0)
			return "This product is sold out. unavailable.";
		else
			return "";
	}

	/**
	 * @param selectedProduct the selectedProduct to set
	 */
	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	public String addToShoppingCart(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = SessionBean.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("CART");
		 if(cart==null){
			 cart = new ShoppingCart();
		 }
		 if(!cart.add(selectedProduct)){
			 context.addMessage(null, new FacesMessage("Fail!",  "We only have "+selectedProduct.getQuantity()+" items in stock for "+selectedProduct.getName()) );
			 return "catalogue";
		 }
		 session.setAttribute("CART",cart);
		return "viewcart";
	}
	
}
