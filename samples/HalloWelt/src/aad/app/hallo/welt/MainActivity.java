
package aad.app.hallo.welt;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        
        long costInDollars = 10000000;
        
        // TODO Any exchange rate conversion...
        
        TextView upgradeTextView = (TextView) this.findViewById(R.id.upgradeTextView);
        
        Locale locale = Locale.getDefault();
        String costFormatted = NumberFormat.getCurrencyInstance(locale).format(costInDollars);
        
        String upgradeString = this.getResources().getString(R.string.upgrade);
        String costString = String.format("%s %s", upgradeString, costFormatted);
        
        upgradeTextView.setText(costString);      
        
    }        
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
    
}
