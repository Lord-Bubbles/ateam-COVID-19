package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read files from an external source and return a list of
 * those data points for a particular country. It is also used to write a
 * summary output to a file.
 * 
 * @author paul
 *
 */
public class CsvReaderWriter {

	/**
	 * This static method is called by giving two different file paths and a
	 * country. The first file path should be a file of all the confirmed cases
	 * across 8 different countries, and the second file path should be a file
	 * of all the death rates across 8 different countries.
	 * 
	 * @param filePath1 - file of confirmed cases
	 * @param filePath2 - file of death rates
	 * @param country   - country to graph
	 * @return List<Event> - list of dates, confirmed cases, death rates for a
	 *         country
	 */
	public static List<Event> readCsv(String filePath1, String filePath2,
			String country) {

		BufferedReader reader1 = null;
		BufferedReader reader2 = null;
		List<Event> events = new ArrayList<Event>();
		try {
			String line = "";
			reader1 = new BufferedReader(new FileReader(filePath1)); // confirmed
			reader2 = new BufferedReader(new FileReader(filePath2)); // death

			// find the index of the CSV which corresponds to the country
			int index = 0;
			String[] countries = reader1.readLine().split(",");
			for (int i = 1; i < countries.length; i++) { // index 0 is the date
				if (countries[i].equals(country))
					index = i;
			}
			reader2.readLine(); // read the first title

			while ((line = reader1.readLine()) != null) {
				String line2 = reader2.readLine();
				String[] fields = line.split(",");
				String[] fields2 = line2.split(",");
				if (fields.length > 0) {
					Event event = new Event();
					event.setDate(fields[0]);
					event.setCases(Integer.parseInt(fields[index]));
					event.setDeaths(Integer.parseInt(fields2[index]));
					events.add(event);
				}
			}
			System.out.println("COUNTRY: " + countries[index]); // print name
			for (Event u : events) // print to see the data
				System.out.printf("[date=%s, cases=%d, deaths=%d]\n",
						u.getDate(), u.getCases(), u.getDeaths());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader1.close();		// close the files
				reader2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return events;
	}

//	public static void main(String[] args) {
//	String filePath = "us.csv";
//	// writeCsv(filePath);
//	readCsv(filePath);
//}

//public static void writeCsv(String filePath) {
//	List<Event> events = new ArrayList<Event>();
//	
//	// create demo countries
//	Event event = new Event();
//	event.setDate("2020-01-21");
//	event.setCases(2);
//	event.setDeaths(1);
//	
//	
//	FileWriter fileWriter = null;
//	
//	fileWriter.append("Date", "Cases", "Deaths");
//	try {
//		fileWriter = new FileWriter(filePath);
//		
//		for(Event e: events) {
//			fileWriter.append();
//			fileWriter.append
//		}
//		
//	}
//}
}
