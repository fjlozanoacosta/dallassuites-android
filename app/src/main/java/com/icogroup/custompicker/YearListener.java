package com.icogroup.custompicker;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class YearListener implements OnScrollListener{

	PickerAdapter monthAdapter, dayAdapter, yearAdapter; 
	ArrayList<String> days;
	
	boolean scrolling = false;
	int snapTo = 0;
	
	public YearListener(ListView lv, PickerAdapter monthAdapter, PickerAdapter dayAdapter, PickerAdapter yearAdapter) {
		this.monthAdapter = monthAdapter;
		this.dayAdapter = dayAdapter;	
		this.yearAdapter = yearAdapter;		
		days = new ArrayList<String>();
		init(lv);
	}
	
	public void init(ListView lv){
		lv.setSelection(1);
		yearAdapter.setMid(2);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		 switch (scrollState) {
		    case OnScrollListener.SCROLL_STATE_IDLE:
//		        Log.i("TEST", "IDLE");
		        if (scrolling)
		        {				        	
		        	snapTo = view.getFirstVisiblePosition();
		        	int visibleChildCount = (view.getLastVisiblePosition() - view.getFirstVisiblePosition()) + 1;
		        	
		        	if(visibleChildCount == 4)
		        		snapTo++;
		        	
		        	yearAdapter.setMid(snapTo+1);	
		        	view.smoothScrollToPositionFromTop(snapTo, 0, 300);	        		
	        		
	    			Calendar cal = new GregorianCalendar(Integer.parseInt(yearAdapter.getMid()), 
							Integer.parseInt(Fecha.parseToNumber(monthAdapter.getMid())) - 1,
							1); 
					// Get the number of days in that month 
					int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					days = new ArrayList<String>();
					days.add("--");
					
					for (int i = 1; i <= maxDays; i++)
					{
						if(i<=9)
							days.add("0" + i);
						else
							days.add("" + i);
					}		
					
					days.add("--");
					
					dayAdapter.actData(days);
	        			
		        }
		        
		        scrolling = false;
		        
		        break;
		    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
		    case OnScrollListener.SCROLL_STATE_FLING:
		        scrolling = true;
		        break;
		    }		
	}
}
