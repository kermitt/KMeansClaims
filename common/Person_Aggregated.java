package common;

public class Person_Aggregated {
	public double x = 0;
	public double y = 0;
	public double z = 0; 
	
	public double[] riv; 
	public String person_id = "";
	
	//patient_paid_amount ( might be negative )
	public double positive_patient_paid_amount = 0;
	public double negative_patient_paid_amount = 0;
	public int seen_positive_patient_paid_amount = 0; 
	public int seen_negative_patient_paid_amount = 0; 
	//pill load
	public double positive_days_supply_count = 0;
	public double negative_days_supply_count = 0;
	public int seen_positive_days_supply_count = 0;
	public int seen_negative_days_supply_count = 0;

	public double positive_ingredient_cost_paid_amount = 0; 
	public double negative_ingredient_cost_paid_amount = 0; 
	public int seen_positive_ingredient_cost_paid_amount = 0;
	public int seen_negative_ingredient_cost_paid_amount = 0;
	
	public String display() { 
		String out = ""; 
		out += "person_id..........................| " + person_id + "\n";
		out += "positive_patient_paid_amount-------| " + positive_patient_paid_amount + "\n";
		out += "negative_patient_paid_amount-------| " + negative_patient_paid_amount + "\n";
		out += "positive_days_supply_count-------| " + positive_days_supply_count + "\n";
		out += "negative_days_supply_count..| " + negative_days_supply_count + "\n";

		return out; 
	}
	
	public Person_Aggregated(String person_id) {
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
	public double getPositivePaidAmount() { 
		return positive_patient_paid_amount / seen_positive_patient_paid_amount;
	}
	public double getNegativePaidAmount() { 
		return negative_patient_paid_amount / seen_negative_patient_paid_amount;
	}

	public void addDaysSuppyCount( double amount ) {
		if ( amount > 0 ) {
			positive_days_supply_count += 1;
			seen_positive_days_supply_count += 1;
		} else if ( amount < 0 ) {
			// can this ever be negative?
			negative_days_supply_count += 1;
			seen_negative_days_supply_count += 1;
		}
	}
	public double getPositiveDaysSuppy() { 
		return positive_days_supply_count / seen_positive_days_supply_count;
	}
	public double getNegativeDaysSupplyCount() { 
		return negative_days_supply_count / seen_negative_days_supply_count;
	}
	
///////	
	/* 
	public double positive_ingredient_cost_paid_amount = 0; 
	public double negative_ingredient_cost_paid_amount = 0; 
	public int seen_positive_ingredient_cost_paid_amount = 0;
	public int seen_negative_ingredient_cost_paid_amount = 0;

	public void addDaysSuppyCount( double amount ) {
		if ( amount > 0 ) {
			positive_days_supply_count += 1;
			seen_positive_days_supply_count += 1;
		} else if ( amount < 0 ) {
			// can this ever be negative?
			negative_days_supply_count += 1;
			seen_negative_days_supply_count += 1;
		}
	}
	public double getPositiveDaysSuppy() { 
		return positive_days_supply_count / seen_positive_days_supply_count;
	}
	public double getNegativeDaysSupplyCount() { 
		return negative_days_supply_count / seen_negative_days_supply_count;
	}
	*/
}

