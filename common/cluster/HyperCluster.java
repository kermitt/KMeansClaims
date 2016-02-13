package common.cluster;

import java.util.ArrayList;
import java.util.List;
import common.Caller;

public class HyperCluster {

	public List<HyperPoint> points = new ArrayList<HyperPoint>();
	public HyperPoint centroid;
	public int id;

	public HyperCluster(int id) {
		this.id = id;
		this.centroid = null;
	}

	public void describe() {
		String msg = "id=" + id + "  members=" + points.size();
		Caller.log( msg );
	}
}