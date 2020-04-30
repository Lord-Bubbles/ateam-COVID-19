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

public class RealTimeGraph extends Application {

	ScheduledExecutorService scheduledExecutorService;
	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
	static int current = 0;

	public RealTimeGraph() {
		xAxis.setLabel("Time (Days)");
		xAxis.setAnimated(false); // axis animations are removed
		yAxis.setAnimated(false); // axis animations are removed
		lineChart.setTitle("RealTime Trends over 40 day Timespan");
		lineChart.setAnimated(false);
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		series1.setName("Confirmed Cases");
		series2.setName("Confirmed Deaths");
		lineChart.getData().addAll(series1, series2);
		// Input data to the chart
		List<Event> weeklyCases = CsvReaderWriter.readCsv("us.csv");
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(() -> { // lambda
			// Update the chart
			if (current >= weeklyCases.size() - 1) {
				scheduledExecutorService.shutdownNow();
			}
			Platform.runLater(() -> {
				series1.getData()
						.add(new XYChart.Data<>(
								weeklyCases.get(current - 1).getDate()
										.substring(6),
								weeklyCases.get(current - 1).getCases()));
				series2.getData()
						.add(new XYChart.Data<>(
								weeklyCases.get(current - 1).getDate()
										.substring(6),
								weeklyCases.get(current - 1).getDeaths()));
				if (series1.getData().size() > WINDOW_WIDTH / 10) {
					series1.getData().remove(0);
					series2.getData().remove(0);
				}
			});
			current++;
		}, 0, 1, TimeUnit.SECONDS);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(lineChart, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		scheduledExecutorService.shutdownNow();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public LineChart getRealTimeGraph() {
		return lineChart;
	}
}
