package common.cluster;

import common.Caller;

public class ClusterTest { 
    public static void main(String[] args) {
    	KMeans kmeans = new KMeans();
    	kmeans.init();
    	kmeans.calculate();
    	Caller.log("The end");
    }    
}