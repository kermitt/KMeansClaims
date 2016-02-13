package common;

import java.util.Date;

public class SingleRecord {
	public String person_id = null; //1
	public String gender_code = null;
	public Integer ccs_category_id = null;
	public String ndc_code	= null;
	public String drug_label_name = null;
	public String drug_group_description = null;
	public Integer days_supply_count = null;
	public Date filled_date = null;
	public Double patient_paid_amount = null;
	public Double ingredient_cost_paid_amount = null;
	public Boolean after_cure = null;
	public Boolean during_treatment = null; 
	public Boolean before_diagnosis = null; 
	
	public void display() { 
		String out = ""; 
		out += "1 person_id\t'" +person_id + "'\n";
		out += "2 gender_code\t'" +gender_code + "'\n";
		out += "3 ccs_category_id\t'" +ccs_category_id + "'\n";
		out += "4 ndc_code\t'" + ndc_code+ "'\n";
		out += "5 drug_label_name\t'" +drug_label_name + "'\n";
		out += "6 drug_group_description\t'" + drug_group_description+ "'\n";
		out += "7 days_supply_count\t'" + days_supply_count+ "'\n";
		out += "8 filled_date\t'" + filled_date+ "'\n";
		out += "9 patient_paid_amount\t'" +patient_paid_amount + "'\n";
		out += "10 ingredient_cost_paid_amount\t'" + ingredient_cost_paid_amount+ "'\n";
		out += "11 after_cure\t'" + after_cure+ "'\n";
		out += "12 during_treatment\t'" + during_treatment+ "'\n";
		out += "13 before_diagnosis\t'" + before_diagnosis+ "'\n";
		
		Caller.log( out );
		
 		
		
	}
}
