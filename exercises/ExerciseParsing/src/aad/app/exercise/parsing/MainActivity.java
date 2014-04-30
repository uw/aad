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
			//getBookList();
			new GetBookListAsyncTask().execute();
			break;

		case R.id.ipButton:
			//getIPAddress();
			new GetIPAsyncTask().execute();
			break;
		}
	}
	
	protected void getIPAddress() {

		StringBuilder sb = new StringBuilder();
		sb.append("Your IP Address is ");
		
		JsonReader reader;
		try {
			
			// TODO 2.1 - Implement a JsonReader for an Object
			
			InputStream inputStream = getHTTPInputStream("http://ip.jsontest.com/");
			reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

			String ip;
			reader.beginObject();
			while (reader.hasNext()) {

				String name = reader.nextName();
				
				if (name.equalsIgnoreCase("ip")) {
					ip = reader.nextString();
					sb.append(ip);
				} else {
					sb.append("Something Else Found!");
				}
			}
			reader.endObject();

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

			InputStream inputStream = getHTTPInputStream("https://raw.githubusercontent.com/uw/aad/master/samples/HelloHTTP/assets/books.json");
			reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

			String title;
			String isbn;
			reader.beginArray();
			while (reader.hasNext()) {

				reader.beginObject();
				while (reader.hasNext()) {

					String name = reader.nextName();
					
					if (name.equalsIgnoreCase("title")) {
						title = reader.nextString();
						sb.append(title);
						sb.append(" : ");
					} else if (name.equalsIgnoreCase("isbn10")) {
						isbn = reader.nextString();
						sb.append(isbn);
						sb.append("\n");
					} else {
						sb.append("Something Else Found!");
					}

				}
				reader.endObject();
			}
			reader.endArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		sendTextMessage(sb.toString());
	}
	
	// TODO 5.1 - Add some Asynchronicity
	
	private class GetBookListAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			getBookList();
			return null;
		}
	}

	private class GetIPAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			getIPAddress();
			return null;
		}
	}

}
