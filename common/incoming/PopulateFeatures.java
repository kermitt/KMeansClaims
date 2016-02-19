package common.incoming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.SingleRecord;
import common.Caller;
import common.Library;
import common.Seen;

import java.io.Serializable;

public class PopulateFeatures implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public Map < String, Seen > gender = new HashMap < String, Seen >(); 
	public Map < String, Seen > ccs_category_id = new HashMap < String, Seen >(); 
	public Map < String, Seen > drug_label_name = new HashMap < String, Seen >(); 
	public Map < String, Seen > drug_group_description = new HashMap < String, Seen >(); 
	
	public void populate( List < SingleRecord > everything) {

		boolean do_or_doNotShow_flag = false; // true = lots of prints
		
		for ( SingleRecord record : everything ) {
			if ( gender.containsKey(record.gender_code )) { 
				gender.get( record.gender_code).seen++;
				Caller.log("Adding gender 2 ", do_or_doNotShow_flag );

			} else {
				double[] riv = Library.getRIV();
				gender.put( record.gender_code, new Seen( riv ));
				Caller.log("Adding gender 1 " ,do_or_doNotShow_flag);
			}
			String cat_id_as_string = (String)record.ccs_category_id.toString();
			if ( ccs_category_id.containsKey(cat_id_as_string)) {
				ccs_category_id.get( cat_id_as_string ).seen++;
				Caller.log("ccs_category_id 2 ",do_or_doNotShow_flag);
			} else {
				double[] riv = Library.getRIV(); 
				ccs_category_id.put( cat_id_as_string, new Seen( riv ));
				Caller.log("ccs_category_id 1 ",do_or_doNotShow_flag);
			}
			
			if ( drug_label_name.containsKey(record.drug_label_name)) {
				drug_label_name.get(record.drug_label_name).seen++;
				Caller.log("drug_label_name 2 ",do_or_doNotShow_flag);

			} else {
				double[] riv = Library.getRIV(); 
				drug_label_name.put(record.drug_label_name, new Seen( riv ));
				Caller.log("drug_label_name 1 ",do_or_doNotShow_flag);
			}			
			
			if ( drug_group_description.containsKey(record.drug_group_description)) {
				drug_group_description.get(record.drug_group_description).seen++;
				Caller.log("drug_group_description 2 ",do_or_doNotShow_flag);

			} else {
				double[] riv = Library.getRIV(); 
				drug_group_description.put(record.drug_group_description, new Seen( riv ));
				Caller.log("drug_group_description 1 ",do_or_doNotShow_flag);
			}			

		}		
	}
}




//DESCRIPTIVE		
//|	days_supply_count	patient_paid_amount	|	ingredient_cost_paid_amount	|	


// hm after_cure	|	during_treatment	|	before_diagnosis		|

