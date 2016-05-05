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

@ManagedBean(name="supercategory")
@SessionScoped
public class SuperCategory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Category> categoryArray = new ArrayList<Category>();
	private static ArrayList<Product> productArray = new ArrayList<Product>();

	private MysqlConnect db = new MysqlConnect();
	private ResultSet rs;
	
	public SuperCategory(){
		
		try {
			
			rs = db.query("SELECT DISTINCT category_name from category left join products on products.product_category=category.category_id where product_category=category_id ORDER BY category_name ASC");
			
			while(rs.next()){
				
				Category category = new Category(rs.getString("category_name"));
				
				categoryArray.add(category);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Category> getCategoryArray(){
		
		return categoryArray;
		
	}
	
	public static void addToProductArray(Product n){
		
		productArray.add(n);
	}
	
	public ArrayList<Product> getProductArray(){
		
		return productArray;
	}
	
	public String saveAction() {
	    
		//get all existing value but set "editable" to false 
		for (Product product : productArray){
			product.setEditable(false);
		}
		//return to current page
		return null;
		
	}
	
	public String editAction(Product product) {
	    
		product.setEditable(true);
		return null;
	}
}
