/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author songhokun
 *
 */
@ManagedBean(name="descriptionbean")
@SessionScoped
public class ProductDescriptionBean implements Serializable{
	
	private static final long serialVersionUID = -3695237896794573474L;
	private Product selectedProduct;
	
	public Product getSelectedProduct() {
		return selectedProduct;
	}
	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	
	public String savechanges(){
		FacesContext context = FacesContext.getCurrentInstance();
		MysqlConnect db = new MysqlConnect();
		
		try {
			String command = "UPDATE `shopdb`.`products` SET `product_description`='"+selectedProduct.getDescription()+
					"', `product_spec`='"+selectedProduct.getSpec()+"' WHERE `product_ID`='"+selectedProduct.getProductID()+"'";
			db.insert(command);
			db.close();
			db = null;
			context.addMessage(null, new FacesMessage("Description was sucessfully updated.") );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.addMessage(null, new FacesMessage("Error! Description could not be updated") );
		}
		
		return "manageproducts";
	}
	
}
