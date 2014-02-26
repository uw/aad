package aad.app.hello.game;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.game_view);
        
        // Need API Level 11 or higher
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

	@Override
	protected void onPause() {
		super.onPause();
		
		// Finish to resync the Animation thread
		this.finish();
	}

    
}
