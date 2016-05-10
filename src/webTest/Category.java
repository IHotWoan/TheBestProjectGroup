package webTest;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Jonathan
 *
 */

//@ManagedBean(name="category")
//@SessionScoped
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	private String categoryID;
	private boolean editable;
	private boolean deletable;
	private boolean displayBrands;
	
	private ArrayList<SubCategory> subCategoryArray = new ArrayList<SubCategory>();
	
	private MysqlConnect db;
	private ResultSet rs;
	
	public Category(){
		
	}
	
	public Category(String categoryName){
		this.db= new MysqlConnect();
		setCategoryName(categoryName);
		
		try{
			
			rs = db.query("SELECT category_ID from category where category_name='"+this.categoryName+"' is TRUE");
			
			rs.next();
			String ID = rs.getString(1);
			categoryID = ID;
			
			rs = db.query("SELECT DISTINCT brand_name from brands inner join products on products.product_brand=brands.brand_id where product_category='"+ID+"'");
			
		while(rs.next()){
			
			SubCategory subCategory = new SubCategory(rs.getString("brand_name"), getCategoryName());
			
			subCategoryArray.add(subCategory);
			
		}
		this.db.close();
		this.db=null;
		
		} catch (SQLException e) {
		e.printStackTrace();
		}
			
	}
	
	public String filterProductCategory(String categoryName){
		
		SuperCategory.clearProductArray();
		new Category(categoryName);

		return "index";
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public ArrayList<SubCategory> getSubCategoryArray(){
		
		return subCategoryArray;
		
	}

	public boolean isDisplayBrands() {
		return displayBrands;
	}

	public void setDisplayBrands(boolean displayBrands) {
		this.displayBrands = displayBrands;
	}


}
