package ch.makery.farming.model.crops

abstract class Crop(val name: String,
                    private var _growthTimeInSeconds: Int,
                    var cost: Double,
                    var sellPrice: Double,
                    var seedsAvailable: Int, // New attribute for seed availability
                    var isMature: Boolean = false) {

  // Convert growth time from seconds to minutes and seconds
  def convertToMinutes: String = {
    val minutes = _growthTimeInSeconds / 60
    val seconds = _growthTimeInSeconds % 60
    f"$minutes%d minutes and $seconds%d seconds"
  }

  // Show crop description
  def getDescription(): String = s"The $name costs $cost coins to plant and can be sold for $sellPrice coins when mature."

  // Show available seeds
  def getAvailableSeeds(): String = s"You have $seedsAvailable seeds available."

  // Method to remove the crop, deducting 10 coins
  def removeCrop(playerCoins: Int): (Boolean, String) = {
    if (playerCoins >= 10) {
      (true, s"$name has been removed, costing 10 coins.")
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

  def checkMaturity(): Boolean = _growthTimeInSeconds == 0 && isMature

}

