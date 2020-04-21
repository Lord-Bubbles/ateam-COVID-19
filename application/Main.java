package application;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane root = new BorderPane();
    List<String> data = new ArrayList<>(); // List of countries for which we have data;

    // Adding sample data for testing
    // TODO: Read data into the data list from csv file
    data.add("Spain");
    data.add("France");
    data.add("United States of America (USA)");
    data.add("Japan");
    data.add("China");

    HBox box = new HBox();
    Button globalStats = new Button();

    // Setting up the search field with auto-complete
    ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(data));
    AutoComplete.autoCompleteComboBoxPlus(comboBox, (typedText, itemToCompare) -> 
	itemToCompare.toLowerCase().startsWith(typedText.toLowerCase()));
    comboBox.setMinWidth(comboBox.getWidth());
    comboBox.setMinHeight(comboBox.getHeight());


    box.getChildren().addAll(globalStats, comboBox);
    root.setTop(box);
    root.setCenter(new HBox());
    Image exitImage = new Image("/exit.png", 16, 16, false, false);
    Button exit = new Button("", new ImageView(exitImage)); // Create exit button
    exit.setTooltip(new Tooltip("Exit")); // Tooltip for the exit button
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

    VBox checkBox = new VBox();
    CheckBox diffusion = new CheckBox("Diffusion rate"); // Checkbox for showing data
    diffusion.setSelected(true);
    CheckBox death = new CheckBox("Death rate");
    death.setSelected(true);
    CheckBox recovery = new CheckBox("Recovery rate");
    recovery.setSelected(true);
    checkBox.getChildren().addAll(diffusion, death, recovery);
    TitledPane menu = new TitledPane("Menu", checkBox);
    HBox top = new HBox();
    Label title = new Label(); // Title of data window
    top.getChildren().addAll(title);
    location.setTop(top);
    location.setLeft(menu);
    MonthlyLineGraph month = new MonthlyLineGraph();
    location.setCenter(month.getMonthlyGraph());
    location.setBottom(returnButton);

    returnButton.setOnAction(e -> { // Return back to the main screen
      primaryStage.getScene().setRoot(root);
    });
    
    comboBox.setOnKeyPressed(e -> { // Make sure that scene changes only when the user confirms
                                    // their choice (ENTER) in the ComboBox
      String curSelected = AutoComplete.getComboBoxValue(comboBox);
      if (e.getCode() == KeyCode.ENTER && data.contains(curSelected)) {
        primaryStage.getScene().setRoot(location);
        title.setText("Data about " + curSelected); // Update title of data window
      }
    });

    exit.setOnAction(e -> { // Close the application
      primaryStage.close();
    });

    death.setOnAction(e -> {
      if (death.isSelected()) {
        // Display data about death rate
      } else {
        // Don't display data about death rate
      } 
    });

    recovery.setOnAction(e -> {
      if (recovery.isSelected()) {
        // Display data about recovery rate
      } else {
        // Don't display data about recovery rate
      }
    });

    diffusion.setOnAction(e -> {
      if (diffusion.isSelected()) {
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

/**
 * Class for making Auto-Complete Combo-Boxes for use in the GUI
 * @author Andrew Li
 */
class AutoComplete {

  /**
   * Defines the method by which we determine how items go into the ComboBox list
   */
  public interface AutoCompleteComparator<T> {
    /**
     * Determines whether the typed text can be matched to any data values in the list
     * @param typedText the typed text to be matched
     * @param objectToCompare the data value with which the typed text will be compared
     * @return true if objectToCompare contains typedText, false otherwise
     */
    boolean matches(String typedText, T objectToCompare);
  }

  /**
   * Sets up the ComboBox to have an auto-complete feature, using the given Comparator to 
   * determine how to order the items within the auto-complete box
   * @param comboBox the ComboBox to implement the auto-complete feature in
   * @param comparatorMethod the method by which to order the items in the box
   */
  public static <T> void autoCompleteComboBoxPlus(ComboBox<T> comboBox, 
      AutoCompleteComparator<T> comparatorMethod) {
    ObservableList<T> data = comboBox.getItems();

    comboBox.setEditable(true);
    comboBox.getEditor().focusedProperty().addListener(observable -> {
      if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
        comboBox.getEditor().setText(null);
      }
    });
    comboBox.addEventHandler(KeyEvent.KEY_PRESSED, t -> comboBox.hide()); // Hide the ComboBox when
                                                                          // user is typing
    comboBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

      private boolean moveCaretToPos = false;
      private int caretPos; // Position of cursor

      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
	  caretPos = -1;
	  if (comboBox.getEditor().getText() != null) {
	    moveCaret(comboBox.getEditor().getText().length());
	  }
	  return;
	} else if (event.getCode() == KeyCode.DOWN) {
	  if (!comboBox.isShowing()) {
	    comboBox.show();
          }
	  caretPos = -1;
	  if (comboBox.getEditor().getText() != null) {
	    moveCaret(comboBox.getEditor().getText().length());
	  }
	  return;
	} else if (event.getCode() == KeyCode.BACK_SPACE) {
	  if (comboBox.getEditor().getText() != null) {
	    moveCaretToPos = true;
	    caretPos = comboBox.getEditor().getCaretPosition();
	  }
	} else if (event.getCode() == KeyCode.DELETE) {
	  if (comboBox.getEditor().getText() != null) {
	    moveCaretToPos = true;
	    caretPos = comboBox.getEditor().getCaretPosition();
	  }
	} else if (event.getCode() == KeyCode.ENTER) {
	  return;
	}

	if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT 
            || event.getCode().equals(KeyCode.SHIFT)
	    || event.getCode().equals(KeyCode.CONTROL) || event.isControlDown()
	    || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
	    || event.getCode() == KeyCode.TAB) {
	  return;
	}

	ObservableList<T> list = FXCollections.observableArrayList();
	for (T aData : data) {
	  if (aData != null && comboBox.getEditor().getText() != null && 
              comparatorMethod.matches(comboBox.getEditor().getText(), aData)) {
	    list.add(aData);
	  }
	}
	String t = "";
	if (comboBox.getEditor().getText() != null) {
	  t = comboBox.getEditor().getText();
	}

	comboBox.setItems(list);
	comboBox.getEditor().setText(t);
	if (!moveCaretToPos) {
	  caretPos = -1;
	}
	moveCaret(t.length());
	if (!list.isEmpty()) {
	  comboBox.show();
	}
      }

      /**
       * Moves the caret depending on the text in the field
       * @param textLength the length of the text in the field
       * */
      private void moveCaret(int textLength) {
	if (caretPos == -1) {
	  comboBox.getEditor().positionCaret(textLength);
	} else {
	  comboBox.getEditor().positionCaret(caretPos);
	}
	moveCaretToPos = false;
      }
    });
  }

  /**
   * Gets the currently selected value in the given ComboBox
   * @param comboBox the comboBox to get the currently selected value out of
   * @return the currently selected value in the given ComboBox, or null if none are selected
   */
  public static <T> T getComboBoxValue(ComboBox<T> comboBox) {
    if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
      return null;
    } else {
      return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
    }
  }

}
