/**
 * 
 */
package webTest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;

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
	
	private Product[] topProducts = new Product[10];
	private Product[] specialSelection = new Product[3];
	
	
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

	/**
	 * Constructor of the highlight product bean.
	 */
	public HighlightProductBean(){
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
				rs = db.query("SELECT ordereditems_quantity FROM shopdb.ordereditems where ordereditems_product="+i);
				int counts=0;
				while(rs.next()){
					counts+=rs.getInt(1);
				}
				productsales.add(new Productmatch(String.valueOf(i),counts));
				
			}
			rs = db.query("SELECT banner_productID FROM shopdb.banners;");
			for(int i=0;i<specialSelection.length;i++){
				rs.next();
				String receivedID = rs.getString(1);
				specialSelection[i]=SuperCategory.getSpecificProduct(receivedID);
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
		
		for(int i=0;i<10;i++){
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
	
	public String saveSelection(){
		try {
			MysqlConnect db = new MysqlConnect();
			
			for(int i=0;i<specialSelection.length;i++){
				db.insert("UPDATE `shopdb`.`banners` SET `banner_productID`='"+specialSelection[i].getProductID()+"' WHERE `banner_id`="+i);			
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
