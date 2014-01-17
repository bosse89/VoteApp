package com.example.clientapp;



import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ConnecterActivity extends Activity {

	
	public TCPClient mTcpClient;
	private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connecter);
		
		//kör oncklickfunktion för Connect
		//ta text från textfält
		//skicka text/int
		
		//
		final EditText ipText = (EditText) findViewById(R.id.ipText);
		
		final EditText portText = (EditText) findViewById(R.id.portText);
		
		final EditText pwText = (EditText) findViewById(R.id.pwText);
		
		Button send = (Button)findViewById(R.id.connect_button);
		
		
		
		
		
		send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                String ip = ipText.getText().toString();
                int port = Integer.parseInt(portText.getText().toString());
                String pw = pwText.getText().toString();
                
                //new connectTask(ip,port,pw).execute("");
                //Ok, if we get here the connection should work, otherwise we would have gotten a catch
                Intent intent = new Intent("com.example.clientapp.VoteActivity1");
                Bundle b = new Bundle();
                
                b.putString("ip",ip);
                b.putInt("port",port);
                intent.putExtras(b);
                startActivity(intent);
                //setContentView(R.layout.activity_vote);
            }
        });
		
		
		//vote();
		
		
	}
	
	/*
	public void vote(){
		
		Button vote = (Button)findViewById(R.id.voteButt);
		
		final EditText voteText = (EditText) findViewById(R.id.voteText);
		vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                
               
                //int port = Integer.parseInt(portText.getText().toString());
                String vote = voteText.getText().toString();
             	 //sends the message to the server
               // if (mTcpClient != null) {
               //     mTcpClient.sendMessage(vote);
                //}
 
                //refresh the list
                //voteText.setText("");
                
                
            }
        });
		
		
	}
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connecter, menu);
		return true;
	}

	
	
	
	public class connectTask extends AsyncTask<String,String,TCPClient> {
		private String sip;
		private int sp;
		private String spw;
		
		public connectTask(String ip, int port,String pw){
			sip= ip;
			sp=port;
			spw=pw;
		}
		 
        @Override
        protected TCPClient doInBackground(String... message) {
 
            //we create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            }, sip,sp);
            mTcpClient.run();
 
            return null;
        }
 
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
 
            //in the arrayList we add the messaged received from server
            //arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            //mAdapter.notifyDataSetChanged();
        }
    }
	
	
	
	
	
}
