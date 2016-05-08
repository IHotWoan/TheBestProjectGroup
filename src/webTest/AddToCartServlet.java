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
import javax.servlet.http.HttpSession;

/**
 * @author songhokun
 *
 */
@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet{
	private static final long serialVersionUID = 5400211768109066983L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Long productID = Long.valueOf(request.getParameter("id"));
	 String productID = request.getParameter("productid");
	 if(productID==null)
		 throw new ServletException("Product ID is not entered!");
	 
	 HttpSession session = request.getSession();
	 
	 ShoppingCart cart = (ShoppingCart) session.getAttribute("CART");
	 if(cart==null){
		 cart = new ShoppingCart();
	 }
	 session.setAttribute("CART",cart);
	 response.sendRedirect("index");
	}
}

