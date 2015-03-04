package com.example.logintest.utils;

import android.text.TextUtils;


public class FormatUtils {

	public static final String removeBOM(String data) {

		if (TextUtils.isEmpty(data)) {

		return data;

		}


		if (data.startsWith("\ufeff")) {

		return data.substring(1);

		} else {

		return data;

		}

		}
	
}
