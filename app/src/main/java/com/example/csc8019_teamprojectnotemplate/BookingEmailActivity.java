package com.example.csc8019_teamprojectnotemplate;
/**
 * @Screen BookingEmailActivity
 * @description BookingEmailActivity is used to give the email id of the user and thereby sends a realtime Email of booking confirmation
 * we have created a Gmail for our team project which is used to send confirmation mail to the user automatically in realtime
 * @author Raj Kiran Pasumarthi
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BookingEmailActivity extends AppCompatActivity {

    /**
     * Declaring the state variables
     */
    private Button back;
    private Button next;
    TextView mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_email);


        /**
         * initializing the state variables
         */
        back = findViewById(R.id.buttonBack);
        mail = findViewById(R.id.inputEmail);
        next = findViewById(R.id.buttonNext);


        /**
         * setting up the back button action
         */

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                /* Transition animation between screens */
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


        /**
         * initializing all the required values from intent to variables with appropriate datatypes
         */
        String date = getIntent().getStringExtra("date");
        String castle = getIntent().getStringExtra("castle");
        String station = getIntent().getStringExtra("selectedStation");
        Intent mIntent = getIntent();
        int tickets = mIntent.getIntExtra("passengerNo", 0);
        String journeytime = getIntent().getStringExtra("duration");
        String IbBustime = getIntent().getStringExtra("IbBustime");
        String ObBustime = getIntent().getStringExtra("ObBustime");
        String[] busses = mIntent.getStringArrayExtra("busses");
        String[] BusOperators = mIntent.getStringArrayExtra("busoperator");

        /**
         * initializing intent and transferring data to next activity using intent
         */
        Intent act = new Intent(BookingEmailActivity.this, BookingSuccessActivity.class);
        act.putExtra("date", date);
        act.putExtra("castle", castle);
        act.putExtra("selectedStation", station);
        act.putExtra("passengerNo", tickets);
        act.putExtra("duration", journeytime);
        act.putExtra("IbBustime", IbBustime);
        act.putExtra("ObBustime", ObBustime);
        act.putExtra("busses", busses);
        act.putExtra("busOperator", BusOperators);


        /**
         * Creating a string and initializing in an order that should be displayed in the Email
         */
        String display_in_mail = "You have successfully booked your tickets \nOutbound Details\n" + station +" --> "+castle+ "\n" + date
                + "\n" + "Passengers:" + tickets + "\nBus Details: " + busses[0] + "\nDeparts: " + ObBustime +
                "\nBusOperator: " + BusOperators[0]+"\n\nInbound Details\n"+castle+" --> "+ station + "\n" + date
                + "\n" + "Passengers:" + tickets + "\nBus Details: " + busses[busses.length-1] + "\nDeparts: " + IbBustime +
                "\nBusOperator: " + BusOperators[BusOperators.length-1]+"\n\n\nThanks for using our service :)\n\nTeam13";


        /**
         * Next button is used to move to next activity
         * this method starts the intent which is initialized above
         * It performs sending mail in real time
         * It starts the intent
         */

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkmail = mail.getText().toString();
                //below is the pattern for validating email.
                //It includes Alpha-numerics, dot, hyphen and underscore followed by "@AnyDomain.com(any three characters after dot) and it should not start with numerics
                String regexPattern = "^[a-zA-Z]+[(a-zA-Z0-9-\\\\_\\\\.!\\\\D)]*[(a-zA-Z0-9)]+@[(a-zA-Z)]+\\.[(a-zA-Z)]{3}";
                boolean b = Pattern.compile(regexPattern).matcher(checkmail).matches();
                if (b == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid Email", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startActivity(act);
                    /* Transition animation between screens */
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    final String username = "ncl.team13@gmail.com";
                    final String password = "testing@team13";
                    String msg = "congrats!!!";
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getText().toString()));
                        message.setSubject("Team13:Booking confirmation");
                        message.setText(display_in_mail);
                        Transport.send(message);
                        Toast.makeText(getApplicationContext(), "Confirmation mail sent successfully", Toast.LENGTH_LONG).show();
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
}







