package ch.makery.farming.view

import ch.makery.farming.MainApp
import ch.makery.farming.model.items.PlayerState
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label}
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import scalafx.Includes._

@sfxml
class GardenOverviewController(
  private var gardenGrid: GridPane,
  private var coinsLabel: Label,
  private var totalharvestplantLabel: Label) {

  private var playerState = new PlayerState()

  coinsLabel.setText("Coins: " + playerState.getCoins())
  totalharvestplantLabel.setText("Total Harvested Plants: " + playerState.getTotalHarvestedPlants())


  def returnHome(): Unit = {
    MainApp.showWelcome()
  }

  def getShop(): Unit = {
    MainApp.showShop()
  }

  def getInventory(): Unit = {
    MainApp.showInventory()
  }

  def handlePlantClick(event: ActionEvent): Unit = {
    val clickedButton = event.source.asInstanceOf[javafx.scene.control.Button]
    val plotIndex = clickedButton.id.value.stripPrefix("Grid").toInt

    if (playerState.isOccupied(plotIndex)) {
      showOccupiedMessage()
    } else {
      MainApp.showPlantCrop(plotIndex)
    }
  }

  private def showOccupiedMessage(): Unit = {
    val alert = new Alert(AlertType.Information){
    title = "Plot Occupied"
    headerText = "Plot Occupied"
    contentText = "This plot is already occupied. Please choose another plot"
    }
    alert.showAndWait()
  }
}

