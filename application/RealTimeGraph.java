package application;

import java.util.List;
import java.util.concurrent.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is used to represent a Real Time Graph of a country that plots
 * both the confirmed cases and the death rates of COVID19 across a 40 day
 * timespan.
 * 
 * @author paul
 *
 */
@SuppressWarnings("unchecked")
public class RealTimeGraph extends Application {

	ScheduledExecutorService scheduledExecutorService;
	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
	static int current = 0;
	private XYChart.Series<String, Number> series1; // confirmed cases
	private XYChart.Series<String, Number> series2; // death rates

	/**
	 * This constructor is used to set the labels and titles of the graphs
	 */
	public RealTimeGraph() {
		xAxis.setLabel("Time (Days)");
		xAxis.setAnimated(false); // axis animations are removed
		yAxis.setAnimated(false); // axis animations are removed
		lineChart.setTitle("RealTime Trends over 40 day Timespan");
		lineChart.setAnimated(false);
		series1 = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		series1.setName("Confirmed Cases");
		series2.setName("Confirmed Deaths");
	}

	/**
	 * This method is used if you want to individually start up the real time
	 * graph. This method is not explicitly used.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(lineChart, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method is used to stop the real time animation once the data plots
	 * have finished listing.
	 * 
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		scheduledExecutorService.shutdownNow();
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
	 * This method is used to return a chart of a real time graph over a 40 day
	 * time-span. This method uses a csv reader to retrieve a list of the
	 * specified country's confirmed cases and death rates.
	 * 
	 * @param country - country to graph
	 * @return chart - a chart of both data points plotted
	 */
	public LineChart getRealTimeGraph(String country) {
                current = 0;
		lineChart.getData().addAll(series1, series2);
		// Input data to the chart
		List<Event> weeklyCases = FxUtils.data.get(country);
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(() -> { // lambda
			// Update the chart
			if (current >= weeklyCases.size() - 1) {
				scheduledExecutorService.shutdownNow();
			}
			Platform.runLater(() -> {
				series1.getData().add(new XYChart.Data<>( // confirmed cases
						weeklyCases.get(current - 1).getDate().substring(6),
						weeklyCases.get(current - 1).getCases()));
				series2.getData() // death rates
						.add(new XYChart.Data<>(
								weeklyCases.get(current - 1).getDate()
										.substring(6),
								weeklyCases.get(current - 1).getDeaths()));
				if (series1.getData().size() > WINDOW_WIDTH / 10) {
					series1.getData().remove(0); // only plot 40 days
					series2.getData().remove(0); // remove prior points
				}
			});
			current++;
		}, 0, 1, TimeUnit.SECONDS);
		return lineChart;
	}
}
