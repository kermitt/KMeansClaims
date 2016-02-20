Lifecycle


step 1: execute.LogicMain

step 2:
	step 2a: ReadDataFile.parseFile returns List<SingleRecord>
	step 2b: PopulateFeatures.populate to create Maps of unique clustering features and assign each entry a RIV ( 		data are taken 		from the List<SingleRecord> in step 2a 
		These Maps will be used later - This step is a good candidate to offload to some stable datastore
	step 2c: Using the List<SingleRecord> from step2a and create a 
		"Map<String, Person_Aggregated> people = new HashMap<String, Person_Aggregated>();"
		The aggregated people in this will have condense the 900000+ records into 16K+ objects
		These aggregated people will have their summed RIVs and their ave descriptive stats assigned to them.
		
 
		
step 3: Roll through the Map < String, Person_Aggregated > people map and form CLUSTERS


Currently, the data are: 
person_id	|	gender_code	|	ccs_category_id	|	ndc_code	|	drug_label_name	|	drug_group_description	|	days_supply_count	|	
filled_date	|	patient_paid_amount	|	ingredient_cost_paid_amount	|	after_cure	|	during_treatment	|	before_diagnosis		|
1	|	M	|	29	|	00591352530	|	LIDOCAINE 5 % PATCH	|	DERMATOLOGICALS	|	30	|	12/10/2014	|	33	|	182.53	|	True	|	False	|	False		|



	
