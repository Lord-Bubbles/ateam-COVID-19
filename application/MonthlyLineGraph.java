package application;

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

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = new BorderPane();

		// Group root = new Group();
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		final LineChart chart = new LineChart(xAxis, yAxis);
		chart.setTitle("COVID 19 MONTHLY TRENDS");
		
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Number of Cases");
		series1.getData().add(new XYChart.Data("Jan", 200));
		series1.getData().add(new XYChart.Data("Feb", 1200));
		series1.getData().add(new XYChart.Data("Mar", 2700));
		series1.getData().add(new XYChart.Data("Apr", 10000));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Number of Deaths");
		series2.getData().add(new XYChart.Data("Jan", 33));
		series2.getData().add(new XYChart.Data("Feb", 100));
		series2.getData().add(new XYChart.Data("Mar", 245));
		series2.getData().add(new XYChart.Data("Apr", 500));
		
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Number of Recoveries");
		series3.getData().add(new XYChart.Data("Jan", 40));
		series3.getData().add(new XYChart.Data("Feb", 400));
		series3.getData().add(new XYChart.Data("Mar", 750));
		series3.getData().add(new XYChart.Data("Apr", 1600));
		
		Scene scene = new Scene(chart, 800, 600);
		chart.getData().addAll(series1, series2, series3);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
