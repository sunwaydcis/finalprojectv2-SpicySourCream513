package ch.makery.farming.view

import ch.makery.farming.MainApp
import ch.makery.farming.model.items.PlayerState
import javafx.fxml.FXML
import javafx.scene.control.{Alert, Button, Label}
import javafx.scene.layout.GridPane
import scalafx.scene.control.Alert.AlertType
import javafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class GardenOverviewController(
                                @FXML private var gardenGrid: GridPane,
                                @FXML private var coinsLabel: Label,
                                @FXML private var totalharvestplantLabel: Label ) {

  var plotStatus: Array[Boolean] = Array.fill(9)(false)
  private val playerState = new PlayerState()

  @FXML
  def initialize(): Unit = {
    for (i <- 0 until 9) {
      val button = new Button()
      button.setOnAction(_ => handlePlantClick(new ActionEvent(button, button)))
      gardenGrid.add(button, i % 3, i / 3)
    }
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
    coinsLabel.setText("Coins: " + playerState.getCoins())
    totalharvestplantLabel.setText("Total Harvested Plants: " + playerState.getTotalHarvestedPlants())
  }

  def handlePlantClick(event: ActionEvent): Unit = {
    val button = event.getSource.asInstanceOf[Button]
    val plotIndex = gardenGrid.getChildren.indexOf(button)

    if (isOccupied(plotIndex)) {
      showOccupiedMessage()
    } else {
      MainApp.showPlantCrop(plotIndex)
    }
    updateUI()
  }

  def isOccupied(plotIndex: Int): Boolean = {
    plotStatus(plotIndex)
  }

  def occupyPlot(plotIndex: Int): Unit = {
    plotStatus(plotIndex) = true
  }

  def unoccupyPlot(plotIndex: Int): Unit = {
    plotStatus(plotIndex) = false
  }

  private def showOccupiedMessage(): Unit = {
    val alert = new Alert(AlertType.Information)
    alert.setTitle("Plot Occupied")
    alert.setHeaderText(null)
    alert.setContentText("This plot is already occupied.")
    alert.showAndWait()
  }
}
