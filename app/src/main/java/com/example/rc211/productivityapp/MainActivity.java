package com.example.rc211.productivityapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    int p1GameScore=0;
    int p2GameScore=0;
    int p1SetScore=0;
    int p2SetScore=0;
    String filename = "myfile";
    String fileContents = "Hello world!";
    FileOutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView p1Score=findViewById(R.id.p1Score);
        final TextView p2Score=findViewById(R.id.p2Score);
        final EditText inputTxt =findViewById(R.id.textInput);
        final Button button=findViewById(R.id.confirmButton);
        final Button p1Fault=findViewById(R.id.p1Fault);


        final Button p2Fault=findViewById(R.id.p2Fault);
        final String typedText = inputTxt.getText().toString();
        System.out.println(typedText);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                   FileInputStream test=openFileInput("myfile");
                   fileContents=fileContents+test.toString();
                    System.out.println(test.getFD());
                }catch (IOException e){
                    //didnt work
                }
                fileContents=(inputTxt.getText().toString());
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(fileContents.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        p1Fault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println(fileContents);
                if(p1GameScore==0){
                    p1GameScore=15;
                }
                else if(p1GameScore==15){
                    p1GameScore=30;
                }
                else if(p1GameScore==30){
                    p1GameScore=40;
                }
                else if(p1GameScore==40&&p2GameScore<40){
                    p1GameScore=0;
                    p1SetScore++;
                }
                else if(p1GameScore==40&&p2GameScore==40){
                    p1GameScore=50;
                }
                else if(p1GameScore==40&&p2GameScore==50){
                    p1GameScore=40;
                    p2GameScore=40;
                }
                else if(p1GameScore==50){
                    p1GameScore=0;
                    p2GameScore=0;
                    p1SetScore++;
                }
                if(p1GameScore==50){
                    p1Score.setText("AD");
                    p2Score.setText("-");
                }
                else{
                    p1Score.setText(p1GameScore+"");
                    p2Score.setText(p2GameScore+"");
                }

            }
        });
        p2Fault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(p2GameScore==0){
                    p2GameScore=15;
                }
                else if(p2GameScore==15){
                    p2GameScore=30;
                }
                else if(p2GameScore==30){
                    p2GameScore=40;
                }
                else if(p2GameScore==40&&p1GameScore<40){
                    p2GameScore=0;
                    p2SetScore++;
                }
                else if(p2GameScore==40&&p1GameScore==40){
                    p2GameScore=50;
                }
                else if(p2GameScore==40&&p1GameScore==50){
                    p2GameScore=40;
                    p1GameScore=40;
                }
                else if(p2GameScore==50){
                    p2GameScore=0;
                    p1GameScore=0;
                    p2SetScore++;
                }
                if(p2GameScore==50){
                    p2Score.setText("AD");
                    p1Score.setText("-");
                }
                else{
                    p2Score.setText(p2GameScore+"");
                    p1Score.setText(p1GameScore+"");
                }

            }
        });
    }
}
