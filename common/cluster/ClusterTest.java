package common.cluster;

import common.Caller;

public class ClusterTest { 
    public static void main(String[] args) {
    	simpleTestRun();
    	Caller.log("The end");
    }
    private static boolean simpleTestRun() {
    	

    	
    	KMeans kmeans = new KMeans();
    	
    	int number_of_clusters = 3;
    	int number_of_points = 20;
    	double min_coordinate = -1;
    	double max_coordinate = 1;
    	
    	kmeans.init(number_of_clusters,number_of_points,min_coordinate,max_coordinate);
    	
    	boolean show_state = true; 
    	
    	kmeans.calculate( show_state );

    	
    	boolean show_everything = false; 
    	if ( show_everything ) { 
    		String everything = getContext_ofThePoints( kmeans ); 
    		Caller.log( everything );
    	}
    	
    	
    	int total_points = 0;
    	for ( HyperCluster cluster : kmeans.clusters) {
    		total_points += cluster.points.size();
    	}
    	boolean isOk = number_of_points == total_points; 
    	Caller.log( isOk, " total points " + total_points  );
    	return isOk ; 
    	
    }    
    
    private static String getContext_ofThePoints( KMeans kmeans) { 
    	String result = ""; 
    	int ith = 0;
    	for ( HyperCluster cluster : kmeans.clusters ) { 
    		
    		result += ith + " : " + "\t" + cluster.points.size() + "\n";
    		
    		for ( HyperPoint point : cluster.points ) {
    			result += "\t\tx=" + point.x + " y=" + point.y + "\n";
    		}
    	}
    	return result;
    }
}

