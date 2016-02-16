package common.incoming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Caller;
import common.Library;
import common.Person_Aggregated;
import common.SingleRecord;
import common.cluster.HyperHelper;
import common.cluster.HyperPoint;
import common.cluster.KMeans;

public class IncomingDriver {

	public static void main(String... strings) {

		Map<String, Person_Aggregated> people = createCollectionOfAggregatedPeople();
		
		doCluster( people );
	}

	private static Map<String, Person_Aggregated> createCollectionOfAggregatedPeople() { 	

		Map<String, Person_Aggregated> people = new HashMap<String, Person_Aggregated>();

		ReadDataFile rdf = new ReadDataFile();

		int limit = 1000000;
		long t1 = System.currentTimeMillis();
		List<SingleRecord> records = rdf.parseFile(Library.REAL_DATA_FILE, limit);

		PopulateFeatures pf = new PopulateFeatures();
		pf.populate(records);

		int count = 0;
		for (SingleRecord record : records) {
			count++;

			String key = record.person_id;

			if ( key != null) {
				if (people.containsKey(record.person_id)) {
					
					people.get(key).addVector(pf.drug_group_description.get(record.drug_group_description).riv);
					// ccs_category_id is breaky 
					// person.addVector(pf.ccs_category_id.get(record.ccs_category_id).riv);
					people.get(key).addVector(pf.drug_label_name.get(record.drug_label_name).riv);
					people.get(key).addVector(pf.gender.get(record.gender_code).riv);
					
				} else {

					Person_Aggregated person = new Person_Aggregated(key);

					person.addVector(pf.drug_group_description.get(record.drug_group_description).riv);
					// ccs_category_id is breaky 
					// person.addVector(pf.ccs_category_id.get(record.ccs_category_id).riv);
					person.addVector(pf.drug_label_name.get(record.drug_label_name).riv);
					person.addVector(pf.gender.get(record.gender_code).riv);

					people.put(key, person);

				}
			}
			if (count % 100000 == 0) {
				Caller.note("Passing " + count + " in adding vectors of " + " " + records.size() + " unique people = "
						+ people.size());
			}

		}
		Caller.note("Unique people count = " + people.size());

		
		int count_angle_assignment = 0; 
		double[] controlRiv = Library.getControlRIV();
		for ( String key : people.keySet() ) {
			count_angle_assignment++; 
			
			double[] riv = people.get(key).riv; 
			double angle = Library.vectorCosineSimilarity(riv,  controlRiv);
			people.get(key).x = angle; 
			//TODO!
			people.get(key).y = 0; // I can stack up all the dimensions just like this...   ...planning to make the 'y' be 'cost'
			
			if ( count_angle_assignment % 100000 == 0 ) { 
				Caller.note("Angle assignment passing " + count_angle_assignment );
			}
		}
		
		long delta = System.currentTimeMillis() - t1;

		Caller.log("\n*** The end ( ms = " + delta + " ) ***");

		
		return people; 
	}

	private static void doCluster(Map<String, Person_Aggregated> people) {

		long t1 = System.currentTimeMillis();

		KMeans kmeans = new KMeans();

		List <HyperPoint> points = new ArrayList < HyperPoint > (); 
		for ( String key : people.keySet()) {
			double x = people.get( key ).x;
			double y = 0;
			HyperPoint p = new HyperPoint(x,y);
			points.add(p);
			
		}
		
		
		int number_of_clusters = 200;
		int number_of_points = points.size();
		double[] lowhigh = HyperHelper.findLowhigh(points);
//		double min_coordinate = lowhigh[0];
//		double max_coordinate = lowhigh[1];


		kmeans.init(number_of_clusters, points);

		boolean show_state = false;
		kmeans.calculate(show_state);

		for (int i = 0; i < kmeans.clusters.size(); i++) {

			int id = kmeans.clusters.get(i).id;
			int count = kmeans.clusters.get(i).points.size();
			Caller.note(i + " cluster " + id + " has " + count + " members ");
		}

		long delta = System.currentTimeMillis() - t1;

		Caller.log("The end (ms=" + delta + ")");
	}

}
