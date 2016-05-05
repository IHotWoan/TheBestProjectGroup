package Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import webTest.Category;
import webTest.LocalConnect;
import webTest.Product;
import webTest.SubCategory;
import webTest.SuperCategory;

public class TestingClass {
	
	public static void main(String args[]) throws SQLException{
		
		SuperCategory cat = new SuperCategory();
		
		for(int i=0;i<cat.getProductArray().size();i++){
			
			System.out.println(cat.getProductArray().get(i).getName());
			
		}
			

	}
	
}
