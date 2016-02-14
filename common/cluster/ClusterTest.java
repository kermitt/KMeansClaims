package common.cluster;

import java.util.ArrayList;
import java.util.List;

import common.Caller;

public class ClusterTest {
	public static void main(String[] args) {
		boolean test1 = simpleTestRun();
		boolean test2 = findLowhigh();

		Caller.note("\n*** RESULTS ***");
		Caller.log(test1, "simpleTestRun test");
		Caller.log(test2, "findLowhigh test");
		Caller.note("\nThe end");

	}

	public static boolean findLowhigh() {

		List<HyperPoint> points = getTwentyPoints();
		double[] lowhigh = HyperHelper.findLowhigh(points);

		double low = lowhigh[0];
		double high = lowhigh[1];

		// low ought to be about -1 ( maybe more but not much ) and high (
		// reverse ditto )

		boolean isOk = low > -1 && high < 1 && low < 1 && high > -1 && low < high;
		Caller.log(isOk, " low " + low + " high " + high);
		return isOk;
	}

	private static List<HyperPoint> getTwentyPoints() {

		List<HyperPoint> points = new ArrayList<HyperPoint>();

		points.add(new HyperPoint(0.4729120327131837, -0.35291370286267876));
		points.add(new HyperPoint(-0.11622645808069088, -0.861974038583607));
		points.add(new HyperPoint(0.3620376953309681, -0.7939160442059567));
		points.add(new HyperPoint(0.9416443313746354, 0.0019767313521603658));
		points.add(new HyperPoint(0.5708753853323103, -0.7132492450805858));
		points.add(new HyperPoint(0.4162937597882319, -0.5137675987210792));
		points.add(new HyperPoint(-0.025530056748800378, -0.7069428122635821));
		points.add(new HyperPoint(0.7867907828393772, -0.3247880879149898));
		points.add(new HyperPoint(0.2986523327887409, -0.8180446082561099));
		points.add(new HyperPoint(0.24540389229342363, 0.9456182334570411));
		points.add(new HyperPoint(0.059480113667125245, 0.6511968109509425));
		points.add(new HyperPoint(0.2008927399787317, 0.8194040934879718));
		points.add(new HyperPoint(-0.10939911414912418, 0.7794746230214593));
		points.add(new HyperPoint(-0.45145889144454476, 0.8839335934278323));
		points.add(new HyperPoint(-0.5218136709538208, 0.3663552914684478));
		points.add(new HyperPoint(-0.37350351428269435, 0.4503137537955939));
		points.add(new HyperPoint(-0.9847441650282387, -0.349082652613262));
		points.add(new HyperPoint(-0.9761321230904221, -0.5924191043621558));
		points.add(new HyperPoint(-0.9818768233701178, 0.029584544776639188));
		points.add(new HyperPoint(-0.590495478461607, -0.5967563278634866));

		return points;
	}

	private static boolean simpleTestRun() {

		KMeans kmeans = new KMeans();

		int number_of_clusters = 3;
		int number_of_points = 20;
		double min_coordinate = -1;
		double max_coordinate = 1;

		List<HyperPoint> points = HyperHelper.createRandomPoints(min_coordinate, max_coordinate, number_of_points);
		kmeans.init(number_of_clusters, points);

		boolean show_state = true;

		kmeans.calculate(show_state);

		boolean show_everything = false;
		if (show_everything) {
			Caller.note("--------- START -----------");
			String everything = getContext_ofThePoints(kmeans);
			Caller.log(everything);
			Caller.note("---------- END ----------");
		}

		int total_points = 0;
		for (HyperCluster cluster : kmeans.clusters) {
			total_points += cluster.points.size();
		}
		boolean isOk = number_of_points == total_points;
		Caller.log(isOk, " total points " + total_points);
		return isOk;
	}

	private static String getContext_ofThePoints(KMeans kmeans) {
		String result = "";
		int ith = 0;
		for (HyperCluster cluster : kmeans.clusters) {

			result += ith + " : " + "\t" + cluster.points.size() + "\n";

			for (HyperPoint point : cluster.points) {
				result += "\t\tx=" + point.x + " y=" + point.y + "\n";
			}
		}
		return result;
	}
}
