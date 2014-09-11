package com.gora.productsales.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gora.productsales.R;

public class Utils {
	
	public static boolean validateTxt(LinearLayout LLRoot, Context context, boolean isdataRight){
		try {//
			int countRoot = LLRoot != null?LLRoot.getChildCount():0;
			for(int i =0;i<countRoot;i++){
			    View viewChild = LLRoot.getChildAt(i);
			    if(viewChild != null && viewChild instanceof LinearLayout){
			    	LinearLayout LLChild = ((LinearLayout)viewChild);
			    	isdataRight = validateTxt(LLChild,context, isdataRight );
			    }else if(viewChild != null && viewChild instanceof EditText){
	    			 EditText editTxtTemp = (EditText) viewChild;

	    			 if(isStrEmpty(editTxtTemp.getText().toString())){
	    				 editTxtTemp.setError(context.getString(R.string.error_activity_register));
	    				 isdataRight = false;
	    			 }else{
		    				 editTxtTemp.setError(null);
				    }
			    }
			}	
		} catch (Exception e) {
			isdataRight = false;
		}
		return isdataRight;
	}

	public static boolean isStrEmpty(String strToCheck){
		if(strToCheck.isEmpty() || strToCheck.equals("") || strToCheck == null)
			return true;
		else
			return false;
	}
	
	public static void setViewVisibility( View viewtem, int temVisibility){
		if(viewtem != null && viewtem instanceof TextView){
			TextView TxtViewTem  = (TextView) viewtem;
			TxtViewTem.setVisibility(temVisibility);
		}
	}
	
	public static boolean isMatchStr(String strToMatch, String strToMatchSecon){
		return strToMatch.equals(strToMatchSecon) || strToMatch == strToMatchSecon;
	}

	public static int tryCastInt(String strToCast){
		try {
			return Integer.parseInt(strToCast);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean closeKeyBoard(EditText texField, Context context){
		boolean isClose = false;
		if(texField != null){
			InputMethodManager imm = (InputMethodManager)  context.getSystemService(Context.INPUT_METHOD_SERVICE);
			isClose = imm.hideSoftInputFromWindow( texField.getWindowToken(), 0);
		}
		return isClose;
	}
}
