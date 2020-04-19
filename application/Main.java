package application;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    TextField locationField = new TextField("Search here for specific places");
    locationField.setAlignment(Pos.CENTER);
    locationField.setPrefColumnCount(50);
    box.getChildren().addAll(globalStats,  locationField);
    root.setTop(box);
    root.setCenter(new HBox());
    Button exit = new Button("exit");
    exit.setOnAction(e -> {
      primaryStage.close();
    });
    root.setBottom(exit); 

    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Add the stuff and set the primary stage
    primaryStage.setTitle("COVID-19");
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

