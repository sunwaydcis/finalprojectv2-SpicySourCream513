package ch.makery.farming.view

import ch.makery.farming.model.crops.{Carrot, Crop, Wheat}
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
  var plotStatus: Array[Boolean] = _

  // Method to set plotStatus from outside
  def setPlotStatus(status: Array[Boolean]): Unit = {
    plotStatus = status
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
    descriptionLabel.setText(selectedCrop.getDescription())
    currentPlantedCrop = Some(selectedCrop)
  }

  @FXML
  def handlePlantClick(event: ActionEvent): Unit = {
    currentPlantedCrop match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        val plotIndex = getPlotIndex(event)  // Assuming you have a way to get the plot index from the event
        if (!plotStatus(plotIndex)) { // Check if the plot is not already occupied
          crop.seedsAvailable -= 1
          plotStatus(plotIndex) = true  // Mark plot as occupied
          currentPlantedCrop = Some(crop) // Update current planted crop
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

  private def getPlotIndex(event: ActionEvent): Int = {
    // Logic to determine the plot index based on the event or button clicked
    // This will depend on how you're managing the grid and button clicks
    0  // Placeholder
  }

  @FXML
  def handleRemovePlantClick(event: ActionEvent): Unit = {
    val plotIndex = getPlotIndex(event) // Get the plot index based on the event

    // Ensure plotStatus is not null and plotIndex is valid
    if (plotStatus != null && plotIndex >= 0 && plotIndex < plotStatus.length) {
      if (plotStatus(plotIndex)) { // If the plot is occupied
        plotStatus(plotIndex) = false  // Mark plot as unoccupied
        currentPlantedCrop = None      // Remove the planted crop
        showAlert(AlertType.Information, "Removing", Some("Plant removed successfully!"), "The plot is now empty.")
      } else {
        showAlert(AlertType.Warning, "Error", Some("The cell is already empty!"), "No plant to remove.")
      }
    } else {
      // Handle the case where plotStatus is null or plotIndex is invalid
      showAlert(AlertType.Error, "Error", Some("Invalid plot status or index!"), "Please try again.")
    }
  }


  private def showAlert(alertType: AlertType, title: String, header: Option[String], content: String): Unit = {
    val alert = new Alert(alertType)
    alert.setTitle(title)
  }
  }
