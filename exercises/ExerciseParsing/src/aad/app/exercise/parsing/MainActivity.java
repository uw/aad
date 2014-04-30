package aad.app.exercise.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseActivity implements OnClickListener {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.ipButton).setOnClickListener(this);
		findViewById(R.id.bookButton).setOnClickListener(this);
	}

	// TODO 5.2 - Rewire our ClickListener
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bookButton:
			getBookList();
			break;

		case R.id.ipButton:
			getIPAddress();
			break;
		}
	}
	
	protected void getIPAddress() {

		StringBuilder sb = new StringBuilder();
		sb.append("Your IP Address is ");
		
		JsonReader reader;
		try {
			
			// TODO 2.1 - Implement a JsonReader for an Object
			

		} catch (IOException e) {
			e.printStackTrace();
		}

		sendTextMessage(sb.toString());
	}

	protected void getBookList() {

		StringBuilder sb = new StringBuilder();
		sb.append("A list of books:\n");

		JsonReader reader;
		try {
			
			// TODO 3.1 - Implement a JsonReader for an Array
			

		} catch (IOException e) {
			e.printStackTrace();
		}

		sendTextMessage(sb.toString());
	}
	
	// TODO 5.1 - Add some Asynchronicity
	

}
