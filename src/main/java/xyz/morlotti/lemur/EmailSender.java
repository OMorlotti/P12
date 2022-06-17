package xyz.morlotti.lemur;

import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ConfigService;

@Component
public class EmailSender
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	public JavaMailSender getJavaMailSender() throws Exception
	{
		/*------------------------------------------------------------------------------------------------------------*/

		String host;
		String port;
		String mode;
		String user;
		String pass;

		Map<String, String> config = configService.getConfig();

		if((host = config.getOrDefault("smtp_host", "")).isEmpty()
		   ||
		   (port = config.getOrDefault("smtp_port", "")).isEmpty()
		   ||
		   (mode = config.getOrDefault("smtp_mode", "")).isEmpty()
		   ||
		   (user = config.getOrDefault("smtp_user", "")).isEmpty()
		   ||
		   (pass = config.getOrDefault("smtp_pass", "")).isEmpty()
		) {
			throw new Exception("l'application n'est pas configurée");
		}

		/*------------------------------------------------------------------------------------------------------------*/

		// On créé un mailer et on le configure
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(/*------------*/(host));
		mailSender.setPort(Integer.parseInt(port));

		mailSender.setUsername(user);
		mailSender.setPassword(pass);

		Properties props = mailSender.getJavaMailProperties();

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");

		/**/ if("1".equals(mode)) {
			props.put("mail.smtp.ssl.enable", "true");
		}
		else if("2".equals(mode)) {
			props.put("mail.smtp.starttls.enable", "true");
		}

		return mailSender;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public void sendMessage(String from, String to, String cc, String subject, String text) throws Exception
	{
		/*------------------------------------------------------------------------------------------------------------*/
		/* CREATE MAIL SENDER                                                                                         */
		/*------------------------------------------------------------------------------------------------------------*/

		JavaMailSender mailSender = getJavaMailSender();

		/*------------------------------------------------------------------------------------------------------------*/
		/* CREATE EMAIL                                                                                               */
		/*------------------------------------------------------------------------------------------------------------*/

		SimpleMailMessage message = new SimpleMailMessage();

		to = to.trim();
		if(!to.isEmpty()) {
			message.setTo(to.trim());
		}

		cc = cc.trim();
		if(!cc.isEmpty()) {
			message.setCc(cc.trim());
		}

		message.setText(text);
		message.setFrom(from.trim());
		message.setSubject(subject.trim());

		/*------------------------------------------------------------------------------------------------------------*/
		/* SEND EMAIL                                                                                                 */
		/*------------------------------------------------------------------------------------------------------------*/

		mailSender.send(message);

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
