package aad.app.hello.animation;

import android.app.Activity;
import android.os.Bundle;

public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_animation);
    }

    @Override
    public void finish() {
        
        super.finish();
        
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_left_fade_out);
    }

    
    
    
}
