package common.cluster;

import java.util.ArrayList;
import java.util.List;

import common.Caller;

public class HyperCluster {

	public List<HyperPoint> points;
	public HyperPoint centroid;
	public int id;

	public HyperCluster(int id) {
		this.id = id;
		this.points = new ArrayList<HyperPoint>();
		this.centroid = null;
	}

	public List<HyperPoint> getPoints() {
		return points;
	}

	public void addPoint(HyperPoint point) {
		points.add(point);
	}

	public void setPoints(List <HyperPoint> points) {
		this.points = points;
	}

	public HyperPoint getCentroid() {
		return centroid;
	}

	public void setCentroid(HyperPoint centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}

	public void clear() {
		points.clear();
	}
	public void describe() {
		String msg = "id=" + id + "  members=" + points.size();
		Caller.log( msg );
	}
	
	/*
	public void plotCluster() {
		System.out.println("[HyperCluster: " + id + "]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for (HyperPoint p : points) {
			System.out.println(p);
		}
		System.out.println("]");
	}
	*/
}