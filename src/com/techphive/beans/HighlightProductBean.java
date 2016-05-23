/**
 * 
 */
package com.techphive.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.techphive.supportclasses.MysqlConnect;
import com.techphive.supportclasses.Product;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Pick up top selling products for main page.
 * @author songhokun
 *
 */
@ManagedBean(name="highlight")
@SessionScoped
public class HighlightProductBean implements Serializable{
	private static final long serialVersionUID = 4038383404714180997L;
	private final int NUMBER_OF_TOP_PRODUCTS=10;
	private final int NUMBER_OF_SPECIAL=3;
	
	private Product[] topProducts = new Product[NUMBER_OF_TOP_PRODUCTS];
	private Product[] specialSelection = new Product[NUMBER_OF_SPECIAL];
	private ArrayList<String> idSelection = new ArrayList<String>();
	
	public Product[] getTopProducts() {
		return topProducts;
	}

	public Product[] getSpecialSelection() {
		return specialSelection;
	}

	public void setTopProducts(Product[] topProducts) {
		this.topProducts = topProducts;
	}

	public void setSpecialSelection(Product[] specialSelection) {
		this.specialSelection = specialSelection;
	}
	

	public ArrayList<String> getIdSelection() {
		return idSelection;
	}

	public void setIdSelection(ArrayList<String> idSelection) {
		this.idSelection = idSelection;
	}

	/**
	 * Constructor of the highlight product bean.
	 */
	public HighlightProductBean(){
		HttpSession session = SessionBean.getSession();
        if(session.getAttribute("supercategory")==null)
        	new SuperCategory();
        
		/**
		 * Temporry array list which is used to count sales of product
		 */
		ArrayList<Productmatch> productsales = new ArrayList<Productmatch>();
		//SELECT COUNT(*) FROM shopdb.products;
		try {
			MysqlConnect db = new MysqlConnect();
			ResultSet rs;
			
			rs = db.query("SELECT COUNT(*) FROM shopdb.products;");
			rs.next();
			int n = rs.getInt(1);
			
			for(int i=1;i<=n;i++){
				String command="SELECT ordereditems_quantity FROM ordereditems inner join products on "
						+ "ordereditems.ordereditems_product = products.product_ID where product_deleted=false "
						+ "and ordereditems_product="+i;
				rs = db.query(command);
				int counts=0;
				while(rs.next()){
					counts+=rs.getInt(1);
				}
				if(counts!=0)
					productsales.add(new Productmatch(String.valueOf(i),counts));
			}
			rs = db.query("SELECT banner_productID FROM shopdb.banners;");
			for(int i=0;i<NUMBER_OF_SPECIAL;i++){
				rs.next();
				String receivedID = rs.getString(1);
				specialSelection[i]=SuperCategory.getSpecificProduct(receivedID);
				idSelection.add(specialSelection[i].getProductID());
			}
			db.close();
			db = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		productsales.sort(new Comparator<Productmatch>(){
			@Override
			public int compare(Productmatch first, Productmatch second){
				return second.count - first.count;
			}
		});
		
		for(int i=0;i<NUMBER_OF_TOP_PRODUCTS;i++){
			topProducts[i]=SuperCategory.getSpecificProduct(productsales.get(i).ID);
		}
	}
	private class Productmatch{
		private String ID;
		private int count;
		
		public Productmatch(String inID, int c){
			this.ID=inID;
			this.count = c;
		}
		
	}
	
	//this one is not working. it does not receive proper inputs from select one menu in managemainpage (admin page).
	public String saveSelection(){
		try {
			MysqlConnect db = new MysqlConnect();
			
			for(int i=0;i<NUMBER_OF_SPECIAL;i++){
				db.insert("UPDATE `shopdb`.`banners` SET `banner_productID`='"+idSelection.get(i)+"' WHERE `banner_id`="+i);
				specialSelection[i]=SuperCategory.getSpecificProduct(idSelection.get(i));
			}
			
			db.close();
			db = null;
		} catch (SQLException e) {
			System.err.println("There was an error during saving the selection!!");
			e.printStackTrace();
		}
		return "managemainpage";
	}
}
