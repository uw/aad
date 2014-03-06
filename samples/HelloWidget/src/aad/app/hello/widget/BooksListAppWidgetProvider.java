package aad.app.hello.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class BooksListAppWidgetProvider extends AppWidgetProvider {

	public static String EXTRA_BOOK = "aad.aap.hello.widget.BOOK";
	
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    
    	for (int i = 0; i < appWidgetIds.length; i++) {
    		
    		// Get the RemoteViews of the widget
    		RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.books_list_appwidget);

    		// Set the remote adapter to the RemoteViewsService
    		Intent serviceIntent = new Intent(context, BooksListService.class);    		
    		widget.setRemoteAdapter(appWidgetIds[i], R.id.widgetBookListView, serviceIntent);

            // Handle clicking the content
    		Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			widget.setPendingIntentTemplate(R.id.widgetBookListView, pendingIntent);

			// Tell the AppWidgetManager to perform an update on the current app widget
			appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
    	}
    }
}