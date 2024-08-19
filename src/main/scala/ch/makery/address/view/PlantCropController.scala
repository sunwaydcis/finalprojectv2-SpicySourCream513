package ch.makery.address.view

import javafx.scene.control.{ComboBox, Button}
import scalafxml.core.macros.sfxml
import javafx.stage.Stage

@sfxml
class PlantCropController(private var cropComboBox: ComboBox[String],
                          private var plantButton: Button) {

  private var parentController: GardenOverviewController = _

  // Set the parent controller to communicate back
  def setParentController(controller: GardenOverviewController): Unit = {
    parentController = controller
  }

  def initialize(): Unit = {
    // Populate the ComboBox with available crops
    cropComboBox.getItems.addAll("Carrot", "Corn", "Pumpkin", "Tomato", "Wheat")
  }

  // Method to handle the plant action
  def plantCrop(): Unit = {
    val selectedCropName = cropComboBox.getValue

    // Example: Define crop data (you might pull this from a model instead)
    val selectedCrop = selectedCropName match {
      case "Carrot"  => Crop("Carrot", 5, 10)
      case "Corn"    => Crop("Corn", 7, 8)
      case "Pumpkin" => Crop("Pumpkin", 10, 5)
      case "Tomato"  => Crop("Tomato", 4, 12)
      case "Wheat"   => Crop("Wheat", 3, 15)
    }


    // Close the current window
    val stage = plantButton.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }
}

case class Crop(name: String, growthTime: Int, seedQuantity: Int)
