package com.gora.productsales.async;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gora.productsales.model.Product;
import com.gora.productsales.source.CustomSQLiteOpenHelper;

public class AsyncSelectProducts extends AsyncTask<Context, Void, ArrayList<Product>> {

	@Override
	protected ArrayList<Product> doInBackground(Context... params) {
		ArrayList<Product> arrayProduct = null;
		try {
			CustomSQLiteOpenHelper dbProduct = new CustomSQLiteOpenHelper(params[0]);
			arrayProduct = dbProduct.selectProducts();
		} catch (Exception e) {
			Log.e("AsyncSelectProducts", "doInBackground() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
		return arrayProduct;
	}	
}
