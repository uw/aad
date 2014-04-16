package aad.app.exercise.tags;

import java.nio.charset.Charset;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

public class BaseActivity extends Activity {

	protected TextView mTextView;
	protected TextView mPathTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO 3.1 Get our View references
		
	}

	// TODO 3.2 Create a useful method to return our URI if available
	

}
