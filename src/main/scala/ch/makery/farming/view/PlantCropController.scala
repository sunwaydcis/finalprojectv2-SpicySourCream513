package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Wheat, Crop}
import javafx.collections.FXCollections
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

  private val cropNames: List[String] = List("Carrot", "Wheat")
  private val crops: List[Crop] = List(new Carrot(), new Wheat())

  private var isGridCellOccupied: Boolean = false
  private var currentPlantedCrop: Option[Crop] = None

  @FXML
  def initialize(): Unit = {
    populateChoiceBox()
    cropChoiceBox.setOnAction(_ => updateCropDetails())
  }

  def populateChoiceBox(): Unit = {
    val observableCropNames = FXCollections.observableArrayList(cropNames: _*)
    cropChoiceBox.setItems(observableCropNames)
    if (!observableCropNames.isEmpty) {
      cropChoiceBox.getSelectionModel.selectFirst()
      updateCropDetails()  // Ensure that the details are updated immediately upon initialization
    }
  }

  def updateCropDetails(): Unit = {
    val selectedName = cropChoiceBox.getValue
    if (selectedName != null) {
      crops.find(_.name == selectedName).foreach { crop =>
        seedsLabel.setText(s"${crop.seedsAvailable} seeds available.")
        descriptionLabel.setText(crop.getDescription())
      }
    }
  }

  def handlePlantClick(): Unit = {
    val selectedName = cropChoiceBox.getValue
    val selectedCrop = crops.find(_.name == selectedName)

    selectedCrop match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        crop.seedsAvailable -= 1
        isGridCellOccupied = true
        currentPlantedCrop = Some(crop)
        showAlert(AlertType.Information, "Planting", Some(s"Successfully planted ${crop.name}!"), s"Seeds left: ${crop.seedsAvailable}")
        updateUIAfterPlanting()
      case Some(crop) =>
        showAlert(AlertType.Warning, "Error", Some("Not enough seeds to plant!"), "Please check your seed inventory.")
      case None =>
        showAlert(AlertType.Error, "Error", Some("Invalid selection"), "No valid crop selected.")
    }
  }

  def updateUIAfterPlanting(): Unit = {
    cropChoiceBox.getSelectionModel.clearSelection()
    seedsLabel.setText("")
    descriptionLabel.setText("Select a crop to see details.")
  }


  def handleRemovePlantClick(): Unit = {
    if (!isGridCellOccupied) {
      showAlert(AlertType.Warning, "Error", Some("The cell is already empty!"), "No plant to remove.")
      return
    }

    currentPlantedCrop.foreach { crop =>
      val removeResult = crop.removeCrop(100) // Assuming player always has enough coins
      isGridCellOccupied = false
      currentPlantedCrop = None
      showAlert(removeResult._1 match {
        case true => AlertType.Information
        case false => AlertType.Warning
      }, "Removing", Some(removeResult._2), "The plant has been removed.")
    }
  }

  private def showAlert(alertType: AlertType, title: String, header: Option[String], content: String): Unit = {
    val alert = new Alert(alertType)
    alert.setTitle(title)
    alert.setHeaderText(header.orNull)
    alert.setContentText(content)
    alert.showAndWait()
  }
}
