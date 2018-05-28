package com.wangzy.work.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author wangzy
 * 
 */
public class MyPie extends View {

	private int w;
	private int h;
	private int r;

	private Paint paint;
	private Point pointMid;

	private int[] color = { 0xffbbffaa, Color.LTGRAY, 0xff889922, 0xff567234, 0xff987123, 0xff090871 };// 颜色
	private float[] data = { 200, 20, 50, 80, 40, 90, 100, 130 };// 数据

	private float total = 0;// 用于计算数据和
	private float totalDegree = 0;// 总共旋转的角度
	private String tag = "pie";
	private static final int MIN_POINT = 2;
	private Point lastEventPoint;
	private int currentTargetIndex;

	private boolean isInitAnimate = false;// 是否正在进行动画初始化
	private int initAnimateCount = 0;// initAnimateCount 等于data.length的时候，初始化完毕

	public MyPie(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		setDrawingCacheEnabled(true);
		createFadeData();
	}

	private void createFadeData() {
		float[] data = { 100, 20, 50, 80, 40, 90, 100, 130 };// 数据
		setData(data);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		w = getWidth();
		h = getHeight();
		r = computRadius(w, h);
		Point midPoint = midPoint();

		canvas.drawColor(Color.TRANSPARENT);
		paint.setColor(Color.WHITE);

		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(midPoint.x, midPoint.y, r, paint);// 画出大圆
		paint.setColor(Color.DKGRAY);
		canvas.drawCircle(midPoint.x, midPoint.y, 10, paint);// 画中心点
		// 画饼图
		RectF rect = new RectF(midPoint.x - r, midPoint.y - r, midPoint.x + r, midPoint.y + r);
		float startAngle = totalDegree;

		if (isInitAnimate) {// 动画初始化的时候一次画一个数据
			for (int i = 0; i < initAnimateCount; i++) {
				float sweepAngle = 360 * (getPercent(data[i]));
				paint.setColor(getColor(i));
				canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
				startAngle += sweepAngle;
			}

		} else {// 正常情况
			for (int i = 0; i < data.length; i++) {
				float sweepAngle = 360 * (getPercent(data[i]));
				paint.setColor(getColor(i));
				canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
				startAngle += sweepAngle;
			}
		}

		// =====画出金色外边=========
		paint.setColor(0xffff9933);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(midPoint.x, midPoint.y, r, paint);
		// ===画小圈圈
		canvas.drawCircle(midPoint.x, midPoint.y, 20, paint);
	}

	public void pre() {// 顺时针
		int datasize = data.length;
		float offset = 0;
		if (currentTargetIndex > 0) {
			offset = getDegreeByIndex(currentTargetIndex) / 2 + getDegreeByIndex(currentTargetIndex - 1) / 2;
			currentTargetIndex--;
		} else if (currentTargetIndex == 0) {
			offset = getDegreeByIndex(currentTargetIndex) / 2 + getDegreeByIndex(datasize - 1) / 2;
			currentTargetIndex = datasize - 1;
		}

		Log.i(tag, "pre+offset:" + currentTargetIndex);
		totalDegree += offset;
		invalidate();
	}

	public void next() {// 逆时针
		int datasize = data.length;
		float offset = 0;
		if (currentTargetIndex < (datasize - 1)) {
			offset = getDegreeByIndex(currentTargetIndex) / 2 + getDegreeByIndex(currentTargetIndex + 1) / 2;
			currentTargetIndex++;
		} else if (currentTargetIndex == (datasize - 1)) {
			offset = getDegreeByIndex(currentTargetIndex) / 2 + getDegreeByIndex(0) / 2;
			currentTargetIndex = 0;
		}
		totalDegree -= offset;
		Log.i(tag, "pre－offset:" + currentTargetIndex);
		invalidate();
	}

	private float getDegreeByIndex(int index) {
		return (data[index] / getTotal()) * 360;
	}

	public void setData(float[] data) {

		this.data = data;
		this.total = 0;
		this.currentTargetIndex = 0;
		this.lastEventPoint = null;
		this.totalDegree = 90 - getDegreeByIndex(0) / 2;
		initAnimate();
	}

	private void initAnimate() {
		initAnimateCount = 0;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					initAnimateCount++;
					isInitAnimate = true;
					handler.sendEmptyMessage(0);

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (initAnimateCount == data.length) {
						isInitAnimate = false;
						break;
					}
				}
			}
		}).start();

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			invalidate();
		};
	};

	/**
	 * 处理饼图的转动
	 */
	public boolean onTouchEvent(MotionEvent event) {
		if (isInitAnimate) {
			return true;
		}

		Point eventPoint = new Point((int) event.getX(), (int) event.getY());
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lastEventPoint = eventPoint;
			break;
		case MotionEvent.ACTION_MOVE:
			// 这里处理滑动
			float d1 = angleFrom0(lastEventPoint, midPoint());
			float d2 = angleFrom0(eventPoint, midPoint());
			totalDegree = (totalDegree + (d2 - d1)) % 360;
			lastEventPoint = eventPoint;
			invalidate();
			break;
		case MotionEvent.ACTION_UP: {
			int targetAngle = getTargetDegree(totalDegree);
			currentTargetIndex = computeCurrentIndex();
			int offset = (int) getOffsetOfPartCenter(targetAngle, currentTargetIndex);
			totalDegree += offset;
			invalidate();
			Log.i(tag, "index:" + currentTargetIndex);
		}
			break;
		default:
			break;
		}

		return true;
	}

	private int computeCurrentIndex() {
		int targetAngle = getTargetDegree(totalDegree);
		currentTargetIndex = getEventPart((int) targetAngle);
		return currentTargetIndex;
	}

	protected float getOffsetOfPartCenter(int degree, int targetIndex) {
		float currentSum = 0;
		for (int i = 0; i <= targetIndex; i++) {
			currentSum += (data[i] / getTotal()) * 360;
		}
		float offset = degree - (currentSum - (data[targetIndex] / getTotal()) * 360 / 2);
		// 超过一半,则offset>0；未超过一半,则offset<0
		return offset;
	}

	protected int getTargetDegree(int startDegree) {
		int targetDegree = -1;
		int tmpStart = startDegree;
		if (tmpStart < 0) {
			tmpStart += 360;
		}
		if (tmpStart < 90) {
			targetDegree = 90 - tmpStart;
		} else {
			targetDegree = 360 + 90 - tmpStart;
		}
		return targetDegree;
	}

	protected int getTargetDegree(float totaldegree) {
		float targetDegree = -1;
		float tmpStart = totaldegree;
		if (tmpStart < 0) {
			tmpStart += 360;
		}
		if (tmpStart < 90) {
			targetDegree = 90 - tmpStart;
		} else {
			targetDegree = 360 + 90 - tmpStart;
		}
		return (int) targetDegree;
	}

	protected int getEventPart(int degree) {
		float currentSum = 0;
		for (int i = 0; i < data.length; i++) {
			currentSum += (data[i] / getTotal()) * 360;
			if (currentSum >= degree) {
				return i;
			}
		}
		return -1;
	}

	// =====业务相关方法
	private float getTotal() {
		if (0 != total) {
			return total;
		} else if (null != data) {
			total = 0;
			for (float d : data) {
				total += d;
			}
			return total;
		}
		return 0;
	}

	private float getPercent(float data) {
		return data / getTotal();
	}

	private int getColor(int i) {
		return color[i % color.length];
	}

	// ========以下是工具方法=======
	private int computRadius(int w, int h) {
		int r = (Math.min(w, h) == w ? w / 2 : h / 2);
		return r;
	}

	private Point midPoint() {
		if (null == pointMid) {
			pointMid = new Point(w / 2, h / 2);
		}
		return pointMid;
	}

	public int getCurrentTargetIndex() {
		return currentTargetIndex;
	}

	/**
	 * view中心点到View的右边缘中心点为0度开始顺时针旋转的角度为正，逆时针为负数，这样方便计算正反转
	 * @param eventPoint
	 * @param center
	 * @return
	 */
	protected float angleFrom0(Point eventPoint, Point center) {
		int x = eventPoint.x - center.x;
		int y = eventPoint.y - center.y;
		double z = Math.hypot(Math.abs(x), Math.abs(y));
		double sa = (double) Math.abs(y) / z;
		int xpoint = 0;
		if (xpoint > MIN_POINT) {
			z = Math.pow(z, 2);
		}
		double ts = Math.asin(sa);
		float d = (float) (ts / 3.14f * 180f);// 角度
		float rd = 0;
		if (x <= 0 && y <= 0) {
			rd = 180 + d;
		} else if (x >= 0 && y <= 0) {
			rd = 360 - d;
		} else if (x <= 0 && y >= 0) {
			rd = 180 - d;
		} else {
			rd = d;
		}
		return rd;
	}

	
}
