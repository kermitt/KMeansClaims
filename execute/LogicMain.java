package execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Caller;
import common.Library;
import common.Person_Aggregated;
import common.SingleRecord;
import common.cluster.HyperPoint;
import common.cluster.KMeans;
import common.incoming.PopulateFeatures;
import common.incoming.ReadDataFile;

public class LogicMain {
	private static Map<String, Person_Aggregated> people = new HashMap<String, Person_Aggregated>();
	private static PopulateFeatures pf;

	public static void main(String... strings) {
		Map<String, Person_Aggregated> people = step1_createCollectionOfAggregatedPeople();
		List<HyperPoint> points = step2_createHyperPointsList(people);
		step3a_showClusters( points  );
		
		Caller.note("The end");
	}

	
	
	private static Map<String, Person_Aggregated> step1_createCollectionOfAggregatedPeople() {
		ReadDataFile rdf = new ReadDataFile();

		int limit = 1000000;
		long t1 = System.currentTimeMillis();
		List<SingleRecord> records = rdf.parseFile(Library.REAL_DATA_FILE, limit);

		///// TODO! This ought to came from a .ser file or database or something
		pf = new PopulateFeatures();
		pf.populate(records);

		int count = 0;
		for (SingleRecord record : records) {
			_populateAggregatedPeople(record);
			count++;
			if (count % 300000 == 0) {
				Caller.note("Passing " + count + " of " + " " + records.size() + " uniques " + people.size());
			}
		}
		Caller.note("Unique people count = " + people.size());
		_populateAngles();
		long delta = System.currentTimeMillis() - t1;

		Caller.log("\n*** The end ( ms = " + delta + " ) ***");

		return people;
	}
	
	private static void _populateAngles() {
		int count_angle_assignment = 0;
		double[] controlRiv = Library.getControlRIV();
		
		Caller.log("people size " + people.size());
		
		for (String key : people.keySet()) {
			count_angle_assignment++;

			double[] riv = people.get(key).riv;
			double angle = Library.vectorCosineSimilarity(riv, controlRiv);
			people.get(key).x = angle;
			people.get(key).y = 0;

			if (count_angle_assignment % 100000 == 0) {
				Caller.log("Angle assignment passing " + count_angle_assignment);
			}
		}
		

		
	}
	private static void _populateAggregatedPeople(SingleRecord record) {
		String key = record.person_id;

		if (people.containsKey(record.person_id)) {

			people.get(key).addVector(pf.drug_group_description.get(record.drug_group_description).riv);
			people.get(key).addVector(pf.drug_label_name.get(record.drug_label_name).riv);
			people.get(key).addVector(pf.gender.get(record.gender_code).riv);

		} else {

			Person_Aggregated person = new Person_Aggregated(key);

			person.addVector(pf.drug_group_description.get(record.drug_group_description).riv);
			person.addVector(pf.drug_label_name.get(record.drug_label_name).riv);
			person.addVector(pf.gender.get(record.gender_code).riv);

			people.put(key, person);
		}
	}

	/*
	 * private static List < HyperCluster > doit(List <HyperPoint> points ) {
	 * int number_of_clusters = (int)Math.sqrt( points.size() );
	 * 
	 * double[] lowhigh = HyperHelper.findLowhigh(points); KMeans kmeans = new
	 * KMeans(); kmeans.init(number_of_clusters, points);
	 * 
	 * boolean show_state = false; kmeans.calculate(show_state);
	 * 
	 * return kmeans.clusters; }
	 */

	private static void step3a_showClusters(List<HyperPoint> points ) {

		int number_of_clusters = (int) Math.sqrt(points.size());
		if (number_of_clusters == 0) {
			number_of_clusters = 1;
		}
		KMeans kmeans = new KMeans();
		kmeans.init(number_of_clusters, points);
		boolean show_state = false;
		kmeans.calculate(show_state);

		for (int i = 0; i < kmeans.clusters.size(); i++) {

			int count = kmeans.clusters.get(i).points.size();
			if ( count == 0 ) {
				// skip it 
			} else { 
				//Caller.note(  kmeans.clusters.get(i).id + " has " + count + " members ");
				Caller.note( kmeans.clusters.get(i).id +  "|" +  count );
				
				step3b_showChildrenClusters( kmeans.clusters.get(i).points);
			}
		}
	}
	
	private static void step3b_showChildrenClusters(List<HyperPoint> points ) {
		int number_of_clusters = (int) Math.sqrt(points.size());
		if (number_of_clusters == 0) {
			number_of_clusters = 1;
		}
		KMeans kmeans = new KMeans();
		kmeans.init_no_prints(number_of_clusters, points);
		boolean show_state = false;
		kmeans.calculate(show_state);

		for (int i = 0; i < kmeans.clusters.size(); i++) {

			int id = kmeans.clusters.get(i).id;
			int count = kmeans.clusters.get(i).points.size();
			if ( count == 0 ) {
				// skip it 
			} else { 
				//Caller.note(   "\t" +  " cluster " + id + " has " + count + " members with " + kmeans.clusters.size() + " clusters ");
				Caller.note(  "||" +  count );
				
			}
		}
	}

	
	
	private static List<HyperPoint> step2_createHyperPointsList(Map<String, Person_Aggregated> people) {
		List<HyperPoint> points = new ArrayList<HyperPoint>();
		for (String key : people.keySet()) {
			double x = people.get(key).x;
			double y = 0;
			HyperPoint p = new HyperPoint(x, y);
			points.add(p);
		}
		return points;
	}
}