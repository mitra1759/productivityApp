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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String filename = "groceryFile";//name of my file
    FileOutputStream outputStream;//creates a fileoutputstream to save data to the file
    FileInputStream inputStream;//creates a fileinputstream to load data from the file
    ArrayList<String> groceryList;//array of strings that save groceries
    int p1GameScore=0;
    int p2GameScore=0;
    int p1SetScore=0;
    int p2SetScore=0;
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


            }
        });

        p1Fault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
    public void save(){
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);//creates a 'stream' to save data to file
            //saves the strings in the groceryList array
            for(int i = 0; i < groceryList.size(); i++){
                outputStream.write(groceryList.get(i).getBytes());//writes data to the file
                if(i < groceryList.size() - 1){
                    outputStream.write("\n".getBytes());//indents the writing to make new line
                }
            }

            outputStream.close();//closes the 'stream' to stop saving
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void load(){
        try{
            inputStream = openFileInput(filename);//creates a 'stream' of data in bytes
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputStreamReader inputReader = new InputStreamReader(inputStream);//converts the byte stream to characters
        BufferedReader bufferedReader = new BufferedReader(inputReader);//acts as a buffer for the info; processes it?

        try {
            String line = bufferedReader.readLine();//saves the line read to a temp variable to avoid reading next line during check
            while (line != null){
                groceryList.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
