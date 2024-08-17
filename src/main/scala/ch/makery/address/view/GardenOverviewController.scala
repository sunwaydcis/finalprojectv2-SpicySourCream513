package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.scene.control.Label
import scalafxml.core.macros.sfxml

@sfxml
class GardenOverviewController(private val coin: Label,
                               private val totalharvestplant: Label){
  def getHome():Unit = {
    MainApp.showWelcome()
  }
  def getShop():Unit = {
  }
  def getInventory():Unit = {
  }

}


