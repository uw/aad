package aad.app.hello.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ColorFragment extends Fragment {

    private int mColor = 0; // Default to black
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment and set the color
        View v = inflater.inflate(R.layout.color_layout, container, false);
        setColor(mColor);
        return v;
    }
    
    
    @Override
	public void onResume() {

        this.getView().setBackgroundColor(mColor);
		super.onResume();
	}


	public void setColor(int color) {        
        
        mColor = color;
        View view = this.getView();
        if (view != null)
        	view.setBackgroundColor(mColor);
        
    }

}
