package ch.makery.farming.model.items

class PlayerState(var coins: Int = 100,
                  var totalHarvestedPlants: Int = 0) {

  // Array to track occupied plots
  var plotStatus: Array[Boolean] = Array.fill(9)(false)

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
  def deductCoins(amount: Int): Boolean = {
    if (coins >= amount) {
      coins -= amount
      true
    } else {
      false
    }
  }

  // Method to add coins, e.g., when selling a crop
  def addCoins(amount: Int): Unit = {
    coins += amount
  }

  // Method to increment total harvested plants
  def addHarvestedPlants(count: Int): Unit = {
    totalHarvestedPlants += count
  }

  def getCoins(): Int = {
    coins
  }

  def getTotalHarvestedPlants(): Int = {
    totalHarvestedPlants
  }



}
