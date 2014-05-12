
package aad.app.hello.facebook;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.*;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.model.GraphObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HelloFacebookActivity extends Activity implements OnClickListener, StatusCallback {

    public static final String TAG = HelloFacebookActivity.class.getSimpleName();

    private Session mSession;
    private TextView mMainTextView;
    private FriendsSQLiteOpenHelper mFriendsDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.control);

        this.findViewById(R.id.postsButton).setOnClickListener(this);
        this.findViewById(R.id.readButton).setOnClickListener(this);

        mMainTextView = (TextView) this.findViewById(R.id.mainTextView);

        // Trick for dumping your hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("aad.app.hello.facebook", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, "onCreate() " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (NameNotFoundException e) {
            Log.e(TAG, "onCreate() e: ", e);
        }
        catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "onCreate() e: ", e);
        }

        // Open a Facebook Session
        mSession = Session.openActiveSession(this, true, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult()");
        super.onActivityResult(requestCode, resultCode, data);

        mSession = Session.getActiveSession();

        if (mSession != null)
            mSession.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick()");

        if (mSession == null)
            return;

        if (view.getId() == R.id.postsButton)
            showPost();
        else
            showTV();

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        // mSession.closeAndClearTokenInformation();
        super.onDestroy();
    }

    private void showPost() {
        Log.d(TAG, "showPost() SessionState: " + mSession.getState());
        if (mSession.isOpened()) {

            for (String permission : mSession.getPermissions()) {
                Log.d(TAG, "showPost() permission: " + permission);
            }

            // Request read_stream permission if we don't have it
            if (!mSession.getPermissions().contains("read_stream")) {

                Log.w(TAG, "showPost() Did not find access_token permission");

                NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("read_stream"));
                newPermissionsRequest.setCallback(new StatusCallback() {

                    @Override
                    public void call(Session session, SessionState state, Exception exception) {
                        Log.d(TAG, "call() NewPermissionsRequest: " + state);
                        if (state == SessionState.OPENED) {
                            Session.setActiveSession(session);
                            mSession = session;
                        }

                    }
                });

                mSession.requestNewReadPermissions(newPermissionsRequest);
                return;
            }

            requestPosts();

        }
    }

    private void requestFriends() {

        Request r = new Request(mSession, "me/friends", null, HttpMethod.GET, new Request.Callback() {

            @Override
            public void onCompleted(Response response) {
                Log.d(TAG, "Request:onCompleted() response: " + response.toString());

                GraphObject go = response.getGraphObject();
                if (go == null) {
                    Log.e(TAG, "requestFriends:onCompleted() GraphObject is null");
                    return;
                }

                try {

                    JSONArray data = go.getInnerJSONObject().getJSONArray("data");

                    if (mFriendsDBHelper == null)
                        mFriendsDBHelper = new FriendsSQLiteOpenHelper(HelloFacebookActivity.this);
                    
                    SQLiteDatabase wdb = mFriendsDBHelper.getWritableDatabase();

                    // Clean out all existing friends
                    wdb.delete(FriendsSQLiteOpenHelper.TABLE_NAME, null, null);

                    ContentValues cv = new ContentValues();

                    // Add all the found friends
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jName = data.getJSONObject(i);
                        cv.put("fb_id", jName.getInt("id"));
                        cv.put("fb_name", jName.getString("name"));

                        wdb.insert(FriendsSQLiteOpenHelper.TABLE_NAME, null, cv);
                    }

                    wdb.close();

                    Log.i(TAG, "requestFriends:onCompleted() Friend Download Complete");

                }
                catch (JSONException e) {
                    Log.e(TAG, "requestFriends:onCompleted() JSONException: ", e);
                }

            }
        });

        // Actually execute the Request
        r.executeAsync();

    }

    private void requestPosts() {

        // "/me/posts?fields=message" does not seem to work...
        Request r = new Request(mSession, "me/posts", null, HttpMethod.GET, new Request.Callback() {

            @Override
            public void onCompleted(Response response) {
                // Log.d(TAG, "Request:onCompleted() response: " +
                // response.toString());

                GraphObject go = response.getGraphObject();
                if (go == null) {
                    Log.e(TAG, "requestPosts() GraphObject is null");
                    return;
                }

                StringBuilder sb = new StringBuilder();

                try {

                    JSONArray data = go.getInnerJSONObject().getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = (JSONObject) data.get(i);

                        if (object.has("message")) {
                            String message = (String) object.get("message");
                            Log.d(TAG, "message = " + message);
                            sb.append(message + "\n");
                        }

                    }

                }
                catch (JSONException e) {
                    Log.e(TAG, "showTV() JSONException: ", e);
                }

                mMainTextView.setText(sb.toString());
            }
        });

        // Actually execute the Request
        r.executeAsync();
    }

    private void requestTV() {
        if (mSession.isOpened()) {
        	
            Request r = new Request(mSession, "/me/television", null, HttpMethod.GET, new Request.Callback() {

                @Override
                public void onCompleted(Response response) {
                    Log.d(TAG, "Request:onCompleted() response: " + response.toString());

                    StringBuilder sb = new StringBuilder();

                    GraphObject go = response.getGraphObject();
                    try {

                        JSONArray data = go.getInnerJSONObject().getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = (JSONObject) data.get(i);
                            String name = (String) object.get("name");
                            Log.d(TAG, "name = " + name);
                            sb.append(name + "\n");
                        }

                    }
                    catch (JSONException e) {
                        Log.e(TAG, "showTV() JSONException: ", e);
                    }

                    mMainTextView.setText(sb.toString());
                }
            });

            // Actually execute the Request
            r.executeAsync();
        }
    }

    private void showTV() {
        Log.d(TAG, "showTV() SessionState: " + mSession.getState());
        
        if (mSession.isOpened()) {

            for (String permission : mSession.getPermissions()) {
                Log.d(TAG, "showPost() permission: " + permission);
            }

            // Request read_stream permission if we don't have it
            if (!mSession.getPermissions().contains("user_likes")) {

                Log.w(TAG, "showTV() Did not find user_likes permission");

                NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("user_likes"));
                newPermissionsRequest.setCallback(new StatusCallback() {

                    @Override
                    public void call(Session session, SessionState state, Exception exception) {
                        Log.d(TAG, "call() NewPermissionsRequest: " + state);
                        if (state == SessionState.OPENED) {
                            Session.setActiveSession(session);
                            mSession = session;
                        }

                    }
                });

                mSession.requestNewReadPermissions(newPermissionsRequest);
                return;
            }
            
            requestTV();
        }
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        Log.d(TAG, "call() SessionState: " + state);

        if (state == SessionState.CLOSED_LOGIN_FAILED) {
            Log.e(TAG, "call() Exception: " + exception.getMessage());
            return;
        }

        Log.d(TAG, "call() AccessToken: " + mSession.getAccessToken());

        if (session.isOpened()) {
            // Load our friends
            requestFriends();
        }

    }

}
