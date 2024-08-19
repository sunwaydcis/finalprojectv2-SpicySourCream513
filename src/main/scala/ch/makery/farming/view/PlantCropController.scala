package ch.makery.farming.view

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ComboBox, Label}
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert

case class Crop(name: String, seedsAvailable: Int, description: String)

class PlantCropController {

  // Sample data, you would replace this with your actual data source
  private val crops: List[Crop] = List(
    Crop("Tomato", 5, "A juicy red fruit, good for salads."),
    Crop("Carrot", 10, "A root vegetable, usually orange."),
    Crop("Wheat", 20, "A cereal grain used for bread."),
    Crop("Corn", 8, "A yellow vegetable used in many dishes.")
  )

  // JavaFX controls linked to the FXML file
  @FXML private var cropComboBox: ComboBox[String] = _
  @FXML private var seedsLabel: Label = _
  @FXML private var descriptionLabel: Label = _
  @FXML private var plantButton: Button = _
  @FXML private var removePlantButton: Button = _

  // This method is automatically called after the FXML file is loaded
  @FXML
  def initialize(): Unit = {
    // Convert crop names to an ObservableList and set it to the ComboBox
    val cropNames: ObservableList[String] = FXCollections.observableArrayList(crops.map(_.name): _*)
    cropComboBox.setItems(cropNames)

    // Add listener to ComboBox to update UI when a new crop is selected
    cropComboBox.setOnAction(_ => updateCropDetails())
  }

  // Updates the seed count and description based on the selected crop
  private def updateCropDetails(): Unit = {
    val selectedCropName = cropComboBox.getValue
    crops.find(_.name == selectedCropName) match {
      case Some(crop) =>
        seedsLabel.setText(s"You have ${crop.seedsAvailable} seeds for this plant")
        descriptionLabel.setText(crop.description)
      case None =>
        seedsLabel.setText("You have 0 seeds for this plant")
        descriptionLabel.setText("No description available")
    }
  }

  // Handle the plant button click
  @FXML
  def handlePlantClick(): Unit = {
    val selectedCropName = cropComboBox.getValue
    crops.find(_.name == selectedCropName) match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        // Logic to plant the crop, e.g., reduce the seed count, update the grid, etc.
        new Alert(AlertType.Information) {
          initOwner(plantButton.getScene.getWindow)
          title = "Planting"
          headerText = None
          contentText = s"Successfully planted $selectedCropName!"
        }.showAndWait()
      case Some(_) =>
        new Alert(AlertType.Warning) {
          initOwner(plantButton.getScene.getWindow)
          title = "Error"
          headerText = None
          contentText = s"Not enough seeds to plant $selectedCropName!"
        }.showAndWait()
      case None =>
        new Alert(AlertType.Warning) {
          initOwner(plantButton.getScene.getWindow)
          title = "Error"
          contentText = "No crop selected!"
        }.showAndWait()
    }
  }

  // Handle the remove plant button click
  @FXML
  def handleRemovePlantClick(): Unit = {
    // Logic to remove the crop, e.g., clear the plot in the grid
    new Alert(AlertType.Information) {
      initOwner(removePlantButton.getScene.getWindow)
      title = "Removing"
      contentText = "Successfully removed the plant!"
    }.showAndWait()
  }
}
