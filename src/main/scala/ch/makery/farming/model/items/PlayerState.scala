package ch.makery.farming.model

class PlayerState(var coins: Int, //Initial coins
                  var totalHarvestedPlants: Int) {



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

}
