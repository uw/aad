package aad.app.hello.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class BooksListViewsFactory implements RemoteViewsFactory {
	
	private Context mContext;
	private BooksDatabaseHelper mDBHelper;
	private ArrayList<String> mBooks;

	public BooksListViewsFactory(Context context, Intent intent) {
		mContext = context;
		mDBHelper = new BooksDatabaseHelper(context);
		mBooks = new ArrayList<String>();
	}

	@Override
	public void onCreate() {
		// HACK Cheating a bit here for time - stuffing the values we want to display into an ArrayList
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		Cursor c = db.query("books", null, null, null, null, null, null);
		while (c.moveToNext()) {
			mBooks.add(c.getString(c.getColumnIndex("name")));
		}
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public int getCount() {
		return (mBooks.size());
	}

	@Override
	public RemoteViews getViewAt(int position) {

		RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.book_row);

		row.setTextViewText(android.R.id.text1, mBooks.get(position));

		Intent i = new Intent();
		Bundle extras = new Bundle();

		extras.putString(BooksListAppWidgetProvider.EXTRA_BOOK, mBooks.get(position));
		i.putExtras(extras);
		row.setOnClickFillInIntent(android.R.id.text1, i);

		return (row);
	}

	@Override
	public RemoteViews getLoadingView() {
		return (null);
	}

	@Override
	public int getViewTypeCount() {
		return (1);
	}

	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public boolean hasStableIds() {
		return (true);
	}

	@Override
	public void onDataSetChanged() {
	}
}