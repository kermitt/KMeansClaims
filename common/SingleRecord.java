package common;

import java.util.Date;

public class SingleRecord {

	// Not clustering w/ this but it is the key, so, important
	public String person_id = null; //
	
	// TODO : Call APIHUB to fetch a LIST of associated 'ccs_category_ids'
	public String ndc_code	= null;
	
	/// ACTUAL CLUSTERING 
	public Integer ccs_category_id = null;
	public String drug_label_name = null;
	public String drug_group_description = null;
	public String gender_code = null;
	// TODO : USE TIME IN ACTUAL CLUSTER
	public Date filled_date = null;
	// on individual : when was person diag? 
	// when 1st time fill drug for precscription? Was that fill time before or after the diag?
	// when was the last fill time? = during...    and after = 'after_cure'
	// TODO : USE STATES ( before / after / during ) IN ACTUAL CLUSTER
	public Boolean after_cure = null;
	public Boolean during_treatment = null; 
	public Boolean before_diagnosis = null; 
	/// DESCRIPTIVE STATS - not clustering on these, but it is interesting stuff
	public Integer days_supply_count = null;
	public Double patient_paid_amount = null;
	public Double ingredient_cost_paid_amount = null;

	public void display() { 
		String out = "";

		out += " 1 person_id--------------------| " + person_id+ "\n";
		out += " 2 gender_code..................| " + "\n";
		out += " 3 ccs_category_id--------------| " + gender_code+ "\n";
		out += " 4 ndc_code.....................| " +ndc_code + "\n";
		out += " 5 drug_label_name--------------| " +drug_label_name + "\n";
		out += " 6 drug_group_description.......| " + drug_group_description+ "\n";
		out += " 7 days_supply_count------------| " + days_supply_count+ "\n";
		out += " 8 filled_date..................| " + filled_date+ "\n";
		out += " 9 patient_paid_amount----------| " + patient_paid_amount+ "\n";
		out += "10 ingredient_cost_paid_amount..| " + ingredient_cost_paid_amount+ "\n";
		out += "11 after_cure-------------------| " + after_cure+ "\n";
		out += "12 during_treatment.............| " + during_treatment+ "\n";
		out += "13 before_diagnosis-------------| " + before_diagnosis+ "\n";
		
		Caller.log( out );
	}
}