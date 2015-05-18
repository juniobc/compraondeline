package br.com.entidade;

import android.graphics.Bitmap;

public class Produto {
	
	private String preco;
	private String nome;
	private String quantidade;
	private String cdBarra;
	private String nrLat;
	private String nrLong;
	
	public Produto(){}
	
	public Produto(String preco, String nome, String quantidade, String nr_lat, String nr_long) {
		
		super();
		
		this.preco = preco;
		this.nome = nome;
		this.quantidade = quantidade;
		this.nrLat = nr_lat;
		this.nrLong = nr_long;
		
	}
	
	public String getNrLat(){
		
		return this.nrLat;
		
	}
	
	public String getNrLong(){

		return this.nrLong;

	}
	
	public String getPreco(){
		
		return preco;
		
	}
	
	public String getNome(){
		
		return nome;
		
	}
	
	public String getQuantidade(){
		
		return quantidade;
		
	}
	
	public String getCdBarra(){
		
		return cdBarra;
		
	}
	
	public String setPreco(String preco){
		
		this.preco = preco;
		
		return preco;
		
	}
	
	public String setNome(String nome){
		
		this.nome = nome;
		
		return nome;
		
	}
	
	public String setQuantidade(String quantidade){
		
		this.quantidade = quantidade;
		
		return quantidade;
		
	}
	
	public String setCdBarra(String cdBarra){
		
		this.cdBarra = cdBarra;
		
		return cdBarra;
		
	}
	
	public void setNrLat(String lat){
		
		this.nrLat = lat;
		
	}
	
	public void setNrLong(String nrLong){

		this.nrLong = nrLong;

	}
	
	
}
