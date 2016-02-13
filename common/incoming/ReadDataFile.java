package common.incoming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Caller;
import common.SingleRecord;

public class ReadDataFile {
	public List<SingleRecord> parseFile(String fileName, int limit) {

		List<SingleRecord> records = new ArrayList<SingleRecord>();
		File file = new File(fileName);
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int depth = 0;
			while ((line = bufferedReader.readLine()) != null) {
				
				if (line != null && depth > 0 && depth < limit && line.length() > 40 ) {
					try { 
						records.add(createSingleRecord(line));
					
					} catch ( Exception boom ) {
						Caller.log("BOOM! ( Likely the line itself is poorly constructed )  " + boom.getMessage() + "   LINE: " + line ); 
					}
				}
				depth++;
				
				if ( depth % 300000 == 0 ) {
					Caller.note("...passing " + depth + " :: SingleRecords created " + records.size());
				}
			}
			fileReader.close();
			Caller.note("Total seen: " + records.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}

	public List<SingleRecord> parseFile(String fileName) {
		int limit = 1000000;
		List<SingleRecord> records = parseFile(fileName, limit);
		return records;
	}

	public SingleRecord createSingleRecord(String candidate) {
		SingleRecord sr = new SingleRecord();

		String[] pieces = candidate.split("\\|");
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = pieces[i].trim();
		}

		sr.person_id = pieces[0];
		sr.gender_code = pieces[1];
		sr.ccs_category_id = Integer.parseInt(pieces[2]); // Integer
		sr.ndc_code = pieces[3];
		sr.drug_label_name = pieces[4];
		sr.drug_group_description = pieces[5];
		sr.days_supply_count = Integer.parseInt(pieces[6]); // int
		sr.filled_date = getDate_mmddyyyy(pieces[7]); // date
		sr.patient_paid_amount = Double.parseDouble(pieces[8]); // double
		sr.ingredient_cost_paid_amount = Double.parseDouble(pieces[8]); // double
		sr.after_cure = Boolean.valueOf(pieces[9]); // bool
		sr.during_treatment = Boolean.valueOf(pieces[10]); // bool
		sr.before_diagnosis = Boolean.valueOf(pieces[11]); // bool

		return sr;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	public Date getDate_mmddyyyy(String date_mmddyyyy) {
		try {
			return sdf.parse(date_mmddyyyy);
		} catch (ParseException e) {
			Caller.log("Failbot! " + date_mmddyyyy + " recieved " + e.getMessage() );
		}
		return null;
	}
}