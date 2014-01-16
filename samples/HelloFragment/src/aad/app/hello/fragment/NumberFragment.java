package aad.app.hello.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NumberFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment and set the color
        View v = inflater.inflate(R.layout.number_layout, container, false);
        
        // Populate the TextView with the argument bundle
        Bundle argumentsBundle = getArguments(); 
        int number = argumentsBundle.getInt("number");
        
        TextView numberTextView = (TextView) v.findViewById(R.id.numberTextView);
        numberTextView.setText(String.valueOf(number));
        return v;
    }
}
