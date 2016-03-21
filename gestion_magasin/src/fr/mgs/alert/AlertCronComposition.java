package fr.mgs.alert;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.mgs.web.storekeeper.AlertController;

public class AlertCronComposition {
	
	public static void sendMessage(String text) { 
	    
		final String username = "trielli.anthony84@gmail.com";
		final String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("trielli.anthony84@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("trielli.anthony84@gmail.com"));
			message.setSubject("Alerte Mail");
			message.setText(text);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	    
	}
	public static void main (String [] args){
		
		// on instancie la classe de construction d'alerte
		AlertController Al = new AlertController();
		Al.init();
		String corps_message ="<div style='margin:auto;'>";
		
		// Liste des produit en demande
		corps_message += "<h2>Produits en demande </h2><table><th><td>R�f�rence Produit</td><td>D�signation</td></th>";
		for (int i = 0 ; i < Al.getOnDemandProducts().size(); ++i){
			corps_message +="<tr><td>"+Al.getOnDemandProducts().get(i).getProductId()+"</td><td>"+Al.getOnDemandProducts().get(i).getDesignation()+"</td></tr>";
		}
		corps_message +="</table>";
		
		// Produits date expiration
		corps_message += "<h2>Lots expir�s </h2><table><th><td>R�f�rence Lot </td><td>Date d'expiration</td></th>";
		for (int i = 0 ; i < Al.getOutOfDateLots().size(); ++i){
			corps_message +="<tr><td>"+Al.getOutOfDateLots().get(i).getLotId()+"</td><td>"+Al.getOutOfDateLots().get(i).getExpirationDate()+"</td></tr>";
		}
		corps_message +="</table>";
		
		// Produits stock faible
		corps_message += "<h2>Produits stock faible </h2><table><th><td>R�f�rence Produit</td><td>D�signation</td></th>";
		for (int i = 0 ; i < Al.getShortageStockProducts().size(); ++i){
			corps_message +="<tr><td>"+Al.getShortageStockProducts().get(i).getProductId()+"</td><td>"+Al.getShortageStockProducts().get(i).getDesignation()+"</td></tr>";
		}
		corps_message +="</table>";
		
		sendMessage(corps_message);
	}
}
