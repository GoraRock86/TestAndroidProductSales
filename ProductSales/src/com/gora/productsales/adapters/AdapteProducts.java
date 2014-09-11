package com.gora.productsales.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gora.productsales.R;
import com.gora.productsales.model.Product;

public class AdapteProducts  extends BaseAdapter {
	private List<Product> arrayProducts = null;
	private LayoutInflater inflater = null;
	private Context context = null;
	private TypedArray navMenuIcons = null;
	
	@SuppressLint("Recycle")
	public AdapteProducts(Context _context, List<Product> _arrayProducts){
		arrayProducts = _arrayProducts;
		context = _context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		navMenuIcons = context.getResources().obtainTypedArray(R.array.cat_drawer_img);
	}

	@Override
	public int getCount() {
		return  arrayProducts == null ? 0 : arrayProducts.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayProducts!=null? arrayProducts.get(position):null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			Product obj = arrayProducts != null ? arrayProducts.get(position) : null;
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_list_product, null);
				holder = new ViewHolder();
				holder.imgProduct = (ImageView) convertView.findViewById(R.id.imageViewItemProduct);
				holder.txtProductName = (TextView) convertView.findViewById(R.id.txtProductName);
				holder.txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductSPrice);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (obj != null) {
				String[] ArrImgs = context.getResources().getStringArray(R.array.ImageCatalog);
				for (int i = 0; i < ArrImgs.length; i++) {
					if(ArrImgs[i].equals(obj.getProductPhoto())){
						holder.imgProduct.setImageResource(navMenuIcons.getResourceId(i, -1));
						break;
					}
				}
				holder.txtProductName.setText(obj.getName());
				holder.txtProductPrice.setText(obj.getSalePrice());
			}
		} catch (Exception e) {
			Log.e("AdapterProductos", "getView Exception: " + e != null && e.getMessage() != null ? e.getMessage() : e + "");
		}
		return convertView;
	}

	static class ViewHolder{
		TextView txtProductName = null;
		TextView txtProductPrice = null;
		ImageView imgProduct = null;
	}

}
