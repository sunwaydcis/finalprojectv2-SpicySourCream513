package ch.makery.farming.view

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ComboBox, Label}
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import scalafxml.core.macros.sfxml

@sfxml
class PlantCropController(private val crops: List[Crop] = Crop.defaultCrops(),
                          @FXML private var cropComboBox: ComboBox[String],
                          @FXML private var seedsLabel: Label,
                          @FXML private var descriptionLabel: Label,
                          @FXML private var plantButton: Button,
                          @FXML private var removePlantButton: Button) {

  // To track whether the grid cell is occupied
  private var isGridCellOccupied: Boolean = false
  private var currentPlantedCrop: Option[Crop] = None

  @FXML
  def initialize(): Unit = {
    val cropNames: ObservableList[String] = FXCollections.observableArrayList(crops.map(_.name): _*)
    cropComboBox.setItems(cropNames)
    cropComboBox.getSelectionModel.selectFirst() // Automatically select the first crop in the list
    updateCropDetails()
    cropComboBox.setOnAction(_ => updateCropDetails())
  }

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

  def handlePlantClick(): Unit = {
    if (isGridCellOccupied) {
      new Alert(AlertType.Warning) {
        initOwner(plantButton.getScene.getWindow)
        title = "Error"
        headerText = None
        contentText = s"The cell is already occupied by ${currentPlantedCrop.get.name}!"
      }.showAndWait()
      return
    }

    val selectedCropName = cropComboBox.getValue
    crops.find(_.name == selectedCropName) match {
      case Some(crop) if crop.seedsAvailable > 0 =>
        isGridCellOccupied = true
        currentPlantedCrop = Some(crop)
        // Logic to mark the grid cell as occupied with this crop
        new Alert(AlertType.Information) {
          initOwner(plantButton.getScene.getWindow)
          title = "Planting"
          headerText = None
          contentText = s"Successfully planted $selectedCropName! The cell is now occupied by $selectedCropName."
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

  def handleRemovePlantClick(): Unit = {
    if (!isGridCellOccupied) {
      new Alert(AlertType.Warning) {
        initOwner(removePlantButton.getScene.getWindow)
        title = "Error"
        headerText = None
        contentText = "The cell is already empty!"
      }.showAndWait()
      return
    }

    isGridCellOccupied = false
    currentPlantedCrop = None
    new Alert(AlertType.Information) {
      initOwner(removePlantButton.getScene.getWindow)
      title = "Removing"
      headerText = None
      contentText = "Successfully removed the plant! The cell is now empty."
    }.showAndWait()
  }
}

case class Crop(name: String, seedsAvailable: Int, description: String)

object Crop {
  def defaultCrops(): List[Crop] = List(
    Crop("Tomato", 5, "A juicy red fruit, good for salads."),
    Crop("Carrot", 10, "A root vegetable, usually orange."),
    Crop("Wheat", 20, "A cereal grain used for bread."),
    Crop("Corn", 8, "A yellow vegetable used in many dishes.")
  )
}
