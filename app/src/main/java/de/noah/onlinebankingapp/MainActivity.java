package de.noah.onlinebankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



import java.io.Serializable;



public class MainActivity extends AppCompatActivity implements Serializable {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ImageView button_login_signup = (ImageView) findViewById(R.id.getStarted);
        Intent Login = new Intent(this, Login.class);


        button_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" click");
                startActivity(Login);
            }
        });
    }
}