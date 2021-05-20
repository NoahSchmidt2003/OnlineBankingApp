package de.noah.onlinebankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    Conn connection;
    String email;
    String password;
    String password2;
    AlertDialog.Builder signUP;
    AlertDialog signUPSuccess;
    AlertDialog.Builder passMatch;
    AlertDialog passDoNotMatch;
    AlertDialog.Builder userTaken;
    AlertDialog userTakenError;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();
        connection = new Conn();
        connection.connect();
        final EditText email_form = findViewById(R.id.email_address_signup);
        final EditText password_form = findViewById(R.id.password_signup);
        final EditText password_form_reenter = findViewById(R.id.password_signup_reenter);
        final Button signUpButton = findViewById(R.id.signup_button_small);
        final TextView email_text = findViewById(R.id.email_text_signup);
        final TextView password_text = findViewById(R.id.password_text_signup);
        final TextView password_text_reenter = findViewById(R.id.password_text_reenter);
        Intent home = new Intent(SignUp.this , MainActivity.class);

        AlertDialog.Builder error_missing = new AlertDialog.Builder(this);
        error_missing.setMessage("Error! Please check if all input forms are filled.");
        error_missing.setCancelable(true);


        error_missing.setPositiveButton(
                "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog missing_input = error_missing.create();

        signUP = new AlertDialog.Builder(this);
        signUP.setMessage("Account creation successful you will be redirected to the start page");
        signUP.setCancelable(true);

        signUP.setPositiveButton(
                "OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){dialog.cancel(); startActivity(home);}
        }
        );

        signUPSuccess = signUP.create();

        passMatch = new AlertDialog.Builder(this);
        passMatch.setMessage("The two entered passwords didn't match. Please check if the spelling is correct");
        passMatch.setCancelable(true);

        passMatch.setPositiveButton(
                "OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        password_form.setText("");
                        password_form_reenter.setText("");
                        dialog.cancel();
                    }
                }
        );
        passDoNotMatch = passMatch.create();

        userTaken = new AlertDialog.Builder(this);
        userTaken.setMessage("Unfortunately this account name is already picked please choose another one");
        userTaken.setCancelable(true);

        userTaken.setPositiveButton(
                "OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        email_form.setText("");
                        password_form.setText("");
                        password_form_reenter.setText("");
                        email_text.setVisibility(View.VISIBLE);
                        password_text.setVisibility(View.VISIBLE);
                        password_text_reenter.setVisibility(View.VISIBLE);
                        

                        dialog.cancel();
                    }
                }
        );

        userTakenError = userTaken.create();

        email_form.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email_text.setVisibility(View.INVISIBLE);
                if(password_form_reenter.getText().toString().equals("")){
                    password_text_reenter.setVisibility(View.VISIBLE);
                }
                if(password_form.getText().toString().equals("")){
                    password_text.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        password_form.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password_text.setVisibility(View.INVISIBLE);
                if(password_form_reenter.getText().toString().equals("")){
                    password_text_reenter.setVisibility(View.VISIBLE);
                }
                if(email_form.getText().toString().equals("")){
                    email_text.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        password_form_reenter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password_text_reenter.setVisibility(View.INVISIBLE);
                if(password_form.getText().toString().equals("")){
                    password_text.setVisibility(View.VISIBLE);
                }
                if(email_form.getText().toString().equals("")){
                    email_text.setVisibility((View.VISIBLE));
                }
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_form.getText().toString();
                password = password_form.getText().toString();
                password2 = password_form_reenter.getText().toString();

                if(email.equals("") || password.equals("") || password2.equals("")){
                    missing_input.show();
                    return;
                }
                if(password.equals(password2)){
                    SignUUP(email, password);
                }else{
                    passDoNotMatch.show();
                }

            }
        });


    }

    public void SignUUP(String email, String password){
        System.out.println("luul" + password + email);
        String SignUPCheck = connection.SignUp(email,password);
        System.out.println("testöö " + SignUPCheck);
        if(SignUPCheck.equals("success")){
            signUPSuccess.show();
        }else if(SignUPCheck.equals("conn")){

        }else if(SignUPCheck.equals("user")){
            userTakenError.show();
        }

    }
}