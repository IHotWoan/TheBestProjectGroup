package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Jonathan
 *
 */

@ManagedBean(name="subcategory")
@SessionScoped
public class SubCategory implements Serializable{

	private static final long serialVersionUID = 1L;

	private String subCategoryName;
	
	private ArrayList<Product> productArray = new ArrayList<Product>();
	
	private MysqlConnect db = new MysqlConnect();
	private ResultSet rs;
	
	public SubCetegory(){
		
	}
	public SubCategory(String subCategoryName){
		
		setSubCategoryName(subCategoryName);
		
		try{
			rs = db.query("SELECT brand_ID from brands where brand_name='"+this.subCategoryName+"' is TRUE");
			
			rs.next();
			String ID = rs.getString(1);
			
			rs= db.query("SELECT * from products where product_brand='"+ID+"'");
		
		while(rs.next()){
			
			Product product = new Product();
			
			product.setProductID(rs.getString("product_ID"));
			product.setName(rs.getString("product_name"));
			product.setCategoryID(rs.getString("product_category"));
			product.setDescription(rs.getString("product_description"));
			product.setPrice(Double.parseDouble(rs.getString("product_price")));
			product.setQuantity(Integer.parseInt(rs.getString("product_quantity")));
			
			productArray.add(product);
			Category.addToProductArray(product);
			SuperCategory.addToProductArray(product);
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
		}
			
	}
		
	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public ArrayList<Product> getProductArray(){
		
		return productArray;
		
	}
	
}
