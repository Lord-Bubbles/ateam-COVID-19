package application;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import java.util.Map;
import java.util.List;

/**
 * Class for making Auto-Complete Combo-Boxes for use in the GUI
 * @author Andrew Li
 */
public class FxUtils {

  @SuppressWarnings("unchecked")
  public static final Map<String, List<Event>> data = CsvReaderWriter.readCsv("confirmedglobal.csv", "deathsglobal.csv");

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
  public static void autoCompleteComboBoxPlus(ComboBox<String> comboBox, 
      AutoCompleteComparator<String> comparatorMethod) {
    ObservableList<String> data = comboBox.getItems();

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
        if (event.getCode() == KeyCode.UP) { // Up arrow pressed
	  caretPos = -1;
	  if (comboBox.getEditor().getText() != null) {
	    moveCaret(comboBox.getEditor().getText().length());
	  }
	  return;
	} else if (event.getCode() == KeyCode.DOWN) { // Down arrow pressed
	  if (!comboBox.isShowing()) {
	    comboBox.show();
          }
	  caretPos = -1;
	  if (comboBox.getEditor().getText() != null) {
	    moveCaret(comboBox.getEditor().getText().length());
	  }
	  return;
	} else if (event.getCode() == KeyCode.BACK_SPACE) { // Back-space pressed
	  if (comboBox.getEditor().getText() != null) {
	    moveCaretToPos = true;
	    caretPos = comboBox.getEditor().getCaretPosition();
	  }
	} else if (event.getCode() == KeyCode.DELETE) { // Delete button pressed
	  if (comboBox.getEditor().getText() != null) {
	    moveCaretToPos = true;
	    caretPos = comboBox.getEditor().getCaretPosition();
	  }
	} else if (event.getCode() == KeyCode.ENTER) { // Enter pressed
	  return;
	}

	if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT 
            || event.getCode().equals(KeyCode.SHIFT)
	    || event.getCode().equals(KeyCode.CONTROL) || event.isControlDown()
	    || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
	    || event.getCode() == KeyCode.TAB) {
	  return;
	}

	ObservableList<String> list = FXCollections.observableArrayList();
	for (String aData : data) { // Auto-complete repopulation of list
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
	moveCaret(t.length()); // Move cursor to correct position
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
