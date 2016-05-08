/**
 * 
 */
package webTest;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 * @author songhokun
 *
 */
@ManagedBean(name="shoppingcartbean")
@SessionScoped
public class ShoppingCartBean implements Serializable{
	private ShoppingCart cart;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1625821307853226223L;
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
	
}
