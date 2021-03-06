package com.techphive.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.Category;
import com.techphive.supportclasses.MysqlConnect;
import com.techphive.supportclasses.Product;
import com.techphive.supportclasses.SubCategory;

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
	private static ArrayList<Product> deletedProductArray = new ArrayList<Product>();
	
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
		if(session.getAttribute("supercategory")==null)
			session.setAttribute("supercategory", this);
	}
	
	public String productSearch(){
		
		db = new MysqlConnect();
		
		try {
			
			productArray.clear();
			
			rs = db.query("SELECT * FROM products inner join category on products.product_category=category.category_id inner join brands on products.product_brand=brands.brand_ID where product_deleted=false");
			
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
					searchProduct.setSpec(rs.getString("product_spec"));
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
					searchProduct.setSpec(rs.getString("product_spec"));
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
					searchProduct.setSpec(rs.getString("product_spec"));
					searchProduct.setPrice(Double.parseDouble(rs.getString("product_price")));
					searchProduct.setQuantity(Integer.parseInt(rs.getString("product_quantity")));
					
					productArray.add(searchProduct);
				}
				
			}
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db=null;
		
		return "index";
		
	}
	
	public String refreshAllProducts(){
		
		categoryArray.clear();
		productArray.clear();
		deletedProductArray.clear();
		subCategoryArray.clear();
		
		db = new MysqlConnect();
		try {

			rs = db.query("SELECT * FROM category");

			while(rs.next()){
				
				Category category = new Category(rs.getString("category_name"));
				
				categoryArray.add(category);
				
			}
			
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
	
	public static void addToDeletedProductArray(Product n){
	
		deletedProductArray.add(n);
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
		FacesContext context = FacesContext.getCurrentInstance();
		
		
		db = new MysqlConnect();
		for (Product product : productArray){
			
			if(product.getProductID().equals(getProductID())){
				product.setEditable(false);
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
					e.printStackTrace();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
				}
				
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
		FacesContext context = FacesContext.getCurrentInstance();
		
		db = new MysqlConnect();
		for (Category category : categoryArray){

			if(category.getCategoryID().equals(getCategoryID())){
				category.setEditable(false);
				try {
					db.insert("UPDATE `shopdb`.`category` SET category_ID='"+category.getCategoryID()+"', category_name='"+category.getCategoryName()
							+"' where category_ID='"+getCategoryID()+"'");
				} catch (SQLException e) {
					e.printStackTrace();
					context.addMessage("categorygrowl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
				}
				context.addMessage(null, new FacesMessage("Success",  "Change is saved in the database") );
				
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
		FacesContext context = FacesContext.getCurrentInstance();
		
		db = new MysqlConnect();
		for (Product product : productArray){
			
			if(product.getProductID().equals(productID)){
				try {
					product.setDeletable(false);
					ResultSet rs = db.query("SELECT COUNT(*) FROM shopdb.ordereditems where ordereditems_product='"+productID+"' ;");
					rs.next();
					if(rs.getInt(1)==0){
						productArray.remove(product);
						db.insert("DELETE FROM `shopdb`.`products` WHERE `product_ID`='"+productID+"';");
						context.addMessage(null, new FacesMessage("Success",  "Product is deleted from the database.") );
					}
					else
					{
						db.insert("UPDATE `shopdb`.`products` set product_deleted=true WHERE product_id='"+productID+"'");
						deletedProductArray.add(product);
						productArray.remove(product);
						context.addMessage(null, new FacesMessage("Success",  "Product is archieved") );
					}
					
					break;
				} catch (SQLException e) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
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
	
	public String undoProductDelete(String productID){
		FacesContext context = FacesContext.getCurrentInstance();
		
		db = new MysqlConnect();
		for (Product product : deletedProductArray){
			
			if(product.getProductID().equals(productID)){
				try {
					product.setDeletable(false);
					db.insert("UPDATE `shopdb`.`products` set product_deleted=false WHERE product_id='"+productID+"'");
					deletedProductArray.remove(product);
					productArray.add(product);
					context.addMessage(null, new FacesMessage("Success",  "Product is restored") );
					break;
				} catch (SQLException e) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
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
		
	    if(product.isDeletable()){
	    	product.setDeletable(false);
	    }
	    else{
	    	product.setDeletable(true);
	    }
		return null;
	}

	public String confirmCategoryDelete(String categoryID) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		db = new MysqlConnect();
		for (Category category : categoryArray){

			if(category.getCategoryID().equals(categoryID)){
				try {
					category.setDeletable(false);
					db.insert("DELETE FROM category WHERE category_id='"+categoryID+"'");
					categoryArray.remove(category);
					context.addMessage(null, new FacesMessage("Success",  "Change is saved in the database") );
					break;
				}
				catch (SQLException e) {
					context.addMessage("categorygrowl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
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
		
		if(category.isDeletable()){
			
			category.setDeletable(false);
		}
		else{
			category.setDeletable(true);
		}
		return null;
	}
	
	/*
	 * addProduct() gives error if product name has punctuation in it like apostrophes etc.
	 */
	
	public String addProduct(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		db = new MysqlConnect();
		try {
			db.insert("INSERT into `shopdb`.`products` (product_name,product_category,product_brand,product_price,product_description,product_quantity) "
					+ "VALUES ('"+getProductName()+"', "+Integer.parseInt(getProductCategory())+", "+Integer.parseInt(getProductBrand())+", "+getProductPrice()+", '"+getProductDescription()+"', "+getProductQuantity()+")");
			
			Product product = new Product();
			product.setName(getProductName());
			product.setCategoryID(getProductCategory());
			product.setBrandID(getProductBrand());
			for(Category c : categoryArray){
				if(c.getCategoryID().equals(this.productCategory)){
					product.setCategoryName(c.getCategoryName());
					break;
				}
			}
			for(SubCategory sc: subCategoryArray){
				if(sc.getSubCategoryID().equals(this.productBrand)){
					product.setBrandName(sc.getSubCategoryName());
					break;
				}
			}
			product.setPrice(getProductPrice());
			product.setDescription(getProductDescription());
			product.setQuantity(getProductQuantity());
			
			rs = db.query("SELECT product_ID from products order by product_ID desc limit 1");
			rs.next();
			product.setProductID(rs.getString(1));
			
			productArray.add(product);
			db.close();
			db = null;
			
			context.addMessage(null, new FacesMessage("Sussess",  "The product is added to the database sucessfully") );
			//After adding the new product, the form should be reset // Songho
			this.productName="";
			this.productCategory="";
			this.productBrand="";
			this.productPrice=0.0;
			this.productDescription="";
			this.productQuantity=0;
			
		} catch (SQLException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Product could not be added due to error in db!"));
			e.printStackTrace();
		}
		return null;
	}

	public static void clearProductArray() {
		
		productArray.clear();
		deletedProductArray.clear();
		
	}

	public String addCategory(){
		FacesContext context = FacesContext.getCurrentInstance();
		
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
			context.addMessage(null, new FacesMessage("Sussess",  "The Category is added to the database sucessfully") );
			
		} catch (SQLException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Category could not be added due to error in db!"));
			e.printStackTrace();
		}
		return null;
	}

	public String addSubCategory(){
		FacesContext context = FacesContext.getCurrentInstance();
		
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
			context.addMessage(null, new FacesMessage("Sussess",  "The brand is added to the database sucessfully") );
			
		} catch (SQLException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Brand could not be added due to error in db!"));
			e.printStackTrace();
		}
		return null;
	}

	public String confirmSubCategoryEdit() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		db = new MysqlConnect();
		for (SubCategory subcategory : subCategoryArray){

			if(subcategory.getSubCategoryID().equals(getSubCategoryID())){
				subcategory.setEditable(false);
				try {
					db.insert("UPDATE `shopdb`.`brands` SET brand_ID='"+subcategory.getSubCategoryID()+"', brand_name='"+subcategory.getSubCategoryName()
							+"' where brand_ID='"+getSubCategoryID()+"'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					context.addMessage("subcategorygrowl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
				}
				context.addMessage("subcategorygrowl", new FacesMessage("Brand was sucessfully updated.") );
				
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
		FacesContext context = FacesContext.getCurrentInstance();
		db = new MysqlConnect();
		for (SubCategory subcategory : subCategoryArray){

			if(subcategory.getSubCategoryID().equals(subCategoryID)){
				try {
					subcategory.setDeletable(false);
					db.insert("DELETE FROM brands WHERE brand_id='"+subCategoryID+"'");
					subCategoryArray.remove(subcategory);
					context.addMessage("subcategorygrowl", new FacesMessage("Brand was sucessfully deleted.") );
					break;
				}
				catch (SQLException e) {
					e.printStackTrace();
					context.addMessage("subcategorygrowl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Error!", "Changes could not be saved due to error in db!"));
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
		
		if(subcategory.isDeletable()){
			subcategory.setDeletable(false);
		}
		else{
			subcategory.setDeletable(true);
		}
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

	public ArrayList<Product> getDeletedProductArray() {
		return deletedProductArray;
	}

	public void setDeletedProductArray(ArrayList<Product> deletedProductArray) {
		SuperCategory.deletedProductArray = deletedProductArray;
	}
	
}
