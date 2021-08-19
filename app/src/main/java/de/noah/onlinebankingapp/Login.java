package de.noah.onlinebankingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.io.Serializable;


public class Login extends Activity implements Serializable{

    String user_email;
    String user_password;
    String key = "Ies41Zygx|d[$<y3Xw0x#n\\~pI=a1871@*-#rKA4.J*%b>LfPtx7vw#vVU61{8^`<v4ip25Mz2qKT@^0i670tr87jJR%*}_%s0cDRlE<sB6aEQdDGK*m0)Nkbe/K\\FFO1$:x=y8tmq8+`zZ.^&r4i4+Fv.4P>k3:fJky.JmV35861]I3n.\"DV6c)G1!AHD<E712xWGu3a*5*Uk5\\!R7a0V71*&#Npj[f3401k(P1Tgy[1waXtjBv?%5B0(NH.Tl";
    String key_id = "725";
    public Conn connection;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText email_form = (EditText)findViewById(R.id.login_email);
        final EditText password_form = (EditText)findViewById(R.id.login_password);
        final View button_login = findViewById(R.id.button_login);
        final View button_signup = findViewById(R.id.signup_button);


        email_form.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(email_form.getText().toString().equals("Enter your email")){
                    email_form.setText("");
                }
                if(password_form.getText().toString().equals("")){
                    password_form.setText("Enter your password");
                }
                return false;
            }
        });

        password_form.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(password_form.getText().toString().equals("Enter your password")){
                    password_form.setText("");
                }
                if (email_form.getText().toString().equals("")) {
                    email_form.setText("Enter your email");
                }
                return false;
            }

        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                user_email = email_form.getText().toString();
                user_password = password_form.getText().toString();
                System.out.println(user_email + "   " + user_password);
                login();

            }
        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(Login.this, SignUp.class);
                signUp.putExtra("key_id", key_id);
                signUp.putExtra("key", key);
                startActivity(signUp);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(){
        connection = new Conn();
        connection.connect();
        boolean login_check = connection.login(user_email, user_password);
        System.out.println(login_check);
        Intent Home = new Intent(Login.this, HomeActivity.class);
        //Home.putExtra("connection", getConnection());
        Home.putExtra("password", user_password);
        Home.putExtra("email", user_email);
        Home.putExtra("key_id", key_id);
        Home.putExtra("key", key);
        startActivity(Home);
    }


}