package iotgateway;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

/*
 * this class is to send email to user and set all the parameter in STMP
 */
public class Smtp {

	public Smtp() {
		// TODO Auto-generated constructor stub
	}
/*
 * send email
 * @param mess: the message to send in email
 * @return 
 */
	public void send1(String mess) throws Exception{
        //make connection 
        Properties p = new Properties();
        // set the email"s host we use 
        p.setProperty("mail.host", "smtp.gmail.com");
        p.setProperty("mail.port", "465");
        // use the password way to certify
        p.setProperty("mail.smtp.auth", "true");
        // set protocol
        p.setProperty("mail.transport.protocol", "smtp");

        // start SSl
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        p.put("mail.smtp.ssl.enable", "true");
        p.put("mail.smtp.ssl.socketFactory", sf);

        // create session
        Session session = Session.getDefaultInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //set the email and password
                PasswordAuthentication pa = new PasswordAuthentication("jiaoyi199507", "jxjy2006");
                
                return pa;
            }
        });

        session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
            //set  email we use to send the email
            msg.setFrom(new InternetAddress("xxx@gmail.com"));
            //set email address to send the email
            msg.setRecipient(RecipientType.TO, new InternetAddress(
                    "xxx@gmail.com"));
            //set the topic name of email
            msg.setSubject("Data Alarm!! ");
            //set the content of email
            msg.setText(mess);
          //send action
            Transport.send(msg);
        
   }

}
