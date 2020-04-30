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

public class WeeklyLineGraph extends Application {

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = new BorderPane();

		// Group root = new Group();
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Week");
		final LineChart chart = new LineChart(xAxis, yAxis);
		chart.setTitle("COVID 19 WEEKLY TRENDS");
		
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Number of Cases");
		series1.getData().add(new XYChart.Data("Jan-Wk-1", 200));
		series1.getData().add(new XYChart.Data("Jan-Wk-2", 300));
		series1.getData().add(new XYChart.Data("Jan-Wk-3", 400));
		series1.getData().add(new XYChart.Data("Jan-Wk-4", 500));
		series1.getData().add(new XYChart.Data("Jan-Wk-5", 550));
		series1.getData().add(new XYChart.Data("Jan-Wk-6", 600));
		series1.getData().add(new XYChart.Data("Feb-Wk-1", 650));
		series1.getData().add(new XYChart.Data("Feb-Wk-2", 800));
		series1.getData().add(new XYChart.Data("Feb-Wk-3", 820));
		series1.getData().add(new XYChart.Data("Feb-Wk-4", 1200));
		series1.getData().add(new XYChart.Data("Mar-Wk-1", 1600));
		series1.getData().add(new XYChart.Data("Mar-Wk-2", 2000));
		series1.getData().add(new XYChart.Data("Mar-Wk-3", 2800));
		series1.getData().add(new XYChart.Data("Mar-Wk-4", 2900));
		series1.getData().add(new XYChart.Data("Mar-Wk-5", 5000));
		series1.getData().add(new XYChart.Data("Apr-Wk-1", 5100));
		series1.getData().add(new XYChart.Data("Apr-Wk-2", 7000));
		series1.getData().add(new XYChart.Data("Apr-Wk-3", 10000));
		series1.getData().add(new XYChart.Data("Apr-Wk-4", 10500));
		series1.getData().add(new XYChart.Data("Apr-Wk-5", 30000));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Number of Deaths");
		series2.getData().add(new XYChart.Data("Jan-Wk-1", 50));
		series2.getData().add(new XYChart.Data("Jan-Wk-2", 100));
		series2.getData().add(new XYChart.Data("Jan-Wk-3", 300));
		series2.getData().add(new XYChart.Data("Jan-Wk-4", 500));
		series2.getData().add(new XYChart.Data("Jan-Wk-5", 550));
		series2.getData().add(new XYChart.Data("Jan-Wk-6", 560));
		series2.getData().add(new XYChart.Data("Feb-Wk-1", 570));
		series2.getData().add(new XYChart.Data("Feb-Wk-2", 610));
		series2.getData().add(new XYChart.Data("Feb-Wk-3", 630));
		series2.getData().add(new XYChart.Data("Feb-Wk-4", 650));
		series2.getData().add(new XYChart.Data("Mar-Wk-1", 800));
		series2.getData().add(new XYChart.Data("Mar-Wk-2", 1000));
		series2.getData().add(new XYChart.Data("Mar-Wk-3", 1020));
		series2.getData().add(new XYChart.Data("Mar-Wk-4", 1070));
		series2.getData().add(new XYChart.Data("Mar-Wk-5", 2000));
		series2.getData().add(new XYChart.Data("Apr-Wk-1", 2010));
		series2.getData().add(new XYChart.Data("Apr-Wk-2", 2200));
		series2.getData().add(new XYChart.Data("Apr-Wk-3", 3000));
		series2.getData().add(new XYChart.Data("Apr-Wk-4", 3500));
		series2.getData().add(new XYChart.Data("Apr-Wk-5", 4000));
		
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Number of Recoveries");
		series3.getData().add(new XYChart.Data("Jan-Wk-1", 180));
		series3.getData().add(new XYChart.Data("Jan-Wk-2", 190));
		series3.getData().add(new XYChart.Data("Jan-Wk-3", 200));
		series3.getData().add(new XYChart.Data("Jan-Wk-4", 350));
		series3.getData().add(new XYChart.Data("Jan-Wk-5", 400));
		series3.getData().add(new XYChart.Data("Jan-Wk-6", 450));
		series3.getData().add(new XYChart.Data("Feb-Wk-1", 500));
		series3.getData().add(new XYChart.Data("Feb-Wk-2", 530));
		series3.getData().add(new XYChart.Data("Feb-Wk-3", 550));
		series3.getData().add(new XYChart.Data("Feb-Wk-4", 700));
		series3.getData().add(new XYChart.Data("Mar-Wk-1", 720));
		series3.getData().add(new XYChart.Data("Mar-Wk-2", 750));
		series3.getData().add(new XYChart.Data("Mar-Wk-3", 800));
		series3.getData().add(new XYChart.Data("Mar-Wk-4", 810));
		series3.getData().add(new XYChart.Data("Mar-Wk-5", 840));
		series3.getData().add(new XYChart.Data("Apr-Wk-1", 900));
		series3.getData().add(new XYChart.Data("Apr-Wk-2", 910));
		series3.getData().add(new XYChart.Data("Apr-Wk-3", 1000));
		series3.getData().add(new XYChart.Data("Apr-Wk-4", 1400));
		series3.getData().add(new XYChart.Data("Apr-Wk-5", 1600));
		
		Scene scene = new Scene(chart, 800, 600);
		chart.getData().addAll(series1, series2, series3);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
