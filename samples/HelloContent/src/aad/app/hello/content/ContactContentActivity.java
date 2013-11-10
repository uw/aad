package aad.app.hello.content;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;

public class ContactContentActivity extends ListActivity {
    
    private SimpleCursorAdapter mAdapter;
    
    public static final String[] PROJECTION = new String[] {
       ContactsContract.Contacts._ID, 
       ContactsContract.CommonDataKinds.Email.DATA,
       ContactsContract.CommonDataKinds.Email.CONTACT_STATUS};
        
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        ContentResolver cr = this.getContentResolver();               
        Cursor c = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        PROJECTION, 
                        null, 
                        null, 
                        null);
        
        // Setup our mapping from the cursor result to the display field
        String[] from = { ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.CONTACT_STATUS };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        
        // Create a simple cursor adapter
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, from, to);

        this.getListView().setAdapter(mAdapter);
    }

}