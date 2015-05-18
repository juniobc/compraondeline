package br.com.compraondeline;

import br.com.localizacao.GPSTracker;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
  
/**
 * @author mwho
 *
 */
public class Tab2Fragment extends Fragment {
	
	private TextView nr_lat;
	private TextView nr_long;
	private String nrLat;
	private String nrLong;
	private View view;
	
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	this.view = inflater.inflate(R.layout.tab2, container, false);
    	
    	buscaLocal();
    	
    	nr_lat = (TextView) this.view.findViewById(R.id.lat_prod);
    	nr_lat.setText(this.nrLat);
    	
    	nr_long = (TextView) this.view.findViewById(R.id.long_prod);
    	nr_long.setText(this.nrLong);
    		    	
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        return view;
    }
    
    @Override
    public void onPause() {

    	super.onPause();
      
      	buscaLocal();
	 	
		nr_lat = (TextView) view.findViewById(R.id.lat_prod);
		nr_lat.setText(this.nrLat);
		
		nr_long = (TextView) view.findViewById(R.id.long_prod);
		nr_long.setText(this.nrLong);
      
    }
    
    public void buscaLocal(){
    	
    	GPSTracker gps;

		// create class object
		gps = new GPSTracker(getActivity());

		// check if GPS enabled     
		if(gps.canGetLocation()){

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			
			this.nrLat = Double.toString(latitude);
			this.nrLong = Double.toString(longitude); 
			
		}else{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

    }
    
    
}
