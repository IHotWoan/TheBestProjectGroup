/**
 * 
 */
package webTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author songhokun
 *
 */
@WebServlet("/productImageServlet")
public class ProductImageServlet extends HttpServlet{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4410586872804737929L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        //Long productID = Long.valueOf(request.getParameter("id"));
		 String productID = request.getParameter("id");
		 if(productID==null)
			 throw new ServletException("product id is not provided!");
		 
		 MysqlConnect db = new MysqlConnect();
		 try {
	    	String query="SELECT product_image, LENGTH(product_image) AS imageContentLength, product_imageextension FROM products WHERE product_ID="+productID;
	        ResultSet resultSet = db.query(query);
	
	        if (resultSet.next()) {
	        	Blob imageBlob = resultSet.getBlob("product_image");
	        	//tries to improve stability; it becomes unstable if there is no image picture on the product row.
	        	if(imageBlob == null){
	        		 response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        		 db.close();
	        		 return ;
	        	}
	        	response.setContentType(resultSet.getString("product_imageextension"));
	        	//response.setContentType(getServletContext().getMimeType(String.valueOf(productID)));
	            response.setContentLength(resultSet.getInt("imageContentLength"));
	            response.setHeader("Content-Disposition", "inline;filename=\"" + productID + "\"");
	

	            InputStream in = imageBlob.getBinaryStream();
	            OutputStream out = response.getOutputStream();
	            int b;
	            while ((b = in.read()) != -1) {
	                out.write(b);
	            }
	
	            in.close();
	            out.flush();
	            out.close();
	        } else {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        }
	        db.close();
	        db=null;
	        
	    } catch (SQLException e) {
	        throw new ServletException("Something failed at SQL/DB level.", e);
	    }
	 }
}
