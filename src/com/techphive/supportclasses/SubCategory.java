package com.techphive.supportclasses;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.techphive.beans.SuperCategory;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

/**
 * @author Jonathan
 *
 */

//@ManagedBean(name="subcategory")
//@SessionScoped
public class SubCategory implements Serializable{

	private static final long serialVersionUID = 1L;

	private String subCategoryName;
	private String subCategoryID;
	private boolean editable;
	private boolean deletable;
	
	private MysqlConnect db;
	private ResultSet rs;
	
	public SubCategory(){
		
	}
	
	public SubCategory(String subCategoryName, String categoryName){
		this.db=new MysqlConnect();
		setSubCategoryName(subCategoryName);
		
		try{
			
			rs = db.query("SELECT category_ID from category where category_name='"+categoryName+"' is TRUE");
			
			rs.next();
			String categoryID = rs.getString(1);
			
			rs = db.query("SELECT brand_ID from brands where brand_name='"+this.subCategoryName+"' is TRUE");
			
			rs.next();
			String ID = rs.getString(1);
			subCategoryID=ID;
			
			rs= db.query("SELECT * from products where product_brand='"+ID+"' and product_category='"+categoryID+"'");
		
		while(rs.next()){
			
			Product product = new Product();
			
			//what gets changed here must be changed in SuperCategory (productSearch method) aswell.
			product.setProductID(rs.getString("product_ID"));
			product.setName(rs.getString("product_name"));
			product.setCategoryID(categoryID);
			product.setCategoryName(categoryName);
			product.setBrandName(subCategoryName);
			product.setBrandID(ID);
			product.setDescription(rs.getString("product_description"));
			product.setSpec(rs.getString("product_spec"));
			product.setPrice(Double.parseDouble(rs.getString("product_price")));
			product.setQuantity(Integer.parseInt(rs.getString("product_quantity")));
			
			SuperCategory.addToProductArray(product);
			
		}
		db.close();
		db = null;
		} catch (SQLException e) {
		e.printStackTrace();
		}
			
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

	public String filterProductBrand(String brandName, String categoryName){
		
		SuperCategory.clearProductArray();
		new SubCategory(brandName, categoryName);
		return "index";
	}
	
}
