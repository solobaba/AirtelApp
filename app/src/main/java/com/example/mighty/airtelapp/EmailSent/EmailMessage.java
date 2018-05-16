package com.example.mighty.airtelapp.EmailSent;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;


public class EmailMessage extends AsyncTask<String, Void, String> {
    private final Context context;
    private Session session;

    //Declaring Variables
    


    //Information to send email
    private String email;
    private String subject;
    private String message;

    //Progress dialog to show while sending email
    //private ProgressDialog progressDialog;

    //Class Constructor
    public EmailMessage(Context context, String email, String subject, String message){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        //progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
        Toast.makeText(context,"Sending message Please wait...",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params){
        //Creating properties
        //Configuring properties for gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        //Creating a new session
//        session = Session.getDefaultInstance(properties, new Authenticator() {
//            //Authenticating the password
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(Addresses.EMAIL, Addresses.PASSWORD);
//            }
//        });

        try {
            //Creating MimeMessage object
            MimeMessage msg = new MimeMessage(session);
            //Set sender address
            msg.setFrom(new InternetAddress("solomon.oduniyi@gmail.com"));
            //Set receiver
            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            //Set subject
            msg.setSubject(subject);
            //Adding content or message
            msg.setContent(message);
            //Sending email
            Transport.send(msg);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void execute() {
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
    }
}