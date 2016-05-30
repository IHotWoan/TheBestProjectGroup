/**
 * 
 */
package com.techphive.supportclasses;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author songhokun
 *
 * Mail is a supporting class for ShoppingCartBean class and OrderTable class.
 * The class sends an e-mail 
 */

public class Mail {

	 private Session session;
	 
	 public Mail(){
		 Context initialContext;
		 try {
			initialContext = new InitialContext();
			session = (Session) initialContext.lookup("java:/shopmail");
			
		} catch (NamingException e) {
			System.err.println("No SMTP information is set up from the WildFly");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 public void send(String addresses, String topic, String textMessage) {
		 try {
			 	session.getProperties().put("mail.smtp.starttls.enable", true);
			 	MimeMessage message = new MimeMessage(session);
			 	String senderemail = session.getProperty("mail.smtp.user");
			 	
			 	message.setSender(new InternetAddress(senderemail));
			 	message.setFrom(new InternetAddress(senderemail,"TechPhive"));
			 	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
			 	message.setSubject(topic);
			 	message.setContent(textMessage, "text/html; charset=utf-8");
	            
	            Transport.send(message);
	            }
		 catch (MessagingException | UnsupportedEncodingException e) {
	            	Logger.getLogger(Mail.class.getName()).log(Level.WARNING, "Cannot send mail", e);
	        }
		 }

}
