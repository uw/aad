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
		mTextView = (TextView) this.findViewById(R.id.mainTextView);
		mPathTextView = (TextView) this.findViewById(R.id.pathTextView);
	}

	// TODO 3.2 Create a useful method to return our URI if available
	protected Uri getNDEFUri(Intent receivedIntent) {

		Uri xsUri = null;
		
		// TODO 3.3 Check to see if the Intent Action is NDEF_DISCOVERED
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(receivedIntent.getAction())) {

			// Store some information about our Messages and Records
			StringBuilder sb = new StringBuilder();
			
			// Get the NDEF Message Data			
			Parcelable[] ndefMessagesData = receivedIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			
			// TODO 3.4 Go through our NdefMessages (We should only have one)
			if (ndefMessagesData != null) {

				NdefMessage[] ndefMessages = new NdefMessage[ndefMessagesData.length];
				for (int i = 0; i < ndefMessagesData.length; i++) {

					ndefMessages[i] = (NdefMessage) ndefMessagesData[i];
					sb.append(ndefMessages[i].toString());
					sb.append(System.getProperty("line.separator"));
					
					// TODO 3.5 Go through our NdefRecords (Again we should only have one) 
					NdefRecord[] ndefRecords = ndefMessages[i].getRecords();
					for (int j = 0; j < ndefRecords.length; j++) {
						
						sb.append("- " + ndefRecords[j].toString());
						sb.append(System.getProperty("line.separator"));

						// TODO 3.6 Extract our URI value
						// First check that we are getting a well known URI type
						if (ndefRecords[j].getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecords[j].getType(), NdefRecord.RTD_URI)) {

							// Extract the string from the Payload
							String xsUriString = new String(ndefRecords[j].getPayload(), Charset.forName("UTF-8"));

							// Construct a URI
							xsUri = Uri.parse(xsUriString);

							// Log the found URI
							sb.append(System.getProperty("line.separator"));
							sb.append("Found URI: " + xsUri.toString());

							// TODO Do something with your URI
						}
					}
				}
			}

			if (sb.length() > 0)
				mTextView.setText(sb.toString());
		}
		
		if (xsUri != null)
			return xsUri;
		else
			return null;
	}

}
