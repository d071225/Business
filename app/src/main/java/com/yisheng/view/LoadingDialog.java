package com.yisheng.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yisheng.business.R;

/**
 * 
 * 网络加载等待提示框
 *
 */
public class LoadingDialog extends Dialog {
	public LoadingDialog(Context context) {
		super(context, R.style.MyDialogStyleTop);
	}

	private LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);
		LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
		linearLayout.getBackground().setAlpha(150);
	}
}
