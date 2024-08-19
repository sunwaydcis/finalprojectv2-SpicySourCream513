package ch.makery.farming.model.crops

class Wheat extends Crop(name = "Wheat",
  growthTimeInSeconds = 5,  // 5 seconds
  health = 100,
  cost = 12,
  sellPrice = 30) {

  override def grow(): Unit = {
    super.grow()
  }

  override def harvest(): Option[Int] = {
    super.harvest()
  }

  override def removeCrop(playerCoins: Int): (Boolean, Int) = {
    super.removeCrop(playerCoins)
  }
}
