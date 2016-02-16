package common;

public class Person_Aggregated {
	public double x = 0;
	public double y = 0;
	public double z = 0; 
	
	public double[] riv; 
	public String person_id = "";
	
	public Person_Aggregated(String person_id ) {
		this.person_id = person_id; 
		this.riv = Library.getControlRIV();
	}
	
	public void addVector(double[] r ) {
		for ( int i = 0; i < r.length; i++ ) {
			riv[i] += r[i];
		}
	}
}