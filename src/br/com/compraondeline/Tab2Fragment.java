package br.com.compraondeline;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.entidade.Produto;
import br.com.localizacao.GPSTracker;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.maps.CustomMapFragment;
import br.com.model.*;

/**
 * @author Sebastio Junio
 *
 */
public class Tab2Fragment extends Fragment implements CustomMapFragment.OnMapReadyListener  {
	
	private static TextView end_prod;
	private static Geocoder geocoder;
		
	private static CustomMapFragment mMapFragment;
	private static GoogleMap mMap;
	
	private double nrLat;
	private double nrLong;
	private Button cadastrar;
	private EditText nmProd;
	private EditText nmPreco;
	private EditText nmQuant;
	
	public Tab2Fragment() {
	    super();

	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.tab2, container, false);
    	
    	buscaLocal();
    	
    	mMapFragment = CustomMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.map, mMapFragment).commit();
    	
    	Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnMapReadyListener) {
            ((OnMapReadyListener) fragment).onMapReady();
        }
        
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
    	
    	end_prod = (TextView) view.findViewById(R.id.end_prod);
    	
    	nmProd = (EditText) view.findViewById(R.id.nm_prod);
    	nmPreco = (EditText) view.findViewById(R.id.preco_prod);
    	nmQuant = (EditText) view.findViewById(R.id.quant_prod);
    	
    	cadastrar = (Button) view.findViewById(R.id.btn_busca_prod);
    	/*cadastrar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
            	
            	if(nmProd.getText().toString().trim().equals("")){            		
            		
            		Toast.makeText(getActivity(), "Informe o nome do produto", Toast.LENGTH_LONG).show();
            		
            	}else if(nmPreco.getText().toString().trim().equals("")){            		
            		
            		Toast.makeText(getActivity(), "Informe o preco", Toast.LENGTH_LONG).show();
            		
            	}else if(nmQuant.getText().toString().trim().equals("")){            		
					
					Toast.makeText(getActivity(), "Informe a quantidade", Toast.LENGTH_LONG).show();
					
				}else{
					
					DataHandler db = new DataHandler(getActivity());
	            	
	                db.addProduto(new Produto(nmProd.getText().toString(), nmPreco.getText().toString(), 
	                		nmQuant.getText().toString(), Double.toString(nrLat), Double.toString(nrLong))); 
	                
	                List<Produto> produtos = db.getAllProdutos();       
	                
	                for (Produto cn : produtos) {
	                    String log = "Nome: "+cn.getNome()+" ,Preco: " + cn.getPreco() 
	                    		+ " ,Quantidade: " + cn.getQuantidade() + " ,Latitude: " + cn.getNrLat() + " ,Latitude: " + cn.getNrLong();
	                        // Writing Contacts to log
	                    Toast.makeText(getActivity(), log, Toast.LENGTH_LONG).show();
	            	
	                }			
					
				}  	
            	
            }
        });*/
    	
        return view;
    }

    
    @Override
    public void onMapReady() {
        mMap = mMapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(this.nrLat, this.nrLong), 15);
        mMap.animateCamera(cameraUpdate);
        
        buscaProduto();
        
        for (Produto object : buscaProduto()) {
			System.out.println(object);
			mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(Double.parseDouble(object.getNrLat().replace(",", ".")), 
	        		Double.parseDouble(object.getNrLong().replace(",", "."))))
	        .title(object.getNome())
	        );
		}
        
    }
    
	public List<Produto> buscaProduto(){
	    	
	    	List<Produto> produtoList ;
	    	
	    	DataHandler db = new DataHandler(getActivity());
	    	
	    	produtoList = db.getAllProdutos();
	    	
	    	return produtoList;
    	
    }
    
    public static void updateLocation(double nrLat, double nrLong){
    	
    	String endereco;
    	
    	endereco = "";
    	
    	mMap = mMapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(nrLat, nrLong), 15);
        mMap.animateCamera(cameraUpdate);
        
        try {    		
    	
	    	List<Address> addresses = geocoder.getFromLocation(nrLat, nrLong, 1);
	    	
	        if (addresses.size() > 0) {
	        	
	            Address address = addresses.get(0);
	            
	            if(address.getLocality() != null)
	            	endereco = endereco + " " + address.getLocality();
	            if(address.getSubLocality() != null)
	            	endereco = endereco + " " + address.getSubLocality();
	            if(address.getSubAdminArea() != null)
	            	endereco = endereco + " " + address.getSubAdminArea();
	            if(address.getAdminArea() != null)
	            	endereco = endereco + " " + address.getAdminArea();
	            if(address.getCountryName() != null)
	            	endereco = endereco + " " + address.getCountryName();
	            
	            end_prod.setText(endereco);
	            //result.append(address.getLocality()).append("\n");
	            //result.append(address.getCountryName());
	        }
        
    	}catch (IOException e) {
            Log.e("Tab2Fragment", e.getMessage());
        }
    	
    }
    
    /**
     * Listener interface to tell when the map is ready
     */
    public static interface OnMapReadyListener {
        void onMapReady();
    }
    
    public void buscaLocal(){
    	
    	GPSTracker gps;

		// create class object
		gps = new GPSTracker(getActivity());

		// check if GPS enabled     
		if(gps.canGetLocation()){
			
			this.nrLat = gps.getLatitude();
			this.nrLong = gps.getLongitude();
			
		}else{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

    }
    
    
}
