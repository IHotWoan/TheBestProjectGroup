package webTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Jonathan
 *
 */
public class SubCategory {

	private String subCategoryName;
	
	private ArrayList<Product> productArray = new ArrayList<Product>();
	
	private LocalConnect db = new LocalConnect();
	private ResultSet rs;
	
	public SubCategory(String subCategoryName){
			
		this.subCategoryName = subCategoryName;
		
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
			
		}
		
		} catch (SQLException e) {
		e.printStackTrace();
		}
			
	}
		
	public ArrayList<Product> getProductArray(){
		
		return productArray;
		
	}
	
}
