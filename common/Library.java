package common;

import java.text.NumberFormat;

public class Library {

	public static int RIV_LENGTH = 30;
	public static String REAL_DATA_FILE = "raw_pharma_29.txt";
	public static String TEST_DATA_FILE = "raw_pharma_29_test.txt";

	public static double[] getRIV() {
		double[] riv = new double[RIV_LENGTH];
		for (int i = 0; i < RIV_LENGTH; i++) {
			int v = 1;
			if (Math.random() > 0.5) {
				v = -1;
			}
			riv[i] = v;
		}
		return riv;
	}

	public static double[] getControlRIV() {
		double[] riv = new double[RIV_LENGTH];
		riv[0] = 1; // prevent pure ZEROs in the downstream calculations
		return riv;
	}

	public static String measureFreeMemory() {
		Runtime runtime = Runtime.getRuntime();

		NumberFormat format = NumberFormat.getInstance();

		//long maxMemory = runtime.maxMemory();
		//long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();

		//sb.append("free memory: " + format.format(freeMemory / 1024) + "<br/>");
		//sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "<br/>");
		//sb.append("max memory: " + format.format(maxMemory / 1024) + "<br/>");
		//sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory))));

		String free_memory = "" + format.format(freeMemory / 1024);
		//String allocated_memory = "" + format.format(allocatedMemory / 1024);

		return free_memory;
	}
	
	/* angle! ( DocumentSimilarity ) */
	public static double vectorCosineSimilarity(double[] control, double[] vector) {
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
		double magnitude2 = 0.0;
		double cosineSimilarity = 0.0;

		for (int i = 0; i < control.length; i++) {
			dotProduct += control[i] * vector[i]; // a.b
			magnitude1 += Math.pow(control[i], 2); // (a^2)
			magnitude2 += Math.pow(vector[i], 2); // (b^2)
		}

		magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
		magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

		if (magnitude1 != 0.0 | magnitude2 != 0.0) {
			cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		} else {
			return 0.0;
		}
		return cosineSimilarity;
	}
}