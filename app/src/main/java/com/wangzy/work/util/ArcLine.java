//package com.wangzy.work.util;
//
//import java.util.Random;
//
//import android.graphics.Point;
//import android.graphics.PointF;
//
//import com.baidu.mapapi.map.Geometry;
//import com.baidu.mapapi.map.Graphic;
//import com.baidu.mapapi.map.Symbol;
//import com.baidu.platform.comapi.basestruct.GeoPoint;
//import com.baidu.platform.comapi.map.Projection;
//
//public class ArcLine {
//
//	private static final long serialVersionUID = 1L;
//	private GeoPoint geoStart;
//	private Point pOutS;
//	private Point pOutE;
//
//	private GeoPoint geoMid;
//	private GeoPoint geoMidArc;
//	private GeoPoint geoEnd;
//	private Line line;
//	private Graphic graphics;
//	private Projection mapject;
//	private TraceBean traceBeanStart;
//	private TraceBean traceBeanEnd;
//
//	private float distanceDegree = 4500*4500;
//	private float pdistanceMin = 1500 * 1500;//经过测试这个距离比较合适，如果两个点的距离太近，可能会画出长的那一段弧，减小这个数值就可以了
//	private float pdistanceMax = 2000 * 2000;// 调节距离
//	private float pdistance = pdistanceMin;// 弧线重点距离两点中心的位置
//
//	private int r = 0x00;
//	private int g = 0x99;
//	private int b = 0xcc;
//
//	public final int eq_type_eq = 1;// 相同航线
//	public final int eq_type_re = 2;// 相反航线
//	public final int eq_type_not_eq = 3;// 不相关航线
//	private boolean isAddDistance = false;
//	private int dtimes;// 同组数据剃度增加倍数
//
//	public ArcLine(Projection mapject, GeoPoint geoStart, GeoPoint geoEnd, TraceBean tbs, TraceBean tbe, boolean adddistance, int dtimes)
//			throws Exception {
//		// 生成Graphic对象
//		this.isAddDistance = adddistance;
//		this.dtimes = dtimes;
//		this.pdistance = this.pdistance + distanceDegree * dtimes;
//		graphics = createGraphicsAndInit(mapject, geoStart, geoEnd, tbs, tbe);
//	}
//	public ArcLine(Projection mapject, GeoPoint geoStart, GeoPoint geoEnd, TraceBean tbs, TraceBean tbe, boolean adddistance, int dtimes,
//			int r,
//			int g,
//			int b)
//			throws Exception {
//		// 生成Graphic对象
//		this.isAddDistance = adddistance;
//		this.dtimes = dtimes;
//		this.pdistance = this.pdistance + distanceDegree * dtimes;
//		graphics = createGraphicsAndInit(mapject, geoStart, geoEnd, tbs, tbe, r, g, b);
//	}
//
//	public ArcLine(Projection mapject, GeoPoint geoStart, GeoPoint geoEnd, TraceBean tbs, TraceBean tbe, int r, int g, int b)
//			throws Exception {
//		// 生成Graphic对象
//		graphics = createGraphicsAndInit(mapject, geoStart, geoEnd, tbs, tbe, r, g, b);
//	}
//
//	private Graphic createGraphicsAndInit(Projection mapject, GeoPoint geoStart, GeoPoint geoEnd, TraceBean tbs, TraceBean tbe)
//			throws Exception {
//		// 设定样式
//		Symbol arcSymbol = new Symbol();
//		Symbol.Color arcColor = arcSymbol.new Color();
//		arcColor.red = r;
//		arcColor.green = g;
//		arcColor.blue = b;
//		arcColor.alpha = 255;
//		arcSymbol.setLineSymbol(arcColor, 3);
//		// 生成Graphic对象
//		graphics = new Graphic(init(mapject, geoStart, geoEnd, tbs, tbe), arcSymbol);
//		return graphics;
//	}
//
//	private Graphic createGraphicsAndInit(Projection mapject, GeoPoint geoStart, GeoPoint geoEnd, TraceBean tbs, TraceBean tbe, int r,
//			int g, int b) throws Exception {
//		// 设定样式
//		Symbol arcSymbol = new Symbol();
//		Symbol.Color arcColor = arcSymbol.new Color();
//		arcColor.red = r;
//		arcColor.green = g;
//		arcColor.blue = b;
//		arcColor.alpha = 255;
//		arcSymbol.setLineSymbol(arcColor, 3);
//		// 生成Graphic对象
//		graphics = new Graphic(init(mapject, geoStart, geoEnd, tbs, tbe), arcSymbol);
//		return graphics;
//	}
//
//	private Geometry init(Projection mapject, GeoPoint geoStart, GeoPoint geoEnd, TraceBean tbs, TraceBean tbe) throws Exception {
//		this.geoStart = geoStart;
//		this.geoEnd = geoEnd;
//		this.mapject = mapject;
//		this.traceBeanStart = tbs;
//		this.traceBeanEnd = tbe;
//
//		pOutS = new Point();
//		pOutS = mapject.toPixels(geoStart, pOutS);
//
//		pOutE = new Point();
//		pOutE = mapject.toPixels(geoEnd, pOutE);
//
//		if (pOutS.x == 0 || pOutS.y == 0 || pOutE.x == 0 || pOutE.y == 0) {
//			throw new Exception("经纬度转换坐标出错在ArcLine.init 中:geoS:" + geoStart.getLatitudeE6() + " ,geoEnd:" + geoEnd.getLongitudeE6());
//		}
//
//		// 计算2点的中点
//		Point midePoint = new Point((pOutS.x + pOutE.x) / 2, (pOutS.y + pOutE.y) / 2);
//		geoMid = mapject.fromPixels(midePoint.x, midePoint.y);
//		line = MathTool.computeLineBy2Point(pOutE, pOutS);// 求出起始点所在的直线
//		float absK = Math.abs(line.getK());
//		if (absK < 0.5 || absK > 30) {
//			pdistance = pdistanceMax + distanceDegree * dtimes;
//		}
//		float kv = MathTool.getVerticalSlop(line.getK());// 获取法线斜率
//		Line lf = MathTool.getLineByKP(kv, midePoint);// 获取法线所在的方程
//		// 1、先考虑特殊情况,就是法线斜率为0
////		TraceBean centerChinaCity = CityAreaTool.cityMap.get("成都");// 以成都为中国的中心点
////		GeoPoint centerPointChina = new GeoPoint((int) (Double.parseDouble(centerChinaCity.getLat()) * 1E6),
////				(int) (Double.parseDouble(centerChinaCity.getLng()) * 1E6));
////		Point cp = new Point();
////		mapject.toPixels(centerPointChina, cp);
//		// 求放置飞机的点
//		PointF locationPoint = new PointF();
//		locationPoint.y = midePoint.y;
//		locationPoint.x = midePoint.x;
//
//		// boolean addOrPlus = random();
//		double x = 0;
//		double y = 0;
//		if (isAddDistance) {
//			x = midePoint.x + Math.sqrt((pdistance / (1 + lf.getK() * 1 * lf.getK())));// ＋－，都可以，有两个交点
//		} else {
//			x = midePoint.x - Math.sqrt((pdistance / (1 + lf.getK() * 1 * lf.getK())));// ＋－，都可以，有两个交点
//		}
//		y = lf.getK() * x + lf.getB();
//		locationPoint.x = (float) x;
//		locationPoint.y = (float) y;
//
//		// 下面两段代码用于验证计算结果，
//		geoMidArc = mapject.fromPixels((int) locationPoint.x, (int) locationPoint.y);
//		if (null == geoMidArc) {
//		}
//		Geometry arcGeometry = new Geometry();
//		arcGeometry.setArc(geoStart, geoMidArc, geoEnd);
//		return arcGeometry;
//	}
//
//	public double getAngle() {
//		double angle = Math.atan(line.getK()) / Math.PI * 180;
//		double retAngle = angle;
//		// 旋转图像是通过Matrix类得setRotate方法设置要旋转的角度（正值为顺时针旋转，负值为逆时针旋转）
//		if (pOutS.x >= pOutE.x) {
//			if (pOutS.y >= pOutE.y) {
//				retAngle = -(90 + (90 - angle));
//			} else {
//				retAngle = -(90 + (90 - angle));
//			}
//		} else {
//			if (pOutS.y >= pOutE.y) {
//				retAngle = angle;
//			} else {
//				retAngle = angle;
//			}
//		}
//		return retAngle;
//	}
//
//	public int eqLine(ArcLine arcline) {
//		String l1 = this.traceBeanStart.getLocationName() + this.traceBeanEnd.getLocationName();
//		String l2 = arcline.getTraceBeanStart().getLocationName() + arcline.getTraceBeanEnd().getLocationName();
//		if (l1.equals(l2)) {// 起点和终点想同你
//			return eq_type_eq;
//		}
//		String l23 = arcline.getTraceBeanEnd().getLocationName() + arcline.getTraceBeanStart().getLocationName();
//		if (l1.equals(l23)) {
//			return eq_type_re;
//		}
//		return eq_type_not_eq;
//	}
//
//	private boolean random() {
//		Random rand = new Random();
//		isAddDistance = rand.nextBoolean();
//		return isAddDistance;
//	}
//
//	public GeoPoint getGeoMidArc() {
//		return geoMidArc;
//	}
//
//	public void setGeoMidArc(GeoPoint geoMidArc) {
//		this.geoMidArc = geoMidArc;
//	}
//
//	public TraceBean getTraceBeanStart() {
//		return traceBeanStart;
//	}
//
//	public void setTraceBean(TraceBean traceBean) {
//		this.traceBeanStart = traceBean;
//	}
//
//	public GeoPoint getGeoStart() {
//		return geoStart;
//	}
//
//	public void setGeoStart(GeoPoint geoStart) {
//		this.geoStart = geoStart;
//	}
//
//	public GeoPoint getGeoMid() {
//		return geoMid;
//	}
//
//	public void setGeoMid(GeoPoint geoMid) {
//		this.geoMid = geoMid;
//	}
//
//	public GeoPoint getGeoEnd() {
//		return geoEnd;
//	}
//
//	public void setGeoEnd(GeoPoint geoEnd) {
//		this.geoEnd = geoEnd;
//	}
//
//	public Line getLine() {
//		return line;
//	}
//
//	public void setLine(Line line) {
//		this.line = line;
//	}
//
//	public Graphic getGraphics() {
//		return graphics;
//	}
//
//	public void setGraphics(Graphic graphics) {
//		this.graphics = graphics;
//	}
//
//	public int getR() {
//		return r;
//	}
//
//	public void setR(int r) {
//		this.r = r;
//	}
//
//	public int getG() {
//		return g;
//	}
//
//	public void setG(int g) {
//		this.g = g;
//	}
//
//	public int getB() {
//		return b;
//	}
//
//	public void setB(int b) {
//		this.b = b;
//	}
//
//	public TraceBean getTraceBeanEnd() {
//		return traceBeanEnd;
//	}
//
//	public void setTraceBeanEnd(TraceBean traceBeanEnd) {
//		this.traceBeanEnd = traceBeanEnd;
//	}
//
//	public float getPdistance() {
//		return pdistance;
//	}
//
//	public void setPdistance(float pdistance) throws Exception {
//		this.pdistance = pdistance;
//		init(mapject, geoStart, geoEnd, this.traceBeanStart, this.traceBeanEnd);
//	}
//
//}
