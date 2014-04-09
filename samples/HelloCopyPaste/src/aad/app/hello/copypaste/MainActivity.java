package aad.app.hello.copypaste;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private String TAG = "MainActivity";
    
    private ClipboardManager mClipboardManager;
    private EditText mEditText;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        
        setContentView(R.layout.activity_main);
        
        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        
        mEditText = (EditText) findViewById(R.id.mainEditText);
    }
        
    // Initialize the main menu
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
			case R.id.menu_copy:
				TextView mainTextView = (TextView) findViewById(R.id.mainTextView);
			    ClipData clipCopy = ClipData.newPlainText("HelloCopyPaste Content", mainTextView.getText());
			    mClipboardManager.setPrimaryClip(clipCopy);
				break;
								
			case R.id.menu_paste:
				ClipData clipPaste = mClipboardManager.getPrimaryClip();
				String clipText = clipPaste.getItemAt(0).getText().toString();
				mEditText.setText(clipText);				
				break;
				
			case R.id.menu_clear_edittext:
				mEditText.getText().clear();
				break;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
    
}