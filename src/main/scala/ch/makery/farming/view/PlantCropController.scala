package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Crop, Wheat}
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ChoiceBox, Label}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml

@sfxml
class PlantCropController(
                          @FXML private var cropChoiceBox: ChoiceBox[String],
                          @FXML private var seedsLabel: Label,
                          @FXML private var descriptionLabel: Label,
                          @FXML private var plantButton: Button,
                          @FXML private var removePlantButton: Button) {

  private val crops: Map[String, Crop] = Map(
    "Carrot" -> new Carrot,
    "Wheat" -> new Wheat
  )
  private var isGridCellOccupied: Boolean = false
  private var currentPlantedCrop: Option[Crop] = None

  @FXML
  def initialize(): Unit = {
    populateChoiceBox()
    updateCropDetails()
    cropChoiceBox.setOnAction(_ => updateCropDetails())
  }

  def populateChoiceBox(): Unit = {
    val cropNames = FXCollections.observableArrayList("Carrot", "Wheat")
    cropChoiceBox.setItems(cropNames)
    cropChoiceBox.getSelectionModel.selectFirst()
    updateCropDetails()  // Show details of the first crop initially
  }

  def getDescription(cropName: String): Option[String] = {
    crops.get(cropName).map(_.getDescription)
  }

  def getAvailableSeeds(cropName: String): Option[Int] = {
    crops.get(cropName).map(_.getAvailableSeeds)
  }

  def updateCropDetails(): Unit = {
    val selectedCrop = crops.get(cropChoiceBox.getValue)
    selectedCrop.foreach { crop =>
      seedsLabel.setText(crop.getAvailableSeeds(selectedCrop))
      descriptionLabel.setText(crop.getDescription(selectedCrop))
    }
  }

  def handlePlantClick(): Unit = {
    // Retrieve the selected crop from the ComboBox using the selected name.
    val selectedCrop = crops.get(cropChoiceBox.getValue)

    // Check if the selected crop is defined and if there are sufficient seeds available.
    selectedCrop match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        // Deduct one seed from the available count and mark the current plant.
        crop.seedsAvailable -= 1
        currentPlantedCrop = Some(crop)

        // Show information alert indicating successful planting.
        showAlert(AlertType.Information, "Planting", Some(s"Successfully planted ${crop.name}!"), "The cell is now occupied by ${crop.name}.")
      case Some(crop) =>
        // Show warning alert when there are not enough seeds available.
        showAlert(AlertType.Warning, "Error", Some("Not enough seeds to plant!"), "Please check your seed inventory.")
      case None =>
        // Show an error alert when no crop is selected or found.
        showAlert(AlertType.Error, "Error", Some("Invalid selection"), "No valid crop selected.")
    }
  }


  def handleRemovePlantClick(): Unit = {
    if (!isGridCellOccupied) {
      showAlert(AlertType.Warning, "Error", Some("The cell is already empty!"), "No plant to remove.")
      return
    }

    val removeResult = currentPlantedCrop.get.removeCrop(100) // Assuming player always has enough coins for demo purposes
    isGridCellOccupied = false
    currentPlantedCrop = None
    showAlert(removeResult._1 match {
      case true => AlertType.Information
      case false => AlertType.Warning
    }, "Removing", Some(removeResult._2), "The plant has been removed.")
  }

  private def showAlert(alertType: AlertType, title: String, header: Option[String], content: String): Unit = {
    val alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(header.orNull)
    alert.setContentText(content)
    alert.showAndWait()
  }
}
