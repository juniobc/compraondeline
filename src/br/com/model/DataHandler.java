package br.com.model;
import android.database.sqlite.*;
import android.content.*;
import br.com.entidade.*;
import android.database.*;
import java.util.*;


public class DataHandler extends SQLiteOpenHelper
{

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "compraondeline";

    // Contacts table name
    private static final String TABLE_PRODUTO = "produtos";

    // Contacts Table Columns names
    private static final String KEY_ID = "cd_produto";
    private static final String KEY_NM_PROD = "nm_prod";
    private static final String KEY_TP_UN_PROD = "tp_un_prod";
    private static final String KEY_QT_TP_UN = "qt_tp_un";
    private static final String KEY_PRECO = "vl_prod";
    private static final String KEY_QUANT = "qt_prod";
	private static final String KEY_CD_BARRA= "cd_barra";
	private static final String KEY_LAT = "cd_Lat";
	private static final String KEY_LONG = "cd_long";
	private static final String KEY_TP_CAD = "tp_cad";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PRODUTO + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NM_PROD + " TEXT,"
			+ KEY_TP_UN_PROD + " TEXT," + KEY_QT_TP_UN + " INTEGER,"
			+ KEY_PRECO + " REAL," + KEY_CD_BARRA + " TEXT," + KEY_LAT + " TEXT," + 
			KEY_LONG + " TEXT," + KEY_QUANT + " INTEGER," + KEY_TP_CAD + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NM_PROD, produto.getNome());
        values.put(KEY_TP_UN_PROD, produto.getTpUnidade());
        values.put(KEY_QT_TP_UN, produto.getQtUnidade());
        values.put(KEY_PRECO, produto.getPreco());
        values.put(KEY_QUANT, produto.getQuantidade());
		values.put(KEY_LAT, produto.getNrLat());
		values.put(KEY_LONG, produto.getNrLong());
		values.put(KEY_TP_CAD, produto.getTpCad());

        // Inserting Row
        db.insert(TABLE_PRODUTO, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Produto getProduto(int nm_prod) {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean tpCad;

        Cursor cursor = db.query(TABLE_PRODUTO, new String[] { KEY_ID,
									 KEY_NM_PROD, KEY_TP_UN_PROD, KEY_QT_TP_UN, KEY_PRECO, KEY_CD_BARRA, KEY_QUANT, 
									 KEY_LAT, KEY_LONG, KEY_TP_CAD }, KEY_NM_PROD + "=?",
								 new String[] { String.valueOf(nm_prod) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        if(cursor.getInt(8) == 0){
        	
        	tpCad = false;
        	
        }else{
        	
        	tpCad = true;
        	
        }

        Produto produto = new Produto(cursor.getString(1), cursor.getString(2), 
									  cursor.getFloat(3), cursor.getFloat(4), cursor.getInt(5)
									  , cursor.getString(6), cursor.getString(7), tpCad);
        // return contact
        return produto;
    }

    // Getting All Contacts
    public List<Produto> getAllProdutos() {
        List<Produto> produtoList = new ArrayList<Produto>();
        Boolean tpCad;
        // Select All Query
        String selectQuery = "SELECT "+KEY_NM_PROD+", "+KEY_TP_UN_PROD+", "+KEY_QT_TP_UN+", "+KEY_PRECO+", "+KEY_QUANT+", "
        +KEY_CD_BARRA+", "+KEY_LAT+", "+KEY_LONG+", "+KEY_TP_CAD+" FROM " + TABLE_PRODUTO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setNome(cursor.getString(0));
                produto.setTpUnidade(cursor.getString(1));
                produto.setQtUnidade(cursor.getFloat(2));
				produto.setPreco(cursor.getFloat(3));
				produto.setQuantidade(cursor.getInt(4));
				produto.setNrLat(cursor.getString(5));
				produto.setNrLong(cursor.getString(6));
				
				if(cursor.getInt(7) == 0){
		        	
		        	tpCad = false;
		        	
		        }else{
		        	
		        	tpCad = true;
		        	
		        }
				
				produto.setTpCad(tpCad);
				
                // Adding contact to list
                produtoList.add(produto);
            } while (cursor.moveToNext());
        }

        // return contact list
        return produtoList;
    }

    // Updating single contact
    public int updateProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        int tpCad;

        ContentValues values = new ContentValues();
        values.put(KEY_NM_PROD, produto.getNome());
        values.put(KEY_TP_UN_PROD, produto.getTpUnidade());
        values.put(KEY_QT_TP_UN, produto.getQtUnidade());
        values.put(KEY_PRECO, produto.getPreco());
        values.put(KEY_CD_BARRA, produto.getCdBarra());
        values.put(KEY_QUANT, produto.getQuantidade());
        values.put(KEY_LAT, produto.getNrLat());
        values.put(KEY_LONG, produto.getNrLong());
        
        if(!produto.getTpCad()){
        	
        	tpCad = 0;
        	
        }else{
        	
        	tpCad = 1;
        	
        }
        
        values.put(KEY_TP_CAD, tpCad);

        // updating row
        return db.update(TABLE_PRODUTO, values, KEY_ID + " = ?",
						 new String[] { String.valueOf(produto.getNome()) });
    }

    // Deleting single contact
    public void deleteProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUTO, KEY_NM_PROD + " = ?",
				  new String[] { String.valueOf(produto.getNome()) });
        db.close();
    }
    
 // Deleting single contact
    public void deleteAllProduto() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUTO, null, null);
        db.close();
    }


    public int getProdutosCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUTO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
	
	
	
}
