package br.com.compraondeline.auxiliar;

import java.util.List;

import br.com.compraondeline.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.entidade.Produto;

public class ListaProdutos extends ArrayAdapter<Produto> {
	Context context;
	int resource;
	List<Produto> data = null;
	public ListaProdutos(Context context, int resource, List<Produto> object) {
		super(context, resource, object);
		this.context = context;
		this.resource = resource;
		this.data = object;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resource, parent,false);
		}
		LinearLayout root = (LinearLayout) convertView.findViewById(R.id.row);
		if((position % 2) == 0){
			root.setBackgroundColor(convertView.getResources().getColor(R.color.white));
		}	
		else{
			root.setBackgroundColor(convertView.getResources().getColor(R.color.white));
		}
		
		Produto object = data.get(position);
		
		TextView nm_produto = (TextView) convertView.findViewById(R.id.nm_produto);
		nm_produto.setText(object.getNome());
		
		TextView qt_produto = (TextView) convertView.findViewById(R.id.qt_produto);
		qt_produto.setText(object.getQuantidade());
		
		TextView vl_produto = (TextView) convertView.findViewById(R.id.vl_produto);
		vl_produto.setText(String.valueOf(object.getPreco()));

		/*TextView tv_numero = (TextView) convertView.findViewById(R.id.tv_numero);
		tv_numero.setText(object.numero);
		TextView tv_tempo = (TextView) convertView.findViewById(R.id.tv_tempo);
		tv_tempo.setText(String.valueOf(object.cont));*/
		
		return convertView;
	}

}
