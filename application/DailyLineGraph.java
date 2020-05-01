
package application;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is used to represent a Daily Line Graph of a country that plots
 * both the confirmed cases and the death rates of COVID19 across four months.
 * 
 * @author paul
 *
 */
@SuppressWarnings("unchecked")
public class DailyLineGraph extends Application {

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
        private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private LineChart chart;
	private XYChart.Series series1; // data for confirmed cases
	private XYChart.Series series2; // data for death rates

	/**
	 * This constructor is used to set the labels and titles of the graphs
	 * 
	 */
	public DailyLineGraph() {
          init();
        }

        /**
         * Re-initialize everything
         * @author Andrew Li
         */
        public void init() {
          xAxis = new CategoryAxis();
          yAxis = new NumberAxis();
          chart = new LineChart(xAxis, yAxis);

          series1 = new XYChart.Series();
          series2 = new XYChart.Series();

          xAxis.setLabel("Day");
	  chart.setTitle("COVID 19 Daily Trends");
	  series1 = new XYChart.Series();
	  series1.setName("Number of Cases");
	  series2 = new XYChart.Series();
	  series2.setName("Number of Deaths");
        }

	/**
	 * This method is used if you want to individually start up the daily line
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
	 * This method is used to return a chart of daily line graph. This method
	 * uses a csv reader to retrieve a list of the specified country's confirmed
	 * cases and death rates.
	 * 
	 * @param country - country to graph
	 * @return chart - a chart of both data points plotted
	 */
	public LineChart getDailyLineGraph(String country) {
		//List<Event> countryStats = CsvReaderWriter
		//		.readCsv("confirmedglobal.csv", "deathsglobal.csv", country);
                List<Event> countryStats = FxUtils.data.get(country);
                init();
		for (Event e : countryStats) {	
			series1.getData().add(		// confirmed cases
					new XYChart.Data(e.getDate().substring(6), e.getCases()));
			series2.getData().add(		// death rates
					new XYChart.Data(e.getDate().substring(6), e.getDeaths()));
		}
		chart.getData().addAll(series1, series2);
		return chart;
	}
}
