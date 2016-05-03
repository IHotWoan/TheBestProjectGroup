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
	
	private String productName;
	private String productCategory;
	private String productBrand;
	private Double productPrice;
	private String productDescription;
	private int productQuantity;
	
	
	private String productID;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}
	
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
	
	public String confirmProductEdit() {
	    
		for (Product product : productArray){
			
			if(product.getProductID().equals(getProductID())){
				try {
					db.insert("UPDATE `shopdb`.`products` SET product_ID='"+product.getProductID()+"', product_name='"+product.getName()+"'"
							+ ", product_category='"+product.getCategoryID()+"', product_description='"+product.getDescription()+"',"
									+ " product_price='"+product.getPrice()+"', product_quantity='"+product.getQuantity()+"' where product_ID='"+getProductID()+"'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			product.setEditable(false);
		}
		
		return null;
		
	}
	
	public String editAction(Product product) {
	    
		product.setEditable(true);
		return null;
	}
	
	public String confirmProductDelete(String productID) {
		
		for (Product product : productArray){
			
			if(product.getProductID().equals(productID)){
				try {
					db.insert("DELETE FROM products WHERE product_id='"+productID+"'");
					product.setDeletable(false);
					productArray.remove(product);
					break;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public String deleteAction(Product product) {
	    
		product.setDeletable(true);
		return null;
	}
	
	/*
	 * addProduct() gives error if product name has punctuation in it like apostrophes etc.
	 */
	
	public String addProduct(){
		
		try {
			db.insert("INSERT into `shopdb`.`products` (product_name,product_category,product_brand,product_price,product_description,product_quantity) "
					+ "VALUES ('"+getProductName()+"', "+Integer.parseInt(getProductCategory())+", "+Integer.parseInt(getProductBrand())+", "+getProductPrice()+", '"+getProductDescription()+"', "+getProductQuantity()+")");
			
			Product product = new Product();
			product.setName(getProductName());
			product.setCategoryID(getProductCategory());
			product.setBrandID(getProductBrand());
			product.setPrice(getProductPrice());
			product.setDescription(getProductDescription());
			product.setQuantity(getProductQuantity());
			
			rs = db.query("SELECT product_ID from products order by product_ID desc limit 1");
			rs.next();
			product.setProductID(rs.getString(1));
			
			productArray.add(product);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
