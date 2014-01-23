package aad.app.hello.loader;

import aad.app.hello.loader.loaders.CustomLoader;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HelloMultipleLoaders extends Activity implements LoaderCallbacks<String> {

    private static final String TAG = HelloMultipleLoaders.class.getSimpleName();
    
    public static int LOADER_A = 0;
    public static int LOADER_B = 1;
    public static int LOADER_C = 2;
    
    private TextView mMainTextView;
    private StringBuilder mStringBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(TAG, "onCreate()");
                
        this.setContentView(R.layout.multiple);
        
        mStringBuilder = new StringBuilder();
        mMainTextView = (TextView) this.findViewById(R.id.mainTextView);
                
        this.getLoaderManager().initLoader(LOADER_A, null, this);
        this.getLoaderManager().initLoader(LOADER_B, null, this);
        this.getLoaderManager().initLoader(LOADER_C, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        
        Log.i(TAG, "onCreateLoader() id: " + id);
        
        switch (id) {
            case 0:
                return new CustomLoader(this, "A");
            case 1:
                return new CustomLoader(this, "B");
            case 2:
                return new CustomLoader(this, "C");
        }
        
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String ourString) {

        Log.i(TAG, "onLoadFinished() ourString: " + ourString);
        
        // Just dump your letter to screen
        
        mStringBuilder.append(ourString);
        mStringBuilder.append("\n");
        
        mMainTextView.setText(mStringBuilder.toString());
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset()");
        // Don't do anything        
    }    

}
