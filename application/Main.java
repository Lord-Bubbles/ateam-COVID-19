package application;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  
  public static final int WINDOW_WIDTH = 400;
  public static final int WINDOW_HEIGHT = 400;

  public void init() {
    // Maybe need this don't know yet
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane root = new BorderPane();

    HBox box = new HBox();
    Button globalStats = new Button();
    TextField locationField = new TextField();
    locationField.setPromptText("Input location here");
    locationField.setAlignment(Pos.CENTER);
    locationField.setPrefColumnCount(50);
    box.getChildren().addAll(globalStats, locationField);
    root.setTop(box);
    root.setCenter(new HBox());
    Image exitImage = new Image("/exit.png", 16, 16, false, false);
    Button exit = new Button("", new ImageView(exitImage));  
    root.setBottom(exit); 

    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Add the stuff and set the primary stage
    primaryStage.setTitle("COVID-19");
    primaryStage.setScene(mainScene);
    primaryStage.show();

    BorderPane location = new BorderPane();
    Image goBack = new Image("/return.png", 16, 16, false, false);
    Button returnButton = new Button("", new ImageView(goBack));
    VBox checkBox = new VBox();
    CheckBox diffusion = new CheckBox("Diffusion rate");
    CheckBox death = new CheckBox("Death rate");
    CheckBox recovery = new CheckBox("Recovery rate");
    checkBox.getChildren().addAll(diffusion, death, recovery);
    TitledPane menu = new TitledPane("Menu", checkBox);
    HBox top = new HBox();
    Label title = new Label("Data about");
    top.getChildren().addAll(title);
    location.setTop(top);
    location.setLeft(menu);
    location.setBottom(returnButton);
    Scene secondary = new Scene(location, WINDOW_WIDTH, WINDOW_HEIGHT);

    returnButton.setOnAction(e -> {
      primaryStage.setScene(mainScene);
    });

    locationField.setOnAction(e -> {
      primaryStage.setScene(secondary);
      title.setText("Data about "+ locationField.getCharacters().toString());
    });

    exit.setOnAction(e -> {
      primaryStage.close();
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}

