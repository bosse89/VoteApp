package com.example.clientapp;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
 
import java.util.ArrayList;
 
public class MainActivity extends Activity
{
    
 
    //So this is the method run when app is started
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
        super.onCreate(savedInstanceState);
    	
        //Start at the connect page
        setContentView(R.layout.activity_main);
        
        Button send = (Button)findViewById(R.id.connect_TCP);
        
        
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	startActivity(new Intent("com.example.clientapp.ConnecterActivity"));
            }
        });
        
        Button send2 = (Button)findViewById(R.id.connect_bt);
        
        
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	startActivity(new Intent("com.example.clientapp.ConnecterBTActivity"));
            }
        });
 
    }
 
    
}