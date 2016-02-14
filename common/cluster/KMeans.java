package common.cluster;

import java.util.ArrayList;
import java.util.List;

import common.Caller;
import common.cluster.HyperPoint;

public class KMeans {

	private int number_of_clusters;
//	private double min_coordinate, max_coordinate;
	private List<HyperPoint> points;// = new ArrayList<HyperPoint>();

	public List<HyperCluster> clusters = new ArrayList<HyperCluster>();

	public KMeans() {
	} // using an 'init' instead of a more 'javaesque' constructor for TDD
		// purposes

	public void init(int noc, List<HyperPoint> points) {
		number_of_clusters = noc;
		//number_of_points = nop;
		double[] lowhigh = HyperHelper.findLowhigh(points);
		double min_coordinate = lowhigh[0];
		double max_coordinate = lowhigh[1];
		this.points = points;
		
		Caller.note("number_of_clusters = " + number_of_clusters);
		Caller.note("number_of_points   = " + points.size());
		Caller.note("min_coordinate\t   =" + min_coordinate);
		Caller.note("max_coordinate\t   = " + max_coordinate);
		Caller.note("-----------");

		for (int i = 0; i < number_of_clusters; i++) {
			HyperCluster cluster = new HyperCluster(i);
			cluster.centroid = HyperHelper.createRandomPoint(min_coordinate, max_coordinate);
			clusters.add(cluster);
		}
	}

	// not really needed, but sometimes it is nice to peek into the guts,
	// iteration by iteration - thing of this as a debugger to peek into the
	// imagination
	private String getContext() {
		String state = "";
		for (int i = 0; i < number_of_clusters; i++) {
			state += "\t" + clusters.get(i).points.size();
		}
		return state;
	}

	public void calculate(boolean show_state) {

		boolean keepAlive = true;
		int limit = 1000;
		int iteration = 0;

		while (keepAlive && iteration < limit) {
			iteration++;

			clearClustersState();
			List<HyperPoint> lastCentroids = getCentroids();
			assignPointsToNearestCluster();
			calculateNewCentroids();
			List<HyperPoint> currentCentroids = getCentroids();

			// Total distance between new and old centroids
			double distance = 0;
			for (int i = 0; i < lastCentroids.size(); i++) {
				distance += HyperHelper.distanceBetweenTwoPoints(lastCentroids.get(i), currentCentroids.get(i));
			}

			if (show_state) {
				// peek_into_the_imagination
				String state = "ith=" + iteration + "\t";
				state += "state: " + getContext() + "\t";
				state += "distance=" + distance;
				Caller.note(state);
			}

			if (distance == 0) {
				keepAlive = false;
			}
		}
	}

	private void clearClustersState() {
		for (HyperCluster cluster : clusters) {
			cluster.points.clear();
		}
	}

	private List<HyperPoint> getCentroids() {
		List<HyperPoint> centroids = new ArrayList<HyperPoint>(number_of_clusters);
		for (HyperCluster cluster : clusters) {
			centroids.add(new HyperPoint(cluster.centroid.x, cluster.centroid.y));
		}
		return centroids;
	}

	private void assignPointsToNearestCluster() {
		double min, max = Double.MAX_VALUE;
		int cluster_index = 0;
		double distance = 0.0;

		for (HyperPoint point : points) {
			min = max;
			for (int i = 0; i < number_of_clusters; i++) {
				HyperCluster c = clusters.get(i);
				distance = HyperHelper.distanceBetweenTwoPoints(point, c.centroid);
				if (distance < min) {
					min = distance;
					cluster_index = i;
				}
			}
			clusters.get(cluster_index).points.add(point);
		}
	}

	private void calculateNewCentroids() {
		for (HyperCluster cluster : clusters) {
			double sumX = 0;
			double sumY = 0;
			List<HyperPoint> list = cluster.points;
			int n_points = list.size();

			for (HyperPoint point : list) {
				sumX += point.x;
				sumY += point.y;
			}

			HyperPoint centroid = cluster.centroid;
			if (n_points > 0) {
				centroid.x = sumX / n_points;
				centroid.y = sumY / n_points;
			}
		}
	}
}