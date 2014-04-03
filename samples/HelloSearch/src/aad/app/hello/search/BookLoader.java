package aad.app.hello.search;

import java.util.ArrayList;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BookLoader extends AsyncTaskLoader<ArrayList<String>> {

	private ArrayList<String> mData;
	private Context mContext;

	public BookLoader(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public ArrayList<String> loadInBackground() {

		SearchDatabaseHelper dbHelper = new SearchDatabaseHelper(mContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT name FROM books", null);

		mData = new ArrayList<String>();
		while (c.moveToNext()) {
			mData.add(c.getString(c.getColumnIndex("name")));
		}

		return mData;
	}

	@Override
	public void deliverResult(ArrayList<String> data) {

		if (isReset()) {
			return;
		}

		// Store the old data while delivering the fresh data
		ArrayList<String> oldData = mData;
		mData = data;

		if (isStarted()) {
			super.deliverResult(data);
		}

		// Clear out the old data
		if (oldData != null && oldData != data) {
			oldData = null;
		}
	}

	@Override
	protected void onStartLoading() {

		if (mData != null) {
			deliverResult(mData);
		}

		if (takeContentChanged() || mData == null) {
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		onStopLoading();

		// Clear out the data
		if (mData != null) {
			mData = null;
		}
	}

	@Override
	public void onCanceled(ArrayList<String> data) {
		super.onCanceled(data);
	}

}
