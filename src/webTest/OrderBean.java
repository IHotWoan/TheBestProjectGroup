/**
 * 
 */
package webTest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 * @author songhokun
 *
 */
@ManagedBean(name="orderbean")
@SessionScoped
public class OrderBean {
	private Order selectedOrder;
	private String ordernumber;
	private String email;

	public OrderBean(){
		HttpSession session = SessionBean.getSession();
		selectedOrder = (Order) session.getAttribute("selectedOrder");
		 if(selectedOrder==null){
			 selectedOrder = new Order();
		 }
		 session.setAttribute("selectedOrder",selectedOrder);
	}
	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}
	
	
}
