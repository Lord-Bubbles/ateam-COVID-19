package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
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
	 * 
	 * @param filePath1 the csv file containing all the countries' confirmed
	 *                  cases
	 * @param filePath2 the csv file containing all the countries' death cases
	 * @return List<Event> a list of all of the countries' data concerning
	 *         COVID-19
	 *
	 * @author Andrew Li
	 */
	public static Map<String, List<Event>> readCsv(String filePath1,
			String filePath2) {

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
				for (int i = 1; i < confirmed.length; i++) { // Add the data to
																// the
																// corresponding
																// country's
																// data list
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
				reader1.close(); // close the files
				reader2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static void writeFile(String country) {
		List<Event> events = FxUtils.data.get(country);
		try {
			PrintWriter writer = new PrintWriter(country + "_data_report.txt");
			writer.println("DATA REPORT for " + country + ":\n");
			writer.println(
					"\t\t========================= Overall Data Analysis =========================");
			writer.println(
					"\n\t(Official Data Compiled from John Hopkins University Center & New York Times)");			
			writer.println("\nStart Date: Jan 21, 2020\t\t"
					+ "End Date: April 27, 2020\n");
			writer.println("Initial Confirmed: " + events.get(0).getCases());
			writer.println("Last Confirmed: "
					+ events.get(events.size() - 1).getCases());
			writer.println("Initial Deaths: " + events.get(0).getDeaths());
			writer.println("Last Deaths: "
					+ events.get(events.size() - 1).getDeaths());
			int confirmedRate = (events.get(events.size() - 1).getCases()
					- events.get(0).getCases()) / events.size();
			int deathRate = (events.get(events.size() - 1).getDeaths()
					- events.get(0).getDeaths()) / events.size();
			writer.println("\nConfirmed Growth Slope: " + confirmedRate
					+ " cases confirmed/day");
			writer.println("Deaths Growth Slope: " + deathRate
					+ " deaths confirmed/day");
			writer.println("Death:Confirmed ratio: "
					+ events.get(events.size() - 1).getDeaths() + "/"
					+ events.get(events.size() - 1).getCases());

			double population = 0.0;
			if (country.equals("China")) {
				population = 1.393 * Math.pow(10, 9);
			} else if (country.equals("US")) {
				population = 328.2 * Math.pow(10, 6);
			} else if (country.equals("United_Kingdom")) {
				population = 66.65 * Math.pow(10, 6);
			} else if (country.equals("Germany")) {
				population = 83.02 * Math.pow(10, 6);
			} else if (country.equals("Italy")) {
				population = 60.36 * Math.pow(10, 6);
			} else if (country.equals("Spain")) {
				population = 46.94 * Math.pow(10, 6);
			} else if (country.equals("France")) {
				population = 66.99 * Math.pow(10, 6);
			} else if (country.equals("Iran")) {
				population = 81.8 * Math.pow(10, 6);
			}
			writer.println("\nPopulation of " + country + ": " + population);
			writer.println("% of Population affected: "
					+ (events.get(events.size() - 1).getCases() / population)
							* 100
					+ "%");
			writer.println("% of Population Dead: "
					+ (events.get(events.size() - 1).getDeaths() / population)
							* 100
					+ "%");
			writer.println("\n\t\t============================== Raw Data: ===============================\n");
			for (int i = 0; i < events.size(); i++) {
				writer.println("Confirmed:" + events.get(i).getCases()
						+ " , Deaths: " + events.get(i).getDeaths());
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("File was not found");
		}
	}

//	public static void main(String[] args) {
//		writeFile("China");
//	}
}
