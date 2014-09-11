package com.gora.productsales.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gora.productsales.R;
import android.support.v4.app.Fragment;

public class FragmentHome extends Fragment {
	public static final String TAG_NAME = FragmentHome.class.toString();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return  inflater.inflate(R.layout.fragment_home, container,false);
	}
}
