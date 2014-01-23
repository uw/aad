package aad.app.exercise.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragment extends Fragment {

    private static final String TAG = "WelcomeFragment";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        Log.i(TAG, "onCreateView()");
        
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    
    
}
