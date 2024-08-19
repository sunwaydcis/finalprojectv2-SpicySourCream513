package ch.makery.farming.model

class PlayerStats(var coins: Int, var totalHarvestedPlants: Int) {

  // Method to deduct coins, e.g., when removing a crop
  def deductCoins(amount: Int): Boolean = {
    if (coins >= amount) {
      coins -= amount
      println(s"Deducted $amount coins. Remaining coins: $coins")
      true
    } else {
      println(s"Not enough coins to deduct $amount. Current coins: $coins")
      false
    }
  }

  // Method to add coins, e.g., when selling a crop
  def addCoins(amount: Int): Unit = {
    coins += amount
    println(s"Added $amount coins. Total coins: $coins")
  }

  // Method to increment total harvested plants
  def incrementHarvestedPlants(): Unit = {
    totalHarvestedPlants += 1
    println(s"Total harvested plants: $totalHarvestedPlants")
  }
}
