package com.example.mighty.airtelapp.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mighty.airtelapp.Utilities.Addresses;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailMessage extends AsyncTask<Void, Void, Void>{

    //Variables declaration
    private Context context;
    private Session session;

    //Email information
    private String email;
    private String subject;
    private  String message;

    //Class constructor
    public EmailMessage(Context context, String email, String subject, String message){
        //Variables initialization
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute ();
        Toast.makeText(context, "Email sent", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute (aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        //Email properties
        Properties properties = new Properties();

        //Properties configured for gmail address
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        //Session created
        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Addresses.EMAIL, Addresses.PASSWORD);
            }
        });

        try {
            //MimeMessage object
            MimeMessage msg = new MimeMessage(session);
            //Sender address
            msg.setFrom(new InternetAddress(Addresses.EMAIL));
            //Receiver address
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Subject
            msg.setSubject(subject);
            //Message
            msg.setText(message);
            //Sending email
            Transport.send(msg);
        } catch (AddressException e) {
            e.printStackTrace ();
        } catch (MessagingException e) {
            e.printStackTrace ();
        } return null;
    }
}
