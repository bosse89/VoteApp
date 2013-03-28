package com.example.clientapp;

//import com.example.clientapp.VoteActivity.connectTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class VoteActivity1 extends Activity implements OnCheckedChangeListener {
	
	
	public TCPClient mTcpClient;
	EditText voteText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		
		
		
		
		Bundle b = getIntent().getExtras();
		String ip = b.getString("ip");
		int port = b.getInt("port");
		String pw = "";
		new connectTask(ip,port,pw).execute("");
		
		
		
		//setContentView(R.layout.activity_vote);
		setContentView(R.layout.activity_vote_activity1);

		RadioGroup Gunit =(RadioGroup) findViewById(R.id.radioGroup1);
		Gunit.setOnCheckedChangeListener(this);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_vote_activity1, menu);
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













	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		switch (checkedId){
		case R.id.yes:
			sendString("y");
			break;
		case R.id.kinda:
			sendString("k");
			break;
		case R.id.no:
			sendString("n");
			break;
		}
	}
	
	
	public void sendString(String vote){
		
		//sends the message to the server
		if (mTcpClient != null) {
	   	mTcpClient.sendMessage(vote);
	  	}
		
		
		
	}

}
