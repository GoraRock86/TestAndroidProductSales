package com.gora.productsales.source;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gora.productsales.model.Product;

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
	
	 public static final String TABLE_PRODUCTS = "products";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_DESC = "description";
	  public static final String COLUMN_RPRICE = "regular_price";
	  public static final String COLUMN_SPRICE = "sale_price";
	  public static final String COLUMN_PHOTO = "product_photo";
	  public static final String COLUMN_COLORS = "colors";
	  public static final String COLUMN_STORES = "stores";

	  private static final String DATABASE_NAME = "products.db";
	  private static final int DATABASE_VERSION = 1;
	  @SuppressLint("SdCardPath")
	  private static final String DB_PATH = "/data/data/com.gora.productsales/databases/" + DATABASE_NAME; 
	  
	// Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_PRODUCTS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " 
	      + COLUMN_NAME + " text not null, "
	      + COLUMN_DESC + " text not null, "
	      + COLUMN_RPRICE + " text not null, "
	      + COLUMN_SPRICE + " text not null, "
	      + COLUMN_PHOTO + " text not null, "
	      + COLUMN_COLORS + " text not null, "
	      + COLUMN_STORES + " text not null);";

	public CustomSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void insertProducts(Product product){
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_NAME,product.getName());
			contentValues.put(COLUMN_DESC,product.getDescription());
			contentValues.put(COLUMN_RPRICE,product.getRegularPrice());
			contentValues.put(COLUMN_SPRICE,product.getSalePrice());
			contentValues.put(COLUMN_PHOTO,product.getProductPhoto());
			contentValues.put(COLUMN_COLORS,product.getColors());
			contentValues.put(COLUMN_STORES,product.getStores());

			db.insert(TABLE_PRODUCTS, null, contentValues);
		} catch (Exception e) {
			Log.e("SQLiteOpenHelper", "createProducts() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");		
		} finally { 
			if(db !=null && db.isOpen())
				db.close();
		}
	}

	public ArrayList<Product> selectProducts(){
		SQLiteDatabase db = null;
		ArrayList<Product> arrayProduct = new ArrayList<Product>();
		try {
			db = this.getReadableDatabase();
			Cursor res =  db.rawQuery( "select * from " + TABLE_PRODUCTS, null );
			res.moveToFirst();
		      while(res.isAfterLast() == false){
		    	  Product objProduct = new Product().setId(res.getInt(res.getColumnIndex(COLUMN_ID)))
		    			  .setName(res.getString(res.getColumnIndex(COLUMN_NAME)))
		    			  .setDescription(res.getString(res.getColumnIndex(COLUMN_DESC)))
		    			  .setRegularPrice(res.getString(res.getColumnIndex(COLUMN_RPRICE)))
		    			  .setSalePrice(res.getString(res.getColumnIndex(COLUMN_SPRICE)))
		    			  .setProductPhoto(res.getString(res.getColumnIndex(COLUMN_PHOTO)))
		    			  .setColors(res.getString(res.getColumnIndex(COLUMN_COLORS)))
		    			  .setStores(res.getString(res.getColumnIndex(COLUMN_STORES)));
		    	  arrayProduct.add(objProduct);
		    	  res.moveToNext();
		      }
		} catch (Exception e) {
			Log.e("SQLiteOpenHelper", "createProducts() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");		
		} finally { 
			if(db !=null && db.isOpen())
				db.close();
		}
		return arrayProduct;
	}

	public int updateProducts(Product product){
		SQLiteDatabase db = null;
		int rowsAffected = 0;
		try {
			db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_NAME,product.getName());
			contentValues.put(COLUMN_DESC,product.getDescription());
			contentValues.put(COLUMN_RPRICE,product.getRegularPrice());
			contentValues.put(COLUMN_SPRICE,product.getSalePrice());
			contentValues.put(COLUMN_PHOTO,product.getProductPhoto());
			contentValues.put(COLUMN_COLORS,product.getColors());
			contentValues.put(COLUMN_STORES,product.getStores());

			rowsAffected = db.update(TABLE_PRODUCTS, contentValues, COLUMN_ID  + "=" + product.getId(), null);
		} catch (Exception e) {
			Log.e("SQLiteOpenHelper", "createProducts() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");		
		} finally { 
			if(db !=null && db.isOpen())
				db.close();
		}
		return rowsAffected;
	}

	public boolean deleteProducts(int idProduct){
		SQLiteDatabase db = null;
		boolean actionDone = false;
		try {
			db = this.getWritableDatabase();
			int numrows = db.delete(TABLE_PRODUCTS, COLUMN_ID + " = " + idProduct, null);
			if(numrows>0)
				actionDone = true;
		} catch (Exception e) {
			Log.e("SQLiteOpenHelper", "createProducts() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");		
		} finally { 
			if(db !=null && db.isOpen())
				db.close();
		}
		return actionDone;
	}
}