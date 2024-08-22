package ch.makery.farming.view

import javafx.fxml.FXML
import javafx.scene.control.{Alert, ButtonType, ChoiceBox, Label}
import ch.makery.farming.model.crops.{Carrot, Corn, Crop, Strawberry, Watermelon, Wheat}
import ch.makery.farming.model.items.PlayerState
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml

import javafx.collections.FXCollections

@sfxml
class PlantCropController{

  @FXML private var cropChoicebox: ChoiceBox[String] = _
  @FXML private var seedsLabel: Label = _
  @FXML private var descriptionLabel: Label = _

  private var playerState: PlayerState = new PlayerState()
  private var selectedCrop: Crop = _
  private var currentPlantedCrop: Crop = _


  @FXML
  def initialize(): Unit = {
    cropChoicebox.setItems(FXCollections.observableArrayList("Carrot", "Corn", "Strawberry", "Watermelon", "Wheat"))
    cropChoicebox.setValue("Select Crop")

    cropChoicebox.setOnAction(_ => updateCropDetails())
  }

  def updateCropDetails(): Unit = {
    val cropName = cropChoicebox.getValue
    selectedCrop = cropName match {
      case "Carrot" => new Carrot(seedsAvailable = 5)
      case "Wheat" => new Carrot(seedsAvailable = 5)
      case "Strawberry" => new Carrot(seedsAvailable = 2)
      case "Corn" => new Carrot(seedsAvailable = 3)
      case "Watermelon" => new Carrot(seedsAvailable = 1)
    }

    if (selectedCrop != null) {
      seedsLabel.setText(s"You have ${selectedCrop.getAvailableSeeds} seeds available for this plant")
      descriptionLabel.setText(selectedCrop.getDescription)
    }
  }

  @FXML
  def handlePlantClick(): Unit = {
    if (selectedCrop == null) {
      showAlert("Error", "No crop selected", "Please select a crop to plant.")
      return
    }

    if (selectedCrop.getAvailableSeeds <= 0) {
      showAlert("Error", "Not enough seeds", "You do not have enough seeds to plant this crop. Please check your intentory")
      return
    }

    if (currentPlantedCrop != null) {
      showAlert("Error", "Plot Occupied", "There is already a crop planted in this plot.")
      return
    }

    // Plant the crop
    if (selectedCrop.deductSeedsAvailable(1)) {
      playerState.occupyPlot(0) // Assume plot index 0 for simplicity, adapt as necessary
      currentPlantedCrop = selectedCrop
      showAlert("Success", "Crop Planted", s"${selectedCrop.getName} has been successfully planted.")
    }
  }

  @FXML
  def handleRemovePlantClick(): Unit = {
    if (currentPlantedCrop == null) {
      showAlert("Error", "No Plant to Remove", "There is no plant to remove from this plot.")
      return
    }

    // Remove the crop
    playerState.unoccupyPlot(0) // Assume plot index 0 for simplicity
    currentPlantedCrop = null
    showAlert("Success", "Crop Removed", "The crop has been successfully removed.")
  }

  private def showAlert(title: String, header: String, content: String): Unit = {
    val alert = new Alert(Alert.AlertType.INFORMATION)
    alert.setTitle(title)
    alert.setHeaderText(header)
    alert.setContentText(content)
    alert.showAndWait()
  }
}