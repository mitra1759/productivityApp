package com.example.rc211.productivityapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.AbstractList;


public class MainActivity extends AppCompatActivity {
    String filename = "statFile";//name of my file
    FileOutputStream outputStream;//creates a fileoutputstream to save data to the file
    FileInputStream inputStream;//creates a fileinputstream to load data from the file
    String name;
    String name2;
    int counter = 0;
    boolean loaded = false;
    int[] stat = new int[7];//meanings of each slot are the following:p1setscore,p2setscore,p1matchscore,p2matchscore,p1gamescore,p2gamescore,which player is serving
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();
        final TextView p1Score = findViewById(R.id.p1Score);
        final TextView p2Score = findViewById(R.id.p2Score);
        final TextView p1Score2 = findViewById(R.id.p1Score2);
        final TextView p2Score2 = findViewById(R.id.p2Score2);
        final TextView p1Score3 = findViewById(R.id.p1Score3);
        final TextView p2Score3 = findViewById(R.id.p2Score3);
        final TextView textview = findViewById(R.id.input1);
        final EditText inputTxt = findViewById(R.id.textInput);
        final EditText inputTxt2 = findViewById(R.id.textInput2);
        final Button button = findViewById(R.id.confirmButton);
        final Button reset = findViewById(R.id.reset);
        final Button button2 = findViewById(R.id.confirmButton2);
        final Button p1Fault = findViewById(R.id.p1Fault);
        final Button p2Fault = findViewById(R.id.p2Fault);
        final ImageView serveView = findViewById(R.id.serveView);
        final ImageView serveView2 = findViewById(R.id.serveView2);
        textview.setText("");//the next group of text initializes the views with accurate stats
        p1Fault.setText(name + "'s Point");
        p2Fault.setText(name2 + "'s Point");
        p1Score.setText(stat[4] + "");
        p2Score.setText(stat[5]+ "");
        p1Score2.setText(stat[0] + "");
        p2Score2.setText(stat[1] + "");
        p1Score3.setText(stat[2] + "");
        p2Score3.setText(stat[3] + "");
        if(stat[6]==0){
            serveView.setVisibility(View.VISIBLE);
            serveView2.setVisibility(View.INVISIBLE);
        }
        else {
            serveView.setVisibility(View.INVISIBLE);
            serveView2.setVisibility(View.VISIBLE);
        }
        if(stat[5] == 50){
            p2Score.setText("AD");
            p1Score.setText("-");
        }
        if (stat[4] == 50) {
            p1Score.setText("AD");
            p2Score.setText("-");
        }
        inputTxt.setText(name, TextView.BufferType.EDITABLE);
        inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//changes player 1s name
                name = inputTxt.getText().toString();
                p1Fault.setText(name + "'s Point");
                inputTxt.setText(name, TextView.BufferType.EDITABLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//changes player 2s name
                name2 = inputTxt2.getText().toString();
                p2Fault.setText(name2 + "'s Point");
                inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {//restarts the game and resets the stats
            @Override
            public void onClick(View v) {
                newMatch();
                name="Player 1";
                name2="Player 2";
                inputTxt.setText(name, TextView.BufferType.EDITABLE);
                inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
                p1Fault.setText(name + "'s Point");
                p2Fault.setText(name2 + "'s Point");
                save();
                p1Score.setText(stat[4] + "");
                p2Score.setText(stat[5]+ "");
                p1Score2.setText(stat[0] + "");
                p2Score2.setText(stat[1] + "");
                p1Score3.setText(stat[2] + "");
                p2Score3.setText(stat[3] + "");
                if (stat[4] == 50) {
                    p1Score.setText("AD");
                    p2Score.setText("-");
                }
                if(stat[6]==0){
                    serveView.setVisibility(View.VISIBLE);
                    serveView2.setVisibility(View.INVISIBLE);
                }
                else {
                    serveView.setVisibility(View.INVISIBLE);
                    serveView2.setVisibility(View.VISIBLE);
                }
            }
        });
        p1Fault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//keeps track of all possible results of p1 winning a point
                textview.setText("");
                p1Fault.setText(name + "'s Point");
                if (stat[4] == 0) {
                    stat[4] = 15;
                } else if (stat[4] == 15) {
                    stat[4] = 30;
                } else if (stat[4] == 30) {
                    stat[4] = 40;
                } else if (stat[4] == 40 && stat[5] < 40) {
                    stat[4] = 0;
                    stat[5] = 0;
                    stat[0]++;
                    getServe();
                } else if (stat[4] == 40 && stat[5] == 40) {
                    stat[4] = 50;
                } else if (stat[4] == 40 && stat[5] == 50) {
                    stat[4] = 40;
                    stat[5] = 40;
                } else if (stat[4] == 50) {
                    stat[4] = 0;
                    stat[5] = 0;
                    stat[0]++;//player 1s total games won
                    getServe();
                }
                if (stat[0] >= 6 && stat[0] >= stat[1] + 2) {
                    stat[2]++;//player 1s total sets won
                    stat[0] = 0;
                    stat[1] = 0;
                }
                save();
                p1Score.setText(stat[4] + "");
                p2Score.setText(stat[5]+ "");
                p1Score2.setText(stat[0] + "");
                p2Score2.setText(stat[1] + "");
                p1Score3.setText(stat[2] + "");
                p2Score3.setText(stat[3] + "");
                if (stat[4] == 50) {
                    p1Score.setText("AD");
                    p2Score.setText("-");
                }
                if(stat[6]==0){
                    serveView.setVisibility(View.VISIBLE);
                    serveView2.setVisibility(View.INVISIBLE);
                }
                else {
                    serveView.setVisibility(View.INVISIBLE);
                    serveView2.setVisibility(View.VISIBLE);
                }
                if(stat[2]>=2){
                    textview.setText(name+" Won the Match "+stat[2]+"-"+stat[3]);
                    stat[2]=0;
                    stat[3]=0;
                    newMatch();
                    name="Player 1";
                    name2="Player 2";
                    inputTxt.setText(name, TextView.BufferType.EDITABLE);
                    inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
                    p1Fault.setText(name + "'s Point");
                    p2Fault.setText(name2 + "'s Point");
                    save();
                    p1Score.setText(stat[4] + "");
                    p2Score.setText(stat[5]+ "");
                    p1Score2.setText(stat[0] + "");
                    p2Score2.setText(stat[1] + "");
                    p1Score3.setText(stat[2] + "");
                    p2Score3.setText(stat[3] + "");
                    if (stat[4] == 50) {
                        p1Score.setText("AD");
                        p2Score.setText("-");
                    }
                    if(stat[6]==0){
                        serveView.setVisibility(View.VISIBLE);
                        serveView2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        serveView.setVisibility(View.INVISIBLE);
                        serveView2.setVisibility(View.VISIBLE);
                    }
                }
                else if (stat[3]>=2){
                    textview.setText(name2+" Won the Match "+stat[3]+"-"+stat[2]);
                    stat[2]=0;
                    stat[3]=0;
                    newMatch();
                    name="Player 1";
                    name2="Player 2";
                    inputTxt.setText(name, TextView.BufferType.EDITABLE);
                    inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
                    p1Fault.setText(name + "'s Point");
                    p2Fault.setText(name2 + "'s Point");
                    save();
                    p1Score.setText(stat[4] + "");
                    p2Score.setText(stat[5]+ "");
                    p1Score2.setText(stat[0] + "");
                    p2Score2.setText(stat[1] + "");
                    p1Score3.setText(stat[2] + "");
                    p2Score3.setText(stat[3] + "");
                    if (stat[4] == 50) {//makes sure AD shows up instead of 50
                        p1Score.setText("AD");
                        p2Score.setText("-");
                    }
                    if(stat[6]==0){
                        serveView.setVisibility(View.VISIBLE);
                        serveView2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        serveView.setVisibility(View.INVISIBLE);
                        serveView2.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        p2Fault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//keeps track of all possible results of p2 winning a point
                textview.setText("");
                if (stat[5] == 0) {
                    stat[5] = 15;
                } else if (stat[5] == 15) {
                    stat[5] = 30;
                } else if (stat[5] == 30) {
                    stat[5] = 40;
                } else if (stat[5] == 40 && stat[4] < 40) {
                    stat[4] = 0;
                    stat[5] = 0;
                    stat[1]++;
                    getServe();
                } else if (stat[5] == 40 && stat[4] == 40) {
                    stat[5] = 50;
                } else if (stat[5] == 40 && stat[4] == 50) {
                    stat[5] = 40;
                    stat[4]= 40;
                } else if (stat[5] == 50) {
                    stat[5] = 0;
                    stat[4] = 0;
                    stat[1]++;//player 2s total games won
                    getServe();
                }
                if (stat[1] >= 6 && stat[1] >= stat[0] + 2) {
                    stat[3]++;//player 2s total sets won
                    stat[0] = 0;
                    stat[1] = 0;
                }
                save();
                p1Score.setText(stat[4] + "");
                p2Score.setText(stat[5]+ "");
                p1Score2.setText(stat[0] + "");
                p2Score2.setText(stat[1] + "");
                p1Score3.setText(stat[2] + "");
                p2Score3.setText(stat[3] + "");
                if(stat[5] == 50){//makes sure AD shows up instead of 50
                    p2Score.setText("AD");
                    p1Score.setText("-");
                }
                if(stat[6]==0){
                    serveView.setVisibility(View.VISIBLE);
                    serveView2.setVisibility(View.INVISIBLE);
                }
                else {
                    serveView.setVisibility(View.INVISIBLE);
                    serveView2.setVisibility(View.VISIBLE);
                }
                if(stat[2]>=2){
                    textview.setText(name+" Won the Match "+stat[2]+"-"+stat[3]);
                    stat[2]=0;
                    stat[3]=0;
                    newMatch();
                    name="Player 1";
                    name2="Player 2";
                    inputTxt.setText(name, TextView.BufferType.EDITABLE);
                    inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
                    p1Fault.setText(name + "'s Point");
                    p2Fault.setText(name2 + "'s Point");
                    save();
                    p1Score.setText(stat[4] + "");
                    p2Score.setText(stat[5]+ "");
                    p1Score2.setText(stat[0] + "");
                    p2Score2.setText(stat[1] + "");
                    p1Score3.setText(stat[2] + "");
                    p2Score3.setText(stat[3] + "");
                    if (stat[4] == 50) {
                        p1Score.setText("AD");
                        p2Score.setText("-");
                    }
                    if(stat[6]==0){
                        serveView.setVisibility(View.VISIBLE);
                        serveView2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        serveView.setVisibility(View.INVISIBLE);
                        serveView2.setVisibility(View.VISIBLE);
                    }
                }
                else if (stat[3]>=2){
                    textview.setText(name2+" Won the Match "+stat[3]+"-"+stat[2]);
                    stat[2]=0;
                    stat[3]=0;
                    newMatch();
                    name="Player 1";
                    name2="Player 2";
                    inputTxt.setText(name, TextView.BufferType.EDITABLE);
                    inputTxt2.setText(name2, TextView.BufferType.EDITABLE);
                    p1Fault.setText(name + "'s Point");
                    p2Fault.setText(name2 + "'s Point");
                    save();
                    p1Score.setText(stat[4] + "");
                    p2Score.setText(stat[5]+ "");
                    p1Score2.setText(stat[0] + "");
                    p2Score2.setText(stat[1] + "");
                    p1Score3.setText(stat[2] + "");
                    p2Score3.setText(stat[3] + "");
                    if (stat[4] == 50) {
                        p1Score.setText("AD");
                        p2Score.setText("-");
                    }
                    if(stat[6]==0){
                        serveView.setVisibility(View.VISIBLE);
                        serveView2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        serveView.setVisibility(View.INVISIBLE);
                        serveView2.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    public void save() {//saves the stats
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);//creates a 'stream' to save data to file
            //saves the strings in the groceryList array
            for (int i = 0; i < stat.length; i++) {
                outputStream.write(Integer.toString(stat[i]).getBytes());//writes data to the file
                if (i < stat.length - 1) {
                    outputStream.write("\n".getBytes());//indents the writing to make new line
//                    Toast.makeText(getApplicationContext(),"gygy",Toast.LENGTH_SHORT).show();
                }
            }
            outputStream.write("\n".getBytes());//indents the writing to make new line
            outputStream.write(name.getBytes());//writes data to the file
            outputStream.write("\n".getBytes());//indents the writing to make new line
            outputStream.write(name2.getBytes());//writes data to the file
            outputStream.close();//closes the 'stream' to stop saving
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void load() {//loads the stats
        try {
            inputStream = openFileInput(filename);//creates a 'stream' of data in bytes
            InputStreamReader inputReader = new InputStreamReader(inputStream);//converts the byte stream to characters
            BufferedReader bufferedReader = new BufferedReader(inputReader);//acts as a buffer for the info; processes it?

            try {
                String line = bufferedReader.readLine();//saves the line read to a temp variable to avoid reading next line during check
                while (line != null) {
                    if (counter < stat.length) {
                        stat[counter] = stat[counter] + Integer.parseInt(line);
                    }
                    if (counter == stat.length) {
                        name = line;
                    }
                    name2 = line;
//                    if(counter==0){
//                    Toast.makeText(getApplicationContext(),line+"",Toast.LENGTH_SHORT).show();
                    line = bufferedReader.readLine();
                    counter++;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getServe() {//changes the server
        if (stat[6] == 0) {
            stat[6] = 1;
        } else {
            stat[6] = 0;
        }
    }

    public void newMatch(){//resets stats
        for(int a=0;a<stat.length;a++){
            stat[a]=0;
        }
    }
}

