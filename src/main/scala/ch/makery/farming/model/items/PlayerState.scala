package ch.makery.farming.model.items

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class PlayerState(var coins: Double = 100,
                  var totalHarvestedPlants: Int = 0) {

  // Array to track occupied plots
  private var plotStatus: Array[Boolean] = Array.fill(9)(false)

  def isOccupied(plotIndex: Int): Boolean = {
    plotStatus(plotIndex)
  }

  def occupyPlot(plotIndex: Int): Unit = {
    plotStatus(plotIndex) = true
  }

  def unoccupyPlot(plotIndex: Int): Unit = {
    plotStatus(plotIndex) = false
  }

  // Method to deduct coins, e.g., when removing a crop
  def deductCoins(amount: Double): Unit = {
      coins -= amount
    }

    // Method to add coins, e.g., when selling a crop
  def addCoins(amount: Double): Unit = {
    coins += amount
  }

  // Method to increment total harvested plants
  def addHarvestedPlants(count: Int): Unit = {
    totalHarvestedPlants += count
  }

  def getCoins(): Double = {
    coins
  }

  def getTotalHarvestedPlants(): Int = {
    totalHarvestedPlants
  }

  def showNotEnoughCoinMessage(): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = "Not Enough Coins"
      headerText = "Insufficient Funds"
      contentText = "You don't have enough coins. Please earn more coins by selling your harvested plant"
    }
    alert.showAndWait()
  }
}
