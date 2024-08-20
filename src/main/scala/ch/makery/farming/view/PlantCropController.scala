package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Crop, Wheat}
import javafx.fxml.FXML
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import ch.makery.farming.view.GardenOverviewController

import javafx.scene.control.{Button, Label, MenuButton, MenuItem}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml

@sfxml
class PlantCropController(
                           @FXML private var cropMenuButton: MenuButton,
                           @FXML private var seedsLabel: Label,
                           @FXML private var descriptionLabel: Label,
                           @FXML private var plantButton: Button,
                           @FXML private var removePlantButton: Button) {

  private val crops: List[Crop] = List(new Carrot(), new Wheat())
  private var currentPlantedCrop: Option[Crop] = None

  @FXML
  def initialize(): Unit = {
    cropMenuButton.setText("Select Crop")  // Set default text for the MenuButton
    populateMenuButton()
  }

  def populateMenuButton(): Unit = {
    crops.foreach { crop =>
      val menuItem = new MenuItem(crop.name)
      menuItem.setOnAction(_ => {
        updateCropDetails(crop)
        cropMenuButton.setText(crop.name)
      })
      cropMenuButton.getItems.add(menuItem)
    }
  }

  def updateCropDetails(selectedCrop: Crop): Unit = {
    seedsLabel.setText(s"${selectedCrop.seedsAvailable} seeds available.")
    descriptionLabel.setText(selectedCrop.getDescription())
    currentPlantedCrop = Some(selectedCrop)
  }

  def handlePlantClick(): Unit = {
    currentPlantedCrop match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        crop.seedsAvailable -= 1
        showAlert(AlertType.Information, "Planting", Some(s"Successfully planted ${crop.name}!"), s"Seeds left: ${crop.seedsAvailable}")
        updateUIAfterPlanting()
      case Some(_) =>
        showAlert(AlertType.Warning, "Error", Some("Not enough seeds to plant!"), "Please check your seed inventory.")
      case None =>
        showAlert(AlertType.Error, "Error", Some("No crop selected"), "Please select a crop from the menu.")
    }
  }

  def updateUIAfterPlanting(): Unit = {
  }

  def handleRemovePlantClick(): Unit = {
    if (currentPlantedCrop.isEmpty) {
      showAlert(AlertType.Warning, "Error", Some("The cell is already empty!"), "No plant to remove.")
      return
    }

    currentPlantedCrop.foreach { crop =>
      val removeResult = crop.removeCrop(100) // Assuming player always has enough coins
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
