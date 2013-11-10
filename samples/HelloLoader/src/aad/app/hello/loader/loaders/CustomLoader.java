package aad.app.hello.loader.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class CustomLoader extends AsyncTaskLoader<String> {


    public CustomLoader(Context context) {
        super(context);
    }
    
    public CustomLoader(Context context, String letter) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        // Just return what was passed in
        return null;
    }


    
}
