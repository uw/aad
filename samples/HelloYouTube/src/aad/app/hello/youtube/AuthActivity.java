package aad.app.hello.youtube;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class AuthActivity extends Activity implements OnClickListener {

    public static final String TAG = AuthActivity.class.getSimpleName();    
    public static final String SCOPE = "oauth2:http://gdata.youtube.com";
    
    private static final int REQUEST_SELECT_VIDEO = 1;

    private Account mAccount;
    private String mAuthToken = "";
    
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {            
            setContentView(R.layout.authorized);
            findViewById(R.id.uploadButton).setOnClickListener(AuthActivity.this);
        }
        
    };
    
    public class Upload {
        public Uri data;
        public String title;
    }
    
    public class UploadAsyncTask extends AsyncTask<Upload, Void, String> {

        @Override
        protected String doInBackground(Upload... params) {
            return uploadVideo(params[0].data, params[0].title);
        }
        
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(AuthActivity.this, "Video " + result, Toast.LENGTH_LONG).show();
        }
    }
    
        
        
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        Log.d(TAG, "onActivityResult()");
        
        if (requestCode == REQUEST_SELECT_VIDEO) {
            
            if (data == null)
                return;
            
            Toast.makeText(this, "Uploading: " + data.getDataString(), Toast.LENGTH_LONG).show();

            String uuid = UUID.randomUUID().toString();
            
            Upload upload = new Upload();
            upload.data = data.getData();
            upload.title = uuid.substring(0, 6);
            
            UploadAsyncTask uploadAsyncTask = new UploadAsyncTask();
            uploadAsyncTask.execute(upload);
        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        
        String[] proj = { MediaColumns.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
    private String uploadVideo(Uri dataURI, String title) {
        
        Log.d(TAG, "uploadVideo()");

        //String clientID = "HelloYouTube";
        String developerID = "AI39si4QKqXWs2abpr2FgfjTIjxrWzWoqCXqbaH_IzfUJ79tmKsSL3TZmVSfWlyIoo3vPa6r4nma7kZIMe8pClyLxfeSrfny6Q";
                
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://uploads.gdata.youtube.com/feeds/api/users/default/uploads");
        
        SharedPreferences sp = this.getSharedPreferences(Constants.PREF_NAME, 0);
        String sessionToken = sp.getString(Constants.PREF_KEY_AUTHTOKEN, "");        
        Log.d(TAG, "uploadVideo() Using sessionToken: " + sessionToken);
        
        // Really we would want to capture the numeric result and logically switch on what to display elsewhere...
        String result = "Error uploading Video";
                
        try {
            httpPost.setHeader("Host", "uploads.gdata.youtube.com");
            httpPost.setHeader("Authorization", "AuthSub token=" + sessionToken);
            httpPost.setHeader(
                    "X-GData-Key",
                    "key=" + developerID);
            httpPost.setHeader("Slug", title);

            MultipartEntity entity = new MultipartEntity();
            ContentBody cb = new FileBody(new File(getRealPathFromURI(dataURI)), "video/mp4");
            FormBodyPart fbp = new FormBodyPart("Video", cb);
            entity.addPart(fbp);

            httpPost.setEntity(entity);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpPost);
            result = response.getStatusLine().getReasonPhrase();

            Log.i(TAG, "uploadVideo() response: " + response.getStatusLine());            
            String line;
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                Log.i(TAG, "uploadVideo() response content: " + line);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);                
        
        Bundle b = this.getIntent().getExtras();
        mAccount = (Account) b.get("account");
        
        if (mAccount == null) {
            Log.e(TAG, "onCreate() Account was NULL");
            this.finish();
        }
    }
    

    @Override
    public void onResume() {

        super.onResume();

        Log.d(TAG, "onResume()");

        final SharedPreferences settings = this.getSharedPreferences(Constants.PREF_NAME, 0);

        String accessToken = settings.getString(Constants.PREF_KEY_AUTHTOKEN, "");
        final AccountManagerCallback<Bundle> cb = new AccountManagerCallback<Bundle>() {

            @Override
            public void run(AccountManagerFuture<Bundle> future) {

                Log.d(TAG, "AccountManagerCallback:run()");
                try {
                    final Bundle result = future.getResult();
                    final String accountName = result.getString(AccountManager.KEY_ACCOUNT_NAME);
                    final String authToken = result.getString(AccountManager.KEY_AUTHTOKEN);
                    mAuthToken = authToken;
                    final Intent authIntent = result.getParcelable(AccountManager.KEY_INTENT);

                    Log.d(TAG, "AccountManagerCallback:run() acountName: " + accountName + " authToken: " + authToken);
                    
                    if (accountName != null && authToken != null) {
                        final SharedPreferences.Editor editor = settings.edit();
                        editor.putString(Constants.PREF_KEY_AUTHTOKEN, authToken);
                        editor.commit();
                    }
                    else if (authIntent != null) {
                        startActivity(authIntent);
                    }
                    else {
                        Log.e(TAG, "AccountManager was unable to obtain an authToken.");
                        finish();
                    }
                }
                catch (OperationCanceledException e) {
                    Log.w(TAG, "Authentication Cancelled");
                    finish();
                }
                catch (Exception e) {
                    Log.e(TAG, "Authentication Exception", e);
                    finish();
                }
            }

        };

        Log.d(TAG, "onResume() Getting AuthToken");
        
        AccountManager.get(this).invalidateAuthToken("com.google", accessToken);
        AccountManager.get(this).getAuthToken(mAccount, SCOPE, null, this, cb, null);
        
        // Signal we have authenticated successfully
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void onClick(View v) {        
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("video/*");
        startActivityForResult(i, REQUEST_SELECT_VIDEO);        
    }

    @Override
    protected void onDestroy() {
        
        // Clear out the token
        final SharedPreferences settings = this.getSharedPreferences(Constants.PREF_NAME, 0);
        String accessToken = settings.getString(Constants.PREF_KEY_AUTHTOKEN, "");
        AccountManager.get(this).invalidateAuthToken("com.google", accessToken);
        super.onDestroy();
    }
    
    

}
