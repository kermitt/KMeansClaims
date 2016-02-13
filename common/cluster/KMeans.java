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
    	
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		HyperCluster cluster = new HyperCluster(i);
    		HyperPoint centroid = HyperPoint.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	//Print Initial state
    	//plotClusters();
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
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
        	//Clear cluster state
        	clearClusters();
        	
        	List <HyperPoint>lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List <HyperPoint>currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += HyperPoint.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	//System.out.println("#################");
        	//System.out.println("Iteration: " + iteration);
        	//System.out.println("Centroid distances: " + distance);
        	//plotClusters();
        	String state = "ith=" + iteration + "\t";
        	state += "state: " + getContext()  + "\t";         
        	state += "distance=" + distance; 
        	Caller.note( state );
        	
        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
    }
    
    private void clearClusters() {
    	for(HyperCluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private List<HyperPoint> getCentroids() {
    	List <HyperPoint>centroids = new ArrayList<HyperPoint>(NUM_CLUSTERS);
    	for(HyperCluster cluster : clusters) {
    		HyperPoint aux = cluster.getCentroid();
    		HyperPoint point = new HyperPoint(aux.getX(),aux.getY());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(HyperPoint point : points) {
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	HyperCluster c = clusters.get(i);
                distance = HyperPoint.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
        for(HyperCluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            // finch
            List<HyperPoint> list = cluster.getPoints();
            int n_points = list.size();
            
            for(HyperPoint point : list) {
            	sumX += point.getX();
                sumY += point.getY();
            }
            
            HyperPoint centroid = cluster.getCentroid();
            if(n_points > 0) {
            	double newX = sumX / n_points;
            	double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }	
        }
    }
}