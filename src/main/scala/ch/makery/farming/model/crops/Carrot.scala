package ch.makery.farming.model.crops

class Carrot extends Crop(name = "Carrot",
  growthTimeInSeconds = 70,  // 1 minute and 10 seconds
  health = 100,
  cost = 5,
  sellPrice = 15) {

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
