package aad.app.hello.provider.providers;

import aad.app.hello.provider.Books;
import aad.app.hello.provider.BooksSQLiteOpenHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class BooksContentProvider extends ContentProvider {

    public static final String TAG = BooksContentProvider.class.getSimpleName();

    public static final String AUTHORITY = "aad.app.c12.providers.BooksContentProvider";

    private static final int BOOK = 1;
    private static final int BOOK_ID = 2;
    private static final int BOOK_CONTENT = 3;

    BooksSQLiteOpenHelper mSQLHelper;

    private static final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mURIMatcher.addURI(AUTHORITY, "books", BOOK);
        mURIMatcher.addURI(AUTHORITY, "books/#", BOOK_ID);
        mURIMatcher.addURI(AUTHORITY, "books/content/#", BOOK_CONTENT);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mSQLHelper.getWritableDatabase();
        int count;
        switch (mURIMatcher.match(uri)) {
        case BOOK:
            count = db.delete(Books.Book.TABLE_NAME, selection, selectionArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {

        switch (mURIMatcher.match(uri)) {
        case BOOK:
            return Books.DIR_CONTENT_TYPE;
        case BOOK_ID:
        		return Books.ITEM_CONTENT_TYPE;
        case BOOK_CONTENT:
        		return "text/html";

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv) {

        if (mURIMatcher.match(uri) != BOOK) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = mSQLHelper.getWritableDatabase();
        long rowID = db.insert(Books.Book.TABLE_NAME, null, cv);
        if (rowID > 0) {

            Uri noteUri = ContentUris.withAppendedId(Books.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        else {
            Log.e(TAG, "insert() Error inserting task");
        }

        return null;
    }

    @Override
    public boolean onCreate() {

        mSQLHelper = new BooksSQLiteOpenHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String id;
        
        switch (mURIMatcher.match(uri)) {
        case BOOK:
            qb.setTables(Books.Book.TABLE_NAME);
            break;

        case BOOK_ID:
        		qb.setTables(Books.Book.TABLE_NAME);
        		selection = "_ID = ?";
        		id = uri.getLastPathSegment();
        		selectionArgs = new String[] {id};
        		break;
        		
        case BOOK_CONTENT:
        		qb.setTables(Books.Book.TABLE_NAME);
        		selection = "_ID = ?";
        		id = uri.getLastPathSegment();
        		selectionArgs = new String[] {id};
        		
        		// TODO Open the text/html content for a particular book
        		
        		break;
            
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = mSQLHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mSQLHelper.getWritableDatabase();

        int count;
        switch (mURIMatcher.match(uri)) {
        case BOOK:
            count = db.update(Books.Book.TABLE_NAME, values, selection, selectionArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;

    }

}
