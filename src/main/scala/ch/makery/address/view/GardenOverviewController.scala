package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml
import javafx.scene.control.{Button, Label}
import javafx.fxml.FXMLLoader
import javafx.scene.layout.GridPane
import javafx.stage.{Stage, StageStyle}
import javafx.{scene => jfxs}

@sfxml
class GardenOverviewController(private var gardenGrid: GridPane,
                               private var coinsLabel: Label,
                               private var harvestedPlantsLabel: Label
                              ){


  def initialize(): Unit = {
    updateUI;
  }
  def returnHome(): Unit = {
    MainApp.showWelcome()
  }
/*
  def getShop(): Unit = {
    showPopUp("view/Shop.fxml", "Shop")
  }

  def getInventory(): Unit = {
    showPopUp("view/Inventory.fxml", "Inventory")
  }

  private def showPopUp(fxmlFile: String, title: String): Unit = {
    val resource = getClass.getResource(fxmlFile)
    val loader = new FXMLLoader(resource)
    loader.load();
    val root = loader.getRoot[jfxs.layout.AnchorPane]()
    val stage = new Stage(StageStyle.DECORATED)
    stage.setTitle(title)
    stage.setScene(new jfxs.Scene(root))
    stage.showAndWait()
  }
*/
  def getShop(): Unit = {
    MainApp.showShop()
  }
  def getInventory(): Unit = {
    MainApp.showInventory()
  }

  def updateUI(): Unit = {
    // Here you can set the text of coinsLabel and harvestedPlantsLabel
    // from a shared game state or model
    coinsLabel.setText("Coins: " + getCoins())
    harvestedPlantsLabel.setText("Total Harvested Plants: " + getTotalHarvestedPlants())
  }

  private def getCoins(): Int = {
    100
  }

  private def getTotalHarvestedPlants(): Int = {
    0
  }
}