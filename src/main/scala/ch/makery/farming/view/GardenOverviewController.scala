package ch.makery.farming.view

import ch.makery.farming.MainApp
import scalafxml.core.macros.sfxml
import javafx.scene.control.{Button, Label, Alert}
import javafx.scene.layout.GridPane
import scalafx.scene.control.Alert.AlertType
import javafx.event.ActionEvent

@sfxml
class GardenOverviewController(private val gardenGrid: GridPane,
                               private val coinsLabel: Label,
                               private val harvestedPlantsLabel: Label) {

  // An array to track whether each plot is occupied or planted
  private val plotStatus: Array[Option[String]] = Array.fill(9)(None) // Assuming a 3x3 grid

  def initialize(): Unit = {
    // Bind the buttons in the grid to the handlePlantClick function
    for (i <- 0 until 9) {
      val button = new Button()
      button.setOnAction(_ => handlePlantClick(new ActionEvent(button, button))) // Correctly handle the ActionEvent
      gardenGrid.add(button, i % 3, i / 3) // Adding button to the grid
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
    coinsLabel.setText("Coins: " + getCoins())
    harvestedPlantsLabel.setText("Total Harvested Plants: " + getTotalHarvestedPlants())
  }

  private def getCoins(): Int = { 100 }

  private def getTotalHarvestedPlants(): Int = { 0 }

  // Method to handle when a plot is clicked
  def handlePlantClick(event: ActionEvent): Unit = {
    val button = event.getSource.asInstanceOf[Button]
    val plotIndex = gardenGrid.getChildren.indexOf(button)
    plotStatus(plotIndex) match {
      case None =>
        MainApp.showPlantCrop(plotIndex)
        plotStatus(plotIndex) = Some("Planted")
        updateUI()
      case Some(_) =>
        showOccupiedMessage()
    }
  }

  // Show an alert if the plot is occupied
  private def showOccupiedMessage(): Unit = {
    val alert = new Alert(AlertType.Information)
    alert.setTitle("Plot Occupied")
    alert.setHeaderText(null)
    alert.setContentText("This plot is already occupied.")
    alert.showAndWait()
  }
}
