package com.kaihongtan.kongsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button loginbutton;
    EditText usernameField, passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginbutton = findViewById(R.id.button);
        usernameField = findViewById(R.id.editText);
        passwordField = findViewById(R.id.editText2);

        loginbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(usernameField.getText().toString().equals("donor") && passwordField.getText().toString().equals("donor")){
                    Intent success = new Intent(MainActivity.this , loggedin.class);
                    startActivity(success);
                }

            }
        });
    }
}
