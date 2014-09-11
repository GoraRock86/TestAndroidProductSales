package com.gora.productsales.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gora.productsales.MainActivity;
import com.gora.productsales.R;
import com.gora.productsales.adapters.AdapteProducts;
import com.gora.productsales.async.AsyncSelectProducts;
import com.gora.productsales.model.Product;

public class FragmentProductsList extends Fragment {
	public static final String TAG_NAME = FragmentProductsList.class.toString();
	private View viewContent = null;
	private ListView list = null;
	private View viewLoading = null;
	private FragmentProductDetails fragProductsDetail = null;
	private ArrayList<Product> arrayProduct = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		try {
			viewContent = inflater.inflate(R.layout.fragment_product_list, container,false);
			list = (ListView) viewContent.findViewById(R.id.listViewProducts);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					loadDetail(position);
				}
			});
			viewLoading = viewContent.findViewById(R.id.fLLoading);
			getProducts();
		}catch(Exception e){
			Log.e(TAG_NAME, "onCreateView() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
		return viewContent;
	}

	public void getProducts(){
		try {
			new AsyncSelectProducts(){

				@Override
				protected void onPreExecute() {
					list.setVisibility(View.GONE);
					viewLoading.setVisibility(View.VISIBLE);
				}

				@Override
				protected void onPostExecute(ArrayList<Product> _arrayProduct) {
					list.setVisibility(View.VISIBLE);
					viewLoading.setVisibility(View.GONE);
					arrayProduct = _arrayProduct;
					if(arrayProduct!= null && arrayProduct.size()>0){
						list.setAdapter(new AdapteProducts(getActivity(), arrayProduct)); 
						if(viewContent.findViewById(R.id.lLContentDetails) != null){
							loadDetail(0);
						}
					}else{
						if(list.getAdapter()!=null)
							list.setAdapter(null);
						Toast.makeText(getActivity(), "No products available", Toast.LENGTH_LONG).show();
					}
				}
			}.execute(getActivity());
		} catch (Exception e) {
			Log.e(TAG_NAME, "getProducts() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}		
	}

	protected void loadDetail(int index) {
		try {
			Product product = arrayProduct.get(index);
			if(viewContent.findViewById(R.id.lLContentDetails) != null){
				if(fragProductsDetail!=null){
					if(!fragProductsDetail.isVisible()){
						fragProductsDetail.onDestroy();
						fragProductsDetail= null;
						loadDetail(index);
						return;
					}
					fragProductsDetail.inicializateView(product);
				}else{
					FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
					fragProductsDetail  = new FragmentProductDetails();
					Bundle args = new Bundle();
					args.putParcelable(FragmentProductDetails.KEY_ARGUMENTS, product);
					fragProductsDetail.setArguments(args);
					t.replace(R.id.lLContentDetails, fragProductsDetail, FragmentProductDetails.TAG_NAME);
					t.commit();
				}
			}else{
				Bundle args = new Bundle();
				args.putParcelable(FragmentProductDetails.KEY_ARGUMENTS, product);
				((MainActivity) getActivity()).loadFragmentContent(FragmentProductDetails.class.getName(), args, FragmentProductDetails.TAG_NAME, true);
			}
		} catch (Exception e) {
			
		}
	}
}
