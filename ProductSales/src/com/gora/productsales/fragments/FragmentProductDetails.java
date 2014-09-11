package com.gora.productsales.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gora.productsales.R;
import com.gora.productsales.model.Product;
import com.gora.productsales.source.CustomSQLiteOpenHelper;

public class FragmentProductDetails extends Fragment implements OnClickListener{
	public static final String TAG_NAME = FragmentProductDetails.class.toString();
	public static final String KEY_ARGUMENTS =  "arguments_product";
	private View viewContent = null;
	private ImageView imgProduct = null;
	private TextView txtIdProduct = null, txtProductName = null,
			txtProductDesc = null, txtProductRPrice = null,
			txtProductSPrice = null, txtProductColor = null,
			txtProductStore = null;
	
	private TypedArray navMenuIcons = null;
	private Product product = null;
	
	
	@SuppressLint("Recycle")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		try {
			viewContent = inflater.inflate(R.layout.fragment_detail_product, container,false);
			imgProduct = (ImageView)viewContent.findViewById(R.id.imageViewProduct);
			txtIdProduct = (TextView)viewContent.findViewById(R.id.txtIdProduct);
			txtProductName = (TextView)viewContent.findViewById(R.id.txtProductName);
			txtProductDesc = (TextView)viewContent.findViewById(R.id.txtProductDescription);
			txtProductRPrice = (TextView)viewContent.findViewById(R.id.txtProductRPrice);
			txtProductSPrice = (TextView)viewContent.findViewById(R.id.txtProductSPrice);
			txtProductColor = (TextView)viewContent.findViewById(R.id.txtProductColor);
			txtProductStore = (TextView)viewContent.findViewById(R.id.txtProductStores);
			viewContent.findViewById(R.id.btnDelete).setOnClickListener(this);
			viewContent.findViewById(R.id.btnUpdate).setOnClickListener(this);
			imgProduct.setOnClickListener(this);
			
			navMenuIcons = getResources().obtainTypedArray(R.array.cat_drawer_img);
			
			if(getArguments() != null){
				product  = (Product) getArguments().getParcelable(KEY_ARGUMENTS);
				inicializateView(product);
			}

		} catch (Exception e) {
			Log.e(TAG_NAME, "onCreateView() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
		return  viewContent;
	}
	

	public void inicializateView(Product _product) {
		try {
			product = _product;
			String[] ArrImgs = getActivity().getResources().getStringArray(R.array.ImageCatalog);
			for (int i = 0; i < ArrImgs.length; i++) {
				if(ArrImgs[i].equals(product.getProductPhoto())){
					imgProduct.setImageResource(navMenuIcons.getResourceId(i, -1));
					break;
				}
			}
			txtIdProduct.setText(product.getId() + "");
			txtProductName.setText(product.getName());
			txtProductDesc.setText(product.getDescription());
			txtProductRPrice.setText(product.getRegularPrice());
			txtProductSPrice.setText(product.getSalePrice());
			txtProductColor.setText(product.getColors());
			txtProductStore.setText(product.getStores());
		} catch (Exception e) {
			Log.e(TAG_NAME, "inicializateView() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDelete:
			if(product!=null){
				new AlertDialog.Builder(getActivity()).setMessage("Do you want to delete this aticle?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("Not", dialogClickListener).show();
			}
			break;
		case R.id.btnUpdate:
			DialogFragmentEditProduct dialogUpdate = new DialogFragmentEditProduct(){
				@Override
				public void refreshList(int _rowsAfected){
					if(_rowsAfected > 0){
						FragmentProductDetails.this.refreshList();
					}
				}
			};
			Bundle bundleUpdate = new Bundle();
			bundleUpdate.putParcelable(DialogFragmentEditProduct.KEY_ARGUMENTS, product);
			dialogUpdate.setArguments(bundleUpdate);
			dialogUpdate.show(getChildFragmentManager(), DialogFragmentEditProduct.TAG_NAME);
			break;
		case R.id.imageViewProduct:
			DiallogImageFullSize dialogFrag = new DiallogImageFullSize();
			Bundle bundle =  new Bundle();
			bundle.putString(DiallogImageFullSize.KEY_ARGUMENTS, product.getProductPhoto());
			dialogFrag.setArguments(bundle);
			dialogFrag.show(getChildFragmentManager(), DiallogImageFullSize.TAG_NAME);
			break;
		default:
			break;
		}
	}
	
	private void refreshList(){
		try {
			Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentProductsList.TAG_NAME);
			if(fragment != null && fragment.isVisible()){
				((FragmentProductsList)fragment).getProducts();
			}else{
				getActivity().getSupportFragmentManager().popBackStack();
			}
		} catch (Exception e) {
			Log.e(TAG_NAME, "refreshList() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
	}

	private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			try {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					if(new CustomSQLiteOpenHelper(getActivity()).deleteProducts(product.getId())){
						refreshList();
					}
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			} catch (Exception e) {
				Log.e(TAG_NAME, "onClickDelete() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
			}
		}
	};
}
