package Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import webTest.Category;
import webTest.LocalConnect;
import webTest.Product;
import webTest.SubCategory;
import webTest.SuperCategory;

public class TestingClass {
	
	public static void main(String args[]) throws SQLException{
		
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		
		StringBuilder make = new StringBuilder(input);
		for(int i=0;i<make.length();i++)
		{
			if(make.charAt(i)=='\\' || make.charAt(i)=='\'' || make.charAt(i)=='\"')
				make.insert(i++,'\\');
		}
		System.out.println(make.toString());
	}
	
}
