package application;

import javafx.animation.Animation; 
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Animates a collapsible sidebar
 * 
 * @author Kevin Yu
 */
public class BorderSlide extends VBox
{
  private double expandedSize;
  private Pos flapbarLocation;

  /**
   * Creates a sidebar panel in a BorderPane, containing an horizontal alignment
   * of the given nodes.
   */
  public BorderSlide(double expandedSize,
          final Button controlButton, Pos location, Node... nodes) {

      getStyleClass().add("sidebar");       
      setExpandedSize(expandedSize);
      setVisible(false);

      // Set location
      if (location == null) {
          flapbarLocation = Pos.TOP_CENTER; // Set default location
      }
      flapbarLocation = location;
     
      initPosition();        
      
      // Add nodes in the vbox
      getChildren().addAll(nodes);

      controlButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
              // Create an animation to hide the panel.
              final Animation hidePanel = new Transition() {
                  {
                      setCycleDuration(Duration.millis(250));
                  }

                  @Override
                  protected void interpolate(double frac) {
                      final double size = getExpandedSize() * (1.0 - frac);
                      translateByPos(size);
                  }
              };

              hidePanel.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent actionEvent) {
                      setVisible(false);
                  }
              });

              // Create an animation to show the panel.
              final Animation showPanel = new Transition() {
                  {
                      setCycleDuration(Duration.millis(250));
                  }

                  @Override
                  protected void interpolate(double frac) {
                      final double size = getExpandedSize() * frac;
                      translateByPos(size);
                  }
              };

              showPanel.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent actionEvent) {
                  }
              });

              if (showPanel.statusProperty().get() == Animation.Status.STOPPED
                      && hidePanel.statusProperty().get() == Animation.Status.STOPPED) {

                  if (isVisible()) {
                      hidePanel.play();

                  } else {
                      setVisible(true);
                      showPanel.play();
                  }
              }
          }
      });
  }

  /**
   * Initialize position orientation.
   */
  private void initPosition() {
      switch (flapbarLocation) {
          case TOP_LEFT:
              setPrefHeight(0);
              setMinHeight(0);
              break;
          case BOTTOM_LEFT:
              setPrefHeight(0);
              setMinHeight(0);
              break;
          case BASELINE_RIGHT:
              setPrefWidth(0);
              setMinWidth(0);
              break;
          case BASELINE_LEFT:
              setPrefWidth(0);
              setMinWidth(0);
              break;
      }
  }

  /**
   * Translate the VBox according to location Pos.
   *
   * @param size
   */
  private void translateByPos(double size) {
      switch (flapbarLocation) {
          case TOP_LEFT:
              setPrefHeight(size);
              setTranslateY(-getExpandedSize() + size);                
              break;
          case BOTTOM_LEFT:
              setPrefHeight(size);
              break;
          case BASELINE_RIGHT:
              setPrefWidth(size);
              break;
          case BASELINE_LEFT:
              setPrefWidth(size);
              break;
      }
  }

  /**
   * @return the expandedSize
   */
  public double getExpandedSize() {
      return expandedSize;
  }

  /**
   * @param expandedSize the expandedSize to set
   */
  public void setExpandedSize(double expandedSize) {
      this.expandedSize = expandedSize;
  }
 
}

