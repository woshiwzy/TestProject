//package com.wangzy.work;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map.Entry;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.app.Activity;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.map.ItemizedOverlay;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.model.inner.GeoPoint;
//import com.wangzy.exitappdemo.R;
//import com.wangzy.work.util.ArcLine;
//import com.wangzy.work.util.DrawTool;
//import com.wangzy.work.util.Trace;
//import com.wangzy.work.util.TraceBean;
//import com.wangzy.work.util.TraceGrop;
//import com.wangzy.work.view.ListUtiles;
//
//public class MapActivity extends Activity {
//
//	private MapView mapView;
//	private BMapManager mBMapMan = null;
//	private MyOverlay myOverlay = null;
////	private PopupOverlay pop = null;
////	private OverlayItem mCurItem = null;
////	private View popView;
//	private Handler mHandler;
//	private boolean isAdd = false;
//
//	private int[][]color={
//			{0x00,0x99,0xcc},
//			{0xa2,0xc4,0xc9},
//			{0x6f,0xa8,0xdc},
//			{0x6d,0x9e,0xeb},
//			{0x3c,0x78,0xd8},
//			{0x45,0x81,0x8e},
//			{0x3d,0x85,0xc6},
//			{0x1c,0x45,0x87},
//			};
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		initView();
//	}
//
//	public void initView() {
//		setContentView(R.layout.activity_work_map);
//		mapView=findViewById(R.id.mapView);
//		// 设置启用内置的缩放控件
//		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
//		GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
//		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
////		mMapController.setCenter(point);// 设置地图中心点
////		mMapController.setZoom(12);// 设置地图zoom级别
//		mHandler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				addTraceLine2Map();
//			}
//		};
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		if (isAdd == false) {
//			isAdd = true;
//			delay2AddMap();
//		}
//
//	}
//
//	// 必须要等地图加载完毕才能做地图到平面坐标的映射(Projection 类才能正常工作)，因此需要等一段时间
//	private void delay2AddMap() {
//		if (!isFinishing()) {
//			Timer timer = new Timer();
//			timer.schedule(new TimerTask() {
//				@Override
//				public void run() {
//					mHandler.sendEmptyMessage(0);
//				}
//			}, 1 * 1000);
//		}
//	}
//
//	private void addTraceLine2Map() {
//		ArrayList<Trace> traces =new ArrayList<Trace>();
////		北京,39.91667,116.41667
////		上海,34.50000,121.43333
////		天津,39.13333,117.20000
////		香港,22.20000,114.10000
////		成都,30.66667,104.06667
//		try{
//
//		TraceBean tb1s=new TraceBean("39.91667","116.41667","北京");
//		TraceBean tb1e=new TraceBean("34.50000","121.43333","上海");
//
//		Trace traceb1=new Trace(tb1s, tb1e);
//		Trace traceb11=new Trace(tb1s, tb1e);
//		//================
//		Trace traceb2=new Trace(tb1e,tb1s);
//		Trace traceb21=new Trace(tb1e,tb1s);
//
//		TraceBean tb2s=new TraceBean("22.20000","114.10000","香港");
//		TraceBean tb2e=new TraceBean("34.50000","121.43333","上海");
//		Trace traceb3=new Trace(tb2s,tb2e);
//
//		TraceBean tb4s=new TraceBean("30.66667","104.06667","成都");
//		TraceBean tb4e=new TraceBean("34.50000","121.43333","上海");
//		Trace traceb4=new Trace(tb4e,tb4s);
//
//		traces.add(traceb3);
//		traces.add(traceb4);
//		//====
//		traces.add(traceb2);
//		traces.add(traceb21);
//
//		traces.add(traceb1);
//		traces.add(traceb11);
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		//==================
//		if (!ListUtiles.isEmpty(traces)) {
////			String name = traces.get(0).getPassenger().getName();
//			// ==========路径分组=========================
//			HashMap<String, TraceGrop> traceMap = new HashMap<String, TraceGrop>();
//			for (int i = 0, isize = traces.size(); i < isize; i++) {
//				Trace tempTrace = traces.get(i);
//				String tk = tempTrace.getKey();
//				if (traceMap.containsKey(tk)) {// 如果已经包含组,同一组的key和任何一个trace的key一样的
//					traceMap.get(tk).addTrace(tempTrace);
//				} else {// 不包含组
//					TraceGrop tgrup = new TraceGrop();
//					tgrup.addTrace(tempTrace);
//					String grk = tgrup.getReKey();
//					if (traceMap.containsKey(grk)) {// 设置偏离点点位置（决定一元二次方程解）
//						tgrup.setAddDistance(false);
//					} else {
//						tgrup.setAddDistance(true);
//					}
//					traceMap.put(tgrup.getKey(), tgrup);
//				}
//			}
//			// ===========绘制路径=========================
////			GraphicsOverlay graphicsOverlay = new GraphicsOverlay(mapView);
////			mapView.getOverlays().add(graphicsOverlay);
//
//			myOverlay = new MyOverlay(getResources().getDrawable(R.drawable.pin), mapView);
//			int totalTrace = 0;
//			int colorGroup=0;
//			// 分别从每一组中出去轨迹分别绘制
//			for (Entry<String, TraceGrop> tgropEntry : traceMap.entrySet()) {
//				TraceGrop tgroup = tgropEntry.getValue();
//				ArrayList<Trace> traceLines = tgroup.getTraces();
//				for (int i = 0, isize = traceLines.size(); i < isize; i++) {
//					try {
//						// ＝＝＝绘制弧线＝＝＝
//						Trace tempTrace = traceLines.get(i);
//						TraceBean ts = tempTrace.getStart();
//						TraceBean te = tempTrace.getEnd();
//						ArcLine arcLine = buildPath(ts, te, tgroup.isAddDistance(), i,color[(colorGroup%color.length)]);// 如果地图转经纬度出现异常的话
//						graphicsOverlay.setData(arcLine.getGraphics());
//						// ====添加图钉＝＝＝＝
//						addPin(myOverlay, ts, ts.getLocationName());
//						addPin(myOverlay, te, te.getLocationName());
//						// ====添加弧中点小飞机
//						addPlane(myOverlay, arcLine);
//						totalTrace++;
//					} catch (Exception e) {
//						e.printStackTrace();
////						LogUtil.i(App.tag, "经纬度转坐标错误");
//					}
//				}
//				colorGroup++;
//			}
//			mapView.getOverlays().add(myOverlay);
//			// =========创建弹出监听代码================
//			PopupClickListener popListener = new PopupClickListener() {
//				@Override
//				public void onClickedPopup(int index) {
//				}
//			};
//			pop = new PopupOverlay(mapView, popListener);// 创建弹出框，这里不需要弹出，在自定义地图覆盖物中注视掉了，弹出代码
//			mapView.getController().setZoom(5.2f);// 设置合适的地图放大级别
//			mapView.getController().animateTo(myOverlay.getCenter());
//			mapView.refresh();
//		} else {
//			finish();
//		}
//
//	}
//
//	// 添加小飞机图标
//	private void addPlane(MyOverlay myOverlay, ArcLine xa) {
//		OverlayItem item1 = new OverlayItem(xa.getGeoMidArc(), xa.getTraceBeanStart().getLocationName(), "");
//		Drawable drable = getResources().getDrawable(R.drawable.map_icon_plane);
//		float degree = (float) xa.getAngle();
//		Drawable dra = DrawTool.drawablePrintText(drable, "", degree);
//		item1.setMarker(dra);// 设置中间的小飞机图标
//		item1.setAnchor(0.5f, 0.5f);// 设置锚点，保证图标放置到线上
//		myOverlay.addItem(item1);
//	}
//
//	// 添加图钉图标
//	private void addPin(MyOverlay myOverlay, TraceBean tb, String index) {
//		GeoPoint gp = buildGeoPointFromTraceBean(tb);
//		OverlayItem item1 = new OverlayItem(gp, tb.getLocationName(), "");
//		Drawable d = DrawTool.drawablePrintText(getResources().getDrawable(R.drawable.pin), index);
//		item1.setMarker(d);
//		myOverlay.addItem(item1);
//	}
//
//	private GeoPoint buildGeoPointFromTraceBean(TraceBean tb) {
//		GeoPoint gp = new GeoPoint((int) (Double.parseDouble(tb.getLat()) * 1E6), (int) (Double.parseDouble(tb.getLng()) * 1E6));
//		return gp;
//	}
//
//	// 构建路径
//	private ArcLine buildPath(TraceBean ts, TraceBean te, boolean addDistance, int dtimes) throws Exception {
//		// 设定折线点坐标
//		GeoPoint gps = buildGeoPointFromTraceBean(ts);
//		GeoPoint gpe = buildGeoPointFromTraceBean(te);
//		ArcLine arcline = new ArcLine(mapView.getProjection(), gps, gpe, ts, te, addDistance, dtimes);
//		return arcline;
//	}
//	// 构建路径
//	private ArcLine buildPath(TraceBean ts, TraceBean te, boolean addDistance, int dtimes,int color[]) throws Exception {
//		// 设定折线点坐标
//		GeoPoint gps = buildGeoPointFromTraceBean(ts);
//		GeoPoint gpe = buildGeoPointFromTraceBean(te);
//		ArcLine arcline = new ArcLine(mapView.getProjection(), gps, gpe, ts, te, addDistance, dtimes,color[0],color[1],color[2]);
//		return arcline;
//	}
//	// 地图覆盖物集合
//	public class MyOverlay extends ItemizedOverlay {
//		public MyOverlay(Drawable defaultMarker, MapView mapView) {
//			super(defaultMarker, mapView);
//		}
//
//		@Override
//		public boolean onTap(int index) {
//			OverlayItem item = getItem(index);
//			mCurItem = item;
////			popView = View.inflate(MapActivity.this, R.layout.map_pop_map, null);
//			// pop.showPopup(popView, item.getPoint(), 32);//不需要弹出地图
//			// if (index == 3) {
//			// button.setText("这是一个系统控件");
//			// GeoPoint pt = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 *
//			// 1E6));
//			// // 弹出自定义View
//			// pop.showPopup(button, pt, 32);
//			// } else {
//			// popupText.setText(getItem(index).getTitle());
//			// Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupLeft),
//			// BMapUtil.getBitmapFromView(popupInfo),
//			// BMapUtil.getBitmapFromView(popupRight) };
//			// pop.showPopup(bitMaps, item.getPoint(), 32);
//			// }
//			return true;
//		}
//
//		@Override
//		public boolean onTap(GeoPoint pt, MapView mMapView) {
//			if (pop != null) {
//				pop.hidePop();
////				mMapView.removeView(popView);
//				// mMapView.removeView(button);
//			}
//			return false;
//		}
//
//	}
//
//
//}
