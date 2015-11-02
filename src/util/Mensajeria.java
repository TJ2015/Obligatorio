package util;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mensajeria {	
	
	//Envia un correo a la direccion "enviarA"
	//Con el asunto "asunto"
	//Con el mensaje "mensaje", el cual puede ser en formato HTML
	//Devuelve TRUE si envia el correo, FALSE en otro caso
	public boolean enviarCorreo(String enviarA, String asunto, String mensaje)
	{
		final String usuario = "sapojava@gmail.com";
		final String contrasenia = "sapojava123456";
		
		boolean envio = false;
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		
		Session sesion = Session.getInstance(prop, 
			new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(usuario, contrasenia);
				}
			}
		);
		
		try {
			Message correo = new MimeMessage(sesion);
			correo.setFrom(new InternetAddress(usuario));
			correo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(enviarA));
			correo.setSubject(asunto);
			correo.setContent(mensaje, "text/html; charset=utf-8");
			Transport.send(correo);
			envio = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return envio;
	}
}

