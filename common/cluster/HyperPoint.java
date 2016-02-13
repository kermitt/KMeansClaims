package common.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HyperPoint {

	public double x = 0;
	public double y = 0;

	public HyperPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// Calculates the distance between two points.
	protected static double distance(HyperPoint p, HyperPoint centroid) {
	//	return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
		return Math.sqrt(Math.pow((centroid.y - p.y), 2) + Math.pow((centroid.x - p.x), 2));
	}

	// Creates random point
	protected static HyperPoint createRandomPoint(int min, int max) {
		Random r = new Random();
		double x = min + (max - min) * r.nextDouble();
		double y = min + (max - min) * r.nextDouble();
		return new HyperPoint(x, y);
	}

	protected static List <HyperPoint> createRandomPoints(int min, int max, int number) {
		List <HyperPoint>points = new ArrayList<HyperPoint>(number);
		for (int i = 0; i < number; i++) {
			points.add(createRandomPoint(min, max));
		}
		return points;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
}