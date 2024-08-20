package ch.makery.farming

import ch.makery.farming.view.GardenOverviewController
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.{Stage, StageStyle}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object MainApp extends JFXApp {
  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Farming Game"
    scene = new Scene {
      root = roots
    }
  }

  // actions for display person overview window
  def showGardenOverview() = {
    val resource = getClass.getResource("view/GardenOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showWelcome() = {
    val resource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showInstruction() = {
    val resource = getClass.getResource("view/Instruction.fxml")
    if (resource == null) {
      throw new RuntimeException("Instruction.fxml not found")
    }
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val root = loader.getRoot[jfxs.layout.AnchorPane]
    val stage = new Stage(StageStyle.DECORATED)
    stage.setTitle("Instruction")
    stage.setScene(new Scene(root))
    stage.showAndWait()
  }

  // Show Shop in a New Window
  def showShop() = {
    val resource = getClass.getResource("view/Shop.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val root = loader.getRoot[jfxs.layout.AnchorPane]
    val stage = new Stage(StageStyle.DECORATED)
    stage.setTitle("Shop")
    stage.setScene(new Scene(root))
    stage.showAndWait()
  }

  def showInventory() = {
    val resource = getClass.getResource("view/Inventory.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val root = loader.getRoot[jfxs.layout.AnchorPane]
    val stage = new Stage(StageStyle.DECORATED)
    stage.setTitle("Inventory")
    stage.setScene(new Scene(root))
    stage.showAndWait()
  }

  def showPlantCrop(plotIndex: Int): Unit = {
    val resource = getClass.getResource("view/PlantCrop.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver) // NoDependencyResolver is fine here
    loader.load()
    val root = loader.getRoot[jfxs.layout.AnchorPane]
    val stage = new Stage(StageStyle.DECORATED)
    stage.setTitle(s"Plant a Crop - Plot $plotIndex")
    stage.setScene(new jfxs.Scene(root))
    stage.showAndWait()
  }

  showWelcome()
}

