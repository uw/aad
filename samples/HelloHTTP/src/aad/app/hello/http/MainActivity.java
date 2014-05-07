package aad.app.hello.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import aad.app.hello.http.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    private static String USER_AGENT = "HelloHTTP/1.0";

    private AndroidHttpClient mClient;
    private Context mContext;
    private TextView mHeaderTextView;
    private TextView mContentTextView;
    private String mURLString;
    private StringBuilder mXMLBuilder;
    private ImageView mMainImageView;
        
    // Handler for updating the UI
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            String header = msg.getData().getString("header");
            if (header != null)
                mHeaderTextView.setText(header);
            
            String text = msg.getData().getString("body");
            if (text != null)
                mContentTextView.setText(text);
        }                
    };
    
    
    // Some example class definitions for deserializing the JSON
    class Book {
        String title;
        String isbn10;
    }
    
    // DefaultHandler implementation
    public class SAXHandler extends DefaultHandler {

        public void startDocument() throws SAXException {
            mXMLBuilder.append("startDocument()\n");
        }

        public void endDocument() throws SAXException {
            mXMLBuilder.append("endDocument()\n");
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            mXMLBuilder.append("startElement() " + qName + "\n");
            
            for (int i = 0; i < attributes.getLength(); i++) {
                mXMLBuilder.append(attributes.getQName(i) + " : " + attributes.getValue(i) + "\n");
            }            
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            mXMLBuilder.append("endElement() " + qName + "\n");
        }

        public void characters(char ch[], int start, int length) throws SAXException {
            mXMLBuilder.append("characters() " + new String(ch, start, length) + "\n");
        }
    }
    
    /** An AsycTask used to update the retrieved HTTP header and content displays */
    private class GetAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {

        	mClient = AndroidHttpClient.newInstance(USER_AGENT);
             
             HttpResponse response = null;
             
             String urlString = urls[0];
             
             if (urlString == null) {
            	 Log.e(TAG, "No valid URL string provided");
            	 return null;
             }
             
             // Make a GET request and execute it to return the response 
             HttpGet request = new HttpGet(mURLString);
             try {
                 response = mClient.execute(request);
             }
             catch (IOException e) {
                 e.printStackTrace();
             }
            
            if (response == null) {
                Log.e(TAG, "Error accessing: " + mURLString);
                Toast.makeText(mContext, "Error accessing: " + mURLString, Toast.LENGTH_LONG).show();
                return null;
            }
            
            // Get the content
            BufferedReader bf;
            StringBuilder sb = new StringBuilder();
            try {
                bf = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 8192);
                sb.setLength(0); // Reuse the StringBuilder
                String line;
                while ((line = bf.readLine()) != null) {
                    sb.append(line);
                }
                bf.close();
            }
            catch (IllegalStateException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
                        
            CheckBox jsonCheckBox = (CheckBox) findViewById(R.id.jsonCheckBox);
            CheckBox xmlCheckBox = (CheckBox) findViewById(R.id.xmlCheckBox);
            
            if (jsonCheckBox.isChecked()) {                
                Log.d(TAG, "onPostExecute() Parsing JSON");
                
                // Deserialize the JSON content
                Gson gson = new Gson();
                try {              
                    // Get a collection of our type of Book from GSON
                    Type bookType = new TypeToken<Collection<Book>>(){}.getType();
                    Collection<Book> books = gson.fromJson(sb.toString(), bookType);
                    
                    if (books != null && books.size() > 0) {
                        
                        sb.setLength(0); // Reuse the StringBuilder
                        
                        for (Book b : books) {
                            sb.append("Book titled " + b.title + " and its ISBN is " + b.isbn10 + "\n");  
                        }
                        setText(null, sb.toString());
                    }

                }
                catch (JsonSyntaxException e) {
                    Log.e(TAG, e.getMessage());
                }                

            } 
            else if (xmlCheckBox.isChecked()) {                
                Log.d(TAG, "onPostExecute() Parsing XML");
                
                mXMLBuilder = new StringBuilder();
                
                // Deserialize the XML content
                SAXHandler handler = new SAXHandler();
                try {
                    Xml.parse(sb.toString(), handler);
                }
                catch (SAXException e) {
                    e.printStackTrace();
                }  
                
                setText(null, mXMLBuilder.toString());
            }               
            else {

            	sb.setLength(0); // Reuse the StringBuilder
            	
                // Set the text of the Content
                String body = sb.toString();                                
                
                Header[] headers = response.getAllHeaders();           
                for (Header h : headers) {
                    sb.append(h.getName() + ":" + h.getValue() + "\n");
                }
    
                // Set the text of the Header
                //mHeaderTextView.setText(sb.toString());
                
                String header = sb.toString();
                setText(header, body);
            }
            
            mClient.close();
			return null;            
        }
        
        // Call back to the UI thread to update the textual content
        private void setText(String header, String body) {
            
            Bundle data = new Bundle();
            data.putString("header", header);
            data.putString("body", body);
            Message msg = new Message();
            msg.setData(data);
            mHandler.sendMessage(msg);
        }
    }
    
    /** An AsycTask used to retrieve a bitmap from a URL */
    private class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private URL downloadURL;
        private ImageView mImageView;
        
        public GetImageAsyncTask(ImageView imageView) {
            mImageView = imageView;
        }
        
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                // Just get the first URL
                if (urls.length > 0) {
                    downloadURL = new URL(urls[0]);
                    InputStream is = downloadURL.openStream();

                    Log.i(TAG, "doInBackground() url: " + downloadURL);

                    boolean unsafe = true;
                    if (unsafe) {
                        bitmap = BitmapFactory.decodeStream(is);
                    }
                    else {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        bitmap = BitmapFactory.decodeStream(is, null, options);
                        options.inJustDecodeBounds = false;

                        // Need to reopen the stream
                        is = downloadURL.openStream();
                        if (options.outWidth > 1024) {
                            options.inSampleSize = 16; // 1/16
                            bitmap = BitmapFactory.decodeStream(is, null, options);
                        } else {
                            bitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.RGB_565);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "doInBackground() e: ", e);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (bitmap == null) {
                Log.e(TAG, "Error accessing: " + downloadURL);
                return;
            }
            
            if (mImageView != null)
                mImageView.setImageBitmap(bitmap);
                                    
            super.onPostExecute(bitmap);
        }
        
    }
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        
        findViewById(R.id.getButton).setOnClickListener(this);        
        findViewById(R.id.getImageButton).setOnClickListener(this);

        mHeaderTextView = (TextView) this.findViewById(R.id.headerTextView);
        mContentTextView = (TextView) this.findViewById(R.id.contentTextView);
        
        mMainImageView = (ImageView) this.findViewById(R.id.mainImageView);
    }

    /** Handle the click from the Get button */
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick()");
        
        if (v.getId() == R.id.getButton) {
        	mURLString = ((EditText) this.findViewById(R.id.urlEditText)).getText().toString();	
            new GetAsyncTask().execute(mURLString);
        } else {
            new GetImageAsyncTask(mMainImageView).execute("http://www.largebluebutton.com/largebluebutton.jpg");
        }
    }

}