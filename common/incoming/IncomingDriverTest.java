package common.incoming;

import java.util.Date;
import java.util.List;

import common.Caller;
import common.Library;
import common.SingleRecord;
import common.incoming.PopulateFeatures;
import common.incoming.ReadDataFile;
import common.Seen;
import java.util.Map;

public class IncomingDriverTest {
	public static void main(String... strings) {
		getDate_mmddyyyy();
		readFile();
		bigLoad_timeTest();
		populateFeaturesTest();
		Caller.note("The end");
	}

	@SuppressWarnings("unused")
	private static void unrollMap(String which, Map<String, Seen> seen) {
		Caller.note("***" + which + " ***");
		int count = 0;
		for (String key : seen.keySet()) {
			Caller.note(++count + "\t" + seen.get(key).seen + "\t" + key);
		}
	}

	private static boolean populateFeaturesTest() {
		ReadDataFile rdf = new ReadDataFile();

		int limit = 1000000;
		long t1 = System.currentTimeMillis();
		List<SingleRecord> records = rdf.parseFile(Library.TEST_DATA_FILE, limit);

		PopulateFeatures pf = new PopulateFeatures();
		pf.populate(records);

//		unrollMap("1 LABEL", pf.drug_label_name);
//		unrollMap("2 DESC", pf.drug_group_description);
//		unrollMap("3 GENDER", pf.gender);
//		unrollMap("4 CCS CAT", pf.ccs_category_id);

		for ( SingleRecord rec : records ) { 
		//	rec.display();
		}
		
		long delta = System.currentTimeMillis() - t1;

		boolean isOk = true == pf.drug_label_name.size() > 0 == pf.drug_group_description.size() > 0 == pf.gender
				.size() > 0 == pf.ccs_category_id.size() > 0;

		Caller.log(isOk, " time delta: " + (delta));

		if (!isOk) {
			Caller.note("FAIL! drug_label_name.size() > 0 ?  : " + pf.drug_label_name.size());
			Caller.note("FAIL! drug_group_description.size() > 0 ?  : " + pf.drug_group_description.size());
			Caller.note("FAIL! gender.size() > 0 ? : " + pf.gender.size());
			Caller.note("FAIL! ccs_category_id..size() > 0 ? : " + pf.ccs_category_id.size());
		}
		return isOk;
	}

	private static boolean bigLoad_timeTest() {
		ReadDataFile rdf = new ReadDataFile();

		int limit = 1000000;
		long t1 = System.currentTimeMillis();
		List<SingleRecord> records = rdf.parseFile(Library.REAL_DATA_FILE, limit);
		long delta = System.currentTimeMillis() - t1;

		boolean isOk = false;
		long too_long = 5000l;
		if (records.size() > 0 && delta < too_long) {
			isOk = true;
		}
		Caller.log(isOk, "And the time = " + (delta) + " and size = " + records.size());
		return isOk;
	}

	@SuppressWarnings("deprecation")
	private static boolean getDate_mmddyyyy() {
		boolean isOk = false;

		ReadDataFile rdf = new ReadDataFile();
		Date maxDate = rdf.getDate_mmddyyyy("3/2/2016");
		int y = maxDate.getYear();
		int m = maxDate.getMonth();
		int d = maxDate.getDate();

		m += 1;
		y += 1900;

		if (y == 2016 && m == 3 && d == 2) {
			isOk = true;
		}
		Caller.log(isOk);

		return isOk;
	}

	@SuppressWarnings("deprecation")
	private static boolean readFile() {
		boolean isOk = false;
		String file = Library.TEST_DATA_FILE;
		ReadDataFile rdf = new ReadDataFile();
		List<SingleRecord> records = rdf.parseFile(file, 1000);

		SingleRecord r = records.get(0);

		int y = r.filled_date.getYear();
		int m = r.filled_date.getMonth();
		int d = r.filled_date.getDate();

		m += 1;
		y += 1900;
		if (r.person_id.equals("385177") && y == 2014 && m == 12 && d == 10 && r.gender_code.equals("M")
				&& 29 == r.ccs_category_id.intValue()) {
			isOk = true;
		}
		Caller.log(isOk);
		return isOk;
	}
}