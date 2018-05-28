package com.wangzy.work.util;

import java.util.ArrayList;

import com.wangzy.work.view.ListUtiles;

public class TraceGrop {

	private ArrayList<Trace> traceList;
	private boolean isAddDistance=true;

	public TraceGrop() {
		traceList = new ArrayList<Trace>();
	}

	public void addTrace(Trace trace) {
		traceList.add(trace);
	}

	public ArrayList<Trace> getTraces() {
		return traceList;
	}

	public String getKey() {
		if (!ListUtiles.isEmpty(traceList)) {
		 return	traceList.get(0).getKey();
		}
		return null;
	}

	public String getReKey() {
		if (!ListUtiles.isEmpty(traceList)) {
		return	traceList.get(0).getReKey();
		}
		return null;
	}

	public boolean isAddDistance() {
		return isAddDistance;
	}

	public void setAddDistance(boolean isAddDistance) {
		this.isAddDistance = isAddDistance;
	}

}
