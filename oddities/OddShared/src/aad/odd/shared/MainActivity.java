package aad.odd.shared;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";
	
	private TextView mMainTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMainTextView = (TextView) this.findViewById(R.id.mainTextView);
		
		this.findViewById(R.id.getButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {			
				String value = MainActivity.this.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS).getString("key", "DEFAULT");
				mMainTextView.setText(value);
			}
			
		});
		
		this.findViewById(R.id.putButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				SharedPreferences.Editor editor = MainActivity.this.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS).edit();
				editor.putString("key", "SET");
				editor.apply();
				
				Log.i(TAG, "PUT key: " + "SET");
			}
			
		});		
		
		SharedPreferences.Editor editor = this.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS).edit();
		editor.putString("key", "SET");
		editor.apply();
		
		Log.i(TAG, "key: " + "SET");
		
		startService(new Intent(this, MainService.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
