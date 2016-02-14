package common.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HyperHelper {

	public static HyperPoint createRandomPoint(double min, double max) {
		Random r = new Random();
		double x = min + (max - min) * r.nextDouble();
		double y = min + (max - min) * r.nextDouble();
		return new HyperPoint(x, y);
	}

	public static List<HyperPoint> createRandomPoints(double min, double max, int number) {
		List<HyperPoint> points = new ArrayList<HyperPoint>(number);
		for (int i = 0; i < number; i++) {
			points.add(createRandomPoint(min, max));
		}
		return points;
	}

	public static double distanceBetweenTwoPoints(HyperPoint p1, HyperPoint p2) {
		return Math.sqrt(Math.pow((p2.y - p1.y), 2) + Math.pow((p2.x - p1.x), 2));
	}

	public static double[] findLowhigh(List<HyperPoint> points) {
		double[] lowhigh = new double[2];
		double most = -999999;
		double least = 999999;

		for (HyperPoint p : points) {

			if (least > p.x) {
				least = p.x;
			}
			if (least > p.y) {
				least = p.y;
			}
			if (most < p.x) {
				most = p.x;
			}
			if (most < p.y) {
				most = p.y;
			}
		}
		lowhigh[0] = least;
		lowhigh[1] = most;

		return lowhigh;
	}
}
