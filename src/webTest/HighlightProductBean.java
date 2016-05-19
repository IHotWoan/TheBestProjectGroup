/**
 * 
 */
package webTest;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import webTest.Order.Status;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Pick up top selling products
 * @author songhokun
 *
 */
@ManagedBean(name="highlight")
@SessionScoped
public class HighlightProductBean implements Serializable{
	private static final long serialVersionUID = 4038383404714180997L;
	
	private Product[] topProducts = new Product[10];
	
	public Product[] getTopProducts() {
		return topProducts;
	}
	
	
	public HighlightProductBean(){
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
}
