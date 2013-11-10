
package aad.app.hello.loader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HelloLoader extends Activity {

    private static final String TAG = HelloLoader.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        this.findViewById(R.id.cursorLoaderButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelloLoader.this, HelloCursorLoader.class));
            }
        });

        this.findViewById(R.id.multipleLoadersButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelloLoader.this, HelloMultipleLoaders.class));
            }
        });
        
    }

}
