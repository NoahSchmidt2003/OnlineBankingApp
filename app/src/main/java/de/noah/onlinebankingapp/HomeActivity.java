package de.noah.onlinebankingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements Serializable {
    String email;
    Conn connection;
    String password;
    String key_id;
    String key;
    double balance;
    String balance_str;
    TextView hello_user;
    TextView balance_value;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        email = (String) getIntent().getSerializableExtra("email");
        password = (String) getIntent().getSerializableExtra("password");
        key_id = (String) getIntent().getSerializableExtra("key_id");
        key = (String) getIntent().getSerializableExtra("key");
        connection = new Conn();
        //important
        Objects.requireNonNull(getSupportActionBar()).hide();

        connection.connect();
        boolean login_check = connection.login(email, password);
        System.out.println(login_check);
        hello_user = (TextView) findViewById(R.id.hello_user);
        hello_user.setText("Hello "+ email);
        balance_value = (TextView) findViewById(R.id.balance_value);
        update();

    }
    public void update(){
        balance = connection.update();
        balance_str = String.valueOf(balance);
        balance_value.setText(balance_str+"€");

    }
}