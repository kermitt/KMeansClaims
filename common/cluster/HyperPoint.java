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

	protected static double distanceBetweenTwoPoints(HyperPoint p, HyperPoint centroid) {
		return Math.sqrt(Math.pow((centroid.y - p.y), 2) + Math.pow((centroid.x - p.x), 2));
	}

	protected static HyperPoint createRandomPoint(double min, double max) {
		Random r = new Random();
		double x = min + (max - min) * r.nextDouble();
		double y = min + (max - min) * r.nextDouble();
		return new HyperPoint(x, y);
	}

	protected static List <HyperPoint> createRandomPoints(double min, double max, int number) {
		List <HyperPoint>points = new ArrayList<HyperPoint>(number);
		for (int i = 0; i < number; i++) {
			points.add(createRandomPoint(min, max));
		}
		return points;
	}
}