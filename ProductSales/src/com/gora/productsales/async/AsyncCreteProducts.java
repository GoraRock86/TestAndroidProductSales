package com.gora.productsales.async;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.gora.productsales.appdata.AppData;

public class AsyncCreteProducts extends AsyncTask<Void, Void, JSONArray> {

	@Override
	protected JSONArray doInBackground(Void... arg0) {
		JSONArray arrayProducts = new JSONArray();
		try {
			JSONObject obj = new JSONObject();
			obj.put(AppData.KEY_PRODUCT_NAME, "MacBoock 13").toString();
			obj.put(AppData.KEY_PRODUCT_DECRIPTION, "the noteboock that the people love").toString();
			obj.put(AppData.KEY_PRODUCT_RPRICE, "4,500.00").toString();
			obj.put(AppData.KEY_PRODUCT_SPRICE, "4,100.00").toString();
			obj.put(AppData.KEY_PRODUCT_PHOTO, "Computer").toString();
			obj.put(AppData.KEY_PRODUCT_COLOR, "Red").toString();
			obj.put(AppData.KEY_PRODUCT_STORES, "E-bay").toString();
			arrayProducts.put(obj);

			obj = new JSONObject();
			obj.put(AppData.KEY_PRODUCT_NAME, "Ipad2").toString();
			obj.put(AppData.KEY_PRODUCT_DECRIPTION,"the table that the people love").toString();
			obj.put(AppData.KEY_PRODUCT_RPRICE, "4,500.00").toString();
			obj.put(AppData.KEY_PRODUCT_SPRICE, "4,100.00").toString();
			obj.put(AppData.KEY_PRODUCT_PHOTO, "Ipad").toString();
			obj.put(AppData.KEY_PRODUCT_COLOR, "White").toString();
			obj.put(AppData.KEY_PRODUCT_STORES, "BestBuy").toString();
			arrayProducts.put(obj);

			obj = new JSONObject();
			obj.put(AppData.KEY_PRODUCT_NAME, "Smar-Tv").toString();
			obj.put(AppData.KEY_PRODUCT_DECRIPTION,"the noteboock that the people love").toString();
			obj.put(AppData.KEY_PRODUCT_RPRICE, "4,500.00").toString();
			obj.put(AppData.KEY_PRODUCT_SPRICE, "4,100.00").toString();
			obj.put(AppData.KEY_PRODUCT_PHOTO, "SmartTV").toString();
			obj.put(AppData.KEY_PRODUCT_COLOR, "Red").toString();
			obj.put(AppData.KEY_PRODUCT_STORES, "Amazon").toString();
			arrayProducts.put(obj);
		} catch (JSONException e) {
			Log.e("AsyncCreteProducts", "doInBackground() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
		return arrayProducts;
	}
}
