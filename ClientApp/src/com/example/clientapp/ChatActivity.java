package com.example.clientapp;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
 
import java.util.ArrayList;
 
public class ChatActivity extends Activity
{
    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;
    private TCPClient mTcpClient;
 
    //So this is the method run when app is started
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
        super.onCreate(savedInstanceState);
    	
        //Start at the connect page
        setContentView(R.layout.activity_chat);
        
        
        final EditText ipText = (EditText) findViewById(R.id.ipText);
		
		final EditText portText = (EditText) findViewById(R.id.portText);
		
		final EditText pwText = (EditText) findViewById(R.id.pwText);
		
		Button connect = (Button)findViewById(R.id.connect_button);
        
        //Decide what layout to use for the app
        setContentView(R.layout.activity_main);
 
        arrayList = new ArrayList<String>();
 
        //In the xml file the EditText with id editText is defined here
        final EditText editText = (EditText) findViewById(R.id.editText);
        //same with the button
        Button send = (Button)findViewById(R.id.send_button);
 
        //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        
        //MyCostumADapter seems to hold information about the current state for ex, how many listitems, get views 
        //adapter, seems to be the link between the interface and the tcp-client and mainactivity
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);
 
        // connect to the server
        //Bind this to a button connect 
        new connectTask().execute("");
 
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                String message = editText.getText().toString();
 
                //add the text in the arrayList
                arrayList.add("c: " + message);
 
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }
 
                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
 
    }
 
    public class connectTask extends AsyncTask<String,String,TCPClient> {
 
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
            },"192.168.1.2",4444);
            mTcpClient.run();
 
            return null;
        }
 
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
 
            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
    }
}