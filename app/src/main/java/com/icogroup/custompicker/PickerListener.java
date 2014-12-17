package com.icogroup.custompicker;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import java.util.ArrayList;


public class PickerListener implements OnScrollListener{

	ListView lv, lvmonth, lvday;
	PickerAdapter adapter, dayAdapter, yearAdapter; 
	ArrayList<String> days;
	
	boolean scrolling = false, shouldSnap = false, isMonthAdapter = false;
	int snapTo = 0;
	
	public PickerListener(ListView lv, PickerAdapter adapter) {
		this.lv = lv;
		this.adapter = adapter;
		init();
	}
	
	public PickerListener(ListView lv, ListView lvday, PickerAdapter adapter, PickerAdapter dayAdapter, PickerAdapter yearAdapter) {
		this.lv = lv;
		this.adapter = adapter;
		this.lvday = lvday;
		this.dayAdapter = dayAdapter;	
		this.yearAdapter = yearAdapter;		
		days = new ArrayList<String>();
		isMonthAdapter = true;
		init();
	}
	
	public void init(){
		lv.setSelection(1);
		adapter.setMid(2);
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
		        	shouldSnap = true;	  
		        	int visibleChildCount = (lv.getLastVisiblePosition() - lv.getFirstVisiblePosition()) + 1;
		        	
		        	if(visibleChildCount == 4)
		        		snapTo++;
		        	
		        	adapter.setMid(snapTo+1);	
	        		lv.smoothScrollToPositionFromTop(snapTo, 0, 300);
	        		

		        }
		        
		        scrolling = false;
		        
		        break;
		    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
		    case OnScrollListener.SCROLL_STATE_FLING:
		       // Log.i("TEST", "FLING");
		        scrolling = true;
		        break;
		    }		
	}
}
