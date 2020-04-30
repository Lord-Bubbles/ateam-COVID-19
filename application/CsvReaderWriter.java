package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
         * This static method reads all the countries' data from the given csv files
         * @param filePath1 the csv file containing all the countries' confirmed cases
         * @param filePath2 the csv file containing all the countries' death cases
         * @return List<Event> a list of all of the countries' data concerning COVID-19
         *
         * @author Andrew Li
         */
        public static Map<String, List<Event>> readCsv(String filePath1, String filePath2) {

	  BufferedReader reader1 = null;
	  BufferedReader reader2 = null;
	  Map<String, List<Event>> map = new HashMap<>();
          try {
	    String line = "";
	    reader1 = new BufferedReader(new FileReader(filePath1)); // confirmed
            reader2 = new BufferedReader(new FileReader(filePath2)); // deaths

            reader2.readLine(); // Skip the first line

	    // Add all the countries to the map
	    String[] countries = reader1.readLine().split(",");
            for (int i = 1; i < countries.length; i++) {
              map.put(countries[i], new ArrayList<Event>());
            }
            // Add all the data
            while ((line = reader1.readLine()) != null) {
              String line2 = reader2.readLine();
              String[] confirmed = line.split(",");
              String[] deaths = line2.split(",");
              for (int i = 1; i < confirmed.length; i++) { // Add the data to the corresponding
                                                           // country's data list
                Event event = new Event();
                event.setDate(confirmed[0]);
                event.setCases(Integer.parseInt(confirmed[i]));
                event.setDeaths(Integer.parseInt(deaths[i]));
                map.get(countries[i]).add(event);
              }
            }
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
	return map;
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
