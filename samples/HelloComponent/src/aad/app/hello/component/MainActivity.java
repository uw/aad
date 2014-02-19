
package aad.app.hello.component;

import aad.app.hello.component.R;
import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;

public class MainActivity extends AccountAuthenticatorActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }
}
