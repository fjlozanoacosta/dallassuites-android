package com.dallassuites.custompicker;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import java.util.ArrayList;

public class DayListener implements OnScrollListener{

	PickerAdapter dayAdapter; 
	ArrayList<String> days;
	
	boolean scrolling = false;
	int snapTo = 0;
	
	public DayListener(ListView lv, PickerAdapter adapter) {
		this.dayAdapter = adapter;
		init(lv);
	}
	
	public void init(ListView lv){
		lv.setSelection(1);
		dayAdapter.setMid(2);
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
//
//		        	if(visibleChildCount == 4)
//		        		snapTo++;
//
//		        	dayAdapter.setMid(snapTo+1);
//		        	view.smoothScrollToPositionFromTop(snapTo, 0, 50);

                    //New Code - Smoother Picker
                    float cellHeight = view.getChildAt(0).getHeight();
                    float minDistance = cellHeight;

                    for(int i = 0; i < visibleChildCount; i++)
                    {
                        float position = view.getChildAt(i).getY();
                        float distanceFromMiddle = Math.abs(view.getHeight()/2 - (position + cellHeight/2));

                        if(distanceFromMiddle < minDistance)
                        {
                            minDistance = distanceFromMiddle;
                            snapTo = view.getFirstVisiblePosition() + i;
                        }
                    }

                    dayAdapter.setMid(snapTo);
                    view.smoothScrollToPositionFromTop(snapTo - 1, 0, 50);
		        }
		        
		        scrolling = false;
		        
		        break;
		    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
		        scrolling = true;
		        break;
		    }		
	}
}
