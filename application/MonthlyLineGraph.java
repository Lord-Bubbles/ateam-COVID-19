package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is used to represent a Monthly Line Graph of a country that plots
 * both the confirmed cases and the death rates of COVID19 across four months.
 * 
 * @author paul
 *
 */
@SuppressWarnings("unchecked")
public class MonthlyLineGraph extends Application {

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
	private static final String[] MONTHS = { "JAN", "FEB", "MAR", "APR" };
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private LineChart chart;
	private XYChart.Series series1; // confirmed cases
	private XYChart.Series series2; // death rates

	/**
	 * This constructor is used to set the labels and titles of the graphs
	 */
	public MonthlyLineGraph() {
          init();	
	}
        
        /**
         * Re-initialize everything
         */
        public void init() {
          xAxis = new CategoryAxis();
          yAxis = new NumberAxis();
          chart = new LineChart(xAxis, yAxis);

          xAxis.setLabel("Month");
	  chart.setTitle("COVID 19 Monthly Trends");
	  series1 = new XYChart.Series();
	  series2 = new XYChart.Series();
	  series1.setName("Number of Cases");
	  series2.setName("Number of Deaths");
        }


	/**
	 * This method is used if you want to individually start up the monthly line
	 * graph. This method is not explicitly used.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(chart, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This main method is used to launch the graph program individually. This
	 * method is also not explicitly used.
	 * 
	 * @param args - String input arguments not used.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method is used to return a chart of monthly line graph. This method
	 * uses a csv reader to retrieve a list of the specified country's confirmed
	 * cases and death rates.
	 * 
	 * @param country - country to graph
	 * @return chart - a chart of both data points plotted
	 */
	public LineChart getMonthlyLineGraph(String country) {
		//List<Event> countryStats = CsvReaderWriter
		//		.readCsv("confirmedglobal.csv", "deathsglobal.csv", country);
		List<Event> countryStats = FxUtils.data.get(country);
                List<Event> monthlyCases = new ArrayList<Event>();
                init();
               	for (int i = 0; i < 4; i++) { // adding up all the days to months
			monthlyCases.add(new Event());
			monthlyCases.get(i).setDate(MONTHS[i]);
		}
		for (int i = 0; i < countryStats.size(); i++) {
			int j = Integer
					.parseInt(countryStats.get(i).getDate().substring(6, 7)); // month
			monthlyCases.get(j - 1).setCases(countryStats.get(i).getCases());
			monthlyCases.get(j - 1).setDeaths(countryStats.get(i).getDeaths());
		}
		for (Event e : monthlyCases) {
			series1.getData().add(new XYChart.Data(e.getDate(), e.getCases()));
			series2.getData().add(new XYChart.Data(e.getDate(), e.getDeaths()));
		}
		chart.getData().addAll(series1, series2);

		return chart;

	}

}
