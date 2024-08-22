package ch.makery.farming.model.crops

import ch.makery.farming.model.items.PlayerState

abstract class Crop(
                     val name: String,
                     val growthTimeInSeconds: Int,
                     val cost: Double,
                     val sellPrice: Double,
                     var seedsAvailable: Int,
                     var isMature: Boolean = false,
                     var harvestedCrops: Int = 0
                   ) {

  // Convert growth time from seconds to minutes and seconds
  def getGrowthTime: String = {
    val minutes = growthTimeInSeconds / 60
    val seconds = growthTimeInSeconds % 60
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

  def deductSeedsAvailable(amount: Int): Unit = {
      seedsAvailable -= amount
  }

  def addHarvestedCrops(amount: Int): Unit = {
    harvestedCrops += amount
  }

  def deductHarvestedCrops(amount: Int): Unit = {
    harvestedCrops -= amount
  }

  def getDescription: String = {"This is Plant Description"}

  //remove crop require 10 coin
  def removeCrop(playerState: PlayerState, plotIndex: Int): Unit = {
    playerState.unoccupyPlot(plotIndex)
  }

  // Check if the crop is mature
  def checkMaturity: Boolean = growthTimeInSeconds == 0 && isMature
}
