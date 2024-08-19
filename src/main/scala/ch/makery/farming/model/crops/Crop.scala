package ch.makery.farming.model.crops

abstract class Crop(val name: String,
                    val growthTimeInSeconds: Int,
                    var health: Int,
                    val cost: Double,
                    val sellPrice: Double,
                    var isMature: Boolean = false){

  // Convert growth time from seconds to minutes and seconds
  def growthTime: String = {
    val minutes = growthTimeInSeconds / 60
    val seconds = growthTimeInSeconds % 60
    f"$minutes%d minutes and $seconds%d seconds"
  }

  // Method to show the description of the crop
  def showDescription(): String = {
    s"The $name takes $growthTime to grow. It costs $cost coins to plant and can be sold for $sellPrice coins when mature."
  }

    // Method to simulate growing the crop over time
    def grow(): Unit = {
      if (!isMature) {
        health -= 5
        if (health <= 0) {
          health = 0
          println(s"$name has withered.")
        } else if (growthTimeInSeconds <= 0) {
          isMature = true
          println(s"$name is now mature and ready for harvest.")
        }
      }
    }

    // Method to harvest the crop if it's mature
    def harvest(): Option[Int] = {
      if (isMature) {
        println(s"$name has been harvested.")
        Some(health)  // Return the health as a measure of crop quality
      } else {
        println(s"$name is not yet mature.")
        None
      }
    }

  // Method to remove the crop, deducting 10 coins
  def removeCrop(playerCoins: Int): (Boolean, Int) = {
    if (playerCoins >= 10) {
      println(s"$name has been removed, costing 10 coins.")
      (true, playerCoins - 10)  // Return a tuple indicating successful removal and updated coins
    } else {
      println("Not enough coins to remove the crop.")
      (false, playerCoins)  // Return a tuple indicating failure to remove
    }
  }
  }

