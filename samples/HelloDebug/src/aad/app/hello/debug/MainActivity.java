package aad.app.hello.debug;

import aad.app.hello.fail.Fail;
import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fail f = new Fail();
        f.exampleAccessNull1();
        f.exampleAccessNull2();
        
        f.exampleBadId(this.findViewById(android.R.id.content), R.id.start);
        f.exampleConcurrency();

    }
}