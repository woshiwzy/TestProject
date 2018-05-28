//package com.wangzy.work.util;
//
//import android.graphics.Point;
//
//import com.baidu.platform.comapi.basestruct.GeoPoint;
//import com.baidu.platform.comapi.map.Projection;
//
//public class MapTool {
//
//	public static GeoPoint pix2GeoPoint(Projection prj, Point p) {
//		return prj.fromPixels(p.x, p.y);
//	}
//
//	public static Point geo2Point(Projection prj, GeoPoint geoPoint) {
//		Point p = new Point();
//		prj.toPixels(geoPoint, p);
//		return p;
//	}
//
//}
