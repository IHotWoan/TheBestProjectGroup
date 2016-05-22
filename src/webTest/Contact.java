/**
 * 
 */
package webTest;

/**
 * The class for contact formula.
 * @author songhokun
 *
 */
public class Contact {
	public enum Matter{
		general("General customer service"),
		order("Order takes too long time!"),
		guarantee("Product support"),
		rejected("Why is my order rejected?");
		
		private String label;
		
		private Matter(String label){
			this.label = label;
		}
		public String getLabel(){
			return label;
		}
		};
	
	private Matter subject;
	private String customerName;
	private String customerEmail;
	private String customerMessage;
	private boolean read;
	private int id;
	
	/**
	 * Empty constructor
	 */
	public Contact(){
		
	}
	//getters and setters begin.
	public String getCustomerName() {
		return customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}
	public Matter getSubject() {
		return subject;
	}
	public void setSubject(Matter subject) {
		this.subject = subject;
	}
	public void setSubject(String insubject) {
		for(Matter i : Matter.values()){
			if(i.name().equals(insubject)){
				this.subject = i;
				break;
			}
		}
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
