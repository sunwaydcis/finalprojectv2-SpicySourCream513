package ch.makery.farming.view
import scalafxml.core.macros.sfxml
import ch.makery.farming.MainApp


@sfxml
class WelcomeController {
  def getStart(): Unit = {
    MainApp.showGardenOverview()
  }
  def getInstruction():Unit ={
    MainApp.showInstruction()
  }
}
