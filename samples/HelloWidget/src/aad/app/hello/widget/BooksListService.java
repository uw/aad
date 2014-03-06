package aad.app.hello.widget;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class BooksListService extends RemoteViewsService {

	  @Override
	  public RemoteViewsFactory onGetViewFactory(Intent intent) {
	    return(new BooksListViewsFactory(this.getApplicationContext(), intent));
	  }
	
}
