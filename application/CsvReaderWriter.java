package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderWriter {

//	public static void main(String[] args) {
//		String filePath = "us.csv";
//		// writeCsv(filePath);
//		readCsv(filePath);
//	}

//	public static void writeCsv(String filePath) {
//		List<Event> events = new ArrayList<Event>();
//		
//		// create demo countries
//		Event event = new Event();
//		event.setDate("2020-01-21");
//		event.setCases(2);
//		event.setDeaths(1);
//		
//		
//		FileWriter fileWriter = null;
//		
//		fileWriter.append("Date", "Cases", "Deaths");
//		try {
//			fileWriter = new FileWriter(filePath);
//			
//			for(Event e: events) {
//				fileWriter.append();
//				fileWriter.append
//			}
//			
//		}
//	}

	public static List<Event> readCsv(String filePath) {
		BufferedReader reader = null;
		List<Event> events = new ArrayList<Event>();
		try {
			String line = "";
			reader = new BufferedReader(new FileReader(filePath));
			reader.readLine();

			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");

				if (fields.length > 0) {
					Event event = new Event();
					event.setDate(fields[0]);
					event.setCases(Integer.parseInt(fields[1]));
					event.setDeaths(Integer.parseInt(fields[2]));
					events.add(event);
				}
			}
			for (Event u : events)
				System.out.printf("[date=%s, cases=%d, deaths=%d]\n",
						u.getDate(), u.getCases(),
						u.getDeaths());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return events;
	}
}
