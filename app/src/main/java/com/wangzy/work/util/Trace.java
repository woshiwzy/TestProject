package com.wangzy.work.util;


public class Trace {

	private TraceBean start;
	private TraceBean end;

	// ======
	public final int eq_type_eq = 0;
	public final int eq_type_re = 1;
	public final int eq_type_not_eq = -1;

	public Trace(TraceBean xs, TraceBean xe) throws Exception {
		this.start = xs;
		this.end = xe;
		if (null != xs && xe != null) {
			this.start.setLat(xs.getLat());
			this.start.setLng(xs.getLng());

			this.end.setLat(xe.getLat());
			this.end.setLng(xe.getLng());
		} else {
			throw new Exception("未找到经纬度" + this.start.getLocationName() + "到" + this.end.getLocationName());
		}
		// =========
	}

	public TraceBean getStart() {
		return start;
	}

	public void setStart(TraceBean start) {
		this.start = start;
	}

	public TraceBean getEnd() {
		return end;
	}

	public void setEnd(TraceBean end) {
		this.end = end;
	}

	public int eqTrace(Trace trace) {
		if (null == trace) {
			return eq_type_not_eq;
		}
		String targetLocation = trace.getStart().getLocationName() + trace.getEnd().getLocationName();
		String thisLocation = this.getStart().getLocationName() + this.end.getLocationName();
		String thisLocation2 = this.end.getLocationName() + this.getStart().getLocationName();
		if (targetLocation.equals(thisLocation)) {
			return eq_type_eq;
		}
		if (targetLocation.equals(thisLocation2)) {
			return eq_type_re;
		}
		return eq_type_not_eq;
	}

	public String getKey() {
		String thisLocation = this.getStart().getLocationName() + this.end.getLocationName();
		return thisLocation;
	}
	public String getReKey(){
		String thisLocation =this.end.getLocationName()+ this.getStart().getLocationName();
		return thisLocation;
	}
}
