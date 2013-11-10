
package aad.app.hello.component;

import aad.app.hello.component.R;
import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;

public class HelloComponentActivity extends AccountAuthenticatorActivity {

    private static final String TAG = HelloComponentActivity.class.getSimpleName();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
                
    }
    
    
}
