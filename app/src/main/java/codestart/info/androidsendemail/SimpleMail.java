package codestart.info.androidsendemail;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Luiz Philippe
 * Site: lptag.com.br
 * E-mail: contato@lptag.com.br
 */

public class SimpleMail {

    /**CHANGE ACCORDINGLY**/
    /**MUDE CONFORMENTE**/
    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; //can be your host server smtp@yourdomain.com //pode ser seu servidor host smtp@seudominio.com
    private static final String SMTP_AUTH_USER = "seuemail@gmail.com"; //your login username/email //seu nome de usuário/e-mail de login
    private static final String SMTP_AUTH_PWD  = "suasenha"; //password/secret

    private static Message message;


    public static void sendEmail(String to, String subject, String msg){
        // Recipient's email ID needs to be mentioned.
        // O ID do e-mail do destinatário precisa ser mencionado

        // Sender's email ID needs to be mentioned
        // O ID do e-mail do remetente precisa ser mencionado
        String from = ""; //from //remetente

        final String username = SMTP_AUTH_USER;
        final String password = SMTP_AUTH_PWD;

        // Assuming you are sending email through relay.jangosmtp.net
        // Supondo que você esteja enviando e-mail através do relay.jangosmtp.net
        String host = SMTP_HOST_NAME;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");

        // Get the Session object.
        // Obtenha o objeto Session.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            // Crie um objeto MimeMessage padrão.
            message = new MimeMessage(session);

            // Set From: header field of the header.
            // Seta From (remetente): campo de cabeçalho do cabeçalho.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            // Seta To (destinatario): campo de cabeçalho do cabeçalho.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            // Seta Subject (assunto): campo de cabeçalho
            message.setSubject(subject);

            // Crie a parte da mensagem
            BodyPart messageBodyPart = new MimeBodyPart();

            // Agora defina a mensagem atual
            messageBodyPart.setContent(msg, "text/html");

            // Criar uma mensagem multipartes
            Multipart multipart = new MimeMultipart();

            // Definir parte da mensagem de texto
            multipart.addBodyPart(messageBodyPart);

//            // Part two is attachment
//            // A parte dois é o anexo
//            messageBodyPart = new MimeBodyPart();
//            String filename = Context.;
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(filename);
//            multipart.addBodyPart(messageBodyPart);

            // Envie as partes completas da mensagem
            message.setContent(multipart);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        // mensagem final
                        Transport.send(message);
                        System.out.println("Mensagem enviada com sucesso....");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}