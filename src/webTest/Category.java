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

@ManagedBean(name="category")
@SessionScoped
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	
	private ArrayList<SubCategory> subCategoryArray = new ArrayList<SubCategory>();
	private static ArrayList<Product> productArray = new ArrayList<Product>();
	
	private MysqlConnect db = new MysqlConnect();
	private ResultSet rs;
	
	public Category(){
		
	}
	
	public Category(String categoryName){
		
		setCategoryName(categoryName);
		
		try{
			
			rs = db.query("SELECT category_ID from category where category_name='"+this.categoryName+"' is TRUE");
			
			rs.next();
			String ID = rs.getString(1);
			
			rs = db.query("SELECT DISTINCT brand_name from brands inner join products on products.product_brand=brands.brand_id where product_category='"+ID+"'");
			
		while(rs.next()){
			
			SubCategory subCategory = new SubCategory(rs.getString("brand_name"), getCategoryName());
			
			subCategoryArray.add(subCategory);
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
		}
			
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<SubCategory> getSubCategoryArray(){
		
		return subCategoryArray;
		
	} 
	
	public static void addToProductArray(Product n){
		
		productArray.add(n);
	}
	
	public ArrayList<Product> getProductArray(String n){
		
		setCategoryName(n);
		
		populateArray();
		
		return productArray;
	}
	
	public void populateArray(){
		
		try{
			rs = db.query("SELECT category_ID from category where category_name='"+this.categoryName+"' is TRUE");
			
			rs.next();
			String ID = rs.getString(1);
			
			rs = db.query("SELECT DISTINCT brand_name from brands inner join products on products.product_brand=brands.brand_id where product_category='"+ID+"'");
			
		while(rs.next()){
			
			SubCategory subCategory = new SubCategory(rs.getString("brand_name"), getCategoryName());
			
			subCategoryArray.add(subCategory);
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
		}

	}
	
}
