package aad.app.hello.quickcontactbadge;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {

	private static String TAG = "HelloQuickContactBadge";
	public static int LOADER_CURSOR = 1989;

	private ArrayList<ContactInformation> mContactInformation;
	private ContactAdapter mContactAdapter;

	private class ContactInformation {

		public Uri contactURI;
		public String name;
		public Uri photoURI;

		public ContactInformation(Uri contactURI, String name, Uri photoURI) {
			this.contactURI = contactURI;
			this.name = name;
			this.photoURI = photoURI;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		String[] projection = new String[] { 
				ContactsContract.Data.CONTACT_ID, 
				ContactsContract.Data.DISPLAY_NAME, 
				ContactsContract.Data.PHOTO_URI };

		return new CursorLoader(this, ContactsContract.Data.CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		if (cursor != null && cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
				Uri contactURI = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactID);
				Log.i(TAG, "onLoadFinished() contactURI: " + contactURI.toString());

				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
				String photoURIString = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI));

				Uri photoURI = null;
				if (photoURIString != null)
					photoURI = Uri.parse(photoURIString);

				if (photoURI != null)
					mContactInformation.add(new ContactInformation(contactURI, name, photoURI));
			}

			mContactAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Don't do anything
	}

	public class ContactAdapter extends BaseAdapter {

		private Context mContext;

		public ContactAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mContactInformation.size();
		}

		public Object getItem(int position) {
			return mContactInformation.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			GridViewItem gridViewItem;
			if (convertView == null) {
				gridViewItem = new GridViewItem(mContext);
				gridViewItem.setPadding(1, 1, 1, 1);
			} else {
				gridViewItem = (GridViewItem) convertView;
			}

			// Optionally use QCB
			ApplicationInfo ai;
			boolean useQuickContactBadge = false;
			try {
				ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;
				useQuickContactBadge = bundle.getBoolean("useQuickContactBadge");
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

			if (useQuickContactBadge) {
				gridViewItem.setContactURI(mContactInformation.get(position).contactURI);
			}

			gridViewItem.setName(mContactInformation.get(position).name);
			gridViewItem.setPhoto(mContactInformation.get(position).photoURI, useQuickContactBadge);

			return gridViewItem;
		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");

		setContentView(R.layout.activity_main);

		mContactInformation = new ArrayList<ContactInformation>();
		mContactAdapter = new ContactAdapter(this);

		GridView mainGridView = (GridView) this.findViewById(R.id.mainGridView);
		mainGridView.setAdapter(mContactAdapter);

		getLoaderManager().initLoader(LOADER_CURSOR, null, this);
	}

}