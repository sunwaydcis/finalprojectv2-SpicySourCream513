package ch.makery.address.view
import scalafxml.core.macros.sfxml
import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.scene.control.Button

@sfxml
class WelcomeController {
  def getStart(): Unit = {
    MainApp.showGardenOverview()
  }
  def getInstruction():Unit ={
  }
}
