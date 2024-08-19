package ch.makery.address.view

import ch.makery.address.MainApp
import ch.makery.address.MainApp.getClass
import scalafxml.core.macros.sfxml
import javafx.scene.control.{Button, Label}
import javafx.fxml.FXML
import javafx.scene.layout.GridPane
import javafx.stage.{Stage, StageStyle}
import javafx.scene.Scene
import javafx.fxml.FXMLLoader
import javafx.scene.Parent


@sfxml
class GardenOverviewController(private var gardenGrid: GridPane,
                               private var coinsLabel: Label,
                               private var harvestedPlantsLabel: Label,
                               private var shopButton: Button,
                               private var inventoryButton: Button){


  def initialize(): Unit = {
    updateUI;
  }
  def returnHome(): Unit = {
    MainApp.showWelcome()
  }

    def getShop(): Unit = {
      openWindow("view/shop.fxml", "Shop")
    }

    @FXML
    def getInventory(): Unit = {
      openWindow("view/inventory.fxml", "Inventory")
    }

    private def openWindow(fxmlFile: String, title: String): Unit = {
      val loader = new FXMLLoader(getClass.getResource(fxmlFile))
      val root = loader.load[Parent]()
      val stage = new Stage(StageStyle.DECORATED)
      stage.setTitle(title)
      stage.setScene(new Scene(root))
      stage.show()
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


