/**
 * 
 */
package webTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.Lob;
import javax.servlet.http.Part;

/**
 * @author songhokun
 *
 */
@SessionScoped
@ManagedBean(name="fileuploadbean")
public class FileUploadBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3474817816894473632L;
	private Part file;
	private String productID;
	private int bannerID;
	
	@Lob
    private byte[] image;
	  
	public String productUpload(){
		try{
		  InputStream inputStream = file.getInputStream();   
		  
		  ByteArrayOutputStream output = new ByteArrayOutputStream();
		  byte[] buffer = new byte[10240000];
		  for (int length = 0; (length = inputStream.read(buffer)) > 0;) output.write(buffer, 0, length);
		  
		  String ext= file.getContentType();
		  this.setImage(output.toByteArray());
		  
		  try {
			  MysqlConnect db = new MysqlConnect();
			  Connection conn = db.conn;
			  PreparedStatement statement = conn.prepareStatement("UPDATE products SET product_image =?, product_imageextension=? WHERE product_id=?");
			  
			  statement.setBytes(1, this.image);
			  //statement.setBinaryStream(1, inputStream);
			  statement.setString(2, ext);
			  statement.setInt(3, Integer.parseInt(productID));
			  statement.executeUpdate();
			  
			  db.close();
			  db=null;
			  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	        return "manageproducts";
		}
		catch(IOException e){
			System.err.println("ERROR OCCURRED IN FILE UPLOAING");
			e.printStackTrace();
		}
	        return "productimageupload";
	}
	public String bannerUpload(){
		try{
		  InputStream inputStream = file.getInputStream();   
		  
		  ByteArrayOutputStream output = new ByteArrayOutputStream();
		  byte[] buffer = new byte[10240000];
		  for (int length = 0; (length = inputStream.read(buffer)) > 0;) output.write(buffer, 0, length);
		  
		  String ext= file.getContentType();
		  this.setImage(output.toByteArray());
		  
		  try {
			  MysqlConnect db = new MysqlConnect();
			  Connection conn = db.conn;
			  PreparedStatement statement = conn.prepareStatement("UPDATE banners SET banner_image =?, banner_imageextension=? WHERE banner_id=?");
			  
			  statement.setBytes(1, this.image);
			  //statement.setBinaryStream(1, inputStream);
			  statement.setString(2, ext);
			  statement.setInt(3, bannerID);
			  statement.executeUpdate();
			  
			  db.close();
			  db=null;
			  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	        return "managemainpage";
		}
		catch(IOException e){
			System.err.println("ERROR OCCURRED IN FILE UPLOAING");
			e.printStackTrace();
		}
	        return "bannerimageupload";
	}
	public Part getFile() {
		  return file;
	  }
	 
	  public void setFile(Part file) {
		  this.file = file;
	  }
	  public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		  List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		  if(value == null)
			  throw new ValidatorException(msgs);
		  Part file = (Part)value;
		  
		  if (file.getSize() > 10240000) {
			  msgs.add(new FacesMessage("file too big"));
		  	}
		  /*
		  if (!"text/plain".equals(file.getContentType())) {
			  msgs.add(new FacesMessage("not a text file"));
			  }
			 */
		  if (!msgs.isEmpty()) {
			  throw new ValidatorException(msgs);
		  }
	  }
	public String getProductID() {
		return productID;
	}
	public void setProductID(String inProductID) {		
		this.productID = inProductID;
	}
	public int getBannerID() {
		return bannerID;
	}
	public void setBannerID(int bannerID) {
		this.bannerID = bannerID;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	  
}