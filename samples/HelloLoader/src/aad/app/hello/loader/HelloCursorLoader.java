package aad.app.hello.loader;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class HelloCursorLoader extends Activity implements LoaderCallbacks<Cursor> {

    public static int LOADER_CURSOR = 69;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.cursor);
        this.getLoaderManager().initLoader(LOADER_CURSOR, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        
        String[] projection = new String[] {
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Data.DISPLAY_NAME_PRIMARY,
                ContactsContract.Data.DISPLAY_NAME
        };
        
        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Dump out a list of contacts
        StringBuilder sb = new StringBuilder();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                sb.append(cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)));
                sb.append("\n");
            }
            
            TextView mainTextView = (TextView) this.findViewById(R.id.mainTextView);
            mainTextView.setText(sb.toString());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Don't do anything        
    }

}
