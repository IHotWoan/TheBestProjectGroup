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
 */

public class Mail {

	 private Session session;
	 
	 public Mail(){
		 Context initialContext;
		 try {
			initialContext = new InitialContext();
			session = (Session) initialContext.lookup("java:/shopmail");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	    public void send(String addresses, String topic, String textMessage) {
	 
	        try {
	        	session.getProperties().put("mail.smtp.starttls.enable", true);
	        	MimeMessage message = new MimeMessage(session);
	        	message.setSender(new InternetAddress("linuslee@web.de"));
	        	message.setFrom(new InternetAddress("linuslee@web.de","TechPhive"));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
	            message.setSubject(topic);
	            message.setContent(textMessage, "text/html; charset=utf-8");
	            
	            Transport.send(message);
	 
	        } catch (MessagingException | UnsupportedEncodingException e) {
	            Logger.getLogger(Mail.class.getName()).log(Level.WARNING, "Cannot send mail", e);
	        }
	    }

}
