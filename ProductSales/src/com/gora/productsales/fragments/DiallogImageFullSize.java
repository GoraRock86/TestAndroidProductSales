package com.gora.productsales.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gora.productsales.R;

public class DiallogImageFullSize extends DialogFragment {

	public static final String TAG_NAME = DiallogImageFullSize.class.toString();
	private View viewContent = null;
	private ImageView imgFullSize = null;
	public static final String KEY_ARGUMENTS =  "arguments_fullsize";
	private TypedArray navMenuIcons = null;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Dialog);
	}

	@SuppressLint("Recycle")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		viewContent = inflater.inflate(R.layout.fragment_full_size, container, false);
		imgFullSize = (ImageView)viewContent.findViewById(R.id.imageSource);
		navMenuIcons = getResources().obtainTypedArray(R.array.cat_drawer_img);
		if(getArguments() != null){
			String srtImg  =  getArguments().getString(KEY_ARGUMENTS);
			String[] ArrImgs = getActivity().getResources().getStringArray(R.array.ImageCatalog);
			for (int i = 0; i < ArrImgs.length; i++) {
				if(ArrImgs[i].equals(srtImg)){
					imgFullSize.setImageResource(navMenuIcons.getResourceId(i, -1));
					break;
				}
			}
		}

		return viewContent;
	}

}
