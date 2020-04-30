package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  
  public static final int WINDOW_WIDTH = 900;
  public static final int WINDOW_HEIGHT = 780;

  public void init() {
    // TODO: Maybe need this don't know yet
  }

  /**
   * Initializes the GUI
   * @param primaryStage the primary stage
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Image bg = new Image("/bg.jpg");
    ImageView bgImage = new ImageView();
    bgImage.setImage(bg);
    bgImage.setFitWidth(WINDOW_WIDTH);
    bgImage.setFitHeight(WINDOW_HEIGHT);
    bgImage.setSmooth(true);
    ImageView bg2Image = new ImageView();
    bg2Image.setImage(bg);
    bg2Image.setFitWidth(WINDOW_WIDTH);
    bg2Image.setFitHeight(WINDOW_HEIGHT);
    bg2Image.setSmooth(true);
    bg2Image.setX(WINDOW_WIDTH);
    
    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane root = new BorderPane();
    List<String> data = new ArrayList<>(); // List of countries for which we have data;
    for (String e : FxUtils.data.keySet()) {
      data.add(e);
    }

    //animates the background
    TranslateTransition tt = new TranslateTransition(Duration.millis(50000),
    		bgImage);
    tt.setFromX(0);
    tt.setToX(-1 * WINDOW_WIDTH);
    tt.setInterpolator(Interpolator.LINEAR);
    
    TranslateTransition tt2 = new TranslateTransition(Duration.millis(50000),
    		bg2Image);
    tt2.setFromX(0);
    tt2.setToX(-1 * WINDOW_WIDTH);
    tt2.setInterpolator(Interpolator.LINEAR);
    
    ParallelTransition pt = new ParallelTransition(tt, tt2);
    pt.setCycleCount(Animation.INDEFINITE);
    pt.play();
    
    root.getChildren().add(bgImage);
    root.getChildren().add(bg2Image);

    HBox box = new HBox();

    // Setting up the search field with auto-complete
    ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(data));

    FxUtils.autoCompleteComboBoxPlus(comboBox, (typedText, itemToCompare) -> 
    itemToCompare.toLowerCase().startsWith(typedText.toLowerCase()));
    comboBox.setMinWidth(comboBox.getWidth());
    comboBox.setMinHeight(comboBox.getHeight());

    box.getChildren().addAll(comboBox);
    box.setAlignment(Pos.CENTER);
    root.setCenter(box);
    Image exitImage = new Image("/exit.png", 16, 16, false, false);
    Button exit = new Button("", new ImageView(exitImage)); // Create exit button
    exit.setTooltip(new Tooltip("Exit")); // Tooltip for the exit button
    BorderPane.setAlignment(exit, Pos.BOTTOM_RIGHT);
    root.setBottom(exit); 
    
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Add the above elements and set the primary stage
    primaryStage.setTitle("COVID-19");
    primaryStage.setScene(mainScene);
    primaryStage.show();

    BorderPane location = new BorderPane();
    Image goBack = new Image("/return.png", 16, 16, false, false);
    Button returnButton = new Button("", new ImageView(goBack)); // Return button
    returnButton.setTooltip(new Tooltip("Return")); // Tooltip for the return button

    // Set up the secondary window
    VBox checkBox = new VBox(); // Checkbox for toggling which graph to show
    CheckBox realTime = new CheckBox("Real Time"); // Real-time graph
    realTime.setSelected(true);
    CheckBox monthly = new CheckBox("Monthly"); // Monthly graph
    CheckBox daily = new CheckBox("Daily"); // Daily graph
    checkBox.getChildren().addAll(realTime, monthly, daily);
    CheckBox death = new CheckBox("Confirmed deaths");
    death.setSelected(true);
    CheckBox cc = new CheckBox("Confirmed cases");
    cc.setSelected(true);
    checkBox.getChildren().addAll(death, cc);
    TitledPane menu = new TitledPane("Menu", checkBox);
    HBox top = new HBox();
    Label title = new Label(); // Title of data window
    top.getChildren().addAll(returnButton, title);
    location.setTop(top);
    location.setLeft(menu);
    MonthlyLineGraph month = new MonthlyLineGraph();	// can be used to get monthly graph
    DailyLineGraph days = new DailyLineGraph();		// can be used to get daily graph
    RealTimeGraph real = new RealTimeGraph();		// can be used to get a real time graph

    location.setCenter(real.getRealTimeGraph("China"));
    // location.setCenter(days.getDailyLineGraph("US"));
 // location.setCenter(days.getDailyLineGraph("United_Kingdom"));
 // location.setCenter(days.getDailyLineGraph("Italy"));
 // location.setCenter(days.getDailyLineGraph("France"));
 // location.setCenter(days.getDailyLineGraph("Germany"));
 // location.setCenter(days.getDailyLineGraph("Spain"));
 // location.setCenter(days.getDailyLineGraph("Iran"));
    Button exit2 = new Button("", new ImageView(exitImage)); // Create exit button
    exit2.setTooltip(new Tooltip("Exit")); // Tooltip for the exit button
    BorderPane.setAlignment(exit2, Pos.BOTTOM_RIGHT);
    location.setBottom(exit2);

    returnButton.setOnAction(e -> { // Return back to the main screen
      primaryStage.getScene().setRoot(root);
    });
    
    comboBox.setOnKeyPressed(e -> { // Make sure that scene changes only when the user confirms
                                    // their choice (ENTER) in the ComboBox
      String curSelected = FxUtils.getComboBoxValue(comboBox);
      if (e.getCode() == KeyCode.ENTER && data.contains(curSelected)) {
        primaryStage.getScene().setRoot(location);
        title.setText("Data about " + curSelected); // Update title of data window
      }
    });

    exit.setOnAction(e -> { // Close the application
      primaryStage.close();
    });

    realTime.setOnAction(e -> { // Set the actions of the realTime checkbox to show the real-time graph
      if (realTime.isSelected()) {
        monthly.setSelected(false);
        daily.setSelected(false);
        location.setCenter(real.getRealTimeGraph(FxUtils.getComboBoxValue(comboBox)));
      }
    });

    monthly.setOnAction(e -> { // Set the actions of the monthly checkbox to show the monthly line graph
      if (monthly.isSelected()) {
        realTime.setSelected(false);
        daily.setSelected(false);
        location.setCenter(month.getMonthlyLineGraph(FxUtils.getComboBoxValue(comboBox)));
      }
    });

    daily.setOnAction(e -> { // Set the actions of the daily checkbox to show the daily line graph
      if (daily.isSelected()) {
        monthly.setSelected(false);
        realTime.setSelected(false);
        location.setCenter(days.getDailyLineGraph(FxUtils.getComboBoxValue(comboBox)));
      }
    });

    death.setOnAction(e -> {
      if (death.isSelected()) {
        // Display data about death rate
      } else {
        // Don't display data about death rate
      } 
    });

    cc.setOnAction(e -> {
      if (cc.isSelected()) {
        // Display data about diffusion
      } else {
        // Don't display data about diffusion
      }
    });
  }
  
  public static void main(String[] args) {
    launch(args);
  }
 
}
