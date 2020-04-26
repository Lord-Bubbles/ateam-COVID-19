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

public class MonthlyLineGraph extends Application {

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;
	private static final String[] MONTHS = { "JAN", "FEB", "MAR", "APR" };
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart chart = new LineChart(xAxis, yAxis);

	public MonthlyLineGraph() {
		xAxis.setLabel("Month");
		chart.setTitle("COVID 19 MONTHLY TRENDS");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Number of Cases");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Number of Deaths");
		List<Event> weeklyCases = CsvReaderWriter.readCsv("us.csv");
		List<Event> monthlyCases = new ArrayList<Event>();
		for (int i = 0; i < 4; i++) {
			monthlyCases.add(new Event());
			monthlyCases.get(i).setDate(MONTHS[i]);
		}
		for (int i = 0; i < weeklyCases.size(); i++) {
			int j = Integer
					.parseInt(weeklyCases.get(i).getDate().substring(6, 7)); // get
																				// month
			monthlyCases.get(j - 1).setCases(weeklyCases.get(i).getCases());
			monthlyCases.get(j - 1).setDeaths(weeklyCases.get(i).getDeaths());
		}
		for (Event e : monthlyCases) {
			series1.getData().add(new XYChart.Data(e.getDate(), e.getCases()));
			series2.getData().add(new XYChart.Data(e.getDate(), e.getDeaths()));
		}
		chart.getData().addAll(series1, series2);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
        Scene scene = new Scene(chart, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public LineChart getMonthlyGraph() {
		return chart;
	}

}
