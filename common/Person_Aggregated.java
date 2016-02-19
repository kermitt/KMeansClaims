package common;

public class Person_Aggregated {
	public double x = 0;
	public double y = 0;
	public double z = 0; 
	
	public double[] riv; 
	public String person_id = "";
	
	public double positive_patient_paid_amount = 0;
	public double negative_patient_paid_amount = 0;
	public int seen_positive_patient_paid_amount = 0; 
	public int seen_negative_patient_paid_amount = 0; 
	
	public String display() { 
		String out = ""; 
		out += "person_id..........................| " + person_id + "\n";
		out += "positive_patient_paid_amount-------| " + positive_patient_paid_amount + "\n";
		out += "seen_positive_patient_paid_amount..| " + seen_positive_patient_paid_amount + "\n";
		out += "negative_patient_paid_amount-------| " + negative_patient_paid_amount + "\n";
		out += "seen_negative_patient_paid_amount..| " + seen_negative_patient_paid_amount + "\n";

		return out; 
	}
	
	public Person_Aggregated(String person_id ) {
		this.person_id = person_id; 
		this.riv = Library.getControlRIV();
	}
	
	public void addVector(double[] r ) {
		for ( int i = 0; i < r.length; i++ ) {
			riv[i] += r[i];
		}
	}
	//
	public void addPatient_paid_amount( double amount ) {
		if ( amount > 0 ) {
			positive_patient_paid_amount += amount; 
			seen_positive_patient_paid_amount += 1; 
		} else if ( amount < 0 ) {
			// remember, 'amount' here is a negative number
			negative_patient_paid_amount += amount; 	
			seen_negative_patient_paid_amount += 1; 
		}
	}
}

