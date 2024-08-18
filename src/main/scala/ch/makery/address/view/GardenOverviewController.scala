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
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

@sfxml
class GardenOverviewController(private var gardenGrid: GridPane = _,
                               private var coinsLabel: Label = _,
                               private var harvestedPlantsLabel: Label = _,
                               private var shopButton: Button = _,
                               private var inventoryButton: Button = _,
                              ){

  def getHome(): Unit = {
    MainApp.showWelcome()
  }

  def initializa(): Unit = {
    updateUI;
  }
  def handleReturnHome():Unit = {
    MainApp.showWelcome()


    def handleShopClick(): Unit = {
      openWindow("shop.fxml", "Shop")
    }

    @FXML
    def handleInventoryClick(): Unit = {
      openWindow("inventory.fxml", "Inventory")
    }

    @FXML
    def handleReturnHomeClick(): Unit = {
      val loader = new FXMLLoader(getClass.getResource("Welcome.fxml"))
      val root = loader.load[Parent]()
      val stage = returnHomeButton.getScene.getWindow.asInstanceOf[Stage]
      stage.setScene(new Scene(root))
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

}


