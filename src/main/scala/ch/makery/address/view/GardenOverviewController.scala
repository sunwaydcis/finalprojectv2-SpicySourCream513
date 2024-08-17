package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml

@sfxml
class GardenOverviewController{
  def getStart():Unit = {
    MainApp.showGardenOverview()
  }
  def getInstruction():Unit = {
  }
}
