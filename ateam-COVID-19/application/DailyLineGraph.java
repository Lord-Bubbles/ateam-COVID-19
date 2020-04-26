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

public class DailyLineGraph extends Application {

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart chart = new LineChart(xAxis, yAxis);

	
	public DailyLineGraph() {
		xAxis.setLabel("Week");
		chart.setTitle("COVID 19 WEEKLY TRENDS");
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Number of Cases");
		List<Event> dataCases = CsvReaderWriter.readCsv("us.csv");
		for (Event e : dataCases) {
			series1.getData().add(
					new XYChart.Data(e.getDate().substring(6), e.getCases()));
		}
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Number of Deaths");
		for(Event e : dataCases) {
			series2.getData().add(new XYChart.Data(e.getDate().substring(6), e.getDeaths()));
		}
		chart.getData().addAll(series1, series2);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(chart, 800, 600);;
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

	public LineChart getDailyGraph() {
		return chart;
	}
	
}
