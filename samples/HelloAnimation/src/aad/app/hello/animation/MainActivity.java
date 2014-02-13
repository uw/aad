package aad.app.hello.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    
    private AnimationFragment mAnimationFragment;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		findViewById(R.id.activityButton).setOnClickListener(this);
		findViewById(R.id.fragmentButton).setOnClickListener(this);
		findViewById(R.id.growButton).setOnClickListener(this);
		findViewById(R.id.shrinkButton).setOnClickListener(this);

	}

    @Override
    public void onClick(View v) {
        
        switch (v.getId()) {
            
            case R.id.activityButton:
                startActivity(new Intent(this, AnimationActivity.class));
                
                // This is a little weird... we call it after we startActivity
                overridePendingTransition(R.anim.slide_right_fade_in, android.R.anim.fade_out);
                break;
                
            case R.id.fragmentButton:
                mAnimationFragment = (AnimationFragment) getFragmentManager().findFragmentByTag("AnimationFragment");
                if (mAnimationFragment == null) {
                    
                    mAnimationFragment = new AnimationFragment();
                    
                    getFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                        .add(R.id.mainFrameLayout, mAnimationFragment, "AnimationFragment")
                        .addToBackStack(null)
                        .commit();
                    
                }
                break;
                
            case R.id.growButton:
                AnimatorSet growAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.grow);
                growAnimation.setTarget(findViewById(R.id.progressBar));
                growAnimation.start();
                break;
                
            case R.id.shrinkButton:
                AnimatorSet shrinkAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.shrink);
                shrinkAnimation.setTarget(findViewById(R.id.progressBar));
                shrinkAnimation.start();
                break;
            
        }
        
    }
}