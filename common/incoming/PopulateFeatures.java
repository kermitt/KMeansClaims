package common.incoming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.SingleRecord;
import common.Library;
import common.Seen;

public class PopulateFeatures {
	
	public Map < String, Seen > gender = new HashMap < String, Seen >(); 
	public Map < String, Seen > ccs_category_id = new HashMap < String, Seen >(); 
	public Map < String, Seen > drug_label_name = new HashMap < String, Seen >(); 
	public Map < String, Seen > drug_group_description = new HashMap < String, Seen >(); 
	
	public void populate( List < SingleRecord > everything ) {

		for ( SingleRecord record : everything ) {
			if ( gender.containsKey(record.gender_code )) { 
				gender.get( record.gender_code).seen++;
			} else {
				double[] riv = Library.getRIV();
				gender.put( record.gender_code, new Seen( riv ));
			}
			String cat_id_as_string = (String)record.ccs_category_id.toString();
			if ( ccs_category_id.containsKey(cat_id_as_string)) {
				ccs_category_id.get( cat_id_as_string ).seen++;
			} else {
				double[] riv = Library.getRIV(); 
				ccs_category_id.put( cat_id_as_string, new Seen( riv ));
			}
			
			if ( drug_label_name.containsKey(record.drug_label_name)) {
				drug_label_name.get(record.drug_label_name).seen++;
			} else {
				double[] riv = Library.getRIV(); 
				drug_label_name.put(record.drug_label_name, new Seen( riv ));
			}
			
			if ( drug_group_description.containsKey(record.drug_group_description)) {
				drug_group_description.get(record.drug_group_description).seen++;
			} else {
				double[] riv = Library.getRIV();
				drug_group_description.put(record.drug_group_description, new Seen(riv));
			}
		}		
	}
}




//DESCRIPTIVE		
//|	days_supply_count	patient_paid_amount	|	ingredient_cost_paid_amount	|	


// hm after_cure	|	during_treatment	|	before_diagnosis		|

