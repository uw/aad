package aad.app.hello.provider;

import aad.app.hello.provider.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BooksSQLiteOpenHelper mSQLHelper;
    private SimpleCursorAdapter mAdapter;
    private ListView mTasksListView;
    private EditText mBookNameCreateEditText;
    private EditText mBookISBNCreateEditText;
    private Button mBookCreateButton;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        
        mBookNameCreateEditText = (EditText) this.findViewById(R.id.bookNameCreate);
        mBookISBNCreateEditText = (EditText) this.findViewById(R.id.bookISBNCreate);
        mBookCreateButton = (Button) this.findViewById(R.id.bookCreate);
        mBookCreateButton.setOnClickListener(this);

        // Get the Content Resolver
        ContentResolver cr = this.getContentResolver();

        // API Level < 11
        // Query the Content Resolver
        Cursor c = cr.query(Books.CONTENT_URI, Books.Book.PROJECTION, null, null, null);
        
        // API Level >= 11
        // Use a cursor loader
        //CursorLoader cl = new CursorLoader(this, Tasks.CONTENT_URI, Tasks.Task.PROJECTION, null, null, null);
        //Cursor c = cl.loadInBackground();
        
        if (c == null) {
            Log.e(TAG, "onCreate() Null Cursor from Query");
            return;
        }
        
        // Setup our mapping from the cursor result to the display field
        String[] from = { Books.Book.NAME };
        int[] to = { android.R.id.text1 };        

        // Create a simple cursor adapter
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, from, to);
        
        // Associate the simple cursor adapter to the list view
        mTasksListView = (ListView) this.findViewById(R.id.booksListView); 
        mTasksListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        
        ContentValues cv = new ContentValues();
        cv.put(Books.Book.NAME, mBookNameCreateEditText.getText().toString());
        cv.put(Books.Book.ISBN, mBookISBNCreateEditText.getText().toString());
        ContentResolver cr = this.getContentResolver();
        cr.insert(Books.CONTENT_URI, cv);
    }



}