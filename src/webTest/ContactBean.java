/**
 * 
 */
package webTest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import webTest.Contact.Matter;

/**
 * @author songhokun
 *
 */
@ManagedBean(name="contactbean")
@SessionScoped
public class ContactBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5042343315643971921L;
	private Contact input = new Contact();
	private ArrayList<Contact> unreadMessages = new ArrayList<Contact>();
	private ArrayList<Contact> readMessages = new ArrayList<Contact>();
	
	/**
	 * Constructor
	 */
	public ContactBean(){
		receiveMessages();
	}
	public String registerMessage(){
		try {
			MysqlConnect db = new MysqlConnect();
			String command = "INSERT INTO `shopdb`.`messages` (`message_subject`, `message_name`, `message_email`, `message_content`,`message_read`,`message_time`)"
					+ "VALUES ('"+input.getSubject()+"', '"+input.getCustomerName()+"', '"+input.getCustomerEmail()+"', '"+input.getCustomerMessage()+"','0',NOW());";
			
			db.insert(command);
			db.close();
			
			db=null;
			return "contactregistered";
		}
		catch (SQLException e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("There was an error registering your contact formula") );
		}
		return "contactus";
	}
	
	public String receiveMessages(){
		readMessages.clear();
		unreadMessages.clear();
		try {
			MysqlConnect db = new MysqlConnect();
			ResultSet rs;
			
			rs = db.query("SELECT * FROM shopdb.messages;");
			while(rs.next()){
				Contact temp = new Contact();
				String sub=rs.getString("message_subject");
				temp.setSubject(sub);
				temp.setCustomerName(rs.getString("message_name"));
				temp.setCustomerEmail(rs.getString("message_email"));
				temp.setCustomerMessage(rs.getString("message_content"));
				temp.setRead(rs.getBoolean("message_read"));
				temp.setId(rs.getInt("message_id"));
				temp.setDate(rs.getTimestamp("message_time"));
				
				if(temp.isRead())
					readMessages.add(temp);
				else
					unreadMessages.add(temp);
			}
			
			db.close();
			db = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "managemessages";
	}
	//Begin getters and setters
	public SelectItem[] getSubjectValues(){
		  SelectItem[] items = new SelectItem[Matter.values().length];
		  int i = 0;
		  for(Matter m : Matter.values())
			  items[i++] = new SelectItem(m, m.getLabel());
		  
		  return items;
	}

	public String readMessage(){
		if(this.input.isRead())
			return "viewmessage";
		
		this.input.setRead(true);
		try {
			MysqlConnect db = new MysqlConnect();
			db.insert("UPDATE `shopdb`.`messages` SET `message_read`='1' WHERE `message_id`='"+this.input.getId()+"';");
			
			db.close();
			db = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		readMessages.add(this.input);
		
		int i=unreadMessages.indexOf(this.input);
		if(i!=-1)
			unreadMessages.remove(i);
		
		return "viewmessage";
	}
	public String goback(){
		this.input=new Contact();
		
		return "managemessages";
	}
	public Contact getInput() {
		return input;
	}

	public void setInput(Contact input) {
		this.input = input;
	}
	public ArrayList<Contact> getUnreadMessages() {
		return unreadMessages;
	}
	public ArrayList<Contact> getReadMessages() {
		return readMessages;
	}
	public void setUnreadMessages(ArrayList<Contact> unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
	public void setReadMessages(ArrayList<Contact> readMessages) {
		this.readMessages = readMessages;
	}
	
	
	
	
	
}
