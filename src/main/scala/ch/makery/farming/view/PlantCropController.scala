package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Crop, Wheat}
import ch.makery.farming.model.items.PlayerState
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, MenuButton, MenuItem}
import scalafx.event.ActionEvent
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
  private var playerState: PlayerState = _
  private var plotIndex: Int = -1 // Initialize with an invalid index


  def initializeController(state: PlayerState, index: Int): Unit = {
    playerState = state
    plotIndex = index
  }

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
    descriptionLabel.setText(selectedCrop.getDescription.toString())
    currentPlantedCrop = Some(selectedCrop)
  }

  @FXML
  def handlePlantClick(event: ActionEvent): Unit = {
    currentPlantedCrop match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        if (!playerState.isOccupied(plotIndex)) {
          crop.seedsAvailable -= 1
          playerState.occupyPlot(plotIndex)
          currentPlantedCrop = Some(crop)
          showAlert(AlertType.Information, "Planting", Some(s"Successfully planted ${crop.name}!"), s"Seeds left: ${crop.seedsAvailable}")
        } else {
          showAlert(AlertType.Warning, "Error", Some("This plot is already occupied!"), "Please select another plot.")
        }
      case Some(_) =>
        showAlert(AlertType.Warning, "Error", Some("Not enough seeds to plant!"), "Please check your seed inventory.")
      case None =>
        showAlert(AlertType.Error, "Error", Some("No crop selected"), "Please select a crop from the menu.")
    }
  }

  @FXML
  def handleRemovePlantClick(event: ActionEvent): Unit = {
    if (playerState != null && plotIndex >= 0) {
      if (playerState.isOccupied(plotIndex)) {
        playerState.unoccupyPlot(plotIndex)
        currentPlantedCrop = None
        showAlert(AlertType.Information, "Removing", Some("Plant removed successfully!"), "The plot is now empty.")
      } else {
        showAlert(AlertType.Warning, "Error", Some("The cell is already empty!"), "No plant to remove.")
      }
    } else {
      showAlert(AlertType.Error, "Error", Some("Invalid plot index or player state!"), "Please try again.")
    }
  }


  private def showAlert(alertType: AlertType, title: String, header: Option[String], content: String): Unit = {
    val alert = new Alert(alertType)
    alert.setTitle(title)
  }
  }
