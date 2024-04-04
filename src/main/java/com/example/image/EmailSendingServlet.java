package com.example.image;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/sendEmail")
public class EmailSendingServlet extends HttpServlet {

    // Properties for the email session should be class members so they can be used
    // in both the try block where they are defined and in the try block where the email is sent.
    private Properties emailProperties;
    private Session emailSession;

    // Initialize the servlet and load the email properties
    // This is done once when the servlet is first loaded into memory
    @Override
    public void init() throws ServletException {
        emailProperties = new Properties();
        InputStream input = null;

        try {
            // Load the properties file from the classpath
            input = getClass().getClassLoader().getResourceAsStream("config.properties");

            // Load the properties
            emailProperties.load(input);

            // Configure email session here
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            emailProperties.put("mail.smtp.host", "smtp.gmail.com");
            emailProperties.put("mail.smtp.port", "587");

            emailSession = Session.getInstance(emailProperties, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            emailProperties.getProperty("email.user"),
                            emailProperties.getProperty("email.pass")
                    );
                }
            });

        } catch (IOException ex) {
            throw new ServletException("Could not load email properties", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String messageText = request.getParameter("message");

        try {
            Message mimeMessage = new MimeMessage(emailSession);
            mimeMessage.setFrom(new InternetAddress(emailProperties.getProperty("email.user")));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(messageText);

            Transport.send(mimeMessage);

            response.getWriter().print("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().print("Error during sending email");
        }
    }
}
