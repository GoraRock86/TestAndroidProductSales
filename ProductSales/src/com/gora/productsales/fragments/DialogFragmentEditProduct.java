package com.gora.productsales.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gora.productsales.R;
import com.gora.productsales.model.Product;
import com.gora.productsales.source.CustomSQLiteOpenHelper;
import com.gora.productsales.utils.Utils;

public class DialogFragmentEditProduct extends DialogFragment implements OnClickListener{
	public static final String TAG_NAME = DialogFragmentEditProduct.class.toString();
	public static final String KEY_ARGUMENTS =  "arguments_product_edit";
	private View viewContent = null;
	private ImageView imgProduct = null;
	private EditText txtProductName = null,
			txtProductDesc = null, txtProductRPrice = null,
			txtProductSPrice = null;

	private Spinner spinnerSelectImg = null, spinnerProductColor = null,
			spinnerProductStore = null;

	private TypedArray navMenuIcons = null;
	private Product product = null;
	private String[] catArrayImgs = null,  catArrayColor = null, catArrayStores = null;
	private int rowsAfected = 0;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setGravity(Gravity.CENTER);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}
	
	@SuppressLint("Recycle")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		try {
			viewContent = inflater.inflate(R.layout.fragment_product_editable, container,false);
			imgProduct = (ImageView)viewContent.findViewById(R.id.imageViewProduct);
			txtProductName = (EditText)viewContent.findViewById(R.id.EditTextName);
			txtProductDesc = (EditText)viewContent.findViewById(R.id.EditTextDescription);
			txtProductRPrice = (EditText)viewContent.findViewById(R.id.EditTextRPrice);
			txtProductSPrice = (EditText)viewContent.findViewById(R.id.EditTextSPrice);
			spinnerSelectImg = (Spinner)viewContent.findViewById(R.id.SpinnerSelectImg);
			spinnerProductColor = (Spinner)viewContent.findViewById(R.id.SpinnerColors);
			spinnerProductStore = (Spinner)viewContent.findViewById(R.id.spinnerStore);
			
			navMenuIcons = getResources().obtainTypedArray(R.array.cat_drawer_img);
			catArrayImgs = getActivity().getResources().getStringArray(R.array.ImageCatalog);
			catArrayColor = getResources().getStringArray(R.array.ColorsCatalog);
			catArrayStores = getResources().getStringArray(R.array.StoresCatag);

			// --------------to fill the spinnerSexo
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.item_spiner_gora, catArrayImgs);
			spinnerSelectImg.setAdapter(arrayAdapter);

			ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this.getActivity(),  R.layout.item_spiner_gora, catArrayColor);
			spinnerProductColor.setAdapter(arrayAdapter2);

			ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this.getActivity(),  R.layout.item_spiner_gora, catArrayStores);
			spinnerProductStore.setAdapter(arrayAdapter3);
			
			spinnerSelectImg.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					try {
						setImageSpiner(catArrayImgs[position]);
					} catch (Exception e) {}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});

			viewContent.findViewById(R.id.btnSave).setOnClickListener(this);
			
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
			setImageSpiner(product.getProductPhoto());
			txtProductName.setText(product.getName());
			txtProductDesc.setText(product.getDescription());
			txtProductRPrice.setText(product.getRegularPrice());
			txtProductSPrice.setText(product.getSalePrice());
			setSelectionSpinner(product.getProductPhoto(), catArrayImgs, spinnerSelectImg);
			setSelectionSpinner(product.getColors(), catArrayColor, spinnerProductColor);
			setSelectionSpinner(product.getStores(), catArrayStores, spinnerProductStore);
		} catch (Exception e) {
			Log.e(TAG_NAME, "inicializateView() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
	}

	private void setImageSpiner(String strImage) {
		for (int i = 0; i < catArrayImgs.length; i++) {
			if(catArrayImgs[i].equals(strImage)){
				imgProduct.setImageResource(navMenuIcons.getResourceId(i, -1));
				break;
			}
		}
	}

	private void setSelectionSpinner(String strItem, String[] ArrStr, Spinner spinner) {
		for (int i = 0; i < ArrStr.length; i++) {
			if(ArrStr[i].equals(strItem)){
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	private void fillProduct(){
		try {
			product.setName(txtProductName.getText().toString());
			product.setRegularPrice(txtProductRPrice.getText().toString());
			product.setSalePrice(txtProductSPrice.getText().toString());
			product.setDescription(txtProductDesc.getText().toString());
			product.setProductPhoto(catArrayImgs[spinnerSelectImg.getSelectedItemPosition()]);
			product.setColors(catArrayColor[spinnerProductColor.getSelectedItemPosition()]);
			product.setStores(catArrayStores[spinnerProductStore.getSelectedItemPosition()]);
		} catch (Exception e) {
			Log.e(TAG_NAME, "fillProduct() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
	}

	@Override
	public void onClick(View v) {
		try {
			if(Utils.validateTxt((LinearLayout)viewContent.findViewById(R.id.llContentFields), getActivity(), true)){
				if(product != null){
					fillProduct();
					rowsAfected = new CustomSQLiteOpenHelper(getActivity()).updateProducts(product);
					refreshList(rowsAfected);
				}else{
					product = new Product();
					fillProduct();
					new CustomSQLiteOpenHelper(getActivity()).insertProducts(product);
				}
				Toast.makeText(getActivity(), "successful operation.", Toast.LENGTH_LONG).show();
				this.dismiss();
			}
		} catch (Exception e) {
			Log.e(TAG_NAME, "fillProduct() Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
	}
	
	public void refreshList(int _rowsAfected){
	}
}
