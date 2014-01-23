package aad.app.hello.loader.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class CustomLoader extends AsyncTaskLoader<String> {

    String mLetter;

    public CustomLoader(Context context) {
        super(context);
        
        mLetter = "0";
    }
    
    public CustomLoader(Context context, String letter) {
        super(context);
        
        mLetter = letter;
    }    
    
    @Override
    public void deliverResult(String data) {
        
        if (isStarted())
            super.deliverResult(mLetter);
    }

    @Override
    public String loadInBackground() {
        // NYI, Just return what was passed in
        return mLetter;
    }

    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
    }

    @Override
    protected void onStartLoading() {
        
        forceLoad();
        
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        super.onStopLoading();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
    
}
