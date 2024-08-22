package ch.makery.farming.view

import scalafx.scene.control.{Alert, ChoiceBox, Label}
import ch.makery.farming.model.crops.{Carrot, Corn, Crop, Strawberry, Watermelon, Wheat}
import ch.makery.farming.model.items.PlayerState
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml
import scalafx.collections.ObservableBuffer

@sfxml
class PlantCropController (
  private val plotIndex: Int,
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

    if (selectedCrop.getAvailableSeeds < 1) {
      showAlert("Error", "Not enough seeds", "You do not have enough seeds to plant this crop. Please check your inventory.")
      return
    }

    if (playerState.isOccupied(plotIndex)) {
      showAlert("Error", "Plot Occupied", "This plot is already occupied. Please choose another plot.")
      return
    }

    // Plant the crop
    selectedCrop.deductSeedsAvailable(1)
    playerState.occupyPlot(plotIndex)
    currentPlantedCrop = selectedCrop
    showAlert("Success", "Crop Planted", s"${selectedCrop.getName} has been successfully planted in plot $plotIndex.")
  }


  def handleRemovePlantClick() : Unit = {
    if (currentPlantedCrop == null) {
      showAlert("Error", "No Plant to Remove", "There is no plant to remove from this plot.")
      return
    }

    if (playerState.coins < 10) {
      playerState.showNotEnoughCoinMessage()
      return
    }

    //delete the crop
    playerState.deductCoins(10)
    currentPlantedCrop.removeCrop(playerState, plotIndex)
    currentPlantedCrop = null
    showAlert("Success", "Crop Removed", s"The crop has been removed from plot $plotIndex, costing 10 coins.")
  }


  private def showAlert(_title: String, _header: String, _content: String): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = _title
      headerText =  _header
      contentText = _content
    }
    alert.showAndWait()
  }
}