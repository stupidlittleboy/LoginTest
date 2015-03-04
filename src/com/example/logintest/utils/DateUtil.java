package com.example.logintest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateUtil {
	
	public static void datePickerDialog(Context context, 
			final EditText editText, Calendar calendar) {
		DatePickerDialog.OnDateSetListener dateListener = 
		new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, 
					int year, int month, int dayOfMonth) {
				//Calendar月份是从0开始,所以month要加1
				CharSequence date = year + "-" + (month+1) + "-" + dayOfMonth;
				Date dateFormat = null;
				try {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				editText.setText(new SimpleDateFormat("yyyy-MM-dd").format(dateFormat));
			}
		};
		new DatePickerDialog(context,
				dateListener,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}

}
