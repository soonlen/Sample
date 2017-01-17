package com.wzf.com.sample.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * 上下可乳拉伸的效果
 * @author Administrator
 *
 */
public class BounceListView extends ListView {

	private static int MAX_BOUNCE_DISTANCE = 200;//最大拉伸的距离
	private Context context;
	
	public BounceListView(Context context) {
		super(context);
		init(context);
	}
	public BounceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public BounceListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	private void init(Context context) {
		this.context = context;
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		MAX_BOUNCE_DISTANCE = (int) (metrics.density * MAX_BOUNCE_DISTANCE);
	}
	public void setBounceDistance(int distance) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		MAX_BOUNCE_DISTANCE = (int) (metrics.density * distance);
	}
	@SuppressLint("NewApi") //api为9
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// TODO Auto-generated method stub
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, MAX_BOUNCE_DISTANCE, isTouchEvent);
	}
}
