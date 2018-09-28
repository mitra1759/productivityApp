package com.example.rc211.productivityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText inputTxt =findViewById(R.id.textInput);
        final Button button=findViewById(R.id.confirmButton);
        final String typedText = inputTxt.getText().toString();
        System.out.println(typedText);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println(inputTxt.getText().toString());
            }
        });
    }
}
