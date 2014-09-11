package com.gora.productsales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gora.productsales.appdata.AppData;
import com.gora.productsales.async.AsyncCreteProducts;
import com.gora.productsales.fragments.DialogFragmentEditProduct;
import com.gora.productsales.fragments.FragmentHome;
import com.gora.productsales.fragments.FragmentProductsList;
import com.gora.productsales.model.Product;
import com.gora.productsales.source.CustomSQLiteOpenHelper;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_main);
			if(findViewById(R.id.LLContent)!= null){
				loadFragmentContent(FragmentHome.class.getName(), null, FragmentHome.TAG_NAME, false);
			}

			createProducts();
		} catch (Exception e) {
			Log.e("MainActivity", "onCreate() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
	}

	private void createProducts() {
		new AsyncCreteProducts(){
			@Override
			protected void onPostExecute(JSONArray arrayProducts) {
				try {
					CustomSQLiteOpenHelper dbProduct = new CustomSQLiteOpenHelper(getApplicationContext());
					
					if(dbProduct.selectProducts().size() < 1){
						for (int j = 0; j < arrayProducts.length(); j++) {
							JSONObject obj = null;
							obj = arrayProducts.getJSONObject(j);

							if (obj != null) {
								Product objProduct = new Product().setName(obj.optString(AppData.KEY_PRODUCT_NAME,""))
										.setDescription(obj.optString(AppData.KEY_PRODUCT_DECRIPTION,""))
										.setRegularPrice(obj.optString(AppData.KEY_PRODUCT_RPRICE,""))
										.setSalePrice(obj.optString(AppData.KEY_PRODUCT_SPRICE,""))
										.setProductPhoto(obj.optString(AppData.KEY_PRODUCT_PHOTO,""))
										.setColors(obj.optString(AppData.KEY_PRODUCT_COLOR,""))
										.setStores(obj.optString(AppData.KEY_PRODUCT_STORES,""));
								dbProduct.insertProducts(objProduct);
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(this, "Created by Aldo Godinez", Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void aux_click_view(View view) {
		switch (view.getId()) {
		case R.id.btnCreateProduct:
			DialogFragmentEditProduct dialog = new DialogFragmentEditProduct();
			dialog.show(getSupportFragmentManager(), DialogFragmentEditProduct.TAG_NAME);
			break;
		case R.id.btnShowProduct:
			loadFragmentContent(FragmentProductsList.class.getName(), null, FragmentProductsList.TAG_NAME, true);
			break;
		default:
			break;
		}
	}

	public void loadFragmentContent(String strClasname, Bundle bundle, String tagFragment, boolean addBackStack) {
		try {
			if (!isInsideFragmentManager(tagFragment)) {
				Fragment fragmentToLoad = (Fragment) Class.forName(strClasname).newInstance();
				if(bundle != null)
					fragmentToLoad.setArguments(bundle);
				FragmentManager fragMngr = getSupportFragmentManager();
				FragmentTransaction trans = fragMngr.beginTransaction();
				trans.replace(R.id.LLContent, fragmentToLoad, tagFragment);
				if (addBackStack) {
					trans.addToBackStack(tagFragment);
				}
				trans.commit();
			}
		} catch (Exception e) {
			Log.e("MainActivity", "loadFragmentContent() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
	}

	public boolean isInsideFragmentManager(String strTag){
		Fragment fragmentToFind =  getSupportFragmentManager().findFragmentByTag(strTag);
		if(fragmentToFind!=null){
			getSupportFragmentManager().popBackStack(strTag, 0);
			return true;
		}
		return false;
	}
}
