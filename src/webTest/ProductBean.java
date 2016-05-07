/**
 * 
 */
package webTest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;


/**
 * @author songhokun
 *
 */
@ManagedBean(name="productbean")
@SessionScoped
public class ProductBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1463081015373942774L;
	private Product selectedProduct;

	/**
	 * @return the selectedProduct
	 */
	public Product getSelectedProduct() {
		return selectedProduct;
	}

	/**
	 * @param selectedProduct the selectedProduct to set
	 */
	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	
}
