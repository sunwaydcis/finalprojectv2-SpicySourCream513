package ch.makery.farming.model.crops

abstract class Crop(
                     val name: String,
                     private var _growthTimeInSeconds: Int,
                     val cost: Double,
                     val sellPrice: Double,
                     var seedsAvailable: Int,
                     var isMature: Boolean = false,
                     var harvestedCrops: Int = 0
                   ) {

  // Convert growth time from seconds to minutes and seconds
  def getGrowthTime: String = {
    val minutes = _growthTimeInSeconds / 60
    val seconds = _growthTimeInSeconds % 60
    f"$minutes%d minutes and $seconds%d seconds"
  }

  def getName: String = name

  // Get the cost of the crop
  def getCost: Double = cost

  def getSellPrice: Double = sellPrice

  def getAvailableSeeds: Int = seedsAvailable

  def getHarvestedCrops: Int = harvestedCrops


  def addSeedsAvailable(amount: Int): Unit = {
    seedsAvailable += amount
  }

  def deductSeedsAvailable(amount: Int): Boolean = {
    if (seedsAvailable >= amount) {
      seedsAvailable -= amount
      true
    } else {
      false
    }
  }

  def addHarvestedCrops(amount: Int): Unit = {
    harvestedCrops += amount
  }

  def deductHarvestedCrops(amount: Int): Boolean = {
    if (harvestedCrops >= amount) {
      harvestedCrops -= amount
      true
    } else {
      false
    }
  }

  def getDescription: String = {""}

  // Method to remove the crop with a specified cost
  def removeCrop(playerCoins: Int, removalCost: Int = 10): (Boolean, String) = {
    if (playerCoins >= removalCost) {
      (true, s"$name has been removed, costing $removalCost coins.")
    } else {
      (false, "Not enough coins to remove the crop.")
    }
  }

  // Update maturity based on growth time
  def updateGrowthTime(seconds: Int): Unit = {
    _growthTimeInSeconds -= seconds
    if (_growthTimeInSeconds <= 0) {
      _growthTimeInSeconds = 0
      isMature = true
    }
  }

  // Check if the crop is mature
  def checkMaturity: Boolean = _growthTimeInSeconds == 0 && isMature

  // Override toString method for debugging purposes
  override def toString: String = {
    s"Crop(name=$name, growthTime=${getGrowthTime}, cost=$cost, sellPrice=$sellPrice, seedsAvailable=$seedsAvailable, isMature=$isMature)"
  }
}
