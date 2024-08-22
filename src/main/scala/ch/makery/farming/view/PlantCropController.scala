package ch.makery.farming.view

import scalafx.scene.control.{Alert, ButtonType, ChoiceBox, Label}
import ch.makery.farming.model.crops.{Carrot, Corn, Crop, Strawberry, Watermelon, Wheat}
import ch.makery.farming.model.items.PlayerState
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml
import scalafx.Includes._

import scalafx.collections.ObservableBuffer

@sfxml
class PlantCropController (
  private var cropChoicebox: ChoiceBox[String],
  private var seedsLabel: Label,
  private var descriptionLabel: Label) {

  private var playerState: PlayerState = new PlayerState()
  private var selectedCrop: Crop = _
  private var currentPlantedCrop: Crop = _

    cropChoicebox.items = ObservableBuffer("Carrot", "Corn", "Strawberry", "Watermelon", "Wheat")

    cropChoicebox.setOnAction(_ => updateCropDetails())

  def updateCropDetails(): Unit = {
    val cropName = cropChoicebox.getValue
    selectedCrop = cropName match {
      case "Carrot" => new Carrot()
      case "Wheat" => new Wheat()
      case "Strawberry" => new Strawberry()
      case "Corn" => new Corn()
      case "Watermelon" => new Watermelon()
      case _ => null
    }

    if (selectedCrop != null) {
      seedsLabel.setText(s"You have ${selectedCrop.getAvailableSeeds} seeds available for this plant")
      descriptionLabel.setText(selectedCrop.getDescription)
    }
  }


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

  private def showAlert(_title: String, _header: String, content: String): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = _title
      headerText =  _header
      contentText = content
    }
    alert.showAndWait()
  }
}