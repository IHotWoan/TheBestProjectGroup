package webTest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private ArrayList<SubCategory> subCategoryArray = new ArrayList<SubCategory>();
	
	private String searchString = "";

	private MysqlConnect db;
	private ResultSet rs;
	
	private String productName;
	private String productCategory;
	private String productBrand;
	private Double productPrice;
	private String productDescription;
	private int productQuantity;

	private String categoryName;
	private String categoryID;

	private String subCategoryName;
	private String subCategoryID;
	
	
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

	public String getSubCategoryName() {
		return subCategoryName;
	}
	public String getSubCategoryID() {
		return subCategoryID;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public void setSubCategoryID(String subCategoryID) {
		this.subCategoryID = subCategoryID;
	}


	public SuperCategory(){
		
		refreshAllProducts();
		
		HttpSession session = SessionBean.getSession();
        session.setAttribute("supercategory", this);
	}
	
	public String productSearch(){
		
		db = new MysqlConnect();
		
		try {
			
			productArray.clear();
			
			rs = db.query("SELECT * FROM products inner join category on products.product_category=category.category_id inner join brands on products.product_brand=brands.brand_ID");
			
			while(rs.next()){
				
				Product searchProduct = new Product();
				
				if(rs.getString("product_name").toUpperCase().contains(searchString.toUpperCase())){
					
					searchProduct.setProductID(rs.getString("product_ID"));
					searchProduct.setName(rs.getString("product_name"));
					searchProduct.setCategoryID(rs.getString("category_ID"));
					searchProduct.setCategoryName(rs.getString("category_name"));
					searchProduct.setBrandName(rs.getString("brand_name"));
					searchProduct.setBrandID(rs.getString("brand_ID"));
					searchProduct.setDescription(rs.getString("product_description"));
					searchProduct.setPrice(Double.parseDouble(rs.getString("product_price")));
					searchProduct.setQuantity(Integer.parseInt(rs.getString("product_quantity")));
					
					productArray.add(searchProduct);
					
				}
				else if(rs.getString("brand_name").toUpperCase().contains(searchString.toUpperCase())){
					
					searchProduct.setProductID(rs.getString("product_ID"));
					searchProduct.setName(rs.getString("product_name"));
					searchProduct.setCategoryID(rs.getString("category_ID"));
					searchProduct.setCategoryName(rs.getString("category_name"));
					searchProduct.setBrandName(rs.getString("brand_name"));
					searchProduct.setBrandID(rs.getString("brand_ID"));
					searchProduct.setDescription(rs.getString("product_description"));
					searchProduct.setPrice(Double.parseDouble(rs.getString("product_price")));
					searchProduct.setQuantity(Integer.parseInt(rs.getString("product_quantity")));
					
					productArray.add(searchProduct);
					
				}
				else if(rs.getString("category_name").toUpperCase().contains(searchString.toUpperCase())){
					
					searchProduct.setProductID(rs.getString("product_ID"));
					searchProduct.setName(rs.getString("product_name"));
					searchProduct.setCategoryID(rs.getString("category_ID"));
					searchProduct.setCategoryName(rs.getString("category_name"));
					searchProduct.setBrandName(rs.getString("brand_name"));
					searchProduct.setBrandID(rs.getString("brand_ID"));
					searchProduct.setDescription(rs.getString("product_description"));
					searchProduct.setPrice(Double.parseDouble(rs.getString("product_price")));
					searchProduct.setQuantity(Integer.parseInt(rs.getString("product_quantity")));
					
					productArray.add(searchProduct);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db=null;
		
		return "index";
		
	}
	
	public String refreshAllProducts(){
		
		categoryArray.clear();
		productArray.clear();
		subCategoryArray.clear();
		
		db = new MysqlConnect();
		try {

			rs = db.query("SELECT * FROM category");

			while(rs.next()){
				
				Category category = new Category(rs.getString("category_name"));
				
				categoryArray.add(category);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			rs = db.query("SELECT * FROM brands");

			while(rs.next()){

				SubCategory subCategory = new SubCategory();
				subCategory.setSubCategoryName(rs.getString("brand_name"));
				subCategory.setSubCategoryID(rs.getString("brand_ID"));

				subCategoryArray.add(subCategory);

			}
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db=null;
		
		return "index";
		
	}
	
	public ArrayList<Category> getCategoryArray(){
		
		return categoryArray;
		
	}

	public ArrayList<SubCategory> getSubCategoryArray(){

		return subCategoryArray;

	}
	
	public static void addToProductArray(Product n){
		
		productArray.add(n);
	}
	/**
	 * Songho
	 * @param Product ID in string 
	 * @return a product
	 */
	public static Product getSpecificProduct(String id){
		for(Product p : productArray){
			if(p.getProductID().equals(id)){
				return p;
			}
		}
		return null;
	}
	public ArrayList<Product> getProductArray(){
		
		return productArray;
	}
	
	public String confirmProductEdit() {
		db = new MysqlConnect();
		for (Product product : productArray){
			
			if(product.getProductID().equals(getProductID())){
				try {
					db.insert("UPDATE `shopdb`.`products` SET product_ID='"+product.getProductID()+"', product_name='"+product.getName()+"'"
							+ ", product_category='"+product.getCategoryID()+"', product_description='"+product.getDescription()+"',"
									+ " product_price='"+product.getPrice()+"', product_quantity='"+product.getQuantity()+"', product_brand='"+product.getBrandID()+"' where product_ID='"+getProductID()+"'");
					
					for(Category c: categoryArray){
						if(c.getCategoryID().equals(product.getCategoryID())){
							product.setCategoryName(c.getCategoryName());
							break;
						}
					}
					for(SubCategory s: subCategoryArray){
						if(s.getSubCategoryID().equals(product.getBrandID())){
							product.setBrandName(s.getSubCategoryName());
							break;
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				product.setEditable(false);
				break;
			}
		}
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = null;
		
		return null;
		
	}
	
	public String editAction(Product product) {
	    
		product.setEditable(true);
		return null;
	}

	public String confirmCategoryEdit() {
		db = new MysqlConnect();
		for (Category category : categoryArray){

			if(category.getCategoryID().equals(getCategoryID())){
				try {
					db.insert("UPDATE `shopdb`.`category` SET category_ID='"+category.getCategoryID()+"', category_name='"+category.getCategoryName()
							+"' where category_ID='"+getCategoryID()+"'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				category.setEditable(false);
				break;
			}
		}
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db=null;

		return null;

	}

	public String editCategory(Category category) {

		category.setEditable(true);
		return null;
	}


	
	public String confirmProductDelete(String productID) {
		db = new MysqlConnect();
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
		
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = null;
		return null;
	}
	
	public String deleteAction(Product product) {
	    
		product.setDeletable(true);
		return null;
	}

	public String confirmCategoryDelete(String categoryID) {
		db = new MysqlConnect();
		for (Category category : categoryArray){

			if(category.getCategoryID().equals(categoryID)){
				try {
					db.insert("DELETE FROM category WHERE category_id='"+categoryID+"'");
					category.setDeletable(false);
					categoryArray.remove(category);
					break;
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= null;

		return null;
	}

	public String deleteCategory(Category category) {

		category.setDeletable(true);
		return null;
	}
	
	/*
	 * addProduct() gives error if product name has punctuation in it like apostrophes etc.
	 */
	
	public String addProduct(){
		db = new MysqlConnect();
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
			db.close();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void clearProductArray() {
		
		productArray.clear();
		
	}

	public String addCategory(){
		db= new MysqlConnect();
		try {
			db.insert("INSERT into `shopdb`.`category` (category_name)" + "VALUES ('"+getCategoryName()+"')");

			Category category = new Category();
			category.setCategoryName(getCategoryName());

			rs = db.query("SELECT category_ID from category order by category_ID desc limit 1");
			rs.next();
			category.setCategoryID(rs.getString(1));

			categoryArray.add(category);
			db.close();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String addSubCategory(){
		db = new MysqlConnect();
		try {
			db.insert("INSERT into `shopdb`.`brands` (brand_name)" + "VALUES ('"+getSubCategoryName()+"')");

			SubCategory subcategory = new SubCategory();
			subcategory.setSubCategoryName(getSubCategoryName());

			rs = db.query("SELECT brand_ID from brands order by brand_ID desc limit 1");
			rs.next();
			subcategory.setSubCategoryID(rs.getString(1));

			subCategoryArray.add(subcategory);
			db.close();
			db=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String confirmSubCategoryEdit() {
		db = new MysqlConnect();
		for (SubCategory subcategory : subCategoryArray){

			if(subcategory.getSubCategoryID().equals(getSubCategoryID())){
				try {
					db.insert("UPDATE `shopdb`.`brands` SET brand_ID='"+subcategory.getSubCategoryID()+"', brand_name='"+subcategory.getSubCategoryName()
							+"' where brand_ID='"+getSubCategoryID()+"'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				subcategory.setEditable(false);
				break;
			}
		}
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = null;

		return null;

	}

	public String editSubCategory(SubCategory subcategory) {

		subcategory.setEditable(true);
		return null;
	}


	public String confirmSubCategoryDelete(String subCategoryID) {
		db = new MysqlConnect();
		for (SubCategory subcategory : subCategoryArray){

			if(subcategory.getSubCategoryID().equals(subCategoryID)){
				try {
					db.insert("DELETE FROM brands WHERE brand_id='"+subCategoryID+"'");
					subcategory.setDeletable(false);
					subCategoryArray.remove(subcategory);
					break;
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String deleteSubCategory(SubCategory subcategory) {

		subcategory.setDeletable(true);
		return null;
	}

	public void hideBrands(){

		for(Category category: categoryArray){

			category.setDisplayBrands(false);

		}

	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
}
