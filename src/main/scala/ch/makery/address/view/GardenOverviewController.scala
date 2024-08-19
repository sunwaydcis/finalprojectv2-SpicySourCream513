package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml
import javafx.scene.control.{Button, Label, Alert}
import javafx.scene.image.{Image, ImageView}
import javafx.fxml.FXMLLoader
import javafx.scene.layout.GridPane
import javafx.stage.{Stage, StageStyle}
import javafx.{scene => jfxs}
import scalafx.scene.control.Alert.AlertType

@sfxml
class GardenOverviewController(private var gardenGrid: GridPane,
                               private var coinsLabel: Label,
                               private var harvestedPlantsLabel: Label
                              ){


  // An array to track whether each plot is occupied or planted
  private val plotStatus: Array[Option[String]] = Array.fill(16)(None)

  def initialize(): Unit = {
    updateUI()
  }

  def returnHome(): Unit = {
    MainApp.showWelcome()
  }

  def getShop(): Unit = {
    MainApp.showShop()
  }

  def getInventory(): Unit = {
    MainApp.showInventory()
  }

  def updateUI(): Unit = {
    coinsLabel.setText("Coins: " + getCoins())
    harvestedPlantsLabel.setText("Total Harvested Plants: " + getTotalHarvestedPlants())
  }

  private def getCoins(): Int = { 100 }

  private def getTotalHarvestedPlants(): Int = { 0 }

  // Method to handle when a plot is clicked
  def handlePlotClick(plotIndex: Int): Unit = {
    plotStatus(plotIndex) match {
      case None => MainApp.showPlantCrop(plotIndex)
      case Some(crop) => showOccupiedMessage(crop)
    }
  }

  // Show an alert if the plot is occupied
  private def showOccupiedMessage(crop: String): Unit = {
    val alert = new Alert(AlertType.Information)
    alert.setTitle("Plot Occupied")
    alert.setHeaderText(null)
    alert.setContentText(s"This plot is occupied by $crop.")
    alert.showAndWait()
  }

  // Method to plant a crop
  private def plantCrop(plotIndex: Int, crop: String): Unit = {
    plotStatus(plotIndex) = Some(crop)
    val button = gardenGrid.getChildren.get(plotIndex).asInstanceOf[Button]
    button.setGraphic(new ImageView(new Image(getClass.getResourceAsStream(s"images/$crop.png"))))
  }
}