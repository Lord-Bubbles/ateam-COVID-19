package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class RealTimeGraph extends Application {

	
	 ScheduledExecutorService scheduledExecutorService;
	 final int WINDOW_SIZE = 10;
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
			
		primaryStage.setTitle("RealTime Data");
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time (Weeks)");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setAnimated(false); // axis animations are removed
        
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("RealTime Trends");
        lineChart.setAnimated(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");
        
        lineChart.getData().add(series);
        
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //  Input data to the chart
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss"); // display text
        
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        
        // lambda expression
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10
            Integer random = ThreadLocalRandom.current().nextInt(10);
            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();
                // put random number with current time
                series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), random));
                
                if (series.getData().size() > WINDOW_SIZE) {
                	series.getData().remove(0);
                }
            });
        }, 0, 1, TimeUnit.SECONDS);
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		scheduledExecutorService.shutdownNow();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
