package common.cluster;
 
import java.util.ArrayList;
import java.util.List;

import common.Caller;
import common.cluster.HyperPoint;
 
public class KMeans {
 

    private int NUM_CLUSTERS = 3;    
    //Number of Points
    private int NUM_POINTS = 100;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 10;
    
    private List <HyperPoint>points;
    private List <HyperCluster>clusters;
    
    public KMeans() {
    	this.points = new ArrayList<HyperPoint>();
    	this.clusters = new ArrayList<HyperCluster>();    	
    }
    
    public static void main(String[] args) {
    	
    	KMeans kmeans = new KMeans();
    	kmeans.init();
    	kmeans.calculate();
    }
    

    public void init() {
    	//Create Points
    	points = HyperPoint.createRandomPoints(MIN_COORDINATE,MAX_COORDINATE,NUM_POINTS);
    	
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		HyperCluster cluster = new HyperCluster(i);
    		cluster.centroid = HyperPoint.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
    		clusters.add(cluster);
    	}
    }
    
    
    private String getContext() { 
    	String state = ""; 
		for (int i = 0; i < NUM_CLUSTERS; i++) {
    		state += "\t" + clusters.get(i).points.size();
    	}
		return state;
    }
  /*  
	private void plotClusters() {
		String out = ""; 
		
		for (int i = 0; i < NUM_CLUSTERS; i++) {
    		out += "\t" + clusters.get(i).points.size();
    	}
    }
*/
    
 /*
	private void plotClusters() {
		for (int i = 0; i < NUM_CLUSTERS; i++) {
    		//HyperCluster c = clusters.get(i);
    		//c.plotCluster();
    		clusters.get(i).describe();
    	}
    }
*/    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
//        boolean finish = false;
    	boolean keepAlive = true;
    	int limit = 1000;
        int iteration = 0;
        
        while( keepAlive && iteration < limit ) {
        	iteration++;

        	clearClustersState();
        	List <HyperPoint>lastCentroids = getCentroids();
        	assignPointsToNearestCluster();
        	calculateNewCentroids();
        	List <HyperPoint>currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += HyperPoint.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	String state = "ith=" + iteration + "\t";
        	state += "state: " + getContext()  + "\t";         
        	state += "distance=" + distance; 
        	Caller.note( state );
        	
        	
        	if(distance == 0) {
        		keepAlive = false;
        	}
        }
    }
    
    private void clearClustersState() {
    	for(HyperCluster cluster : clusters) {
    		cluster.points.clear();
    	}
    }
    
    private List<HyperPoint> getCentroids() {
    	List <HyperPoint>centroids = new ArrayList<HyperPoint>(NUM_CLUSTERS);
    	for(HyperCluster cluster : clusters) {
    		centroids.add( new HyperPoint( cluster.centroid.x, cluster.centroid.y));
    	}
    	return centroids;
    }
    
    private void assignPointsToNearestCluster() {
        double min, max = Double.MAX_VALUE;
        int cluster_index = 0;                 
        double distance = 0.0; 
        
        for(HyperPoint point : points) {
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	HyperCluster c = clusters.get(i);
                distance = HyperPoint.distance(point, c.centroid);
                if(distance < min){
                    min = distance;
                    cluster_index = i;
                }
            }
            clusters.get(cluster_index).points.add(point);
        }
    }
    
    private void calculateNewCentroids() {
        for(HyperCluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<HyperPoint> list = cluster.points;
            int n_points = list.size();
            
            for(HyperPoint point : list) {
            	sumX += point.x;
                sumY += point.y;
            }
            
            HyperPoint centroid = cluster.centroid;
            if(n_points > 0) {
            	centroid.x = sumX / n_points;
            	centroid.y = sumY / n_points;
            }	
        }
    }
}