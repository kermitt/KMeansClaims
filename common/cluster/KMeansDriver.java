package common.cluster;

import common.Caller;

public class KMeansDriver {
    public static void main(String[] args) {
    	
    	long t1 = System.currentTimeMillis(); 
    	
    	KMeans kmeans = new KMeans();
    	
    	int number_of_clusters =20;
    	int number_of_points = 10000;
    	double min_coordinate = -1;
    	double max_coordinate = 1;
    	
    	kmeans.init(number_of_clusters,number_of_points,min_coordinate,max_coordinate);
    	
    	
    	boolean show_state = false; 
    	kmeans.calculate( show_state );

    	for ( int i = 0; i < kmeans.clusters.size(); i++ ) { 
    		
    		int id = kmeans.clusters.get(i).id;
    		int count = kmeans.clusters.get(i).points.size();
    		Caller.note( i + " cluster " + id + " has " + count + " members ");
    	}
    	
    	
    	long delta = System.currentTimeMillis() - t1; 
    	
    	
    	Caller.log("The end (ms=" + delta + ")" );	
    }
}