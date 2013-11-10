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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.multiple);
        
        this.getLoaderManager().initLoader(LOADER_A, null, this);
        this.getLoaderManager().initLoader(LOADER_B, null, this);
        this.getLoaderManager().initLoader(LOADER_C, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        CustomLoader cl;
        
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

        Log.i(TAG, "ourString " + ourString);
        
        // Just dump your letter to screen
        TextView mainTextView = (TextView) this.findViewById(R.id.mainTextView);
        String currentText = (String) mainTextView.getText();
        currentText.concat("\n").concat(ourString);
        mainTextView.setTag(currentText);        
    }

    @Override
    public void onLoaderReset(Loader<String> arg0) {
        // Don't do anything
        
    }
    
    

}
