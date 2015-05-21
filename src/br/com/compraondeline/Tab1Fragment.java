package br.com.compraondeline;

import java.io.IOException;



import java.util.List;

import br.com.compraondeline.auxiliar.ListaProdutos;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import br.com.entidade.Produto;
import br.com.model.DataHandler;
  
/**
 * @author mwho
 *
 */
public class Tab1Fragment extends Fragment {
		
	private ListView listProdView;
	
	/*private BuscaCapt buscaCapt;
    private BuscaNfe buscaNfe;
    private BuscaCB buscaCB;
    private GravaChaveAcesso gravaCA;
    private Long codigoBarra;*/
	
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.tab1, container, false); 	
    	
    	//NotaFiscal nfe = new NotaFiscal(emp, produto, 5, json.getString("dt_emis"));

		ListaProdutos adapter = new ListaProdutos(getActivity(),R.layout.lista_prod,buscaProduto());       
		
		listProdView = (ListView) view.findViewById(R.id.list_prod);
		
		listProdView.setAdapter(adapter);
    	
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
    
    public List<Produto> buscaProduto(){
    	
    	List<Produto> produtoList ;
    	
    	DataHandler db = new DataHandler(getActivity());
    	
    	produtoList = db.getAllProdutos();
    	
    	return produtoList;
    	
    }
    

}
