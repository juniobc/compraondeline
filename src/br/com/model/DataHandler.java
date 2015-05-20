package br.com.model;
import android.database.sqlite.*;
import android.content.*;
import br.com.entidade.*;
import android.database.*;
import java.util.*;


public class DataHandler extends SQLiteOpenHelper
{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "compraondeline";

    // Contacts table name
    private static final String TABLE_PRODUTO = "produtos";

    // Contacts Table Columns names
    private static final String KEY_ID = "cd_produto";
    private static final String KEY_NM_PROD = "nm_prod";
    private static final String KEY_PRECO = "vl_prod";
	private static final String KEY_CD_BARRA= "cd_barra";
	private static final String KEY_LAT = "cd_Lat";
	private static final String KEY_LONG = "cd_long";
	private static final String KEY_QUANT = "qt_prod";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PRODUTO + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NM_PROD + " TEXT,"
			+ KEY_PRECO + " TEXT," + KEY_CD_BARRA + " TEXT," + KEY_LAT + " TEXT," + 
			KEY_LONG + " TEXT," + KEY_QUANT + " TEXT" + ")";
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
        values.put(KEY_PRECO, produto.getPreco());
		values.put(KEY_LAT, produto.getNrLat());
		values.put(KEY_LONG, produto.getNrLong());
		values.put(KEY_QUANT, produto.getQuantidade());

        // Inserting Row
        db.insert(TABLE_PRODUTO, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Produto getProduto(int nm_prod) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUTO, new String[] { KEY_ID,
									 KEY_NM_PROD, KEY_PRECO, KEY_CD_BARRA, KEY_QUANT, 
									 KEY_LAT, KEY_LONG }, KEY_NM_PROD + "=?",
								 new String[] { String.valueOf(nm_prod) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Produto produto = new Produto(cursor.getString(1), cursor.getString(0), 
									  cursor.getString(3), cursor.getString(4), cursor.getString(5));
        // return contact
        return produto;
    }

    // Getting All Contacts
    public List<Produto> getAllProdutos() {
        List<Produto> produtoList = new ArrayList<Produto>();
        // Select All Query
        String selectQuery = "SELECT "+KEY_NM_PROD+", "+KEY_PRECO+", "+KEY_CD_BARRA+", "+KEY_QUANT+", "+KEY_LAT+", "+KEY_LONG+" FROM " + TABLE_PRODUTO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setNome(cursor.getString(0));
				produto.setPreco(cursor.getString(1));
				produto.setQuantidade(cursor.getString(3));
				produto.setNrLat(cursor.getString(4));
				produto.setNrLong(cursor.getString(5));
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

        ContentValues values = new ContentValues();
        values.put(KEY_NM_PROD, produto.getNome());
        values.put(KEY_PRECO, produto.getPreco());
        values.put(KEY_CD_BARRA, produto.getCdBarra());
        values.put(KEY_QUANT, produto.getQuantidade());
        values.put(KEY_LAT, produto.getNrLat());
        values.put(KEY_LONG, produto.getNrLong());

        // updating row
        return db.update(TABLE_PRODUTO, values, KEY_NM_PROD + " = ?",
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
