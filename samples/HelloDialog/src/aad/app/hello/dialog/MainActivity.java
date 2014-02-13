package aad.app.hello.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	/**
	 * A Custom Dialog with its own (reused) layout
	 */
	private class CustomDialog extends Dialog {

		public CustomDialog(Context context) {
			super(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_custom);
			
			// Handle the button clicks
			this.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					
					showOKToast(MainActivity.this);					
					dismiss();
					
				}});
			
			this.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					
					showCancelToast(MainActivity.this);					
					dismiss();
					
				}});
		}
		
		
	}

	/**
	 * Setup a DialogOnClickListener for the Dialog
	 */
	private class DialogOnClickListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {

			case DialogInterface.BUTTON_NEGATIVE:
				showCancelToast(MainActivity.this);
				break;

			case DialogInterface.BUTTON_POSITIVE:
				showOKToast(MainActivity.this);
				break;
			}
		}
	}

	private DialogOnClickListener mDialogOnClickListener = new DialogOnClickListener();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		findViewById(R.id.showDialogButton).setOnClickListener(this);
		findViewById(R.id.showCustomDialogButton).setOnClickListener(this);
		findViewById(R.id.showAnotherCustomDialogButton).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.showDialogButton:

			// Swanky way to create via the builder
			
			new AlertDialog.Builder(this)
				.setTitle(R.string.title)
				.setMessage(R.string.message)
				.setPositiveButton(getString(R.string.ok), mDialogOnClickListener)
				.setNegativeButton(getString(R.string.cancel), mDialogOnClickListener)
				.create()
				.show();

			break;

		case R.id.showCustomDialogButton:

			// Customize a plain old Dialog
			
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.dialog_custom);
			dialog.setTitle(R.string.title);

			// Set our button handlers
			
			dialog.findViewById(R.id.okButton).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showOKToast(MainActivity.this);
					dialog.dismiss();
				}
			});
			
			dialog.findViewById(R.id.cancelButton).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showCancelToast(MainActivity.this);
					dialog.dismiss();
				}
			});

			dialog.show();

			break;
			
		case R.id.showAnotherCustomDialogButton:
			
			// Use a Customized Dialog
			
			CustomDialog customDialog = new CustomDialog(this);
			customDialog.setTitle(R.string.title);
			customDialog.show();
			
			break;

		}
	}
	
	private static void showCancelToast(Context context) {
		Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT).show();
	}
	
	private static void showOKToast(Context context) {
		Toast.makeText(context, R.string.ok, Toast.LENGTH_SHORT).show();
	}

}