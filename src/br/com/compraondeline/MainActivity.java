package br.com.compraondeline;

import br.com.localizacao.GPSTracker;
import br.com.rede.BuscaNmProd;
import br.com.util.ParametrosGlobais;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private Button btnShowLocation;
	private Fragment currentFragment;
	private ListView list_prod;
	private EditText nm_prod;
	private BuscaNmProd buscaNmProd;
	private GPSTracker gps;
	
	@Override
	public void onCreate(Bundle bundle){
		
		super.onCreate(bundle);
		setContentView(R.layout.compraondeline);
		
		btnShowLocation = (Button) findViewById(R.id.busca_prod);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
                // create class object
                gps = new GPSTracker(MainActivity.this);
 
                // check if GPS enabled     
                if(gps.canGetLocation()){
                     
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                     
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
        });
		
	}
	
	public void buscaNomeProduto(View view){
	    		    	
			list_prod = (ListView) findViewById(R.id.list_prod);
			nm_prod = (EditText) findViewById(R.id.nm_prod);
			
			buscaNmProd = new BuscaNmProd(MainActivity.this, MainActivity.this, ParametrosGlobais.ORIGEM_ACTIVITY);
			buscaNmProd.execute(new String[]{captcha.getText().toString().toUpperCase(), chave_acesso.getText().toString(), 
					view_state.getText().toString(),event_validation.getText().toString(), token.getText().toString()});
    	
    }

}
